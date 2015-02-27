package com.ivshinaleksei.githubviewer.ui.comment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.ivshinaleksei.githubviewer.R;
import com.melnykov.fab.FloatingActionButton;

public class CommentListFragment extends Fragment{

    private RecyclerView mRecyclerView;
    private View mEmptyHolder;
    private CommentListAdapter mAdapter;
    private OnAddCommentListener mOnAddCommentListener;
    private ProgressBar mProgressBar;
    private CommentsObserver mCommentsObserver;

    public static CommentListFragment newInstance() {
        return new CommentListFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mOnAddCommentListener = (OnAddCommentListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement " + OnAddCommentListener.class.getSimpleName());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnAddCommentListener = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new CommentListAdapter(getActivity());
        mAdapter.registerAdapterDataObserver(new CommentsObserver());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_comment, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.list_comment);
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.attachToRecyclerView(mRecyclerView);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnAddCommentListener.addComment();
            }
        });
        getActivity().setTitle(R.string.title_activity_comment_management);

        mProgressBar = (ProgressBar) rootView.findViewById(android.R.id.progress);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        //mAdapter = new CommentListAdapter(getActivity());
        mCommentsObserver = new CommentsObserver();
        mAdapter.registerAdapterDataObserver(mCommentsObserver);
        mRecyclerView.setAdapter(mAdapter);


        mEmptyHolder = rootView.findViewById(android.R.id.empty);

        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        mAdapter.unregisterAdapterDataObserver(mCommentsObserver);
        mCommentsObserver = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        viewBeforeUpdate(mRecyclerView,mEmptyHolder,mProgressBar);
        getLoaderManager().initLoader(CommentListAdapter.LOADER_ID, null, mAdapter);
    }

    private void viewBeforeUpdate(View dataContainer, View emptyView, ProgressBar progressBar){
        dataContainer.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    public interface OnAddCommentListener {
        void addComment();
    }

    private class CommentsObserver extends RecyclerView.AdapterDataObserver{

        @Override
        public void onChanged() {
            viewAfterUpdate(mRecyclerView, mEmptyHolder, mProgressBar, mAdapter.isEmpty());
        }

        private void viewAfterUpdate(View dataContainer, View emptyView, ProgressBar progressBar, boolean isDataEmpty){
            dataContainer.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            if (isDataEmpty) {
                dataContainer.setVisibility(View.GONE);
                emptyView.setVisibility(View.VISIBLE);
            } else {
                dataContainer.setVisibility(View.VISIBLE);
                emptyView.setVisibility(View.GONE);
            }
        }
    }

}
