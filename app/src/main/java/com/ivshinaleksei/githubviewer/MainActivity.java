package com.ivshinaleksei.githubviewer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivshinaleksei.githubviewer.domain.RepositoryInfo;
import com.ivshinaleksei.githubviewer.domain.RepositoryList;
import com.ivshinaleksei.githubviewer.network.BaseRepositorySearchRequest;
import com.ivshinaleksei.githubviewer.network.RepositoryService;
import com.ivshinaleksei.githubviewer.network.request.builder.SortedRepositorySearchRequestBuilder;
import com.ivshinaleksei.githubviewer.ui.details.RepositoryDetailFragment;
import com.ivshinaleksei.githubviewer.ui.gallery.MyPagerAdapter;
import com.ivshinaleksei.githubviewer.ui.list.MyRecyclerViewAdapter;
import com.ivshinaleksei.githubviewer.ui.list.RepositoryListFragment;
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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (savedInstanceState != null) {
            mCurrentInfo = savedInstanceState.getParcelable(sSelectedRepository);
        }

        mDualPane = (findViewById(R.id.contentFrame) == null);

        initNavigationDrawer();


        if (!mDualPane) {
            // TODO: check on "work?"
            if (getSupportFragmentManager().findFragmentById(R.id.contentFrame) == null) {
                RepositoryListFragment listViewFragment = RepositoryListFragment.newInstance();

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.contentFrame, listViewFragment);
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
//                    InputMethodManager inputMethodManager =
//                            (InputMethodManager) MainActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
//                    inputMethodManager.hideSoftInputFromWindow(MainActivity.this.getCurrentFocus().getWindowToken(), 0);
//
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
            transaction.replace(R.id.contentFrame, newDetailFragment).addToBackStack(null).commit();

        }
    }

    private void initNavigationDrawer() {

        mDrawerList = (ListView) findViewById(R.id.leftNavigationDrawer);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        mDrawerList.setAdapter(
                new ArrayAdapter<>(this, R.layout.list_item_drawer_navigation,
                        getResources().getStringArray(R.array.navigation)));

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerList.bringToFront();
        mDrawerLayout.requestLayout();
    }

    private void selectItem(int position) {
        // TODO: replace switch
        switch (position) {
            case 0: //TODO: Intent comments creation
                break;
            case 1:
                Intent intent = new Intent(this, PictureGalleryActivity.class);
                startActivity(intent);
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
}
