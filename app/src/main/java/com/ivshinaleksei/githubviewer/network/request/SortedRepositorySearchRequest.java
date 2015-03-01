package com.ivshinaleksei.githubviewer.network.request;

import com.ivshinaleksei.githubviewer.domain.RepositoryList;
import com.ivshinaleksei.githubviewer.network.BaseRepositorySearchRequest;

public class SortedRepositorySearchRequest extends BaseRepositorySearchRequest {
    private final String mQuery;
    private final String mSortedBy;
    private final String mOrder;

    private SortedRepositorySearchRequest(String aQuery, String sortedBy, String order) {
        super();
        this.mQuery = aQuery;
        this.mSortedBy = sortedBy;
        this.mOrder = order;
    }

    public static Builder newInstance() {
        return new Builder();
    }

    @Override
    public RepositoryList loadDataFromNetwork() throws Exception {
        return getService().repositoriesSorted(mQuery, mSortedBy, mOrder);
    }

    public static class Builder {

        private String mQuery;
        private String mSortBy;
        private String mOrder;

        public Builder query(String query) {
            this.mQuery = query;
            return this;
        }

        public Builder sortBy(String aField) {
            this.mSortBy = aField;
            return this;
        }

        public Builder order(String aOrder) {
            this.mOrder = aOrder;
            return this;
        }

        public SortedRepositorySearchRequest build() {
            return new SortedRepositorySearchRequest(mQuery, mSortBy, mOrder);
        }
    }
}
