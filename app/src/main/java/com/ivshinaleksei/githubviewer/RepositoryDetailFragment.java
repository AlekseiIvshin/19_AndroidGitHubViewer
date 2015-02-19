package com.ivshinaleksei.githubviewer;

import android.os.Bundle;
import android.os.Parcel;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ivshinaleksei.githubviewer.domain.RepositoryDetails;
import com.ivshinaleksei.githubviewer.domain.RepositoryFullInfo;
import com.ivshinaleksei.githubviewer.domain.impl.RepositoryFullInfoImpl;
import com.ivshinaleksei.githubviewer.domain.RepositoryOwner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class RepositoryDetailFragment extends Fragment {

    public static RepositoryDetailFragment newInstance(Parcel aRepository) {
        RepositoryDetailFragment detailFragment = new RepositoryDetailFragment();
        detailFragment.updateView(aRepository);
        return detailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_repository_detail, container, false);
    }

    public void updateView(Parcel aRepository) {
        if(aRepository!=null){
            RepositoryFullInfo transported = RepositoryFullInfoImpl.CREATOR.createFromParcel(aRepository);
            showDetails(transported);
        }
    }

    public void showDetails(RepositoryFullInfo details){
        showOwnerCard(details.getOwner());
        showRepositoryCard(details);
    }

    public void showOwnerCard(RepositoryOwner owner){
        ImageView ownerAvatar = (ImageView) getActivity().findViewById(R.id.ownerAvatar);
        TextView ownerLogin = (TextView) getActivity().findViewById(R.id.details_ownerLogin);
        ownerLogin.setText(owner.getOwnerLogin());

        // TODO: its stub for avatar. Some wrong: image not scaled to need sizes
        ownerAvatar.setImageResource(R.drawable.github_mark);
    }

    public void showRepositoryCard(RepositoryDetails details){
        TextView repoName = (TextView) getActivity().findViewById(R.id.details_repoFullName);
        repoName.setText(details.getFullName());

        TextView repoStars = (TextView) getActivity().findViewById(R.id.details_repoStars);
        repoStars.setText(details.getStargazersCount() + "");

        DateFormat dateFormat = new SimpleDateFormat(getResources().getString(R.string.dateFormat));

        TextView createdDate = (TextView) getActivity().findViewById(R.id.details_createdDate);
        createdDate.setText(dateFormat.format(details.getCreatedDate()));

        TextView repositoryLanguage = (TextView) getActivity().findViewById(R.id.details_repoLanguage);
        repositoryLanguage.setText(details.getLanguage());

        TextView repoDescription = (TextView)getActivity().findViewById(R.id.details_repoDescription);
        repoDescription.setText(details.getDescription());
    }


}
