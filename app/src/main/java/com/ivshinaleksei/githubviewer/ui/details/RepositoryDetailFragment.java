package com.ivshinaleksei.githubviewer.ui.details;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ivshinaleksei.githubviewer.R;
import com.ivshinaleksei.githubviewer.domain.RepositoryFullInfo;
import com.ivshinaleksei.githubviewer.domain.RepositoryOwner;
import com.ivshinaleksei.githubviewer.utils.MyAbsBitmapLoader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class RepositoryDetailFragment extends Fragment {

    private static final String REPOSITORY_DETAILS = "com.ivshinaleksei.githubviewer.repository.details";

    public static RepositoryDetailFragment newInstance(RepositoryFullInfo aRepository) {
        RepositoryDetailFragment detailFragment = new RepositoryDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(REPOSITORY_DETAILS, aRepository);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_repository_detail, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getArguments() != null) {
            RepositoryFullInfo info = getArguments().getParcelable(REPOSITORY_DETAILS);
            updateView(info);
        }
    }

    public void updateView(RepositoryFullInfo details) {
        showOwnerCard(details.repositoryOwner);
        showRepositoryCard(details);
    }

    public void showOwnerCard(RepositoryOwner owner) {
        final ImageView ownerAvatar = (ImageView) getActivity().findViewById(R.id.details_ownerAvatar);
        TextView ownerLogin = (TextView) getActivity().findViewById(R.id.details_ownerLogin);
        ownerLogin.setText(owner.login);


        ownerAvatar.setImageResource(R.drawable.github_mark);

        if (owner.avatarUrl != null && owner.avatarUrl.trim().length() > 0) {
            // Get image from cache or download from internet
            new MyAbsBitmapLoader() {

                @Override
                protected void onPostExecute(Bitmap bitmap) {
                    if (bitmap != null) {
                        ownerAvatar.setImageBitmap(bitmap);
                    }
                }
            }.execute(owner.avatarUrl);
        }
    }

    public void showRepositoryCard(RepositoryFullInfo details) {
        TextView repoName = (TextView) getActivity().findViewById(R.id.details_repoFullName);
        repoName.setText(details.fullName);

        TextView repoStars = (TextView) getActivity().findViewById(R.id.details_repoStars);
        repoStars.setText(details.stargazersCount + "");

        DateFormat dateFormat = new SimpleDateFormat(getResources().getString(R.string.dateFormat));

        TextView createdDate = (TextView) getActivity().findViewById(R.id.details_createdDate);
        createdDate.setText(dateFormat.format(details.createdDate));

        TextView repositoryLanguage = (TextView) getActivity().findViewById(R.id.details_repoLanguage);
        repositoryLanguage.setText(details.language);

        TextView repoDescription = (TextView) getActivity().findViewById(R.id.details_repoDescription);
        repoDescription.setText(details.description);
    }

}
