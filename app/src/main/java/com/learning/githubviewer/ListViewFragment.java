package com.learning.githubviewer;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.learning.githubviewer.helpers.MySimpleArrayAdapter;

public class ListViewFragment extends ListFragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setListAdapter(getAdapter());

    }

    private ListAdapter getAdapter(){
        return new MySimpleArrayAdapter(this.getView().getContext(),R.layout.list_row,getResources().getStringArray(R.array.stubArray));
    }


}
