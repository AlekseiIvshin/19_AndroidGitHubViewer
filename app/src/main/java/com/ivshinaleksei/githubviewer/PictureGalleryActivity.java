package com.ivshinaleksei.githubviewer;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ivshinaleksei.githubviewer.contracts.RepositoryContract;
import com.ivshinaleksei.githubviewer.ui.gallery.MyPagerAdapter;


public class PictureGalleryActivity extends ActionBarActivity  implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String sPreferencesFilename = PictureGalleryActivity.class.getName() + ".prefs";
    private static final String sLastOpenedItemPosition = PictureGalleryActivity.class.getSimpleName() + ".lastItem";


    public static final int LOADER_ID = 3;
    private static final String[] sProjection =
            {
                    RepositoryContract.RepositoryOwner.OWNER_LOGIN,
                    RepositoryContract.RepositoryOwner.OWNER_AVATAR_URL
            };

    private MyPagerAdapter mPagerAdapter;
    private ViewPager mViewPager;
    private int mLastOpenedPicturePosition = -1;
    private View mEmptyHolder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_gallery);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState != null) {
            mLastOpenedPicturePosition = savedInstanceState.getInt(sLastOpenedItemPosition, -1);
        }
        if (mLastOpenedPicturePosition < 0) {
            SharedPreferences pref = getSharedPreferences(sPreferencesFilename, 0);
            mLastOpenedPicturePosition = pref.getInt(sLastOpenedItemPosition, -1);
        }
        mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), getString(R.string.gallery_noData));

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mPagerAdapter);

        mEmptyHolder = findViewById(android.R.id.empty);

        showHolder(mViewPager, mEmptyHolder, mPagerAdapter.isEmpty());

        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mViewPager.getCurrentItem() >= 0) {
            SharedPreferences.Editor editor = getSharedPreferences(sPreferencesFilename, 0).edit();
            editor.putInt(sLastOpenedItemPosition, mViewPager.getCurrentItem());
            editor.apply();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(sLastOpenedItemPosition, mViewPager.getCurrentItem());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_picture_gallery, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOADER_ID:
                return new CursorLoader(
                        this,
                        RepositoryContract.RepositoryOwner.CONTENT_URI,
                        sProjection,
                        null, null, null);
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mPagerAdapter.changeCursor(data);
        mViewPager.setCurrentItem(mLastOpenedPicturePosition);
        showHolder(mViewPager, mEmptyHolder, mPagerAdapter.isEmpty());
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mPagerAdapter.changeCursor(null);
    }


    /**
     * Show view.
     *
     * @param dataView       view with data
     * @param emptyView      empty view
     * @param isDataSetEmpty is data set for dataView empty
     */
    private void showHolder(View dataView, View emptyView, boolean isDataSetEmpty) {
        if (isDataSetEmpty) {
            show(emptyView);
            hide(dataView);
        } else {
            hide(emptyView);
            show(dataView);
        }
    }

    /**
     * Hide view.
     */
    private void hide(View v) {
        if (v != null) {
            v.setVisibility(View.GONE);
        }
    }

    /**
     * Show view.
     */
    private void show(View v) {
        if (v != null) {
            v.setVisibility(View.VISIBLE);
        }
    }
}
