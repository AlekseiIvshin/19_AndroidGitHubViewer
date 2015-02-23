package com.ivshinaleksei.githubviewer.providers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import com.ivshinaleksei.githubviewer.contracts.RepositoryContract;
import com.ivshinaleksei.githubviewer.dao.RepositoryOpenHelper;


public class RepositoryContentProvider extends ContentProvider {

    private RepositoryOpenHelper repositoryOpenHelper;

    SQLiteDatabase db;

    private static final int REPOSITORY = 1;
    private static final int REPOSITORY_FULLNAME = 2;


    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI("com.ivshinaleksei.githubviewer.provider", "repositories", REPOSITORY);
        sUriMatcher.addURI("com.ivshinaleksei.githubviewer.provider", "repositories/*", REPOSITORY_FULLNAME);
    }

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
        long _id;
        try {
            db.beginTransaction();
            _id = db.insert(RepositoryOpenHelper.REPOSITORY_TABLE_NAME, null, values);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        Uri resUri = new Uri.Builder().authority(RepositoryContract.AUTHORITY).appendPath(_id + "").build();
        getContext().getContentResolver().notifyChange(resUri, null);
        return resUri;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        db = repositoryOpenHelper.getWritableDatabase();
        String sql = "INSERT INTO " + RepositoryOpenHelper.REPOSITORY_TABLE_NAME + "(" +
                RepositoryContract.Columns.FULL_NAME + "," +
                RepositoryContract.Columns.LANGUAGE + "," +
                RepositoryContract.Columns.STARGAZERS_COUNT + "," +
                RepositoryContract.Columns.CREATED_DATE + "," +
                RepositoryContract.Columns.DESCRIPTION + "," +
                RepositoryContract.Columns.REPOSITORY_URL + "," +
                RepositoryContract.Columns.OWNER_LOGIN + "," +
                RepositoryContract.Columns.OWNER_AVATAR_URL + "," +
                RepositoryContract.Columns.OWNER_URL +
                ") VALUES(?,?,?,?,?,?,?,?,?);";
        SQLiteStatement statement = db.compileStatement(sql);
        int count=0;
        try {
            db.beginTransaction();
            db.execSQL("delete from " + RepositoryOpenHelper.REPOSITORY_TABLE_NAME + " where " + RepositoryContract.Columns._ID + ">=0");
            for (ContentValues val : values) {
                statement.clearBindings();
                statement.bindString(1, val.getAsString(RepositoryContract.Columns.FULL_NAME));
                statement.bindString(2, value(val,RepositoryContract.Columns.LANGUAGE,""));
                statement.bindLong(3, val.getAsInteger(RepositoryContract.Columns.STARGAZERS_COUNT));
                statement.bindLong(4, val.getAsLong(RepositoryContract.Columns.CREATED_DATE));
                statement.bindString(5, value(val,RepositoryContract.Columns.DESCRIPTION,""));
                statement.bindString(6, val.getAsString(RepositoryContract.Columns.REPOSITORY_URL));
                statement.bindString(7, val.getAsString(RepositoryContract.Columns.OWNER_LOGIN));
                statement.bindString(8, value(val,RepositoryContract.Columns.OWNER_AVATAR_URL,""));
                statement.bindString(9, val.getAsString(RepositoryContract.Columns.OWNER_URL));
                statement.execute();
                count++;
            }
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            Log.e("RepositoryContentProvider","At "+count+" insert: "+ e.getMessage());
            return -1;
        } finally {
            db.endTransaction();
        }
        getContext().getContentResolver().notifyChange(RepositoryContract.CONTENT_URI, null);
        return count;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        switch (sUriMatcher.match(uri)) {
            case REPOSITORY:
                db = repositoryOpenHelper.getReadableDatabase();
                Cursor cursor = db.query(RepositoryOpenHelper.REPOSITORY_TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
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

    private String value(ContentValues val, String column, String defaultValue){
        String res = val.getAsString(column);
        if(res == null){
            return defaultValue;
        }
        return res;
    }
}
