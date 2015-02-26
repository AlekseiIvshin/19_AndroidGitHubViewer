package com.ivshinaleksei.githubviewer.ui.comment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ivshinaleksei.githubviewer.R;
import com.ivshinaleksei.githubviewer.domain.Comment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.ViewHolder> {


    // TODO: get pattern from resources
    private DateFormat mDateFormat = new SimpleDateFormat("dd.MM.yyyy");

    // TODO: get from content provider
    private List<Comment> mDataSet;

    public CommentListAdapter(){
        mDataSet = new ArrayList<>(4);
        mDataSet.add(new Comment("Title 0", "Message 0", new Date()));
        mDataSet.add(new Comment("Title 1", "Message 1", new Date()));
        mDataSet.add(new Comment("Title 2", "Message 2", new Date()));
        mDataSet.add(new Comment("Title 3", "Message 3", new Date()));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_comment, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(position<0 || position>= mDataSet.size() ){
            throw new IllegalStateException("couldn't move cursor to position " + position);
        }
        holder.setItem(mDataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
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

        public void setItem(Comment comment){
            mTitle.setText(comment.title);
            mMessage.setText(comment.message);
            mCreatedDate.setText(mDateFormat.format(comment.createdDate));
        }
    }
}
