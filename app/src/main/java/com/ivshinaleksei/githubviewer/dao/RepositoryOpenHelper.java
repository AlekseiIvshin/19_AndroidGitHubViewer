package com.ivshinaleksei.githubviewer.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ivshinaleksei.githubviewer.contracts.RepositoryContract;

public class RepositoryOpenHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_TABLE_NAME="repositories";
    private static final String REPOSITORIES_TABLE_CREATE =
            "create table "+DATABASE_TABLE_NAME+" ("+
                    RepositoryContract.Columns._ID+" INTEGER PRIMARY KEY, " +
                    RepositoryContract.Columns.FULL_NAME+" TEXT UNIQUE, " +
                    RepositoryContract.Columns.LANGUAGE+" TEXT," +
                    RepositoryContract.Columns.STARGAZERS_COUNT+" INTEGER, " +
                    RepositoryContract.Columns.CREATED_DATE+" INTEGER," +
                    RepositoryContract.Columns.DESCRIPTION+" TEXT," +
                    RepositoryContract.Columns.REPOSITORY_URL+" TEXT, " +
                    RepositoryContract.Columns.OWNER_LOGIN+" TEXT, " +
                    RepositoryContract.Columns.OWNER_AVATAR_URL+" TEXT, " +
                    RepositoryContract.Columns.OWNER_URL+" TEXT )";

    public RepositoryOpenHelper(Context context) {
        super(context, DATABASE_TABLE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.v("ReposipotoryDAO/onCreate","DataBase creating");
        db.execSQL(REPOSITORIES_TABLE_CREATE);
        Log.v("ReposipotoryDAO/onCreate","DataBase created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
