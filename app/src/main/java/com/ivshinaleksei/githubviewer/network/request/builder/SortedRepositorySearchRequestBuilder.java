package com.ivshinaleksei.githubviewer.network.request.builder;

import com.ivshinaleksei.githubviewer.network.BaseRepositorySearchRequest;
import com.ivshinaleksei.githubviewer.network.request.SortedRepositorySearchRequest;

public class SortedRepositorySearchRequestBuilder {

    private String query;
    private String sortBy;
    private String order;

    public SortedRepositorySearchRequestBuilder(String aQuery) {
        this.query = aQuery;
    }

    public SortedRepositorySearchRequestBuilder sortBy(String aField) {
        this.sortBy = aField;
        return this;
    }

    public SortedRepositorySearchRequestBuilder order(String aOrder) {
        this.order = aOrder;
        return this;
    }

    public BaseRepositorySearchRequest build() {
        return new SortedRepositorySearchRequest(query, sortBy, order);
    }
}
