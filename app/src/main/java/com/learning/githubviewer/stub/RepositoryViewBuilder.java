package com.learning.githubviewer.stub;

import com.learning.githubviewer.domain.RepositoryView;

public class RepositoryViewBuilder {


    public static RepositoryViewBuilder newRepositoryView(){
        return new RepositoryViewBuilder();
    }

    private int id=0;
    private String repoUrl="";
    private String repoName="";
    private int stars=0;

    private RepositoryViewBuilder(){};

    public RepositoryViewBuilder repositoryUrl(String url){
        this.repoUrl = url;
        return this;
    }

    public RepositoryViewBuilder repositoryName(String name) {
        this.repoName = name;
        return this;
    }

    public RepositoryViewBuilder stars(int stars) {
        this.stars = stars;
        return this;
    }

    public RepositoryViewBuilder id(int id){
        this.id = id;
        return this;
    }

    public RepositoryView build(){
        return new RepositoryView(id,repoUrl,repoName,stars);
    }
}
