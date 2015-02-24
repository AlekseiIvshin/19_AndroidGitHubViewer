package com.ivshinaleksei.githubviewer.providers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.ivshinaleksei.githubviewer.contracts.RepositoryContract;
import com.ivshinaleksei.githubviewer.dao.RepositoryOpenHelper;


public class RepositoryContentProvider extends ContentProvider {

    private static final int REPOSITORY = 1;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final String INSERT_QUERY = "INSERT INTO " + RepositoryContract.RepositoryInfo.TABLE_NAME + "(" +
            RepositoryContract.RepositoryInfo.FULL_NAME + "," +
            RepositoryContract.RepositoryInfo.LANGUAGE + "," +
            RepositoryContract.RepositoryInfo.STARGAZERS_COUNT + "," +
            RepositoryContract.RepositoryInfo.CREATED_DATE + "," +
            RepositoryContract.RepositoryInfo.DESCRIPTION + "," +
            RepositoryContract.RepositoryInfo.OWNER_LOGIN + "," +
            RepositoryContract.RepositoryInfo.OWNER_AVATAR_URL +
            ") VALUES(?,?,?,?,?,?,?);";
    static {
        sUriMatcher.addURI("com.ivshinaleksei.githubviewer.provider", "repositories", REPOSITORY);
    }
    SQLiteDatabase db;
    private RepositoryOpenHelper repositoryOpenHelper;

    public RepositoryContentProvider() {
    }

    @Override
    public boolean onCreate() {
        repositoryOpenHelper = new RepositoryOpenHelper(getContext());
        return true;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        db = repositoryOpenHelper.getWritableDatabase();
        long id;
        try {
            db.beginTransaction();
            id = db.insert(RepositoryContract.RepositoryInfo.TABLE_NAME, null, values);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        Uri resUri = new Uri.Builder().authority(RepositoryContract.AUTHORITY).appendPath(id + "").build();
        getContext().getContentResolver().notifyChange(resUri, null);
        return resUri;
    }

    @Override
    public int bulkInsert(Uri uri, @NonNull ContentValues[] values) {
        db = repositoryOpenHelper.getWritableDatabase();
        SQLiteStatement statement = db.compileStatement(INSERT_QUERY);
        int count = 0;
        try {
            db.beginTransaction();
            db.execSQL("delete from " + RepositoryContract.RepositoryInfo.TABLE_NAME + " where " + RepositoryContract.RepositoryInfo._ID + ">=0");
            for (ContentValues val : values) {
                statement.clearBindings();
                statement.bindString(1, val.getAsString(RepositoryContract.RepositoryInfo.FULL_NAME));
                statement.bindString(2, value(val, RepositoryContract.RepositoryInfo.LANGUAGE, ""));
                statement.bindLong(3, val.getAsInteger(RepositoryContract.RepositoryInfo.STARGAZERS_COUNT));
                statement.bindLong(4, val.getAsLong(RepositoryContract.RepositoryInfo.CREATED_DATE));
                statement.bindString(5, value(val, RepositoryContract.RepositoryInfo.DESCRIPTION, ""));
                statement.bindString(6, val.getAsString(RepositoryContract.RepositoryInfo.OWNER_LOGIN));
                statement.bindString(7, value(val, RepositoryContract.RepositoryInfo.OWNER_AVATAR_URL, ""));
                statement.execute();
                count++;
            }
            db.setTransactionSuccessful();
            getContext().getContentResolver().notifyChange(RepositoryContract.CONTENT_URI, null);
        } catch (SQLException e) {
            Log.e("RepositoryContentProvider", "At " + count + " insert: " + e.getMessage());
            return -1;
        } finally {
            db.endTransaction();
        }
        return count;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        switch (sUriMatcher.match(uri)) {
            case REPOSITORY:
                db = repositoryOpenHelper.getReadableDatabase();
                Cursor cursor = db.query(RepositoryContract.RepositoryInfo.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                cursor.setNotificationUri(getContext().getContentResolver(), RepositoryContract.CONTENT_URI);
                return cursor;
            default:
                throw new UnsupportedOperationException("Not yet implemented");
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private String value(ContentValues val, String column, String defaultValue) {
        String res = val.getAsString(column);
        if (res == null) {
            return defaultValue;
        }
        return res;
    }
}
