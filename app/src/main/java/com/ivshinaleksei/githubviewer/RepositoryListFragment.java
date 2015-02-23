package com.ivshinaleksei.githubviewer;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ivshinaleksei.githubviewer.domain.RepositoryFullInfo;
import com.ivshinaleksei.githubviewer.ui.repository.list.MyRecyclerViewAdapter;

public class RepositoryListFragment extends Fragment {

    private OnRepositorySelectedListener mListener;
    private RecyclerView mRecyclerView;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.repository_list_view, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.repository_list_view);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.repository_list_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        MyRecyclerViewAdapter mAdapter = new MyRecyclerViewAdapter(getActivity(), mListener);
        mRecyclerView.setAdapter(mAdapter);
        getLoaderManager().initLoader(MyRecyclerViewAdapter.LOADER_ID, null, mAdapter);
    }

    public interface OnRepositorySelectedListener {
        public void onRepositorySelected(int position,  RepositoryFullInfo data);
    }

}
