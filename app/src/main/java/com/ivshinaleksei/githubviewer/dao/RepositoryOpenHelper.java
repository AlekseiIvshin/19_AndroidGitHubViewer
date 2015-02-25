package com.ivshinaleksei.githubviewer.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ivshinaleksei.githubviewer.contracts.RepositoryContract;

public class RepositoryOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "githubviewer.db";
//    private static final String REPOSITORIES_TABLE_CREATE =
//            "CREATE TABLE " + RepositoryContract.RepositoryInfo.TABLE_NAME + " (" +
//                    RepositoryContract.RepositoryInfo._ID + " INTEGER PRIMARY KEY, " +
//                    RepositoryContract.RepositoryInfo.FULL_NAME + " TEXT UNIQUE, " +
//                    RepositoryContract.RepositoryInfo.LANGUAGE + " TEXT," +
//                    RepositoryContract.RepositoryInfo.STARGAZERS_COUNT + " INTEGER, " +
//                    RepositoryContract.RepositoryInfo.CREATED_DATE + " INTEGER," +
//                    RepositoryContract.RepositoryInfo.DESCRIPTION + " TEXT," +
//                    RepositoryContract.RepositoryInfo.OWNER_LOGIN + " TEXT, " +
//                    RepositoryContract.RepositoryInfo.OWNER_AVATAR_URL + " TEXT);";
    private static final int DATABASE_VERSION = 1;

    public RepositoryOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(REPOSITORIES_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO: stubbed update
        db.execSQL("DROP TABLE IF EXISTS " + RepositoryContract.RepositoryInfo.TABLE_NAME);
        onCreate(db);
    }

}
