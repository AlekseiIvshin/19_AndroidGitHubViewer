package com.ivshinaleksei.githubviewer.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RepositoryPreviewImpl implements RepositoryPreview {

    private final String fullName;
    private final String language;
    private final int stargazersCount;

    @JsonCreator
    public RepositoryPreviewImpl(
            @JsonProperty("full_name") String fullName,
            @JsonProperty("language") String language,
            @JsonProperty("stargazers_count") int stargazersCount) {
        this.fullName = fullName;
        this.language = language;
        this.stargazersCount = stargazersCount;
    }


    @Override
    @JsonProperty("full_name")
    public String getFullName() {
        return fullName;
    }

    @Override
    @JsonProperty("language")
    public String getLanguage() {
        return language;
    }

    @Override
    @JsonProperty("stargazers_count")
    public int getStargazersCount() {
        return stargazersCount;
    }
}
