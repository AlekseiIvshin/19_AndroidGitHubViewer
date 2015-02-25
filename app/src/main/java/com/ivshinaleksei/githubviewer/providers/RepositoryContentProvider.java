package com.ivshinaleksei.githubviewer.providers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.ivshinaleksei.githubviewer.contracts.RepositoryContract;
import com.octo.android.robospice.persistence.ormlite.RoboSpiceDatabaseHelper;


public class RepositoryContentProvider extends ContentProvider {

    private static final int sRepositories = 1;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sUriMatcher.addURI(RepositoryContract.AUTHORITY, RepositoryContract.RepositoryInfo.PATH, sRepositories);
    }

    public RepositoryContentProvider() {
    }

    SQLiteDatabase db;
    private RoboSpiceDatabaseHelper roboSpiceDatabaseHelper;

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        // TODO:
        return "vnd.cursor.item/com.ivshingithubviewer.provider.repository";
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public boolean onCreate() {
        roboSpiceDatabaseHelper = new RoboSpiceDatabaseHelper(getContext(),RepositoryContract.DATABASE_NAME,RepositoryContract.DATABASE_VERSION);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        switch (sUriMatcher.match(uri)) {
            case sRepositories:
                db = roboSpiceDatabaseHelper.getReadableDatabase();
                try {
                    Cursor cursor = db.query(RepositoryContract.RepositoryInfo.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                    cursor.setNotificationUri(getContext().getContentResolver(), uri);
                    return cursor;
                }catch (SQLException e){
                    return null;
                }
            default:
                throw new UnsupportedOperationException("Not yet implemented");
        }
    }
}
