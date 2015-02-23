package com.ivshinaleksei.githubviewer.domain;

import android.content.ContentValues;
import android.database.Cursor;

public interface CursorMapper<T> {
    T get(Cursor cursor, ContentValues values);

    ContentValues marshalling(T value);
}
