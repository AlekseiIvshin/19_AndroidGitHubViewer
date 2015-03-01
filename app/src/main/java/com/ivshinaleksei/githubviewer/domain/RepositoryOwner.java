package com.ivshinaleksei.githubviewer.domain;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ivshinaleksei.githubviewer.contracts.RepositoryContract;

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

    public RepositoryOwner() {
    }


    private RepositoryOwner(Parcel in) {
        this.login = in.readString();
        this.avatarUrl = in.readString();
    }

    public static RepositoryOwner getFromCursor(Cursor cursor) {
        if (cursor.getColumnCount() <= 0) {
            return null;
        }

        int indexLogin = cursor.getColumnIndex(RepositoryContract.RepositoryOwner.OWNER_LOGIN);
        int indexAvatarUrl = cursor.getColumnIndex(RepositoryContract.RepositoryOwner.OWNER_AVATAR_URL);

        RepositoryOwner owner = new RepositoryOwner();
        owner.login = cursor.getString(indexLogin);
        ;
        owner.avatarUrl = cursor.getString(indexAvatarUrl);

        return owner;
    }

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
