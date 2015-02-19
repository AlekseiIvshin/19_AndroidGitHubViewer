package com.ivshinaleksei.githubviewer;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ivshinaleksei.githubviewer.contracts.RepositoryContract;
import com.ivshinaleksei.githubviewer.dao.RepositoryOpenHelper;
import com.ivshinaleksei.githubviewer.domain.RepositoryFullInfo;
import com.ivshinaleksei.githubviewer.network.RepositoryList;
import com.ivshinaleksei.githubviewer.network.RepositoryListRequest;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

public class ListViewFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor>{

    public static final int LOADER_ID = 0;
    private OnRepositorySelectedListener mListener;
    private SimpleCursorAdapter mCursorAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String[] mWordListColumns =
                {
                        RepositoryContract.Columns.FULL_NAME,
                        RepositoryContract.Columns.LANGUAGE,
                        RepositoryContract.Columns.STARGAZERS_COUNT
                };
        int[] mWordListItem = {R.id.list_item_repoFullName, R.id.list_item_repoLanguage, R.id.list_item_repoStars};
        mCursorAdapter = new SimpleCursorAdapter(
                getActivity(),
                R.layout.list_row,
                null,
                mWordListColumns,
                mWordListItem,
                0);
        getListView().setAdapter(mCursorAdapter);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getLoaderManager().initLoader(LOADER_ID,null,this);
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnRepositorySelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // TODO: Stub. Set repository full name from anywhere
        mListener.onRepositorySelected(position, "");
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id){
            case LOADER_ID:
                String[] mProjection =
                        {
                                RepositoryContract.Columns._ID,
                                RepositoryContract.Columns.FULL_NAME,
                                RepositoryContract.Columns.LANGUAGE,
                                RepositoryContract.Columns.STARGAZERS_COUNT
                        };
                    return new CursorLoader(
                        getActivity(),
                        RepositoryContract.CONTENT_URI,
                        mProjection,
                        null,null,null);
            default:return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.changeCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.changeCursor(null);
    }


    public interface OnRepositorySelectedListener {
        public void onRepositorySelected(int position, String repositoryFullName);
    }


}
