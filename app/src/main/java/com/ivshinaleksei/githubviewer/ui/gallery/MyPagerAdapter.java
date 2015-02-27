package com.ivshinaleksei.githubviewer.ui.gallery;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.ivshinaleksei.githubviewer.contracts.RepositoryContract;
import com.ivshinaleksei.githubviewer.domain.RepositoryOwner;


/**
 * Created by Aleksei_Ivshin on 26/02/2015.
 */
public class MyPagerAdapter extends FragmentStatePagerAdapter implements LoaderManager.LoaderCallbacks<Cursor> {

    private Cursor mCursor;
    public static final int LOADER_ID = 3;
    private Context mContext;

    private static final String[] sProjection =
            {
                    RepositoryContract.RepositoryOwner.OWNER_LOGIN,
                    RepositoryContract.RepositoryOwner.OWNER_AVATAR_URL
            };

    public MyPagerAdapter(FragmentManager fm,Context context) {
        super(fm);
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (!mCursor.moveToPosition(position)) {
            throw new IllegalStateException("couldn't move cursor to position " + position);
        }
        RepositoryOwner owner = RepositoryOwner.getFromCursor(mCursor);
        if(owner == null){
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
        if (!mCursor.moveToPosition(position)) {
            throw new IllegalStateException("couldn't move cursor to position " + position);
        }
        RepositoryOwner owner = RepositoryOwner.getFromCursor(mCursor);
        if(owner != null){
            return owner.login;
        }
        // TODO: set title for no name
        return "No name";
    }

    public boolean isEmpty() {
        return getCount() == 0;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOADER_ID:
                return new CursorLoader(
                        mContext,
                        RepositoryContract.RepositoryOwner.CONTENT_URI,
                        sProjection,
                        null, null, null);
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
        changeCursor(data);
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {
        changeCursor(null);
    }

    public void changeCursor(Cursor newCursor) {
        Cursor old = swapCursor(newCursor);
        if (old != null) {
            old.close();
        }
        notifyDataSetChanged();
    }

    public Cursor swapCursor(Cursor newCursor) {
        if (newCursor == mCursor) {
            return null;
        }
        Cursor oldCursor = mCursor;
        mCursor = newCursor;
        return oldCursor;
    }

}
