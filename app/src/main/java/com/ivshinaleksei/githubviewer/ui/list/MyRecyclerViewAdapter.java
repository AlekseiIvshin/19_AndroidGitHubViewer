package com.ivshinaleksei.githubviewer.ui.list;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ivshinaleksei.githubviewer.R;
import com.ivshinaleksei.githubviewer.domain.RepositoryInfo;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>{

    private Cursor mCursor;
    private OnRepositorySelectedListener mSelectedItemListener;


    public void setOnClickListener(OnRepositorySelectedListener listener){
        this.mSelectedItemListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_repository, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mCursor == null) {
            return;
        }
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

    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        holder.listener = mSelectedItemListener;
    }

    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.listener=null;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView repositoryName;
        public TextView repositoryLanguage;
        public TextView starsCounts;
        public OnRepositorySelectedListener listener;

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
            listener.onRepositorySelected(RepositoryInfo.getFromCursor(mCursor));
        }

        public void setItem(RepositoryInfo item) {
            starsCounts.setText(item.stargazersCount + "");
            repositoryLanguage.setText(item.language);
            repositoryName.setText(item.fullName);
        }

    }


    public interface OnRepositorySelectedListener {
        public void onRepositorySelected(RepositoryInfo data);
    }
}
