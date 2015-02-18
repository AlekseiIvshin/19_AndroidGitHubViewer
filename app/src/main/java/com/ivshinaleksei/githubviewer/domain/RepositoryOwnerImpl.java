package com.ivshinaleksei.githubviewer.domain;

// TODO: May be not required
public class RepositoryOwnerImpl implements RepositoryOwner {
    private final String login;
    private final String avatarUrl;
    private final String url;

    public RepositoryOwnerImpl(String login, String avatarUrl, String url) {
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
