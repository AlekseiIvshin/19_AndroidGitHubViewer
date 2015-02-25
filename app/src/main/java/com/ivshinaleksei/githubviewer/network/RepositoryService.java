package com.ivshinaleksei.githubviewer.network;

import android.app.Application;

import com.ivshinaleksei.githubviewer.contracts.RepositoryContract;
import com.ivshinaleksei.githubviewer.domain.RepositoryInfo;
import com.ivshinaleksei.githubviewer.domain.RepositoryList;
import com.ivshinaleksei.githubviewer.domain.RepositoryOwner;
import com.octo.android.robospice.persistence.CacheManager;
import com.octo.android.robospice.persistence.exception.CacheCreationException;
import com.octo.android.robospice.persistence.ormlite.InDatabaseObjectPersisterFactory;
import com.octo.android.robospice.persistence.ormlite.RoboSpiceDatabaseHelper;
import com.octo.android.robospice.retrofit.RetrofitJackson2SpiceService;

import java.util.ArrayList;
import java.util.List;

public class RepositoryService extends RetrofitJackson2SpiceService {


    private final static String sBaseUrl = "https://api.github.com";

    @Override
    public CacheManager createCacheManager(Application application) throws CacheCreationException {
        CacheManager cacheManager = new CacheManager();

        List<Class<?>> classCollection = new ArrayList<Class<?>>();
        classCollection.add(RepositoryInfo.class);
        classCollection.add(RepositoryOwner.class);
        classCollection.add(RepositoryList.class);

        RoboSpiceDatabaseHelper databaseHelper =
                new RoboSpiceDatabaseHelper(application, RepositoryContract.DATABASE_NAME, RepositoryContract.DATABASE_VERSION);
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
