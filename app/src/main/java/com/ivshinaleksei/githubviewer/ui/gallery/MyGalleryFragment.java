package com.ivshinaleksei.githubviewer.ui.gallery;


import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ivshinaleksei.githubviewer.R;

/**
 * Created by Aleksei_Ivshin on 26/02/2015.
 */
public class MyGalleryFragment extends Fragment {
    private static final String sPreferencesFilename = MyGalleryFragment.class.getName() + ".prefs";
    private static final String sLastOpenedPicturePositionPreferenceName = MyGalleryFragment.class.getName() + ".lastOpened";

    private MyPagerAdapter mPagerAdapter;
    private ViewPager mViewPager;
    private int mLastOpenedPicturePosition = -1;
    private View mEmptyHolder;
    private ContentObserver mContentObserver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            if (mLastOpenedPicturePosition < 0) {
                mLastOpenedPicturePosition = savedInstanceState.getInt(sLastOpenedPicturePositionPreferenceName, -1);
            }
        }
        if (mLastOpenedPicturePosition < 0) {
            SharedPreferences pref = getActivity().getSharedPreferences(sPreferencesFilename, 0);
            mLastOpenedPicturePosition = pref.getInt(sLastOpenedPicturePositionPreferenceName, -1);
        }
        mContentObserver = new ContentObserver();
        mPagerAdapter = new MyPagerAdapter(getFragmentManager(),getActivity());
        mPagerAdapter.registerDataSetObserver(mContentObserver);
        getLoaderManager().initLoader(MyPagerAdapter.LOADER_ID,null,mPagerAdapter);
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
    public void onStart() {
        super.onStart();
        if (mLastOpenedPicturePosition >= 0) {
            mViewPager.setCurrentItem(mLastOpenedPicturePosition);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mViewPager.getCurrentItem() >= 0) {
            SharedPreferences.Editor editor = getActivity()
                    .getSharedPreferences(sPreferencesFilename, 0).edit();
            editor.putInt(sLastOpenedPicturePositionPreferenceName, mViewPager.getCurrentItem());
            editor.apply();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        mLastOpenedPicturePosition = mViewPager.getCurrentItem();
    }

    // TODO: stub
    private class ContentObserver extends DataSetObserver {
        @Override
        public void onChanged() {
            showHolder(mViewPager, mEmptyHolder, mPagerAdapter.isEmpty());
        }
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
