package com.ivshinaleksei.githubviewer;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ivshinaleksei.githubviewer.domain.RepositoryDetails;
import com.ivshinaleksei.githubviewer.stub.RepositoryOfRepository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class RepositoryDetailFragment extends Fragment {

    private String repositoryFullName;

    public static final String REPOSITORY_NAME = "githubviewer.repository.name";


    public static RepositoryDetailFragment newInstance(String aRepositoryFullName) {
        Log.v("RepositoryDetailsFragment", "Get new fragment instance");
        RepositoryDetailFragment detailFragment = new RepositoryDetailFragment();
        Bundle newFragmentArguments = new Bundle();
        newFragmentArguments.putString(REPOSITORY_NAME, aRepositoryFullName);
        detailFragment.setArguments(newFragmentArguments);
        return detailFragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(REPOSITORY_NAME, repositoryFullName);
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();
        if(args!=null){
            update(args.getString(REPOSITORY_NAME));
        } else {
            update(repositoryFullName);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_repository_detail, container, false);
    }

    public void updateView(String aRepositoryFullName) {
        if(aRepositoryFullName!=null && !aRepositoryFullName.equalsIgnoreCase(repositoryFullName)){
            update(aRepositoryFullName);
        }
    }

    private void update(String aRepositoryFullName) {
        repositoryFullName = aRepositoryFullName;
        RepositoryDetails details = getRepositoryDetails(repositoryFullName);
        if(details!=null){
            showDetails(details);
        }
    }

    public void showDetails(RepositoryDetails details){
        ImageView ownerAvatar = (ImageView) getActivity().findViewById(R.id.ownerAvatar);
        TextView ownerLogin = (TextView) getActivity().findViewById(R.id.details_ownerLogin);
        if (details.owner != null) {
            ownerLogin.setText(details.owner.login);

            // TODO: its stub for avatar. Some wrong: image not scaled to need sizes
            ownerAvatar.setImageResource(R.drawable.github_mark);
        }

        TextView repoName = (TextView) getActivity().findViewById(R.id.details_repoFullName);
        repoName.setText(details.repositoryName);

        TextView repoStars = (TextView) getActivity().findViewById(R.id.details_repoStars);
        repoStars.setText(details.stargazersCount + "");

        DateFormat dateFormat = new SimpleDateFormat(getResources().getString(R.string.dateFormat));

        TextView createdDate = (TextView) getActivity().findViewById(R.id.details_createdDate);
        createdDate.setText(dateFormat.format(details.createdDate));

        TextView repositoryLanguage = (TextView) getActivity().findViewById(R.id.details_repoLanguage);
        repositoryLanguage.setText(details.language);

        TextView repoDescription = (TextView)getActivity().findViewById(R.id.details_repoDescription);
        repoDescription.setText(details.description);
    }


    private RepositoryDetails getRepositoryDetails(String repoName) {
        return RepositoryOfRepository.getRepositoryDetail(repoName);
    }



}
