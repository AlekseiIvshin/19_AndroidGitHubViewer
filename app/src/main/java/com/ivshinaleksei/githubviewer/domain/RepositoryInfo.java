package com.ivshinaleksei.githubviewer.domain;


import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ivshinaleksei.githubviewer.contracts.RepositoryContract;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.tojc.ormlite.android.annotation.AdditionalAnnotation;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.Date;

@DatabaseTable(tableName = RepositoryContract.RepositoryInfo.TABLE_NAME)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RepositoryInfo implements Parcelable {

    @DatabaseField(columnName = RepositoryContract.RepositoryInfo._ID, generatedId = true)
    public int id;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "repositorylist_id")
    public RepositoryList repositorylist;

    @Element(required = false)
    @DatabaseField(columnName = RepositoryContract.RepositoryInfo.FULL_NAME)
    @JsonProperty("full_name")
    public String fullName;

    @Element(required = false)
    @DatabaseField(columnName = RepositoryContract.RepositoryInfo.LANGUAGE)
    @JsonProperty("language")
    public String language;

    @Element(required = false)
    @DatabaseField(columnName = RepositoryContract.RepositoryInfo.STARGAZERS_COUNT)
    @JsonProperty("stargazers_count")
    public int stargazersCount;

    @Element(required = false)
    @DatabaseField(columnName = RepositoryContract.RepositoryInfo.CREATED_DATE)
    @JsonProperty("created_at")
    public Date createdDate;

    @Element(required = false)
    @DatabaseField(columnName = RepositoryContract.RepositoryInfo.DESCRIPTION)
    @JsonProperty("description")
    public String description;

    @Element(required = false)
    @DatabaseField(foreign = true, canBeNull = false,foreignAutoCreate = true,foreignAutoRefresh = true)
    @JsonProperty("owner")
    public RepositoryOwner repositoryOwner;

    public RepositoryInfo() {
    }

    private RepositoryInfo(String fullName, String language, int stargazersCount, Date date, String description, RepositoryOwner owner) {
        this.fullName = fullName;
        this.language = language;
        this.stargazersCount = stargazersCount;
        this.createdDate = date;
        this.description = description;
        this.repositoryOwner = owner;
    }

    private RepositoryInfo(Parcel in) {
        this.fullName = in.readString();
        this.language = in.readString();
        this.stargazersCount = in.readInt();
        long tmpCreatedDate = in.readLong();
        this.createdDate = tmpCreatedDate == -1 ? null : new Date(tmpCreatedDate);
        this.description = in.readString();
        this.repositoryOwner = in.readParcelable(RepositoryOwner.class.getClassLoader());
    }

    public static RepositoryInfo getFromCursor(Cursor cursor) {
        if(cursor.getColumnCount()<=0){
            return null;
        }
//        int iOwnerLogin = cursor.getColumnIndex(RepositoryContract.RepositoryOwner.OWNER_LOGIN);
//        int iOwnerAvatarUrl = cursor.getColumnIndex(RepositoryContract.RepositoryOwner.OWNER_AVATAR_URL);
        int iRepositoryFullName = cursor.getColumnIndex(RepositoryContract.RepositoryInfo.FULL_NAME);
        int iLanguage = cursor.getColumnIndex(RepositoryContract.RepositoryInfo.LANGUAGE);
        int iStargazersCount = cursor.getColumnIndex(RepositoryContract.RepositoryInfo.STARGAZERS_COUNT);
        int iCreatedDate = cursor.getColumnIndex(RepositoryContract.RepositoryInfo.CREATED_DATE);
        int iDescription = cursor.getColumnIndex(RepositoryContract.RepositoryInfo.DESCRIPTION);

//        String login = cursor.getString(iOwnerLogin);
//        String avatarUrl = cursor.getString(iOwnerAvatarUrl);
        String fullName = cursor.getString(iRepositoryFullName);
        String language = cursor.getString(iLanguage);
        int stargazersCount = cursor.getInt(iStargazersCount);
        long createdDate = cursor.getLong(iCreatedDate) * 1000;
        String description = cursor.getString(iDescription);

        // TODO:
        RepositoryOwner owner = new RepositoryOwner();
        return new RepositoryInfo(fullName, language, stargazersCount, new Date(createdDate), description, owner);
    }

    public ContentValues marshalling() {
        ContentValues values = new ContentValues();
        values.put(RepositoryContract.RepositoryInfo.FULL_NAME, this.fullName);
        values.put(RepositoryContract.RepositoryInfo.LANGUAGE, this.language);
        values.put(RepositoryContract.RepositoryInfo.STARGAZERS_COUNT, this.stargazersCount);
        values.put(RepositoryContract.RepositoryInfo.CREATED_DATE, this.createdDate.getTime() / 1000);
        values.put(RepositoryContract.RepositoryInfo.DESCRIPTION, this.description);
        values.put(RepositoryContract.RepositoryOwner.OWNER_LOGIN, this.repositoryOwner.login);
        values.put(RepositoryContract.RepositoryOwner.OWNER_AVATAR_URL, this.repositoryOwner.avatarUrl);
        return values;
    }

    public static final Parcelable.Creator<RepositoryInfo> CREATOR = new Parcelable.Creator<RepositoryInfo>() {
        public RepositoryInfo createFromParcel(Parcel source) {
            return new RepositoryInfo(source);
        }

        public RepositoryInfo[] newArray(int size) {
            return new RepositoryInfo[size];
        }
    };

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
        dest.writeParcelable(this.repositoryOwner, flags);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RepositoryInfo that = (RepositoryInfo) o;

        if (!createdDate.equals(that.createdDate)) return false;
        if (!fullName.equals(that.fullName)) return false;
        if (language != null ? !language.equals(that.language) : that.language != null)
            return false;
        return repositoryOwner.equals(that.repositoryOwner);

    }

    @Override
    public int hashCode() {
        int result = fullName.hashCode();
        result = 31 * result + (language != null ? language.hashCode() : 0);
        result = 31 * result + createdDate.hashCode();
        result = 31 * result + repositoryOwner.hashCode();
        return result;
    }
}
