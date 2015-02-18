package com.ivshinaleksei.githubviewer;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.ivshinaleksei.githubviewer.helpers.RepositoryListViewAdapter;

public class ListViewFragment extends ListFragment {

    private OnRepositorySelectedListener mListener;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListAdapter(getRepositoryAdapter());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    private ListAdapter getRepositoryAdapter() {
        return new RepositoryListViewAdapter(this.getView().getContext(), R.layout.list_row, null/*TODO: Set arrays*/);
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
        RepositoryListViewAdapter adapter = (RepositoryListViewAdapter) getListAdapter();
        mListener.onRepositorySelected(position, adapter.getItem(position).getFullName());
    }

    public interface OnRepositorySelectedListener {
        public void onRepositorySelected(int position, String repositoryFullName);
    }
}
