package com.ivshinaleksei.githubviewer.domain;

import android.content.ContentValues;
import android.database.Cursor;

import com.ivshinaleksei.githubviewer.contracts.RepositoryContract;

import java.util.Date;

public class RepositoryCursorMapper implements CursorMapper<RepositoryFullInfo> {
    private static RepositoryCursorMapper mapper;
    public static RepositoryCursorMapper getInstance(){
        if(mapper==null){
            synchronized (RepositoryCursorMapper.class){
                if(mapper==null){
                    mapper = new RepositoryCursorMapper();
                }
            }
        }
        return  mapper;
    }

    public static void release(){
        if(mapper!=null){
            synchronized (RepositoryCursorMapper.class){
                if(mapper!=null){
                    mapper = null;
                }
            }
        }
    }

    private RepositoryCursorMapper(){

    }

    @Override
    public RepositoryFullInfo get(ContentValues values) {
        String login = values.getAsString(RepositoryContract.Columns.OWNER_LOGIN);
        String avatarUrl = values.getAsString(RepositoryContract.Columns.OWNER_AVATAR_URL);
        String fullName = values.getAsString(RepositoryContract.Columns.FULL_NAME);
        String language = values.getAsString(RepositoryContract.Columns.LANGUAGE);
        int stargazersCount = values.getAsInteger(RepositoryContract.Columns.STARGAZERS_COUNT);
        long createdDate = values.getAsLong(RepositoryContract.Columns.CREATED_DATE) * 1000;
        String description = values.getAsString(RepositoryContract.Columns.DESCRIPTION);
        RepositoryOwner owner = new RepositoryOwner(login, avatarUrl);
        return new RepositoryFullInfo(fullName, language, stargazersCount, new Date(createdDate), description, owner);
    }

    @Override
    public RepositoryFullInfo get(Cursor cursor) {
        int iOwnerLogin = cursor.getColumnIndex(RepositoryContract.Columns.OWNER_LOGIN);
        int iOwnerAvatarUrl = cursor.getColumnIndex(RepositoryContract.Columns.OWNER_AVATAR_URL);
        int iRepositoryFullName = cursor.getColumnIndex(RepositoryContract.Columns.FULL_NAME);
        int iLanguage = cursor.getColumnIndex(RepositoryContract.Columns.LANGUAGE);
        int iStargazersCount = cursor.getColumnIndex(RepositoryContract.Columns.STARGAZERS_COUNT);
        int iCreatedDate = cursor.getColumnIndex(RepositoryContract.Columns.CREATED_DATE);
        int iDescription = cursor.getColumnIndex(RepositoryContract.Columns.DESCRIPTION);

        String login = cursor.getString(iOwnerLogin);
        String avatarUrl = cursor.getString(iOwnerAvatarUrl);
        String fullName = cursor.getString(iRepositoryFullName);
        String language = cursor.getString(iLanguage);
        int stargazersCount = cursor.getInt(iStargazersCount);
        long createdDate = cursor.getLong(iCreatedDate) * 1000;
        String description = cursor.getString(iDescription);

        RepositoryOwner owner = new RepositoryOwner(login, avatarUrl);
        return new RepositoryFullInfo(fullName, language, stargazersCount, new Date(createdDate), description,  owner);
    }

    @Override
    public ContentValues marshalling(RepositoryFullInfo value) {
        ContentValues values = new ContentValues();
        values.put(RepositoryContract.Columns.FULL_NAME, value.fullName);
        values.put(RepositoryContract.Columns.LANGUAGE, value.language);
        values.put(RepositoryContract.Columns.STARGAZERS_COUNT, value.stargazersCount);
        values.put(RepositoryContract.Columns.CREATED_DATE, value.createdDate.getTime() / 1000);
        values.put(RepositoryContract.Columns.DESCRIPTION, value.description);
        values.put(RepositoryContract.Columns.OWNER_LOGIN, value.repositoryOwner.login);
        values.put(RepositoryContract.Columns.OWNER_AVATAR_URL, value.repositoryOwner.avatarUrl);
        return values;
    }
}
