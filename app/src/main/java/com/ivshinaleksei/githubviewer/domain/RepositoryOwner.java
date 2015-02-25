package com.ivshinaleksei.githubviewer.domain;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ivshinaleksei.githubviewer.contracts.RepositoryContract;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.Element;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RepositoryOwner implements Parcelable {

    @DatabaseField(columnName = RepositoryContract.RepositoryOwner._ID, generatedId = true)
    private int id;

    @Element(required = false)
    @DatabaseField(columnName = RepositoryContract.RepositoryOwner.OWNER_LOGIN)
    @JsonProperty("login")
    public String login;

    @Element(required = false)
    @DatabaseField(columnName = RepositoryContract.RepositoryOwner.OWNER_AVATAR_URL)
    @JsonProperty("avatar_url")
    public String avatarUrl;

    public RepositoryOwner() {
    }


    public RepositoryOwner(String login, String avatarUrl) {
        this.login = login;
        this.avatarUrl = avatarUrl;
    }

    private RepositoryOwner(Parcel in) {
        this.login = in.readString();
        this.avatarUrl = in.readString();
    }

    public static final Parcelable.Creator<RepositoryOwner> CREATOR = new Parcelable.Creator<RepositoryOwner>() {
        public RepositoryOwner createFromParcel(Parcel source) {
            return new RepositoryOwner(source);
        }

        public RepositoryOwner[] newArray(int size) {
            return new RepositoryOwner[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.login);
        dest.writeString(this.avatarUrl);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RepositoryOwner that = (RepositoryOwner) o;

        return login.equals(that.login);

    }

    @Override
    public int hashCode() {
        return login.hashCode();
    }
}
