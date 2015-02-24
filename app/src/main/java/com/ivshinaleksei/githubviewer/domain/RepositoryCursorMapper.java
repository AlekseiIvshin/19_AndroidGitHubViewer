package com.ivshinaleksei.githubviewer.domain;

import android.content.ContentValues;
import android.database.Cursor;

import com.ivshinaleksei.githubviewer.contracts.RepositoryContract;

import java.util.Date;

public class RepositoryCursorMapper implements CursorMapper<RepositoryFullInfo> {
    @Override
    public RepositoryFullInfo get(Cursor cursor, ContentValues values) {
        String login = values.getAsString(RepositoryContract.Columns.OWNER_LOGIN);
        String avatarUrl = values.getAsString(RepositoryContract.Columns.OWNER_AVATAR_URL);
        String ownerUrl = values.getAsString(RepositoryContract.Columns.OWNER_URL);
        String fullName = values.getAsString(RepositoryContract.Columns.FULL_NAME);
        String language = values.getAsString(RepositoryContract.Columns.LANGUAGE);
        int stargazersCount = values.getAsInteger(RepositoryContract.Columns.STARGAZERS_COUNT);
        long createdDate = values.getAsLong(RepositoryContract.Columns.CREATED_DATE) * 1000;
        String description = values.getAsString(RepositoryContract.Columns.DESCRIPTION);
        String repositoryUrl = values.getAsString(RepositoryContract.Columns.REPOSITORY_URL);
        RepositoryOwner owner = new RepositoryOwner(login, avatarUrl, ownerUrl);
        return new RepositoryFullInfo(fullName, language, stargazersCount, new Date(createdDate), description, repositoryUrl, owner);
    }

    @Override
    public ContentValues marshalling(RepositoryFullInfo value) {
        ContentValues values = new ContentValues();
        values.put(RepositoryContract.Columns.FULL_NAME, value.fullName);
        values.put(RepositoryContract.Columns.LANGUAGE, value.language);
        values.put(RepositoryContract.Columns.STARGAZERS_COUNT, value.stargazersCount);
        values.put(RepositoryContract.Columns.CREATED_DATE, value.createdDate.getTime() / 1000);
        values.put(RepositoryContract.Columns.DESCRIPTION, value.description);
        values.put(RepositoryContract.Columns.REPOSITORY_URL, value.repositoryUrl);
        values.put(RepositoryContract.Columns.OWNER_LOGIN, value.repositoryOwner.login);
        values.put(RepositoryContract.Columns.OWNER_AVATAR_URL, value.repositoryOwner.avatarUrl);
        values.put(RepositoryContract.Columns.OWNER_URL, value.repositoryOwner.url);
        return values;
    }
}
