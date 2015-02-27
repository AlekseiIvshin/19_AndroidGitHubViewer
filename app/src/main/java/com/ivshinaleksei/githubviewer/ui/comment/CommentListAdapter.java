package com.ivshinaleksei.githubviewer.ui.comment;

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
import com.ivshinaleksei.githubviewer.domain.Comment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.ViewHolder> implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final int LOADER_ID = 2;
    private static final String[] sProjection =
            {
                    RepositoryContract.Comment._ID,
                    RepositoryContract.Comment.TITLE,
                    RepositoryContract.Comment.MESSAGE,
                    RepositoryContract.Comment.CREATED_DATE
            };

    private Context mContext;
    private Cursor mCursor;

    private DateFormat mDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    public CommentListAdapter(Context context){
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_comment, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            throw new IllegalStateException("couldn't move cursor to position " + position);
        }
        holder.setItem(Comment.getFromCursor(mCursor));
    }

    @Override
    public int getItemCount() {
        if (mCursor != null) {
            return mCursor.getCount();
        }
        return 0;
    }

    public boolean isEmpty(){
        return getItemCount() == 0;
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOADER_ID:
                return new CursorLoader(mContext, RepositoryContract.Comment.CONTENT_URI, sProjection, null, null, null);
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        changeCursor(data);
    }

    @Override
    public void onLoaderReset(Loader loader) {
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

    public final class ViewHolder extends RecyclerView.ViewHolder{

        private TextView mTitle;
        private TextView mMessage;
        private TextView mCreatedDate;

        public ViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.comment_title);
            mMessage = (TextView) itemView.findViewById(R.id.comment_message);
            mCreatedDate = (TextView) itemView.findViewById(R.id.comment_createdDate);
        }

        public void setItem(Comment comment) {
            mTitle.setText(comment.title);
            mMessage.setText(comment.message);
            mCreatedDate.setText(mDateFormat.format(comment.createdDate));
        }
    }
}
