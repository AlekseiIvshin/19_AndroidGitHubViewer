package com.ivshinaleksei.githubviewer.domain.impl;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ivshinaleksei.githubviewer.domain.RepositoryOwner;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RepositoryOwnerImpl implements RepositoryOwner, Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.login);
        dest.writeString(this.avatarUrl);
        dest.writeString(this.url);
    }

    private RepositoryOwnerImpl(Parcel in) {
        this.login = in.readString();
        this.avatarUrl = in.readString();
        this.url = in.readString();
    }

    public static final Parcelable.Creator<RepositoryOwnerImpl> CREATOR = new Parcelable.Creator<RepositoryOwnerImpl>() {
        public RepositoryOwnerImpl createFromParcel(Parcel source) {
            return new RepositoryOwnerImpl(source);
        }

        public RepositoryOwnerImpl[] newArray(int size) {
            return new RepositoryOwnerImpl[size];
        }
    };
}
