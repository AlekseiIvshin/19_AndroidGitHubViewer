package com.ivshinaleksei.githubviewer.network;

import com.octo.android.robospice.retrofit.RetrofitJackson2SpiceService;

public class RepositoryService extends RetrofitJackson2SpiceService {

    private final static String BASE_URL = "https://api.github.com";
// TODO: add cache manager
//    @Override
//    public CacheManager createCacheManager(Application application) throws CacheCreationException {
//        CacheManager cacheManager = new CacheManager();
//        JacksonRetrofitObjectPersisterFactory jacksonRetrofitObjectPersisterFactory = new JacksonRetrofitObjectPersisterFactory(application);
//        cacheManager.addPersister(jacksonRetrofitObjectPersisterFactory);
//        return cacheManager;
//    }

    @Override
    public void onCreate() {
        super.onCreate();
        addRetrofitInterface(GitHub.class);
    }

    @Override
    protected String getServerUrl() {
        return BASE_URL;
    }
}
