package com.ivshinaleksei.githubviewer.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RepositoryOwner implements Parcelable {
    public static final Parcelable.Creator<RepositoryOwner> CREATOR = new Parcelable.Creator<RepositoryOwner>() {
        public RepositoryOwner createFromParcel(Parcel source) {
            return new RepositoryOwner(source);
        }

        public RepositoryOwner[] newArray(int size) {
            return new RepositoryOwner[size];
        }
    };
    @JsonProperty("login")
    public String login;
    @JsonProperty("avatar_url")
    public String avatarUrl;
    @JsonProperty("url")
    public String url;

    public RepositoryOwner() {
    }

    public RepositoryOwner(String login, String avatarUrl, String ownerUrl) {
        this.login = login;
        this.avatarUrl = avatarUrl;
        this.url = ownerUrl;
    }

    private RepositoryOwner(Parcel in) {
        this.login = in.readString();
        this.avatarUrl = in.readString();
        this.url = in.readString();
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
}
