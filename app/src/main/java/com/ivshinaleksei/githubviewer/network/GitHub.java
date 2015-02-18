package com.ivshinaleksei.githubviewer.network;

import com.ivshinaleksei.githubviewer.domain.RepositoryPreview;

import retrofit.http.GET;
import retrofit.http.Query;

public interface GitHub {
    @GET("/search/repositories")
    RepositoryList repositories(@Query("q")String query);
}
