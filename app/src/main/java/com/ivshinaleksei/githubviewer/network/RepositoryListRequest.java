package com.ivshinaleksei.githubviewer.network;

import com.ivshinaleksei.githubviewer.domain.RepositoryFullInfoImpl;
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

/**
 * Created by Aleksei_Ivshin on 18/02/2015.
 */
public class RepositoryListRequest extends RetrofitSpiceRequest<RepositoryList,GitHub> {
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