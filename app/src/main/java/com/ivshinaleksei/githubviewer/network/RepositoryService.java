package com.ivshinaleksei.githubviewer.network;

import android.app.Application;

import com.ivshinaleksei.githubviewer.domain.RepositoryInfo;
import com.octo.android.robospice.persistence.CacheManager;
import com.octo.android.robospice.persistence.exception.CacheCreationException;
import com.octo.android.robospice.persistence.ormlite.InDatabaseObjectPersisterFactory;
import com.octo.android.robospice.persistence.ormlite.RoboSpiceDatabaseHelper;
import com.octo.android.robospice.retrofit.RetrofitJackson2SpiceService;

import java.util.ArrayList;
import java.util.List;

public class RepositoryService extends RetrofitJackson2SpiceService {

    private static final String sDatabaseName = "githubviewer.db";

    private final static String sBaseUrl = "https://api.github.com";

    @Override
    public CacheManager createCacheManager(Application application) throws CacheCreationException {
        CacheManager cacheManager = new CacheManager();

        List<Class<?>> classCollection = new ArrayList<Class<?>>();
        classCollection.add(RepositoryInfo.class);

        RoboSpiceDatabaseHelper databaseHelper =
                new RoboSpiceDatabaseHelper(application, sDatabaseName, 1);
        InDatabaseObjectPersisterFactory inDatabaseObjectPersisterFactory =
                new InDatabaseObjectPersisterFactory(application, databaseHelper, classCollection);
        cacheManager.addPersister(inDatabaseObjectPersisterFactory);
        return cacheManager;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        addRetrofitInterface(GitHub.class);
    }

    @Override
    protected String getServerUrl() {
        return sBaseUrl;
    }
}
