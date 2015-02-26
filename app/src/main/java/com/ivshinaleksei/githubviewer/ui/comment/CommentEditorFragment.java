package com.ivshinaleksei.githubviewer.ui.comment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ivshinaleksei.githubviewer.R;

/**
 * Created by Aleksei_Ivshin on 26/02/2015.
 *
 * Provide main operations on comment: add, edit.
 */
public class CommentEditorFragment extends Fragment {


    public static CommentEditorFragment newInstance() {
        return new CommentEditorFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_comment_editor,container,false);
    }
}
