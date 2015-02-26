package com.ivshinaleksei.githubviewer.ui.comment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.ivshinaleksei.githubviewer.R;
import com.rengwuxian.materialedittext.MaterialEditText;

/**
 * Created by Aleksei_Ivshin on 26/02/2015.
 * <p/>
 * Provide main operations on comment: add, edit.
 */
public class CommentEditorFragment extends Fragment {

    private final static String sPreferenceFileName = CommentEditorFragment.class.getName() + ".prefs";

    private final static String sCommentTitle = CommentEditorFragment.class.getName() + ".comment.title";
    private final static String sCommentMessage = CommentEditorFragment.class.getName() + ".comment.message";

    private EditText mTitleView;
    private EditText mMessageView;

    private String mTitle;
    private String mMessage;

    public static CommentEditorFragment newInstance() {
        return new CommentEditorFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = getActivity().getSharedPreferences(sPreferenceFileName,0);
        mTitle = preferences.getString(sCommentTitle,"");
        mMessage = preferences.getString(sCommentMessage,"");
    }

    @Override
    public void onStart() {
        super.onStart();
        if(!mTitle.isEmpty()){
            mTitleView.setText(mTitle);
        }
        if(!mMessage.isEmpty()){
            mMessageView.setText(mMessage);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (!mTitleView.getText().toString().isEmpty() || !mMessageView.getText().toString().isEmpty()) {
            SharedPreferences.Editor editor = getActivity().getSharedPreferences(sPreferenceFileName, 0).edit();
            editor.putString(sCommentTitle, mTitleView.getText().toString());
            editor.putString(sCommentMessage, mMessageView.getText().toString());
            editor.apply();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_comment_editor, container, false);
        mTitleView = (EditText) rootView.findViewById(R.id.comment_editor_title);
        mMessageView = (EditText) rootView.findViewById(R.id.comment_editor_message);
        return rootView;
    }

    public void createCommet() {
        // TODO: check on empty data. After successfully creating - clean pref file.
    }
}
