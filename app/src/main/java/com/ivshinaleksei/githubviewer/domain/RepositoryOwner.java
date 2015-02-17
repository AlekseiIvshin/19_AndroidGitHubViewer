package com.ivshinaleksei.githubviewer.domain;

/**
 * Created by Aleksei_Ivshin on 13/02/2015.
 */
public class RepositoryOwner {
    public final String login;
    public final String avatarUri;
    public final String ownerUrl;

    public RepositoryOwner(String login, String avatarUri, String url) {
        this.login = login;
        this.avatarUri = avatarUri;
        this.ownerUrl = url;
    }

    @Override
    public String toString() {
        return "RepositoryOwner{" +
                "login='" + login + '\'' +
                ", avatarUri='" + avatarUri + '\'' +
                ", ownerUrl='" + ownerUrl + '\'' +
                '}';
    }
}
