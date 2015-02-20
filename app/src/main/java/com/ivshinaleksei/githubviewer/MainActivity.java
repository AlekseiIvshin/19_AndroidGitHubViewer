package com.ivshinaleksei.githubviewer;

import android.content.ContentValues;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.LruCache;
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

import com.ivshinaleksei.githubviewer.contracts.RepositoryContract;
import com.ivshinaleksei.githubviewer.domain.impl.RepositoryCursorMapper;
import com.ivshinaleksei.githubviewer.domain.impl.RepositoryFullInfoImpl;
import com.ivshinaleksei.githubviewer.network.RepositoryList;
import com.ivshinaleksei.githubviewer.network.RepositoryListRequest;
import com.ivshinaleksei.githubviewer.network.RepositoryService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.okhttp.internal.DiskLruCache;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements ListViewFragment.OnRepositorySelectedListener{

    private static final String CURRENT_POSITION = "githubviewer.list.currentposition";

    private boolean mDualPane;
    private int mCurrentPos = -1;
    private CharSequence mTitle;
    private CharSequence mDrawerTitle;
    private RepositoryListRequest repositoryListRequest;

    private RepositoryCursorMapper repositoryCursorMapper = new RepositoryCursorMapper();

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String aQuery) {
                if(aQuery.length()>=getResources().getInteger(R.integer.minQueryLength)) {
                    repositoryListRequest = new RepositoryListRequest(aQuery);
                    spiceManager.execute(repositoryListRequest, "repositoriesPreviews", DurationInMillis.ONE_MINUTE, new RepositoryListRequestListener());
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
    public void onRepositorySelected(int position,long id, RepositoryFullInfoImpl aRepository) {
        mCurrentPos = position;
        RepositoryDetailFragment detailFragment =
                (RepositoryDetailFragment) getSupportFragmentManager().findFragmentById(R.id.repositoryDetailsFragment);
        if (detailFragment != null && mDualPane) {
            detailFragment.updateView(aRepository);
        } else {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            RepositoryDetailFragment newDetailFragment = RepositoryDetailFragment.newInstance(aRepository);
            transaction.replace(R.id.contentFrame, newDetailFragment).addToBackStack(null).commit();

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
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



    public final class RepositoryListRequestListener implements RequestListener<RepositoryList> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Log.v("RepositoryPreviewRequestListener", "Failure");
            // TODO: add failure info
        }

        @Override
        public void onRequestSuccess(RepositoryList repositoryPreviews) {
            ContentValues[] data = new ContentValues[repositoryPreviews.items.size()];
            List<String> avatars = new ArrayList<>(repositoryPreviews.items.size());
            String avatar = null;
            for(int i = 0;i<data.length;i++){
                data[i]=repositoryCursorMapper.marshalling(repositoryPreviews.items.get(i));
                if((avatar = data[i].getAsString(RepositoryContract.Columns.OWNER_AVATAR_URL))!=null && avatar.trim().length()>0){
                    avatars.add(avatar);
                }
            }
            MainActivity.this.getContentResolver().bulkInsert(RepositoryContract.CONTENT_URI,data);
            cachingImages(avatars);
            // TODO: add success info
        }

        public void cachingImages(List<String> url){

        }
    }
}
