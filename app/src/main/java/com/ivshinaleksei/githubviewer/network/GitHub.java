package com.ivshinaleksei.githubviewer.network;

import com.ivshinaleksei.githubviewer.domain.RepositoryList;

import retrofit.http.GET;
import retrofit.http.Query;

public interface GitHub {

    @GET("/search/repositories")
    RepositoryList repositoriesSorted(@Query("q") String query,
                                      @Query("sort") String sortedBy,
                                      @Query("order") String order);
}
