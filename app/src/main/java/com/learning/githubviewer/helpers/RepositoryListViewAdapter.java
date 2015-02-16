package com.learning.githubviewer.helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.learning.githubviewer.R;
import com.learning.githubviewer.domain.RepositoryView;

public class RepositoryListViewAdapter extends ArrayAdapter<RepositoryView> {
    private final Context context;
    private final RepositoryView[] values;

    public RepositoryListViewAdapter(Context context, int textViewResourceId, RepositoryView[] values) {
        super(context, textViewResourceId, values);

        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_row, parent, false);


        TextView repoName = (TextView) rowView.findViewById(R.id.repoName);
        repoName.setText(values[position].repositoryName);

        TextView stargazersCount = (TextView) rowView.findViewById(R.id.stars);
        stargazersCount.setText(values[position].stargazersCount + "");

        return rowView;
    }

}
