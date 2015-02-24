package com.ivshinaleksei.githubviewer.network;

import com.ivshinaleksei.githubviewer.domain.RepositoryList;
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

public abstract class BaseRepositorySearchRequest extends RetrofitSpiceRequest<RepositoryList, GitHub> {
    public BaseRepositorySearchRequest() {
        super(RepositoryList.class, GitHub.class);
    }
}
