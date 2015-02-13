package com.learning.githubviewer;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.learning.githubviewer.domain.RepositoryView;

import java.net.URI;


public class MainActivity extends ActionBarActivity implements ListViewFragment.OnRepositorySelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("MainActivity", "Created");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRepositorySelected(int position, RepositoryView repositoryView) {
        RepositoryDetailFragment detailFragment = (RepositoryDetailFragment) getSupportFragmentManager().findFragmentById(R.id.repositoryDetailsFragment);
        if (detailFragment != null) {
            Log.v("MainActivity.OnRepositorySelected", "Details fragment found. Update fragment view.");
            detailFragment.updateView(repositoryView);
        } else {
            Log.v("MainActivity.OnRepositorySelected", "Details fragment is null. Create new fragment instance.");
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            RepositoryDetailFragment newDetailFragment = RepositoryDetailFragment.newInstance(repositoryView);
            transaction.replace(R.id.detailsFragment, newDetailFragment).addToBackStack(null).commit();

        }
    }
}
