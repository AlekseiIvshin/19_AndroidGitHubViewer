package com.ivshinaleksei.githubviewer.ui.gallery;


import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ivshinaleksei.githubviewer.R;
import com.ivshinaleksei.githubviewer.contracts.RepositoryContract;

/**
 * Created by Aleksei_Ivshin on 26/02/2015.
 */
public class MyGalleryFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String sPreferencesFilename = MyGalleryFragment.class.getName() + ".prefs";
    private static final String sLastOpenedItemPosition = MyGalleryFragment.class.getSimpleName() + ".lastItem";


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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mLastOpenedPicturePosition = savedInstanceState.getInt(sLastOpenedItemPosition, -1);
        }
        if (mLastOpenedPicturePosition < 0) {
            SharedPreferences pref = getActivity().getSharedPreferences(sPreferencesFilename, 0);
            mLastOpenedPicturePosition = pref.getInt(sLastOpenedItemPosition, -1);
        }
        mPagerAdapter = new MyPagerAdapter(getFragmentManager(), getString(R.string.gallery_noData));

        getLoaderManager().initLoader(MyPagerAdapter.LOADER_ID, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gallery, container, false);

        mViewPager = (ViewPager) rootView.findViewById(R.id.pager);
        mViewPager.setAdapter(mPagerAdapter);

        mEmptyHolder = rootView.findViewById(android.R.id.empty);

        showHolder(mViewPager, mEmptyHolder, mPagerAdapter.isEmpty());

        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mViewPager.getCurrentItem() >= 0) {
            SharedPreferences.Editor editor = getActivity()
                    .getSharedPreferences(sPreferencesFilename, 0).edit();
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
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOADER_ID:
                return new CursorLoader(
                        getActivity(),
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
