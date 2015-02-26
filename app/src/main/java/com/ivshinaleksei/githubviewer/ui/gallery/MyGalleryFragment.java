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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gallery, container, false);

        if (mPagerAdapter.getCount() == 0) {
            show(rootView.findViewById(android.R.id.empty));
            hide(rootView.findViewById(R.id.pager));
        } else {
            hide(rootView.findViewById(android.R.id.empty));
            show(rootView.findViewById(R.id.pager));
        }

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPagerAdapter = new MyPagerAdapter(getFragmentManager());

        SharedPreferences pref = getActivity().getSharedPreferences(sPreferencesFilename, 0);
        mLastOpenedPicturePosition = pref.getInt(sLastOpenedPicturePositionPreferenceName, -1);
    }

    public void hide(View v) {
        if (v != null) {
            v.setVisibility(View.GONE);
        }
    }


    public void show(View v) {
        if (v != null) {
            v.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        mViewPager = (ViewPager) getActivity().findViewById(R.id.pager);
        mViewPager.setAdapter(mPagerAdapter);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mLastOpenedPicturePosition >= 0) {
            SharedPreferences.Editor editor = getActivity().getSharedPreferences(sPreferencesFilename, 0).edit();
            editor.putInt(sLastOpenedPicturePositionPreferenceName, mLastOpenedPicturePosition);
        }
    }
}
