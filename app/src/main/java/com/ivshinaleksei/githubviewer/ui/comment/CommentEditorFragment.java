package com.ivshinaleksei.githubviewer.ui.comment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ivshinaleksei.githubviewer.R;
import com.ivshinaleksei.githubviewer.contracts.RepositoryContract;
import com.ivshinaleksei.githubviewer.domain.Comment;
import com.ivshinaleksei.githubviewer.utils.UiUtils;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rengwuxian.materialedittext.validation.METValidator;

import java.util.Date;

/**
 * Provide main operations on comment: add new comment, edit not saved comment.
 */
public class CommentEditorFragment extends Fragment {

    private final static String sPreferenceFileName = CommentEditorFragment.class.getName() + ".prefs";

    private final static String sCommentTitle = CommentEditorFragment.class.getName() + ".comment.title";
    private final static String sCommentMessage = CommentEditorFragment.class.getName() + ".comment.message";

    private MaterialEditText mTitleView;
    private MaterialEditText mMessageView;

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
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_comment_editor, container, false);
        UiUtils.setupUI(getActivity(), rootView);
        mTitleView = (MaterialEditText) rootView.findViewById(R.id.comment_editor_title);
        mTitleView.addValidator(new LengthValidator(mTitleView.getMinCharacters(), mTitleView.getMaxCharacters()));
        mMessageView = (MaterialEditText) rootView.findViewById(R.id.comment_editor_message);
        mMessageView.addValidator(new LengthValidator(mMessageView.getMinCharacters(), mMessageView.getMaxCharacters()));
        Button sendButton = (Button) rootView.findViewById(R.id.comment_editor_send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkInputData()) {
                    return;
                }
                Comment newComment = new Comment();

                newComment.title = mTitleView.getText().toString();
                newComment.message = mMessageView.getText().toString();
                newComment.createdDate = new Date();

                getActivity().getContentResolver()
                        .insert(RepositoryContract.Comment.CONTENT_URI, newComment.marshalling());

                clearForm();
                clearDataInPreferences();

                getFragmentManager().popBackStack();
            }

            /**
             * Check data in views.
             */
            private boolean checkInputData() {
                return mTitleView.validate() && mMessageView.validate();
            }

            /**
             * Clear editor form.
             */
            private void clearForm() {
                mTitleView.setText("");
                mMessageView.setText("");
            }

            /**
             * Clear delete data from shared preferences.
             */
            private void clearDataInPreferences() {
                SharedPreferences.Editor editor = getActivity().getSharedPreferences(sPreferenceFileName, 0).edit();
                editor.remove(sCommentTitle);
                editor.remove(sCommentMessage);
                editor.apply();
            }
        });

        getActivity().setTitle(R.string.comment_editor_title);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mTitleView.getText().toString().isEmpty() && !mTitle.isEmpty()) {
            mTitleView.setText(mTitle);
        }
        if (mMessageView.getText().toString().isEmpty() && !mMessage.isEmpty()) {
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

    private final class LengthValidator extends METValidator {

        private int mMin;
        private int mMax;

        public LengthValidator(int minCount, int maxCount) {
            super(getString(R.string.comment_editor_error_length));
            this.mMin = minCount;
            this.mMax = maxCount;
        }

        @Override
        public boolean isValid(@NonNull CharSequence text, boolean isEmpty) {
            return (text.length() >= mMin && text.length() <= mMax);
        }
    }
}
