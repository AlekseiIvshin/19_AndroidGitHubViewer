package com.learning.githubviewer.stub;

import com.learning.githubviewer.domain.RepositoryDetails;

import java.util.Date;
import java.util.Random;

/**
 * Created by Aleksei_Ivshin on 13/02/2015.
 */
public class RepositoryDetailsFactory {
    private static final String REPONAME="OwnerLoginName/RepositoryName";
    private static final String FULL_REPONAME="Full RepositoryName ";
    private static final String REPOURL="http://localhost:81/repository";
    private static final String AVATAR_URL="http://localhost:81/users/avatar";
    private static final String LANGUAGE="language ";
    private static final String LOGIN="login ";

    public static RepositoryDetails[] get(int count){
        Random rnd = new Random();
        int min = 0;
        int max = 10;
        RepositoryDetails[] res = new RepositoryDetails[count];
        for(int i =0 ;i<count;i++){
            res[i] = RepositoryDetailsBuilder.newRepositoryDetails().id(i).repositoryName(REPONAME+i).repositoryUrl(REPOURL+i).stars(rnd.nextInt(max)+min)
                    .createdDate(new Date())
                    .fullName(FULL_REPONAME+i)
                    .language(LANGUAGE)
                    .owner(RepositoryOwnerBuilder.newRepositoryOwner().ownerAvatar(AVATAR_URL+i).ownerLogin(LOGIN+i).build())
                    .build();
        }
        return  res;
    }
}
