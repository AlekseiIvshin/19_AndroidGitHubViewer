package com.ivshinaleksei.githubviewer.ui.gallery;


import android.content.SharedPreferences;
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

    private View mListView;
    private View mEmptyHolder;

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
        mPagerAdapter = new MyPagerAdapter(getFragmentManager());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gallery, container, false);

        mViewPager = (ViewPager) rootView.findViewById(R.id.pager);
        mViewPager.setAdapter(mPagerAdapter);

        mListView = rootView.findViewById(R.id.pager);
        mEmptyHolder = rootView.findViewById(android.R.id.empty);

        showHolder(mListView, mEmptyHolder, mPagerAdapter.isEmpty());

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
            SharedPreferences.Editor editor = getActivity().getSharedPreferences(sPreferencesFilename, 0).edit();
            editor.putInt(sLastOpenedPicturePositionPreferenceName, mViewPager.getCurrentItem());
            editor.apply();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(sLastOpenedPicturePositionPreferenceName, mViewPager.getCurrentItem());
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
}
