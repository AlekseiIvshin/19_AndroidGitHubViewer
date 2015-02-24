package com.ivshinaleksei.githubviewer.network;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

public class RepositoryListRequest extends RetrofitSpiceRequest<RepositoryList, GitHub> {
    private final String query;

    public RepositoryListRequest(String aQuery) {
        super(RepositoryList.class, GitHub.class);
        this.query = aQuery;
    }

    @Override
    public RepositoryList loadDataFromNetwork() throws Exception {
        return getService().repositories(query);
    }

}