package com.learning.githubviewer.stub;

import com.learning.githubviewer.domain.RepositoryView;

import java.util.Random;

/**
 * Created by Aleksei_Ivshin on 13/02/2015.
 */
public class RepositotyViewFactory {
    private static final String REPONAME="RepositoryName ";
    private static final String REPOURL="http://localhost:81/repository";

    public static RepositoryView[] get(int count){
        Random rnd = new Random();
        int min = 0;
        int max = 10;
        RepositoryView[] res = new RepositoryView[count];
        for(int i =0 ;i<count;i++){
            res[i] = RepositoryViewBuilder.newRepositoryView().id(i).repositoryName(REPONAME+i).repositoryUrl(REPOURL+i).stars(rnd.nextInt(max)+min).description("Description"+i).build();
        }
        return  res;
    }

}
