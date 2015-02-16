package com.learning.githubviewer;

import android.app.Activity;
import android.content.Context;
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

    private Context context;
    private String repositoryName;
    private int repositoryId;

    public static final String REPOSITORY_NAME = "githubviewer.repository.name";
    public static final String REPOSITORY_ID = "githubviewer.repository.id";


    public static RepositoryDetailFragment newInstance(RepositoryView view) {
        Log.v("RepositoryDetailsFragment", "Get new fragment instance");
        RepositoryDetailFragment detailFragment = new RepositoryDetailFragment();
        Bundle newFragmentArguments = new Bundle();
        newFragmentArguments.putString(REPOSITORY_NAME, view.repositoryName);
        newFragmentArguments.putInt(REPOSITORY_ID, view.id);
        detailFragment.setArguments(newFragmentArguments);
        return detailFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        Log.v("RepositoryDetailsFragment","Attached");
        super.onAttach(activity);
        context = activity;
    }

    @Override
    public void onDetach() {
        Log.v("RepositoryDetailsFragment","Detached");
        super.onDetach();
        context = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(REPOSITORY_NAME, repositoryName);
        outState.putInt(REPOSITORY_ID, repositoryId);
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();
        if(args!=null){
            updateView(args.getString(REPOSITORY_NAME), args.getInt(REPOSITORY_ID));
        } else {
            updateView(repositoryName,repositoryId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v("RepositoryDetailsFragment.onCreateView","Create view");
        if(savedInstanceState!=null){
            repositoryName = savedInstanceState.getString(REPOSITORY_NAME);
            repositoryId = savedInstanceState.getInt(REPOSITORY_ID);
        }
        return inflater.inflate(R.layout.fragment_repository_detail, container, false);
    }

    public void updateView(RepositoryView repositoryView) {
        updateView(repositoryView.repositoryName, repositoryView.id);
    }

    private void updateView(String repositoryName, int id) {
        this.repositoryName = repositoryName;
        this.repositoryId = id;
        RepositoryDetails details = getRepositoryDetails(id);
        if(details==null){
            Log.v("RepositoryDetailsFragment.updateView","Details for id ="+id+" not found");
            return;
        }
        MainActivity activity = (MainActivity) context;

//        ImageView ownerAvatar = (ImageView) activity.findViewById(R.id.ownerAvatar);
//        if (details.owner != null) {
//            if (details.owner.avatarUri == null) {
//                ownerAvatar.setImageResource(R.drawable.github_mark);
//            } else {
//                ownerAvatar.setImageURI(Uri.parse(details.owner.avatarUri));
//            }
//        }

        TextView repoName = (TextView) activity.findViewById(R.id.repoFullName);
        repoName.setText(details.repositoryName);

        TextView stargazersCount = (TextView) activity.findViewById(R.id.repoStars);
        stargazersCount.setText(details.stargazersCount + "");

        DateFormat dateFormat = new SimpleDateFormat(getResources().getString(R.string.dateFormat));

        TextView createdDate = (TextView) activity.findViewById(R.id.createdDate);
        createdDate.setText(dateFormat.format(details.createdDate));

        TextView repositoryLanguage = (TextView) activity.findViewById(R.id.repoLanguage);
        repositoryLanguage.setText(details.language);

        TextView ownerLogin = (TextView) activity.findViewById(R.id.ownerLogin);
        ownerLogin.setText(details.owner.login);

        TextView ownerUrl = (TextView) activity.findViewById(R.id.ownerUrl);
        if (details.owner.ownerUrl == null) {
            ownerUrl.setText("-");
        } else {
            ownerUrl.setText(details.owner.ownerUrl);
        }
    }

    private RepositoryDetails getRepositoryDetails(int id) {
        return RepositoryOfRepository.getRepositoryDetail(id);
    }



}
