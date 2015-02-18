package com.ivshinaleksei.githubviewer;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ivshinaleksei.githubviewer.contracts.RepositoryContract;

public class ListViewFragment extends ListFragment {

    private Cursor mCursor;
    private OnRepositorySelectedListener mListener;
    private CursorAdapter mCursorAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mCursor = getAllRepositories();
        String[] mWordListColumns =
                {
                        RepositoryContract.Columns.FULL_NAME,
                        RepositoryContract.Columns.LANGUAGE,
                        RepositoryContract.Columns.STARGAZERS_COUNT
                };
        int[] mWordListItem = {R.id.list_item_repoFullName, R.id.list_item_repoLanguage, R.id.list_item_repoStars};
        mCursorAdapter = new SimpleCursorAdapter(
                getActivity().getApplicationContext(),
                R.layout.list_row,
                mCursor,
                mWordListColumns,
                mWordListItem,
                0);
        getListView().setAdapter(mCursorAdapter);
    }

    private Cursor getAllRepositories(){
        String[] mProjection =
                {
                        RepositoryContract.Columns._ID,
                        RepositoryContract.Columns.FULL_NAME,
                        RepositoryContract.Columns.LANGUAGE,
                        RepositoryContract.Columns.STARGAZERS_COUNT
                };
        String mSelectionClause = null;
        String[] mSelectionArgs = null;
        String mSortOrder = RepositoryContract.Columns.CREATED_DATE + " DESC";
        return getActivity().getContentResolver().query(
                RepositoryContract.CONTENT_URI,
                mProjection,
                mSelectionClause,
                mSelectionArgs,
                mSortOrder);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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

    public interface OnRepositorySelectedListener {
        public void onRepositorySelected(int position, String repositoryFullName);
    }
}
