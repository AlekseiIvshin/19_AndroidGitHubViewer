package com.ivshinaleksei.githubviewer.network;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

public abstract class AbsRepositorySearchRequest extends RetrofitSpiceRequest<RepositoryList, GitHub> {
    public AbsRepositorySearchRequest() {
        super(RepositoryList.class, GitHub.class);
    }
}
