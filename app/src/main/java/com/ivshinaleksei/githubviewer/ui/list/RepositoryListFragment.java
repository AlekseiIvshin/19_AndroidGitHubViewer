package com.ivshinaleksei.githubviewer.ui.list;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ivshinaleksei.githubviewer.R;
import com.ivshinaleksei.githubviewer.domain.RepositoryInfo;

public class RepositoryListFragment extends Fragment {


    private static final String sCurrentPosition = RepositoryListFragment.class.getSimpleName()+".current.position";

    private OnRepositorySelectedListener mListener;
    private RecyclerView mRecyclerView;
    private int mCurrentPosition;

    public static RepositoryListFragment newInstance(int currentPositoon){
        RepositoryListFragment repositoryListFragment = new RepositoryListFragment();
        if (currentPositoon != 0) {
            Bundle b = new Bundle();
            b.putInt(sCurrentPosition, currentPositoon);
            repositoryListFragment.setArguments(b);
        }
        return repositoryListFragment;
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_repository, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewRepositoryList);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.recyclerViewRepositoryList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(getActivity(), mListener);
        mRecyclerView.setAdapter(adapter);
        getLoaderManager().initLoader(MyRecyclerViewAdapter.LOADER_ID, null, adapter);
    }

    public interface OnRepositorySelectedListener {
        public void onRepositorySelected(int position, RepositoryInfo data);
    }

}
