package com.ivshinaleksei.githubviewer.domain.impl;


import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ivshinaleksei.githubviewer.domain.RepositoryFullInfo;
import com.ivshinaleksei.githubviewer.domain.RepositoryOwner;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RepositoryFullInfoImpl implements RepositoryFullInfo, Parcelable {
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
        this.repositoryOwner = owner;
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

    private RepositoryFullInfoImpl(Parcel in) {
        this.fullName = in.readString();
        this.language = in.readString();
        this.stargazersCount = in.readInt();
        long tmpCreatedDate = in.readLong();
        this.createdDate = tmpCreatedDate == -1 ? null : new Date(tmpCreatedDate);
        this.description = in.readString();
        this.repositoryUrl = in.readString();
        this.repositoryOwner = in.readParcelable(RepositoryOwnerImpl.class.getClassLoader());
    }

    public static final Parcelable.Creator<RepositoryFullInfoImpl> CREATOR = new Parcelable.Creator<RepositoryFullInfoImpl>() {
        public RepositoryFullInfoImpl createFromParcel(Parcel source) {
            return new RepositoryFullInfoImpl(source);
        }

        public RepositoryFullInfoImpl[] newArray(int size) {
            return new RepositoryFullInfoImpl[size];
        }
    };
}
