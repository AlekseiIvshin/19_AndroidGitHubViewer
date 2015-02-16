package com.learning.githubviewer;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.learning.githubviewer.domain.RepositoryView;
import com.learning.githubviewer.helpers.RepositoryListViewAdapter;
import com.learning.githubviewer.stub.RepositoryOfRepository;
import com.learning.githubviewer.stub.RepositotyViewFactory;

import java.net.URI;
import java.net.URISyntaxException;

public class ListViewFragment extends ListFragment {

    private OnRepositorySelectedListener mListener;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListAdapter(getRepostitoryAdapter());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list,container,false);
    }

    private ListAdapter getRepostitoryAdapter(){
        return new RepositoryListViewAdapter(this.getView().getContext(),R.layout.list_row,getStubArray());
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_list, container, false);
//    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.v("RepositoriesList","Attached");
        try{
            mListener  = (OnRepositorySelectedListener) activity;
        } catch (ClassCastException e){
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.v("RepositoriesList","Detached");
    }

    private RepositoryView[] getStubArray(){
        return RepositoryOfRepository.repositoryViews;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.d("Clicked","position:"+position);
        RepositoryListViewAdapter adapter = (RepositoryListViewAdapter) getListAdapter();
        Log.d("Repository list adapter",adapter==null?"null":(adapter.toString()+" uri="+adapter.getItem(position).repoUrl));
        mListener.onRepositorySelected(position, adapter.getItem(position));
    }


    public interface OnRepositorySelectedListener{
        public void onRepositorySelected(int position, RepositoryView repositoryView);
    }
}
