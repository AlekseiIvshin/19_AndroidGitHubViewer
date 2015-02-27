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

import com.ivshinaleksei.githubviewer.R;
import com.melnykov.fab.FloatingActionButton;

public class CommentListFragment extends Fragment{

    private RecyclerView mRecyclerView;
    private View mEmptyHolder;
    private CommentListAdapter mAdapter;
    private OnAddCommentListener mOnAddCommentListener;

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

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new CommentListAdapter(getActivity());
        mAdapter.registerAdapterDataObserver(new CommentsObserver());
        mRecyclerView.setAdapter(mAdapter);


        mEmptyHolder = rootView.findViewById(android.R.id.empty);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        getLoaderManager().initLoader(CommentListAdapter.LOADER_ID, null, mAdapter);
    }

    public interface OnAddCommentListener {
        void addComment();
    }

    private class CommentsObserver extends RecyclerView.AdapterDataObserver{

        @Override
        public void onChanged() {
            showHolder(mRecyclerView, mEmptyHolder, mAdapter.isEmpty());
        }
    }


    /**
     * Show view.
     *
     * @param dataView       view with data
     * @param emptyView      empty view
     * @param isDataSetEmpty is data set for dataView empty
     */
    private void showHolder(View dataView, View emptyView, boolean isDataSetEmpty) {
        if (isDataSetEmpty) {
            show(emptyView);
            hide(dataView);
        } else {
            hide(emptyView);
            show(dataView);
        }
    }

    /**
     * Hide view.
     */
    private void hide(View v) {
        if (v != null) {
            v.setVisibility(View.GONE);
        }
    }

    /**
     * Show view.
     */
    private void show(View v) {
        if (v != null) {
            v.setVisibility(View.VISIBLE);
        }
    }
}
