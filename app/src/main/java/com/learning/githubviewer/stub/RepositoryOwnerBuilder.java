package com.learning.githubviewer.stub;

import com.learning.githubviewer.domain.RepositoryOwner;

/**
 * Created by Aleksei_Ivshin on 13/02/2015.
 */
public class RepositoryOwnerBuilder {

    private String url="";
    private String avatar="";
    private String login="";

    public static RepositoryOwnerBuilder newRepositoryOwner(){
        return new RepositoryOwnerBuilder();
    }

    private RepositoryOwnerBuilder(){};

    public RepositoryOwnerBuilder ownerUrl(String url){
        this.url = url;
        return this;
    }
    public RepositoryOwnerBuilder ownerAvatar(String avatar){
        this.avatar = avatar;
        return this;
    }
    public RepositoryOwnerBuilder ownerLogin(String login){
        this.login = login;
        return this;
    }

    public RepositoryOwner build(){
        return new RepositoryOwner(login,avatar,url);
    }

}
