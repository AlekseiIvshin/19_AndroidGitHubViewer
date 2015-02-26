package com.ivshinaleksei.githubviewer;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ivshinaleksei.githubviewer.contracts.RepositoryContract;
import com.ivshinaleksei.githubviewer.domain.RepositoryInfo;
import com.ivshinaleksei.githubviewer.domain.RepositoryList;
import com.ivshinaleksei.githubviewer.domain.RepositoryOwner;
import com.j256.ormlite.table.TableUtils;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.octo.android.robospice.persistence.ormlite.RoboSpiceDatabaseHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader(getApplicationContext());
    }

    public static void initImageLoader(Context applicationContext) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(applicationContext)
                .discCacheSize(40*1024*1024)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .denyCacheImageMultipleSizesInMemory()
                .threadPriority(Thread.NORM_PRIORITY-2)
                .memoryCacheSize(5*1024*1024)
                .build();

        ImageLoader.getInstance().init(config);
    }

}
