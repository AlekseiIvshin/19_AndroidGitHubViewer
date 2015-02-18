package com.ivshinaleksei.githubviewer.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RepositoryOwnerImpl implements RepositoryOwner {
    private final String login;
    private final String avatarUrl;
    private final String url;

    @JsonCreator
    public RepositoryOwnerImpl(@JsonProperty("login") String login,
                               @JsonProperty("avatar_url") String avatarUrl,
                               @JsonProperty("url") String url) {
        this.login = login;
        this.avatarUrl = avatarUrl;
        this.url = url;
    }

    @Override
    public String getOwnerLogin() {
        return login;
    }

    @Override
    public String getOwnerAvatarUrl() {
        return avatarUrl;
    }

    @Override
    public String getOwnerUrl() {
        return url;
    }
}
