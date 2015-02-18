package com.ivshinaleksei.githubviewer.domain;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RepositoryFullInfoImpl implements RepositoryFullInfo {
    private final String fullName;
    private final String language;
    private final int stargazersCount;
    private final Date createdDate;
    private final String description;
    private final String repositoryUrl;
    private final RepositoryOwnerImpl repositoryOwner;

    @JsonCreator
    public RepositoryFullInfoImpl(
            @JsonProperty("full_name") String fullName,
            @JsonProperty("language") String language,
            @JsonProperty("stargazers_count") int stargazersCount,
            @JsonProperty("created_at")Date createdDate,
            @JsonProperty("description") String description,
            @JsonProperty("url") String repositoryUrl,
            @JsonProperty("owner") RepositoryOwnerImpl owner) {
        this.fullName = fullName;
        this.language = language;
        this.stargazersCount = stargazersCount;
        this.createdDate = createdDate;
        this.description = description;
        this.repositoryUrl = repositoryUrl;
        repositoryOwner = owner;
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
    public RepositoryOwner getOwner() {
        return repositoryOwner;
    }
}
