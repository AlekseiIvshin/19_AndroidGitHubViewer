package com.ivshinaleksei.githubviewer.helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ivshinaleksei.githubviewer.R;
import com.ivshinaleksei.githubviewer.domain.RepositoryView;

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


        TextView repoFullName = (TextView) rowView.findViewById(R.id.list_item_repoFullName);
        repoFullName.setText(values[position].repositoryName);

        TextView repoStars = (TextView) rowView.findViewById(R.id.list_item_repoStars);
        repoStars.setText(values[position].stargazersCount + "");

        TextView repositoryLanguage = (TextView) rowView.findViewById(R.id.list_item_repoLanguage);
        repositoryLanguage.setText(values[position].language);

        TextView repoDescription = (TextView) rowView.findViewById(R.id.list_item_repoDescription);
        repoDescription.setText(values[position].description);

        return rowView;
    }

}
