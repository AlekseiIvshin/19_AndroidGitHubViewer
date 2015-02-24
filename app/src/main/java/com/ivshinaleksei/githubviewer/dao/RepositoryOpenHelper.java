package com.ivshinaleksei.githubviewer.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ivshinaleksei.githubviewer.contracts.RepositoryContract;

public class RepositoryOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "githubviewer.db";
    public static final String REPOSITORY_TABLE_NAME = "repository";
    private static final String REPOSITORIES_TABLE_CREATE =
            "CREATE TABLE " + REPOSITORY_TABLE_NAME + " (" +
                    RepositoryContract.Columns._ID + " INTEGER PRIMARY KEY, " +
                    RepositoryContract.Columns.FULL_NAME + " TEXT UNIQUE, " +
                    RepositoryContract.Columns.LANGUAGE + " TEXT," +
                    RepositoryContract.Columns.STARGAZERS_COUNT + " INTEGER, " +
                    RepositoryContract.Columns.CREATED_DATE + " INTEGER," +
                    RepositoryContract.Columns.DESCRIPTION + " TEXT," +
                    RepositoryContract.Columns.REPOSITORY_URL + " TEXT, " +
                    RepositoryContract.Columns.OWNER_LOGIN + " TEXT, " +
                    RepositoryContract.Columns.OWNER_AVATAR_URL + " TEXT, " +
                    RepositoryContract.Columns.OWNER_URL + " TEXT );";
    private static final int DATABASE_VERSION = 1;

    public RepositoryOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(REPOSITORIES_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO: stubbed update
        db.execSQL("DROP TABLE IF EXISTS " + REPOSITORY_TABLE_NAME);
        onCreate(db);
    }

}
