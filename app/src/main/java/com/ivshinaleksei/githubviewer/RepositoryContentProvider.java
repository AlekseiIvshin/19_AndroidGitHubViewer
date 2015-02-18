package com.ivshinaleksei.githubviewer;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
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
        long _id = db.insert(RepositoryOpenHelper.DATABASE_TABLE_NAME, null, values);
        return new Uri.Builder().authority(RepositoryContract.AUTHORITY).appendPath(_id + "").build();
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        db = repositoryOpenHelper.getWritableDatabase();
        int count = 0;
        // TODO: change to execSQL( many queries)
        try {
            db.beginTransaction();
            for (ContentValues val : values) {
                long _id = db.insertOrThrow(RepositoryOpenHelper.DATABASE_TABLE_NAME, null, val);
                if (_id >= 0) {
                    count++;
                } else {
                    Log.v("bulkInsert", "_ID = " + _id);
                }
            }
        } catch (SQLException e) {
            Log.e("RepositoryContentProvider", e.getMessage());
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
                Log.v("RepositoryContentProvider", "Search repositories");
                db = repositoryOpenHelper.getReadableDatabase();
                Cursor cursor = db.query("repositories", projection, selection, selectionArgs, "", "", sortOrder);
                Log.v("Query","Cursor.getCount() = "+cursor.getCount());
                return cursor;
//            case REPOSITORY_FULLNAME:
//                db = repositoryOpenHelper.getReadableDatabase();
//                // TODO: change to find by id
//                return db.query("repositories",projection,selection,selectionArgs,"","",sortOrder);
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
}
