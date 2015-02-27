package com.ivshinaleksei.githubviewer;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.ivshinaleksei.githubviewer.ui.comment.CommentEditorFragment;
import com.ivshinaleksei.githubviewer.ui.comment.CommentListFragment;


public class CommentManagementActivity extends ActionBarActivity implements CommentListFragment.OnAddCommentListener {

    private  final static String sCommentsListTag = "COMMENTS_LIST";
    private  final static String sCommentsEditorTag = "COMMENTS_EDITOR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_managment);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if (getSupportFragmentManager().findFragmentById(R.id.contentFrame) == null) {
            CommentListFragment commentListFragment = CommentListFragment.newInstance();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.contentFrame, commentListFragment,sCommentsListTag);
            transaction.commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_comment_managment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                CommentListFragment commentListFragment = (CommentListFragment) getSupportFragmentManager().findFragmentByTag(sCommentsListTag);
                if(commentListFragment.isVisible()){
                    NavUtils.navigateUpFromSameTask(this);
                }else {
                    getSupportFragmentManager().popBackStack();
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void addComment() {
        CommentEditorFragment commentEditorFragment = CommentEditorFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.contentFrame, commentEditorFragment,sCommentsEditorTag).addToBackStack(null);
        transaction.commit();
    }
}
