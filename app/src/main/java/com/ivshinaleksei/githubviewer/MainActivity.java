package com.ivshinaleksei.githubviewer;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.ivshinaleksei.githubviewer.contracts.RepositoryContract;
import com.ivshinaleksei.githubviewer.domain.RepositoryFullInfo;
import com.ivshinaleksei.githubviewer.network.RepositoryList;
import com.ivshinaleksei.githubviewer.network.RepositoryListRequest;
import com.ivshinaleksei.githubviewer.network.RepositoryService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;


public class MainActivity extends ActionBarActivity implements ListViewFragment.OnRepositorySelectedListener{

    private static final String CURRENT_POSITION = "githubviewer.list.currentposition";
    private static final String REPOSITORY_FULL_NAME = "githubviewer.list.repository.fullname";

    private boolean mDualPane;
    private int mCurrentPos = -1;
    private CharSequence mTitle;
    private CharSequence mDrawerTitle;
    private String viewedRepositoryFullName;
    private RepositoryListRequest repositoryListRequest;

    private SpiceManager spiceManager = new SpiceManager(RepositoryService.class);

    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDualPane = (Configuration.ORIENTATION_LANDSCAPE == getResources().getConfiguration().orientation);


        initNavigationDrawer();

        if (findViewById(R.id.contentFrame) != null) {

            if (savedInstanceState == null || mCurrentPos < 0) {
                ListViewFragment listViewFragment = new ListViewFragment();

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                transaction.replace(R.id.contentFrame, listViewFragment);
                transaction.commit();
            }
        }

        if(savedInstanceState!=null) {
            mCurrentPos = savedInstanceState.getInt(CURRENT_POSITION);
            viewedRepositoryFullName = savedInstanceState.getString(REPOSITORY_FULL_NAME);
            Log.v("MainActivity.OnCreate", "CurPosition=" + mCurrentPos + "; repoName=" + viewedRepositoryFullName);
            if(viewedRepositoryFullName!=null && mCurrentPos>=0){
                onRepositorySelected(mCurrentPos, viewedRepositoryFullName);
            }
        }
    }



    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    protected void onStart() {
        spiceManager.start(this);
        super.onStart();
        Log.v("MainActiviry.onStart", "Execute repositoriesPreviews");
    }

    @Override
    protected void onStop() {
        spiceManager.shouldStop();
        super.onStop();
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
        outState.putString(REPOSITORY_FULL_NAME, viewedRepositoryFullName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if(s.length()>=getResources().getInteger(R.integer.minQueryLength)) {
                    search(s);
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
    public void onRepositorySelected(int position, String aRepositoryFullName) {
        Log.v("MainActivity.onRepositorySelected", "CurPosition=" + mCurrentPos + "; repoName=" + viewedRepositoryFullName);
        mCurrentPos = position;
        viewedRepositoryFullName = aRepositoryFullName;
        RepositoryDetailFragment detailFragment =
                (RepositoryDetailFragment) getSupportFragmentManager().findFragmentById(R.id.repositoryDetailsFragment);
        if (detailFragment != null && mDualPane) {
            detailFragment.updateView(viewedRepositoryFullName);
        } else {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            RepositoryDetailFragment newDetailFragment = RepositoryDetailFragment.newInstance(viewedRepositoryFullName);
            transaction.replace(R.id.contentFrame, newDetailFragment).addToBackStack(null).commit();

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()){

        }

        return super.onOptionsItemSelected(item);
    }

    private void initNavigationDrawer() {
        mTitle = mDrawerTitle = getTitle();

        ListView navigation = (ListView) findViewById(R.id.leftDrawer);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close) {
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


    private void search(String aQuery) {
        repositoryListRequest = new RepositoryListRequest(aQuery);
        spiceManager.execute(repositoryListRequest, "repositoriesPreviews", DurationInMillis.ONE_MINUTE, new RepositoryListRequestListener());
    }


    public final class RepositoryListRequestListener implements RequestListener<RepositoryList> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Log.v("RepositoryPreviewRequestListener", "Failure");
        }

        @Override
        public void onRequestSuccess(RepositoryList repositoryPreviews) {
            ContentValues[] data = new ContentValues[repositoryPreviews.items.size()];
            for(int i = 0;i<data.length;i++){
                data[i]=map(repositoryPreviews.items.get(i));
            }

            int count = MainActivity.this.getContentResolver().bulkInsert(RepositoryContract.CONTENT_URI,data);
        }

        private ContentValues map(RepositoryFullInfo info){
            ContentValues values = new ContentValues();
            values.put(RepositoryContract.Columns.FULL_NAME, info.getFullName());
            values.put(RepositoryContract.Columns.LANGUAGE,info.getLanguage());
            values.put(RepositoryContract.Columns.STARGAZERS_COUNT,info.getStargazersCount());
            values.put(RepositoryContract.Columns.CREATED_DATE,info.getCreatedDate().getTime()/1000);
            values.put(RepositoryContract.Columns.DESCRIPTION,info.getDescription());
            values.put(RepositoryContract.Columns.REPOSITORY_URL,info.getRepositoryUrl());
            values.put(RepositoryContract.Columns.OWNER_LOGIN,info.getOwner().getOwnerLogin());
            values.put(RepositoryContract.Columns.OWNER_AVATAR_URL,info.getOwner().getOwnerAvatarUrl());
            values.put(RepositoryContract.Columns.OWNER_URL,info.getOwner().getOwnerUrl());
            return values;
        }
    }
}
