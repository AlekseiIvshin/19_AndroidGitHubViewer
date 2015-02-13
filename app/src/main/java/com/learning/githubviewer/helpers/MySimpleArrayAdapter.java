package com.learning.githubviewer.helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.learning.githubviewer.R;

/**
 * Created by Aleksei_Ivshin on 13/02/2015.
 */
public class MySimpleArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;

    public MySimpleArrayAdapter(Context context, int textViewResourceId, String[] values) {
        super(context, textViewResourceId, values);

        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_row,parent,false);
        TextView textView = (TextView) rowView.findViewById(R.id.listItemText);
        textView.setText(values[position]);
        ImageView avatar = (ImageView) rowView.findViewById(R.id.avatar);
        avatar.setImageResource(R.drawable.github_mark);
        return rowView;
    }
}
