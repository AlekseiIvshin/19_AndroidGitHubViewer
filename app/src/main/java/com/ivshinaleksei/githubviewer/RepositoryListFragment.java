package com.ivshinaleksei.githubviewer;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ivshinaleksei.githubviewer.R;
import com.ivshinaleksei.githubviewer.domain.RepositoryPreview;
import com.ivshinaleksei.githubviewer.domain.impl.RepositoryFullInfoImpl;
import com.ivshinaleksei.githubviewer.ui.repository.list.MyRecyclerViewAdapter;

import java.util.ArrayList;

public class RepositoryListFragment extends Fragment {

    private OnRepositorySelectedListener mListener;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.repository_list_view);
//        mLayoutManager = new LinearLayoutManager(getActivity());
//        mRecyclerView.setLayoutManager(mLayoutManager);
//        mAdapter = new MyRecyclerViewAdapter(new ArrayList<RepositoryPreview>());
//        mRecyclerView.setAdapter(mAdapter);
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
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapter(new ArrayList<RepositoryPreview>());
        mRecyclerView.setAdapter(mAdapter);
    }

    public interface OnRepositorySelectedListener {
        public void onRepositorySelected(int position,  RepositoryFullInfoImpl data);
    }

}
