package com.ivshinaleksei.githubviewer.domain;

import android.content.ContentValues;
import android.database.Cursor;

public interface CursorMapper<T> {
    T get(ContentValues values);


    T get(Cursor cursor);

    ContentValues marshalling(T value);
}
