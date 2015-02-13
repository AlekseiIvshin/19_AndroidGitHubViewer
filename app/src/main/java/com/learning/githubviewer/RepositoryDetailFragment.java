package com.learning.githubviewer;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.learning.githubviewer.domain.RepositoryDetails;
import com.learning.githubviewer.domain.RepositoryView;
import com.learning.githubviewer.stub.RepositoryOfRepository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class RepositoryDetailFragment extends Fragment {

    private String repositoryName;
    private int repositoryId;

    public static final String REPOSITORY_NAME = "githubviewer.repository.name";
    public static final String REPOSITORY_ID = "githubviewer.repository.id";


    public static RepositoryDetailFragment newInstance(RepositoryView view) {
        Log.v("RepositoryDetailsFragment","Get new fragment instance");
        RepositoryDetailFragment detailFragment = new RepositoryDetailFragment();
        Bundle newFragmentArguments = new Bundle();
        newFragmentArguments.putString(REPOSITORY_NAME, view.repositoryName);
        newFragmentArguments.putInt(REPOSITORY_ID, view.id);
        detailFragment.setArguments(newFragmentArguments);
        return detailFragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(REPOSITORY_NAME, repositoryName);
        outState.putInt(REPOSITORY_ID, repositoryId);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("RepositoryDetailsFragment.onCreate","Bundle = "+savedInstanceState);
        if (savedInstanceState != null) {
            updateView(savedInstanceState.getString(REPOSITORY_NAME), savedInstanceState.getInt(REPOSITORY_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_repository_detail, container, false);
    }

    public void updateView(RepositoryView repositoryView) {
        updateView(repositoryView.repositoryName, repositoryView.id);
    }

    private void updateView(String repositoryName, int id) {
        RepositoryDetails details = getRepositoryDetails(id);
        if(details==null){
            Log.v("RepositoryDetailsFragment.updateView","Details for id ="+id+" not found");
            return;
        }

        ImageView repoAvatar = (ImageView) getView().findViewById(R.id.repoAvatar);
        repoAvatar.setImageResource(R.drawable.github_mark);

        ImageView ownerAvatar = (ImageView) getView().findViewById(R.id.ownerAvatar);
        if (details.owner != null) {
            if (details.owner.avatarUri == null) {
                ownerAvatar.setImageResource(R.drawable.github_mark);
            } else {
                ownerAvatar.setImageURI(Uri.parse(details.owner.avatarUri));
            }
        }

        TextView repoName = (TextView) getView().findViewById(R.id.repoName);
        repoName.setText(details.repositoryName);

        TextView stargazersCount = (TextView) getView().findViewById(R.id.stars);
        stargazersCount.setText(details.stargazersCount + "");

        DateFormat dateFormat = new SimpleDateFormat(getResources().getString(R.string.dateFormat));

        TextView createdDate = (TextView) getView().findViewById(R.id.createdDate);
        createdDate.setText(dateFormat.format(details.createdDate));

        TextView repositoryLanguage = (TextView) getView().findViewById(R.id.repositoryLanguage);
        repositoryLanguage.setText(details.language);

        TextView ownerLogin = (TextView) getView().findViewById(R.id.ownerLogin);
        ownerLogin.setText(details.owner.login);

        TextView ownerUrl = (TextView) getView().findViewById(R.id.ownerUrl);
        if (details.owner == null || details.owner.ownerUrl == null) {
            ownerUrl.setText("-");
        } else {
            ownerUrl.setText(details.owner.ownerUrl);
        }
    }

    private RepositoryDetails getRepositoryDetails(int id) {
        return RepositoryOfRepository.getRepositoryDetail(id);
    }

}
