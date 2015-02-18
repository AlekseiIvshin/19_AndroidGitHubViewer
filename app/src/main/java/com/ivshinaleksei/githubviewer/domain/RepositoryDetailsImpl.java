package com.ivshinaleksei.githubviewer.domain;

import java.util.Date;
// TODO: May be not required
public class RepositoryDetailsImpl implements RepositoryDetails {

    private final String fullName;
    private final String language;
    private final int stargazersCount;
    private final Date createdDate;
    private final String description;
    private final String repositoryUrl;

    public RepositoryDetailsImpl(String fullName, String language, int stargazersCount, Date createdDate, String description, String repositoryUrl) {
        this.fullName = fullName;
        this.language = language;
        this.stargazersCount = stargazersCount;
        this.createdDate = createdDate;
        this.description = description;
        this.repositoryUrl = repositoryUrl;
    }


    @Override
    public Date getCreatedDate() {
        return createdDate;
    }

    @Override
    public String getRepositoryUrl() {
        return repositoryUrl;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getFullName() {
        return fullName;
    }

    @Override
    public String getLanguage() {
        return language;
    }

    @Override
    public int getStargazersCount() {
        return stargazersCount;
    }
}
