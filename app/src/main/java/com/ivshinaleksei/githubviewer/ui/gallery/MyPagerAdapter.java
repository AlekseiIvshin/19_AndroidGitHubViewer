package com.ivshinaleksei.githubviewer.ui.gallery;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ivshinaleksei.githubviewer.domain.RepositoryOwner;


/**
 * Created by Aleksei_Ivshin on 26/02/2015.
 */
public class MyPagerAdapter extends FragmentStatePagerAdapter{

    public static final int LOADER_ID = 3;
    private Cursor mCursor;
    private final String mNoNameTitle;

    public MyPagerAdapter(FragmentManager fm, String noNameTitle) {
        super(fm);
        this.mNoNameTitle = noNameTitle;
    }

    @Override
    public Fragment getItem(int position) {
        if (!mCursor.moveToPosition(position)) {
            throw new IllegalStateException("couldn't move cursor to position " + position);
        }
        RepositoryOwner owner = RepositoryOwner.getFromCursor(mCursor);
        if (owner == null) {
            return MyGalleryObjectFragment.newInstance("");
        }
        return MyGalleryObjectFragment.newInstance(RepositoryOwner.getFromCursor(mCursor).avatarUrl);
    }

    @Override
    public int getCount() {
        if (mCursor != null) {
            return mCursor.getCount();
        }
        return 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(mCursor == null){
            return null;
        }
        if (!mCursor.moveToPosition(position)) {
            throw new IllegalStateException("couldn't move cursor to position " + position);
        }
        RepositoryOwner owner = RepositoryOwner.getFromCursor(mCursor);
        if (owner != null) {
            return owner.login;
        }
        return mNoNameTitle;
    }

    public boolean isEmpty() {
        return getCount() == 0;
    }


    public void changeCursor(Cursor newCursor) {
        Cursor old = swapCursor(newCursor);
        if (old != null) {
            old.close();
        }
        notifyDataSetChanged();
    }

    private Cursor swapCursor(Cursor newCursor) {
        if (newCursor == mCursor) {
            return null;
        }
        Cursor oldCursor = mCursor;
        mCursor = newCursor;
        return oldCursor;
    }

}
