package com.ivshinaleksei.githubviewer.ui.list;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ivshinaleksei.githubviewer.R;
import com.ivshinaleksei.githubviewer.contracts.RepositoryContract;
import com.ivshinaleksei.githubviewer.domain.RepositoryInfo;
import com.ivshinaleksei.githubviewer.domain.RepositoryList;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

public class RepositoryListFragment extends Fragment {


    private static final String sPreferencesFileName = RepositoryListFragment.class.getSimpleName() + "_prefs";
    private static final String sCurrentPosition = RepositoryListFragment.class.getSimpleName() + ".current.position";

    private OnRepositorySelectedListener mListener;
    private RecyclerView mRecyclerView;
    private MyRecyclerViewAdapter mAdapter;
    private int mCurrentPosition;


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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mCurrentPosition = arguments.getInt(sCurrentPosition);
        } else {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(sPreferencesFileName, 0);
            mCurrentPosition = sharedPreferences.getInt(sCurrentPosition, -1);
        }

        if (mCurrentPosition >= 0) {
            //mListener.onRepositorySelected(RepositoryInfo.getFromCursor(mAdapter.getCursor()));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.recyclerViewRepositoryList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new MyRecyclerViewAdapter(getActivity(),mListener);
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

    public interface OnRepositorySelectedListener {
        public void onRepositorySelected(RepositoryInfo data);
    }


    public final class RepositorySearchRequestListener implements RequestListener<RepositoryList> {

        private Context mContext;

        public RepositorySearchRequestListener(Context context){
            this.mContext = context;
        }

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Log.v(RepositoryListFragment.class.getSimpleName(), "Failure");

            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(
                    mContext,
                    getString(R.string.loadRepositoriesInfo_failure, spiceException.getMessage()),
                    duration);
            toast.setGravity(Gravity.TOP, 0, getResources().getInteger(R.integer.toastOffsetY));
            toast.show();
            ProgressBar mProgress = (ProgressBar) getActivity().findViewById(R.id.progressBar);
            if (mProgress != null) {
                mProgress.setVisibility(View.GONE);
            }
        }

        @Override
        public void onRequestSuccess(RepositoryList repositoryPreviews) {
            Log.v(RepositoryListFragment.class.getSimpleName(), "Success");
            Loader loader = getLoaderManager().getLoader(MyRecyclerViewAdapter.LOADER_ID);
            if(loader!=null){
                loader.reset();
            }else{
                getLoaderManager().initLoader(MyRecyclerViewAdapter.LOADER_ID,null,new MyRecyclerViewAdapter(getActivity(), mListener));
            }
            ProgressBar mProgress = (ProgressBar) getActivity().findViewById(R.id.progressBar);
            if (mProgress != null) {
                mProgress.setVisibility(View.GONE);
            }
        }

    }

}
