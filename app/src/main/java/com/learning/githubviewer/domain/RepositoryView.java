package com.learning.githubviewer.domain;


public class RepositoryView {
    public int id;
    public final String repoUrl;
    public final String repositoryName;
    public final int stargazersCount;

    public RepositoryView(int id,String url, String repositoryName, int stargazersCount) {
        this.repoUrl = url;
        this.repositoryName = repositoryName;
        this.stargazersCount = stargazersCount;
        this.id=id;
    }

    public RepositoryView(RepositoryView value){
        this.id = value.id;
        this.repositoryName = value.repositoryName;
        this.repoUrl = value.repoUrl;
        this.stargazersCount = value.stargazersCount;
    }
}
