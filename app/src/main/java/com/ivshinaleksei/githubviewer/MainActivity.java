package com.ivshinaleksei.githubviewer;

import android.content.ContentValues;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ivshinaleksei.githubviewer.contracts.RepositoryContract;
import com.ivshinaleksei.githubviewer.domain.RepositoryCursorMapper;
import com.ivshinaleksei.githubviewer.domain.RepositoryFullInfo;
import com.ivshinaleksei.githubviewer.network.RepositoryList;
import com.ivshinaleksei.githubviewer.network.RepositoryListRequest;
import com.ivshinaleksei.githubviewer.network.RepositoryService;
import com.ivshinaleksei.githubviewer.ui.details.RepositoryDetailFragment;
import com.ivshinaleksei.githubviewer.ui.list.RepositoryListFragment;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;


public class MainActivity extends ActionBarActivity implements RepositoryListFragment.OnRepositorySelectedListener {


    public static final String CURRENT_POSITION = "githubviewer.list.currentposition";
    public static final String SELECTED_REPOSITORY = "githubviewer.list.repository";

    private boolean mDualPane;
    private int mCurrentPos = -1;
    private RepositoryFullInfo mCurrentInfo;

    private SpiceManager mSpiceManager = new SpiceManager(RepositoryService.class);

    private ActionBarDrawerToggle mDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (savedInstanceState != null) {
            mCurrentPos = savedInstanceState.getInt(CURRENT_POSITION);
            mCurrentInfo = savedInstanceState.getParcelable(SELECTED_REPOSITORY);
        }

        mDualPane = (findViewById(R.id.contentFrame) == null);

        initNavigationDrawer();

        if (!mDualPane) {
            RepositoryListFragment listViewFragment = new RepositoryListFragment();
            if (mCurrentPos != 0) {
                Bundle b = new Bundle();
                b.putInt(CURRENT_POSITION, mCurrentPos);
                listViewFragment.setArguments(b);
            }

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.contentFrame, listViewFragment);
            transaction.commit();
        }

        if (getSupportFragmentManager().findFragmentById(R.id.fragmentRepositoryDetails) != null && mCurrentInfo == null) {
            getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentById(R.id.fragmentRepositoryDetails)).commit();
        }
        if (mCurrentInfo != null) {
            onRepositorySelected(mCurrentPos, mCurrentInfo);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    protected void onStart() {
        mSpiceManager.start(this);
        super.onStart();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURRENT_POSITION, mCurrentPos);
        outState.putParcelable(SELECTED_REPOSITORY, mCurrentInfo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String aQuery) {
                if (aQuery.length() >= getResources().getInteger(R.integer.minQueryLength)) {
                    RepositoryListRequest repositoryListRequest = new RepositoryListRequest(aQuery);
                    mSpiceManager.execute(repositoryListRequest, "repositoriesPreviews." + aQuery, DurationInMillis.ONE_MINUTE, new RepositoryListRequestListener());
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
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        RepositoryCursorMapper.release();
        mSpiceManager.shouldStop();
        super.onStop();
    }

    @Override
    public void onRepositorySelected(int position, RepositoryFullInfo aRepository) {
        mCurrentInfo = aRepository;
        mCurrentPos = position;
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

        final CharSequence mTitle = getTitle();
        final CharSequence mDrawerTitle = getString(R.string.drawerCloseTitleText);

        ListView navigation = (ListView) findViewById(R.id.leftNavigationDrawer);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawerOpen, R.string.drawerClose) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getSupportActionBar().setTitle(mTitle);
                this.syncState();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                getSupportActionBar().setTitle(mDrawerTitle);
                this.syncState();
            }
        };

        navigation.setAdapter(new ArrayAdapter<>(this, R.layout.drawer_nav_list_item_style, getResources().getStringArray(R.array.navigation)));

        drawerLayout.setDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    public final class RepositoryListRequestListener implements RequestListener<RepositoryList> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Log.v("RepositoryPreviewRequestListener", "Failure");
            // TODO: add failure info
        }

        @Override
        public void onRequestSuccess(RepositoryList repositoryPreviews) {
            ContentValues[] data = new ContentValues[repositoryPreviews.items.size()];
            for (int i = 0; i < data.length; i++) {
                data[i] = RepositoryCursorMapper.getInstance().marshalling(repositoryPreviews.items.get(i));
            }
            MainActivity.this.getContentResolver().bulkInsert(RepositoryContract.CONTENT_URI, data);
            // TODO: add success info
        }
    }
}
