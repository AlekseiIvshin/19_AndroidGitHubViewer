package com.ivshinaleksei.githubviewer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivshinaleksei.githubviewer.domain.RepositoryInfo;
import com.ivshinaleksei.githubviewer.domain.RepositoryList;
import com.ivshinaleksei.githubviewer.network.BaseRepositorySearchRequest;
import com.ivshinaleksei.githubviewer.network.RepositoryService;
import com.ivshinaleksei.githubviewer.network.request.builder.SortedRepositorySearchRequestBuilder;
import com.ivshinaleksei.githubviewer.ui.comment.CommentListFragment;
import com.ivshinaleksei.githubviewer.ui.details.RepositoryDetailFragment;
import com.ivshinaleksei.githubviewer.ui.list.MyRecyclerViewAdapter;
import com.ivshinaleksei.githubviewer.ui.list.RepositoryListFragment;
import com.ivshinaleksei.githubviewer.utils.UiUtils;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.io.IOException;
import java.io.StringWriter;


public class MainActivity extends ActionBarActivity implements RepositoryListFragment.OnRepositorySelectedListener {

    private static final String sSelectedRepository = MainActivity.class.getName() + ".selected.repository";
    private static final String sCurrentSavedRepository = "savedRepository";
    private static final String sRepositoryRequestCacheKey = "repositories";
    private static final String sSharedPreferencesName = "prefsFile";

    private static final String sRepositoryListFragmentTag = "repositoryList";
    private static final String sRepositoryDetailsFragmentTag = "repositoryDetails";

    private BaseRepositorySearchRequest mRepositoryListRequest;

    private boolean mDualPane;
    private RepositoryInfo mCurrentInfo;

