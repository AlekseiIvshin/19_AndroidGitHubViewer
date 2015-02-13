package com.learning.githubviewer.stub;

import android.util.Log;

import com.learning.githubviewer.domain.RepositoryDetails;
import com.learning.githubviewer.domain.RepositoryView;

/**
 * Created by Aleksei_Ivshin on 13/02/2015.
 */
public class RepositoryOfRepository {
    private static final int count = 10;

    public final static RepositoryView[] repositoryViews = RepositotyViewFactory.get(count);
    public final static RepositoryDetails[] repositoryDetailses = RepositoryDetailsFactory.get(count);

    public static RepositoryDetails getRepositoryDetail(int id){
        Log.v("RepoOfRepo","Total: views "+repositoryViews.length+" details "+repositoryDetailses.length);
        for(int i=0;i<count;i++){
            if(repositoryDetailses[i].id==id){
               return repositoryDetailses[i];
            }
        }
        return null;
    }
}
