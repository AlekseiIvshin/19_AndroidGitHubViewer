package com.ivshinaleksei.githubviewer.domain;


public class RepositoryView {
    public int id;
    public final String repoUrl;
    public final String repositoryName;
    public final int stargazersCount;
    public final String description;
    public final String language;

    public RepositoryView(int id,String url, String repositoryName, int stargazersCount,String description) {
        this.repoUrl = url;
        this.repositoryName = repositoryName;
        this.stargazersCount = stargazersCount;
        this.id=id;
        this.description = description;
        language = "Language";
    }

    public RepositoryView(RepositoryView value){
        this.id = value.id;
        this.repositoryName = value.repositoryName;
        this.repoUrl = value.repoUrl;
        this.stargazersCount = value.stargazersCount;
        this.description = value.description;
        language = "Language";
    }

    @Override
    public String toString() {
        return "RepositoryView{" +
                "id=" + id +
                ", repoUrl='" + repoUrl + '\'' +
                ", repositoryName='" + repositoryName + '\'' +
                ", stargazersCount=" + stargazersCount +
                ", description='" + description + '\'' +
                ", language='" + language + '\'' +
                '}';
    }
}
