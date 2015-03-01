package com.ivshinaleksei.githubviewer.ui.list;


import android.app.Activity;
import android.content.SharedPreferences;
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


    private static final String sPreferencesFileName = RepositoryListFragment.class.getName() + "_prefs";
    private static final String sCurrentPosition = RepositoryListFragment.class.getName() + ".current.position";

    private OnRepositorySelectedListener mListener;
    private RecyclerView mRecyclerView;
    private int mCurrentPosition;
    private MyRecyclerViewAdapter mAdapter;


    public static RepositoryListFragment newInstance() {
        return new RepositoryListFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnRepositorySelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement " + OnRepositorySelectedListener.class.getSimpleName());
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
        View rootView = inflater.inflate(R.layout.fragment_list_repository, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewRepositoryList);
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mCurrentPosition = arguments.getInt(sCurrentPosition);
        } else {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(sPreferencesFileName, 0);
            mCurrentPosition = sharedPreferences.getInt(sCurrentPosition, -1);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.recyclerViewRepositoryList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new MyRecyclerViewAdapter(getActivity(), mListener);
        mRecyclerView.setAdapter(mAdapter);
        getLoaderManager().initLoader(MyRecyclerViewAdapter.LOADER_ID, null, mAdapter);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mCurrentPosition >= 0) {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(sPreferencesFileName, 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(sCurrentPosition, mCurrentPosition);
            editor.apply();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mAdapter.changeCursor(null);
    }

    public interface OnRepositorySelectedListener {
        public void onRepositorySelected(RepositoryInfo data);
    }


}
