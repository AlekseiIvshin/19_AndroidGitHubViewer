package com.ivshinaleksei.githubviewer.utils;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

public class MyCacheManager implements MyBitmapCacheManager {

    private static MyCacheManager manager;

    public static MyCacheManager getInstance() {
        if (manager == null) {
            synchronized (MyCacheManager.class) {
                if (manager == null) {
                    manager = new MyCacheManager();
                }
            }
        }
        return manager;
    }


    private LruCache<String, Bitmap> mMemoryCache;


    private MyCacheManager() {
        initMemoryCache();
    }


    private void initMemoryCache() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };
    }


    private void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemoryCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemoryCache(String key) {
        return mMemoryCache.get(key);
    }

    @Override
    public Bitmap get(String key) {
        Bitmap result = getBitmapFromMemoryCache(key);
        if (result == null) {
            // TODO: getFromdisk cach
        }
        return result;
    }

    @Override
    public void set(String key, Bitmap bitmap) {
        addBitmapToMemoryCache(key, bitmap);
        // TODO: add to disk cache
    }
}
