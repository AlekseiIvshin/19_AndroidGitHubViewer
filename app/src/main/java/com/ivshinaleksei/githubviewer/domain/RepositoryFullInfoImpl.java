package com.ivshinaleksei.githubviewer.domain;

import java.util.Date;

public class RepositoryFullInfoImpl implements RepositoryFullInfo {
    private final String fullName;
    private final String language;
    private final int stargazersCount;
    private final Date createdDate;
    private final String description;
    private final String repositoryUrl;
    private final String ownerLogin;
    private final String ownerAvatarUrl;
    private final String ownerUrl;

    public RepositoryFullInfoImpl(String fullName, String language, int stargazersCount, Date createdDate, String description, String repositoryUrl, String ownerLogin, String ownerAvatarUrl, String ownerUrl) {
        this.fullName = fullName;
        this.language = language;
        this.stargazersCount = stargazersCount;
        this.createdDate = createdDate;
        this.description = description;
        this.repositoryUrl = repositoryUrl;
        this.ownerLogin = ownerLogin;
        this.ownerAvatarUrl = ownerAvatarUrl;
        this.ownerUrl = ownerUrl;
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

    @Override
    public String getOwnerLogin() {
        return ownerLogin;
    }

    @Override
    public String getOwnerAvatarUrl() {
        return ownerAvatarUrl;
    }

    @Override
    public String getOwnerUrl() {
        return ownerUrl;
    }
}
