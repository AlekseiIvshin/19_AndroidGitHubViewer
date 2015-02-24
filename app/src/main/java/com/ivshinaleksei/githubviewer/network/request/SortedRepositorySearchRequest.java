package com.ivshinaleksei.githubviewer.network.request;

import com.ivshinaleksei.githubviewer.network.AbsRepositorySearchRequest;
import com.ivshinaleksei.githubviewer.network.RepositoryList;

public class SortedRepositorySearchRequest extends AbsRepositorySearchRequest {
    private final String query;
    private final String sortedBy;
    private final String order;

    public SortedRepositorySearchRequest(String aQuery, String sortedBy, String order) {
        super();
        this.query = aQuery;
        this.sortedBy = sortedBy;
        this.order = order;
    }

    @Override
    public RepositoryList loadDataFromNetwork() throws Exception {
        return getService().repositoriesSorted(query, sortedBy, order);
    }
}
