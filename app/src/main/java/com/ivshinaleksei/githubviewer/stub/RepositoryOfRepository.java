package com.ivshinaleksei.githubviewer.stub;

import android.util.Log;

import com.ivshinaleksei.githubviewer.domain.RepositoryDetails;
import com.ivshinaleksei.githubviewer.domain.RepositoryView;

/**
 * Created by Aleksei_Ivshin on 13/02/2015.
 */
public class RepositoryOfRepository {
    private static final int count = 10;

    public final static RepositoryView[] repositoryViews = RepositotyViewFactory.get(count);
    public final static RepositoryDetails[] repositoryDetailses = RepositoryDetailsFactory.get(count);

    public static RepositoryDetails getRepositoryDetail(String name){
        Log.v("RepoOfRepo","Total: views "+repositoryViews.length+" details "+repositoryDetailses.length);
        for(int i=0;i<count;i++){
            if(repositoryDetailses[i].repositoryName.equalsIgnoreCase(name)){
               return repositoryDetailses[i];
            }
        }
        return null;
    }
}
