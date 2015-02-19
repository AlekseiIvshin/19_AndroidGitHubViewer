package com.ivshinaleksei.githubviewer.domain.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.widget.CalendarView;

import com.ivshinaleksei.githubviewer.contracts.RepositoryContract;
import com.ivshinaleksei.githubviewer.domain.CursorMapper;
import com.ivshinaleksei.githubviewer.domain.RepositoryFullInfo;
import com.ivshinaleksei.githubviewer.domain.RepositoryOwner;

import java.util.Date;

/**
 * Created by Aleksei_Ivshin on 19/02/2015.
 */
public class RepositoryCursorMapper implements CursorMapper<RepositoryFullInfo> {
    @Override
    public RepositoryFullInfo get(Cursor cursor,ContentValues values) {
        String login = values.getAsString(RepositoryContract.Columns.OWNER_LOGIN);
        String avatarUrl = values.getAsString(RepositoryContract.Columns.OWNER_AVATAR_URL);
        String ownerUrl = values.getAsString(RepositoryContract.Columns.OWNER_URL);
        String fullName = values.getAsString(RepositoryContract.Columns.FULL_NAME);
        String language = values.getAsString(RepositoryContract.Columns.LANGUAGE);
        int stargazersCount = values.getAsInteger(RepositoryContract.Columns.STARGAZERS_COUNT);
        long createdDate = values.getAsLong(RepositoryContract.Columns.CREATED_DATE);
        String description = values.getAsString(RepositoryContract.Columns.DESCRIPTION);
        String repositoryUrl = values.getAsString(RepositoryContract.Columns.REPOSITORY_URL);
        RepositoryOwnerImpl owner = new RepositoryOwnerImpl(login,avatarUrl,ownerUrl);
        return new RepositoryFullInfoImpl(fullName,language,stargazersCount,new Date(createdDate),description,repositoryUrl,owner);
    }

    @Override
    public ContentValues marshalling(RepositoryFullInfo value) {
        ContentValues values = new ContentValues();
        values.put(RepositoryContract.Columns.FULL_NAME, value.getFullName());
        values.put(RepositoryContract.Columns.LANGUAGE,value.getLanguage());
        values.put(RepositoryContract.Columns.STARGAZERS_COUNT,value.getStargazersCount());
        values.put(RepositoryContract.Columns.CREATED_DATE,value.getCreatedDate().getTime()/1000);
        values.put(RepositoryContract.Columns.DESCRIPTION,value.getDescription());
        values.put(RepositoryContract.Columns.REPOSITORY_URL,value.getRepositoryUrl());
        values.put(RepositoryContract.Columns.OWNER_LOGIN,value.getOwner().getOwnerLogin());
        values.put(RepositoryContract.Columns.OWNER_AVATAR_URL,value.getOwner().getOwnerAvatarUrl());
        values.put(RepositoryContract.Columns.OWNER_URL,value.getOwner().getOwnerUrl());
        return values;
    }
}
