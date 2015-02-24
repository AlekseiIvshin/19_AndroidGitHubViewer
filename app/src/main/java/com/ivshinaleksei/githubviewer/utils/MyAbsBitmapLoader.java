package com.ivshinaleksei.githubviewer.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public abstract class MyAbsBitmapLoader extends AsyncTask<String, Void, Bitmap> {
    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap bitmap = null;
        if (params.length > 0) {
            bitmap = MyBitmapCacheManagerImpl.getInstance().get(params[0]);
            if (bitmap == null) {
                bitmap = getBitmapFromUrl(params[0]);
                if (bitmap != null) {
                    MyBitmapCacheManagerImpl.getInstance().set(params[0], bitmap);
                }
            }
        }
        return bitmap;
    }

    private Bitmap getBitmapFromUrl(String src) {
        try {
            InputStream is = (InputStream) new URL(src).getContent();
            Bitmap myBitmap = BitmapFactory.decodeStream(is);
            return myBitmap;
        } catch (IOException e) {
            Log.e("GetBitMapFromUrl", e.getMessage());
            return null;
        }

    }
}
