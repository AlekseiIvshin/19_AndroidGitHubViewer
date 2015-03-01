package com.ivshinaleksei.githubviewer.domain;


import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.ivshinaleksei.githubviewer.contracts.RepositoryContract;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.Element;

import java.util.Date;

@DatabaseTable(tableName = RepositoryContract.Comment.TABLE_NAME)
public class Comment implements Parcelable {

    public static final Parcelable.Creator<Comment> CREATOR = new Parcelable.Creator<Comment>() {
        public Comment createFromParcel(Parcel source) {
            return new Comment(source);
        }

        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
    @DatabaseField(columnName = RepositoryContract.Comment._ID, generatedId = true)
    public int id;
    @Element(required = false)
    @DatabaseField(columnName = RepositoryContract.Comment.TITLE)
    public String title;
    @Element(required = false)
    @DatabaseField(columnName = RepositoryContract.Comment.MESSAGE)
    public String message;
    @Element(required = false)
    @DatabaseField(columnName = RepositoryContract.Comment.CREATED_DATE)
    public Date createdDate;

    public Comment() {
    }

    private Comment(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.message = in.readString();
        long tmpCreatedDate = in.readLong();
        this.createdDate = tmpCreatedDate == -1 ? null : new Date(tmpCreatedDate);
    }

    public static Comment getFromCursor(Cursor cursor) {
        if (cursor.getColumnCount() <= 0) {
            return null;
        }

        int indexTitle = cursor.getColumnIndex(RepositoryContract.Comment.TITLE);
        int indexMessage = cursor.getColumnIndex(RepositoryContract.Comment.MESSAGE);
        int indexCreatedDate = cursor.getColumnIndex(RepositoryContract.Comment.CREATED_DATE);

        Comment comment = new Comment();
        comment.title = cursor.getString(indexTitle);
        comment.message = cursor.getString(indexMessage);
        comment.createdDate = new Date(cursor.getLong(indexCreatedDate) * 1000);

        return comment;
    }

    public ContentValues marshalling() {
        ContentValues values = new ContentValues(3);
        values.put(RepositoryContract.Comment.TITLE, title);
        values.put(RepositoryContract.Comment.MESSAGE, message);
        values.put(RepositoryContract.Comment.CREATED_DATE, createdDate.getTime() / 1000);
        return values;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.message);
        dest.writeLong(createdDate != null ? createdDate.getTime() : -1);
    }
}
