package com.ivshinaleksei.githubviewer.ui.list;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ivshinaleksei.githubviewer.R;
import com.ivshinaleksei.githubviewer.contracts.RepositoryContract;
import com.ivshinaleksei.githubviewer.domain.RepositoryInfo;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final int LOADER_ID = 0;
    private static final String[] sProjection =
            {
                    RepositoryContract.RepositoryInfo._ID,
                    RepositoryContract.RepositoryInfo.FULL_NAME,
                    RepositoryContract.RepositoryInfo.LANGUAGE,
                    RepositoryContract.RepositoryInfo.STARGAZERS_COUNT,
                    RepositoryContract.RepositoryInfo.CREATED_DATE,
                    RepositoryContract.RepositoryInfo.DESCRIPTION,
                    RepositoryContract.RepositoryOwner.OWNER_LOGIN,
                    RepositoryContract.RepositoryOwner.OWNER_AVATAR_URL
            };

    private Cursor mCursor;
    private Context mContext;
    private RepositoryListFragment.OnRepositorySelectedListener mSelectedItemListener;


    public MyRecyclerViewAdapter(Context context, RepositoryListFragment.OnRepositorySelectedListener listener) {
        this.mContext = context;
        this.mSelectedItemListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_repository, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            throw new IllegalStateException("couldn't move cursor to position " + position);
        }
        RepositoryInfo preview = RepositoryInfo.getFromCursor(mCursor);
        holder.setItem(preview);
    }

    @Override
    public int getItemCount() {
        if (mCursor != null) {
            return mCursor.getCount();
        }
        return 0;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOADER_ID:
                return new CursorLoader(
                        mContext,
                        RepositoryContract.RepositoryInfo.CONTENT_URI,
                        sProjection,
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

    public void changeCursor(Cursor newCursor) {
        Cursor old = swapCursor(newCursor);
        if (old != null) {
            old.close();
        }
        notifyDataSetChanged();
    }

    public Cursor swapCursor(Cursor newCursor) {
        if (newCursor == mCursor) {
            return null;
        }
        Cursor oldCursor = mCursor;
        mCursor = newCursor;
        return oldCursor;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView repositoryName;
        public TextView repositoryLanguage;
        public TextView starsCounts;

        public ViewHolder(View itemView) {
            super(itemView);
            repositoryName = (TextView) itemView.findViewById(R.id.list_item_repoFullName);
            repositoryLanguage = (TextView) itemView.findViewById(R.id.list_item_repoLanguage);
            starsCounts = (TextView) itemView.findViewById(R.id.list_item_repoStars);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mCursor.moveToPosition(getPosition());
            mSelectedItemListener.onRepositorySelected(RepositoryInfo.getFromCursor(mCursor));
        }

        public void setItem(RepositoryInfo item) {
            starsCounts.setText(item.stargazersCount + "");
            repositoryLanguage.setText(item.language);
            repositoryName.setText(item.fullName);
        }


    }
}
