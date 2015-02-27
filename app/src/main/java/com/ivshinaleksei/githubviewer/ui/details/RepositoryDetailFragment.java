package com.ivshinaleksei.githubviewer.ui.details;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ivshinaleksei.githubviewer.R;
import com.ivshinaleksei.githubviewer.domain.RepositoryInfo;
import com.ivshinaleksei.githubviewer.domain.RepositoryOwner;
import com.ivshinaleksei.githubviewer.utils.MyBitmapLoader;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class RepositoryDetailFragment extends Fragment {

    private static final String sRepositoryDetails = RepositoryDetailFragment.class.getName() + ".repository.details";
    private DateFormat mDateFormat;

    private static final DisplayImageOptions sOptions = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.drawable.github_mark)
            .showImageOnFail(R.drawable.github_mark)
            .cacheInMemory(true)
            .cacheOnDisc(true)
            .build();

    public static RepositoryDetailFragment newInstance(RepositoryInfo aRepository) {
        RepositoryDetailFragment detailFragment = new RepositoryDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(sRepositoryDetails, aRepository);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            RepositoryInfo info = getArguments().getParcelable(sRepositoryDetails);
            updateView(info);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mDateFormat = new SimpleDateFormat(getResources().getString(R.string.dateFormat));
    }

    public void updateView(RepositoryInfo details) {
        showOwnerCard(details.repositoryOwner);
        showRepositoryCard(details);
    }

    public void showOwnerCard(RepositoryOwner owner) {
        final ImageView ownerAvatar = (ImageView) getActivity().findViewById(R.id.details_ownerAvatar);
        TextView ownerLogin = (TextView) getActivity().findViewById(R.id.details_ownerLogin);
        ownerLogin.setText(owner.login);

        ImageLoader.getInstance().displayImage(owner.avatarUrl, ownerAvatar,sOptions);
    }

    public void showRepositoryCard(RepositoryInfo details) {
        TextView repoName = (TextView) getActivity().findViewById(R.id.details_repoFullName);
        repoName.setText(details.fullName);

        TextView repoStars = (TextView) getActivity().findViewById(R.id.details_repoStars);
        repoStars.setText(details.stargazersCount + "");

        TextView createdDate = (TextView) getActivity().findViewById(R.id.details_createdDate);
        createdDate.setText(getString(R.string.details_labels_created, mDateFormat.format(details.createdDate)));

        TextView repositoryLanguage = (TextView) getActivity().findViewById(R.id.details_repoLanguage);
        repositoryLanguage.setText(details.language);

        TextView repoDescription = (TextView) getActivity().findViewById(R.id.details_repoDescription);
        repoDescription.setText(details.description);
    }

}
