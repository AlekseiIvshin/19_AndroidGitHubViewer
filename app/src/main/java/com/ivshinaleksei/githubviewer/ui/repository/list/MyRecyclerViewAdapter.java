package com.ivshinaleksei.githubviewer.ui.repository.list;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ivshinaleksei.githubviewer.R;
import com.ivshinaleksei.githubviewer.RepositoryListFragment;
import com.ivshinaleksei.githubviewer.contracts.RepositoryContract;
import com.ivshinaleksei.githubviewer.domain.RepositoryPreview;
import com.ivshinaleksei.githubviewer.domain.impl.RepositoryCursorMapper;
import com.ivshinaleksei.githubviewer.domain.impl.RepositoryFullInfoImpl;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> implements LoaderManager.LoaderCallbacks<Cursor>  {


    public static final int LOADER_ID = 0;
    private Cursor mCursor;
    private RepositoryListFragment.OnRepositorySelectedListener selectedItemListener;

    private Context context;public static final String[] mProjection =
            {
                    RepositoryContract.Columns._ID,
                    RepositoryContract.Columns.FULL_NAME,
                    RepositoryContract.Columns.LANGUAGE,
                    RepositoryContract.Columns.STARGAZERS_COUNT,
                    RepositoryContract.Columns.CREATED_DATE,
                    RepositoryContract.Columns.DESCRIPTION,
                    RepositoryContract.Columns.REPOSITORY_URL,
                    RepositoryContract.Columns.OWNER_LOGIN,
                    RepositoryContract.Columns.OWNER_AVATAR_URL,
                    RepositoryContract.Columns.OWNER_URL
            };

    private RepositoryCursorMapper repositoryCursorMapper = new RepositoryCursorMapper();

    public MyRecyclerViewAdapter(Context context, RepositoryListFragment.OnRepositorySelectedListener listner){
        this.context = context;
        this.selectedItemListener = listner;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup,final int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.repository_list_row_view,viewGroup,false);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedItemListener.onRepositorySelected(i,getRepositoryInfoByPosition(i));
            }
        });
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        if(!mCursor.moveToPosition(i)){
            throw new IllegalStateException("couldn't move cursor to position " + i);}
        RepositoryPreview preview = getRepositoryInfoByPosition(i);
        viewHolder.starsCounts.setText(preview.getStargazersCount()+"");
        viewHolder.repositoryLanguage.setText(preview.getLanguage());
        viewHolder.repositoryName.setText(preview.getFullName());
    }

    @Override
    public int getItemCount() {
        Log.v("MyRecyclerViewAdapter", "GetCount custor: " + mCursor);
        if(mCursor != null){
            Log.v("MyRecyclerViewAdapter", "GetCount custor count: " + mCursor.getCount());
            return mCursor.getCount();
        }
        return 0;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOADER_ID:
                return new CursorLoader(
                        context,
                        RepositoryContract.CONTENT_URI,
                        mProjection,
                        null, null, null);
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
       changeCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        changeCursor(null);
    }

    public void changeCursor(Cursor newCursor){
        Cursor old = swapCursor(newCursor);
        if (old != null) {
            old.close();
        }
    }

    public Cursor swapCursor(Cursor newCursor) {
        if (newCursor == mCursor) {
            return null;
        }
        Cursor oldCursor = mCursor;
        mCursor = newCursor;
        return oldCursor;
    }

    public RepositoryFullInfoImpl getRepositoryInfoByPosition(int position){
        ContentValues values = new ContentValues();
        DatabaseUtils.cursorRowToContentValues(mCursor, values);
        return repositoryCursorMapper.get(mCursor,values);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView repositoryName;
        public TextView repositoryLanguage;
        public TextView starsCounts;

        public ViewHolder(View itemView) {
            super(itemView);
            repositoryName = (TextView) itemView.findViewById(R.id.list_item_repoFullName);
            repositoryLanguage = (TextView) itemView.findViewById(R.id.list_item_repoLanguage);
            starsCounts = (TextView) itemView.findViewById(R.id.list_item_repoStars);
        }

        @Override
        public void onClick(View v) {

        }
    }

}