    private SpiceManager mSpiceManager = new SpiceManager(RepositoryService.class);

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UiUtils.setupUI(this,findViewById(R.id.drawerLayout));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home_white_36dp);

        initNavigationDrawer();

        mDualPane = (findViewById(R.id.contentFrame) == null);

        if (savedInstanceState != null) {
            mCurrentInfo = savedInstanceState.getParcelable(sSelectedRepository);
        }

        if (!mDualPane) {
            if (getSupportFragmentManager().findFragmentById(R.id.contentFrame) == null) {
                RepositoryListFragment listViewFragment = RepositoryListFragment.newInstance();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.contentFrame, listViewFragment,sRepositoryListFragmentTag);
                transaction.commit();
            }
        }

        if (mCurrentInfo == null) {
            SharedPreferences sharedPreferences = getSharedPreferences(sSharedPreferencesName, 0);
            String savedValue = sharedPreferences.getString(sCurrentSavedRepository, null);
            if (savedValue != null) {
                try {
                    mCurrentInfo = new ObjectMapper().readValue(sharedPreferences.getString(sCurrentSavedRepository, ""), RepositoryInfo.class);
                } catch (IOException ignored) {
                }
            }
        }

        onRepositorySelected(mCurrentInfo);

        if (getSupportFragmentManager().findFragmentById(R.id.fragmentRepositoryDetails) != null && mCurrentInfo == null) {
            getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentById(R.id.fragmentRepositoryDetails)).commit();
        }
    }

    @Override
    protected void onStart() {
        mSpiceManager.start(this);
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mCurrentInfo != null) {
            SharedPreferences sharedPreferences = getSharedPreferences(sSharedPreferencesName, 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            StringWriter writer = new StringWriter();
            try {
                new ObjectMapper().writeValue(writer, mCurrentInfo);
            } catch (IOException ignored) {
            }
            editor.putString(sCurrentSavedRepository, writer.toString());
            editor.apply();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(sSelectedRepository, mCurrentInfo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() >= getResources().getInteger(R.integer.minQueryLength)) {
                    UiUtils.hideSoftKeyboard(MainActivity.this);

//                    ProgressBar mProgress = (ProgressBar) findViewById(R.id.progressBar);
//                    if (mProgress != null) {
//                        mProgress.setVisibility(View.VISIBLE);
//                    }
                    mRepositoryListRequest =
                            new SortedRepositorySearchRequestBuilder(query)
                                    .sortBy("stars")
                                    .order("desc")
                                    .build();
                    mSpiceManager.execute(
                            mRepositoryListRequest,
                            sRepositoryRequestCacheKey,
                            DurationInMillis.ONE_MINUTE,
                            new RepositorySearchRequestListener());
                    searchView.setQuery("",false);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return true;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                getSupportFragmentManager().popBackStack();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        mSpiceManager.shouldStop();
        super.onStop();
    }

    @Override
    public void onRepositorySelected(RepositoryInfo aRepository) {
        if (aRepository == null) {
            return;
        }

        mCurrentInfo = aRepository;
        RepositoryDetailFragment detailFragment =
                (RepositoryDetailFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentRepositoryDetails);
        if (detailFragment != null && mDualPane) {
            if (detailFragment.isHidden()) {
                getSupportFragmentManager().beginTransaction().show(detailFragment).commit();
            }
            detailFragment.updateView(aRepository);
        } else {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            RepositoryDetailFragment newDetailFragment = RepositoryDetailFragment.newInstance(aRepository);
            transaction.replace(R.id.contentFrame, newDetailFragment,sRepositoryDetailsFragmentTag).addToBackStack(null).commit();

        }
    }

    private void initNavigationDrawer() {

        mDrawerList = (ListView) findViewById(R.id.leftNavigationDrawer);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

//        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
//        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
//
//        DrawerLayout.LayoutParams params = (android.support.v4.widget.DrawerLayout.LayoutParams) mDrawerList.getLayoutParams();
//        params.width = Math.min((int)dpWidth-getSupportActionBar().getHeight(),320);
//        mDrawerList.setLayoutParams(params);

        mDrawerList.setAdapter(
                new ArrayAdapter<>(this, R.layout.list_item_drawer_navigation,
                        getResources().getStringArray(R.array.navigation)));

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });

        mDrawerList.bringToFront();
        mDrawerLayout.requestLayout();
    }

    private void selectItem(int position) {
        // TODO: replace switch
        switch (position) {
            case 0: {
                Intent intent = new Intent(this, CommentManagementActivity.class);
                startActivity(intent);
            }
            break;
            case 1: {
                Intent intent = new Intent(this, PictureGalleryActivity.class);
                startActivity(intent);
            }
            break;
        }
    }

    public final class RepositorySearchRequestListener implements RequestListener<RepositoryList> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Log.v(MainActivity.class.getSimpleName(), "Failure");

            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(
                    MainActivity.this,
                    getString(R.string.loadRepositoriesInfo_failure, spiceException.getMessage()),
                    duration);
            toast.setGravity(Gravity.TOP, 0, getResources().getInteger(R.integer.toastOffsetY));
            toast.show();
            ProgressBar mProgress = (ProgressBar) findViewById(R.id.progressBar);
            if (mProgress != null) {
                mProgress.setVisibility(View.GONE);
            }
        }

        @Override
        public void onRequestSuccess(RepositoryList repositoryPreviews) {
            Log.v(MainActivity.class.getSimpleName(), "Success");
            // TODO: add normal behaviour on success: update cursor and etc.
            Loader loader = getSupportLoaderManager().getLoader(MyRecyclerViewAdapter.LOADER_ID);
            if (loader != null) {
                loader.reset();
            } else {
                //getSupportLoaderManager().initLoader(MyRecyclerViewAdapter.LOADER_ID,null,new MyRecyclerViewAdapter(this, this));
            }
            ProgressBar mProgress = (ProgressBar) findViewById(R.id.progressBar);
            if (mProgress != null) {
                mProgress.setVisibility(View.GONE);
            }
        }


    }

//    private void hideSoftKeyboard(Activity activity){
//        InputMethodManager inputMethodManager =
//                (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
//        if(activity.getCurrentFocus()!=null) {
//            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
//        }
//    }
//
//    public void setupUI(View view) {
//
//        //Set up touch listener for non-text box views to hide keyboard.
//        if(!(view instanceof EditText)) {
//
//            view.setOnTouchListener(new View.OnTouchListener() {
//
//                public boolean onTouch(View v, MotionEvent event) {
//                    hideSoftKeyboard(MainActivity.this);
//                    return false;
//                }
//
//            });
//        }
//
//        //If a layout container, iterate over children and seed recursion.
//        if (view instanceof ViewGroup) {
//
//            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
//
//                View innerView = ((ViewGroup) view).getChildAt(i);
//
//                setupUI(innerView);
//            }
//        }
//    }
}
