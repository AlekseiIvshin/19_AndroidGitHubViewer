package com.ivshinaleksei.githubviewer.providers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.ivshinaleksei.githubviewer.contracts.RepositoryContract;
import com.ivshinaleksei.githubviewer.domain.Comment;
import com.ivshinaleksei.githubviewer.domain.RepositoryInfo;
import com.ivshinaleksei.githubviewer.domain.RepositoryList;
import com.j256.ormlite.table.TableUtils;
import com.octo.android.robospice.persistence.ormlite.RoboSpiceDatabaseHelper;

import java.util.ArrayList;
import java.util.List;


public class RepositoryContentProvider extends ContentProvider {

    private static final int sRepositories = 1;
    private static final int sComments = 2;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(RepositoryContract.AUTHORITY, RepositoryContract.RepositoryInfo.PATH, sRepositories);
        sUriMatcher.addURI(RepositoryContract.AUTHORITY, RepositoryContract.Comment.PATH, sComments);
    }

    public RepositoryContentProvider() {
    }

    SQLiteDatabase db;
    private RoboSpiceDatabaseHelper mDatabaseHelper;

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
        switch (sUriMatcher.match(uri)) {
            case sRepositories:
                return RepositoryContract.RepositoryInfo.DIR_TYPE;
            case sComments:
                return RepositoryContract.Comment.DIR_TYPE;
            default:
                throw new IllegalArgumentException("Uri " + uri.toString() + " not supported");
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        switch (sUriMatcher.match(uri)) {
            case sComments:
                db = mDatabaseHelper.getWritableDatabase();
                long id = db.insert(RepositoryContract.Comment.TABLE_NAME, null, values);
                getContext().getContentResolver().notifyChange(uri,null);
                return Uri.parse(RepositoryContract.Comment.CONTENT_URI + "/" + id);
            default:
                throw new IllegalArgumentException("Uri " + uri.toString() + " not supported");
        }
    }

    @Override
    public boolean onCreate() {


        List<Class<?>> classCollection = new ArrayList<Class<?>>();
        classCollection.add(RepositoryList.class);
        classCollection.add(RepositoryInfo.class);
        classCollection.add(Comment.class);

        mDatabaseHelper =
                new RoboSpiceDatabaseHelper(getContext(), RepositoryContract.DATABASE_NAME, RepositoryContract.DATABASE_VERSION);
        for (Class<?> clazz : classCollection) {
            try {
                TableUtils.createTableIfNotExists(mDatabaseHelper.getConnectionSource(), clazz);
            } catch (java.sql.SQLException e) {
                Log.e(RepositoryContentProvider.class.getName(), "Initialization table for class " + clazz.getName());
            }
        }
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        switch (sUriMatcher.match(uri)) {
            case sRepositories: {
                db = mDatabaseHelper.getReadableDatabase();
                try {
                    Cursor cursor = db.query(RepositoryContract.RepositoryInfo.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                    cursor.setNotificationUri(getContext().getContentResolver(), uri);
                    return cursor;
                } catch (SQLException e) {
                    return null;
                }
            }
            case sComments: {
                db = mDatabaseHelper.getReadableDatabase();
                try {
                    Cursor cursor = db.query(RepositoryContract.Comment.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                    cursor.setNotificationUri(getContext().getContentResolver(), uri);
                    return cursor;
                } catch (SQLException e) {
                    return null;
                }
            }
            default:
                throw new IllegalArgumentException("Uri " + uri.toString() + " not supported");
        }
    }

}
