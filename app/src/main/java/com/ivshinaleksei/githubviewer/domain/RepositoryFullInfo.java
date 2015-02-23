package com.ivshinaleksei.githubviewer.domain;


import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RepositoryFullInfo implements Parcelable {
    public static final Parcelable.Creator<RepositoryFullInfo> CREATOR = new Parcelable.Creator<RepositoryFullInfo>() {
        public RepositoryFullInfo createFromParcel(Parcel source) {
            return new RepositoryFullInfo(source);
        }

        public RepositoryFullInfo[] newArray(int size) {
            return new RepositoryFullInfo[size];
        }
    };
    @JsonProperty("full_name")
    public String fullName;
    @JsonProperty("language")
    public String language;
    @JsonProperty("stargazers_count")
    public int stargazersCount;
    @JsonProperty("created_at")
    public Date createdDate;
    @JsonProperty("description")
    public String description;
    @JsonProperty("url")
    public String repositoryUrl;
    @JsonProperty("owner")
    public RepositoryOwner repositoryOwner;

    public RepositoryFullInfo() {
    }

    public RepositoryFullInfo(String fullName, String language, int stargazersCount, Date date, String description, String repositoryUrl, RepositoryOwner owner) {
        this.fullName = fullName;
        this.language = language;
        this.stargazersCount = stargazersCount;
        this.createdDate = date;
        this.description = description;
        this.repositoryUrl = repositoryUrl;
        this.repositoryOwner = owner;
    }

    private RepositoryFullInfo(Parcel in) {
        this.fullName = in.readString();
        this.language = in.readString();
        this.stargazersCount = in.readInt();
        long tmpCreatedDate = in.readLong();
        this.createdDate = tmpCreatedDate == -1 ? null : new Date(tmpCreatedDate);
        this.description = in.readString();
        this.repositoryUrl = in.readString();
        this.repositoryOwner = in.readParcelable(RepositoryOwner.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.fullName);
        dest.writeString(this.language);
        dest.writeInt(this.stargazersCount);
        dest.writeLong(createdDate != null ? createdDate.getTime() : -1);
        dest.writeString(this.description);
        dest.writeString(this.repositoryUrl);
        dest.writeParcelable(this.repositoryOwner, flags);
    }
}
