package com.ivshinaleksei.githubviewer.network;

import com.ivshinaleksei.githubviewer.domain.RepositoryPreviewList;
import com.octo.android.robospice.request.SpiceRequest;
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

public class ReposiotryPreviewListRequest extends RetrofitSpiceRequest<RepositoryPreviewList,GitHub>{
    private final String query;

    public ReposiotryPreviewListRequest(String aQuery) {
        super(RepositoryPreviewList.class, GitHub.class);
        this.query = aQuery;
    }

    @Override
    public RepositoryPreviewList loadDataFromNetwork() throws Exception {
        return  getService().repositories(query);
    }
}
