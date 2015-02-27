package com.ivshinaleksei.githubviewer.ui.comment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ivshinaleksei.githubviewer.R;
import com.ivshinaleksei.githubviewer.contracts.RepositoryContract;
import com.ivshinaleksei.githubviewer.domain.Comment;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Date;

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

        SharedPreferences preferences = getActivity().getSharedPreferences(sPreferenceFileName, 0);
        mTitle = preferences.getString(sCommentTitle, "");
        mMessage = preferences.getString(sCommentMessage, "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_comment_editor, container, false);
        mTitleView = (EditText) rootView.findViewById(R.id.comment_editor_title);
        mMessageView = (EditText) rootView.findViewById(R.id.comment_editor_message);
        Button sendButton = (Button) rootView.findViewById(R.id.comment_editor_send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Comment newComment = new Comment(
                        mTitleView.getText().toString(),
                        mMessageView.getText().toString(),
                        new Date());
                getActivity().getContentResolver()
                        .insert(RepositoryContract.Comment.CONTENT_URI, newComment.marshalling());

                clearForm();
                clearDataInPreferences();

                getFragmentManager().popBackStack();
            }
        });

        getActivity().setTitle(R.string.comment_editor_title);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!mTitle.isEmpty()) {
            mTitleView.setText(mTitle);
        }
        if (!mMessage.isEmpty()) {
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getFragmentManager().popBackStack();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Clear editor form
     */
    private void clearForm() {
        mTitleView.setText("");
        mMessageView.setText("");
    }

    /**
     * Clear delete data from shared preferences
     */
    private void clearDataInPreferences() {
        SharedPreferences.Editor editor = getActivity().getSharedPreferences(sPreferenceFileName, 0).edit();
        editor.remove(sCommentTitle);
        editor.remove(sCommentMessage);
        editor.apply();
    }

}
