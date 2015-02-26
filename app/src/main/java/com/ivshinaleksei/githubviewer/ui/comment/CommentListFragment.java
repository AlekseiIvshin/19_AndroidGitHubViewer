package com.ivshinaleksei.githubviewer.ui.comment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ivshinaleksei.githubviewer.R;
import com.melnykov.fab.FloatingActionButton;

public class CommentListFragment extends Fragment{

    private RecyclerView mRecyclerView;
    private CommentListAdapter mAdapter;
    FloatingActionButton mFab;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_comment, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.list_comment);
        mFab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        mFab.attachToRecyclerView(mRecyclerView);
        mFab.show();
        mFab.bringToFront();
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new CommentListAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }
}
