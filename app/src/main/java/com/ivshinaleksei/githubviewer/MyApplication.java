package com.ivshinaleksei.githubviewer;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class MyApplication extends Application {

    public static void initImageLoader(Context applicationContext) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(applicationContext)
                .discCacheSize(40 * 1024 * 1024)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .denyCacheImageMultipleSizesInMemory()
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .memoryCacheSize(5 * 1024 * 1024)
                .build();

        ImageLoader.getInstance().init(config);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader(getApplicationContext());
    }

}
