package com.ivshinaleksei.githubviewer.utils;

import android.graphics.Bitmap;

public interface MyBitmapCacheManager {
    Bitmap get(String key);
    void set(String key,Bitmap bitmap);
}
