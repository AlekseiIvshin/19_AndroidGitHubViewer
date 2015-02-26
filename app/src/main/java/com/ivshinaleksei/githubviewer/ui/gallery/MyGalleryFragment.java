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
    private static final String sPreferencesFilename = MyGalleryFragment.class.getSimpleName() + ".prefs";
    private static final String sLastOpenedPicturePositionPreferenceName = MyGalleryFragment.class.getSimpleName() + ".lastOpened";

    private MyPagerAdapter mPagerAdapter;
    private ViewPager mViewPager;
    private int mLastOpenedPicturePosition = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null) {
            if (mLastOpenedPicturePosition < 0) {
                mLastOpenedPicturePosition = savedInstanceState.getInt(sLastOpenedPicturePositionPreferenceName,-1);
            }
        }
        if(mLastOpenedPicturePosition < 0){
            SharedPreferences pref = getActivity().getSharedPreferences(sPreferencesFilename, 0);
            mLastOpenedPicturePosition = pref.getInt(sLastOpenedPicturePositionPreferenceName, -1);
        }
        mPagerAdapter = new MyPagerAdapter(getFragmentManager());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gallery, container, false);

        if (mPagerAdapter.isEmpty()) {
            show(rootView.findViewById(android.R.id.empty));
            hide(rootView.findViewById(R.id.pager));
        } else {
            hide(rootView.findViewById(android.R.id.empty));
            show(rootView.findViewById(R.id.pager));
        }

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        mViewPager = (ViewPager) getActivity().findViewById(R.id.pager);
        mViewPager.setAdapter(mPagerAdapter);

        if(mLastOpenedPicturePosition>=0){
            mViewPager.setCurrentItem(mLastOpenedPicturePosition);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mLastOpenedPicturePosition = mViewPager.getCurrentItem();
        if (mLastOpenedPicturePosition >= 0) {
            SharedPreferences.Editor editor = getActivity().getSharedPreferences(sPreferencesFilename, 0).edit();
            editor.putInt(sLastOpenedPicturePositionPreferenceName, mLastOpenedPicturePosition);
            editor.apply();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(sLastOpenedPicturePositionPreferenceName,mViewPager.getCurrentItem());
    }



    private void hide(View v) {
        if (v != null) {
            v.setVisibility(View.GONE);
        }
    }


    private void show(View v) {
        if (v != null) {
            v.setVisibility(View.VISIBLE);
        }
    }
}
