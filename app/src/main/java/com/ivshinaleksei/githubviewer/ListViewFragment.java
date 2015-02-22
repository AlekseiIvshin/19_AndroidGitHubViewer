package com.ivshinaleksei.githubviewer;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ivshinaleksei.githubviewer.contracts.RepositoryContract;
import com.ivshinaleksei.githubviewer.domain.impl.RepositoryCursorMapper;
import com.ivshinaleksei.githubviewer.domain.impl.RepositoryFullInfoImpl;

public class ListViewFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final int LOADER_ID = 0;
    public static final String[] mProjection =
            {
                    RepositoryContract.Columns._ID,
                    RepositoryContract.Columns.FULL_NAME,
                    RepositoryContract.Columns.LANGUAGE,
                    RepositoryContract.Columns.STARGAZERS_COUNT,
                    RepositoryContract.Columns.CREATED_DATE,
                    RepositoryContract.Columns.DESCRIPTION,
                    RepositoryContract.Columns.REPOSITORY_URL,
                    RepositoryContract.Columns.OWNER_LOGIN,
                    RepositoryContract.Columns.OWNER_AVATAR_URL,
                    RepositoryContract.Columns.OWNER_URL
            };

    private OnRepositorySelectedListener mListener;
    private SimpleCursorAdapter mCursorAdapter;

    private RepositoryCursorMapper repositoryCursorMapper = new RepositoryCursorMapper();

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
                R.layout.repository_list_row_view,
                null,
                mWordListColumns,
                mWordListItem,
                0);
        getListView().setAdapter(mCursorAdapter);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getLoaderManager().initLoader(LOADER_ID, null, this);
        return inflater.inflate(R.layout.repository_list_view, container, false);
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
        showSelectedRepository(position);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOADER_ID:
                return new CursorLoader(
                        getActivity(),
                        RepositoryContract.CONTENT_URI,
                        mProjection,
                        null, null, null);
            default:
                return null;
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

    private void showSelectedRepository(int position){
        RepositoryFullInfoImpl repositoryFullInfo = getRepositoryInfoByPosition(position);
        if(repositoryFullInfo != null){
            mListener.onRepositorySelected(position,repositoryFullInfo);
        }
    }

    public interface OnRepositorySelectedListener {
        public void onRepositorySelected(int position,  RepositoryFullInfoImpl data);
    }

    public RepositoryFullInfoImpl getRepositoryInfoByPosition(int position){
        Cursor cursor = (Cursor) getListView().getItemAtPosition(position);
        ContentValues values = new ContentValues();
        DatabaseUtils.cursorRowToContentValues(cursor,values);
        return repositoryCursorMapper.get(cursor,values);
    }

}
