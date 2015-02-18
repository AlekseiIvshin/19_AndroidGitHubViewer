package com.ivshinaleksei.githubviewer.network;

import com.ivshinaleksei.githubviewer.domain.RepositoryPreview;
import com.ivshinaleksei.githubviewer.domain.RepositoryPreviewList;

import retrofit.http.GET;
import retrofit.http.Query;

public interface GitHub {
    @GET("/search/repositories")
    RepositoryPreviewList repositories(@Query("q")String query);
}
