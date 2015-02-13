package com.learning.githubviewer;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.learning.githubviewer.domain.RepositoryView;
import com.learning.githubviewer.helpers.RepositoryListViewAdapter;
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

    private ListAdapter getRepostitoryAdapter(){
        return new RepositoryListViewAdapter(this.getView().getContext(),R.layout.list_row,getStudArray());
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            mListener  = (OnRepositorySelectedListener) activity;
        } catch (ClassCastException e){
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    private RepositoryView[] getStudArray(){
        return RepositotyViewFactory.get(10);
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
