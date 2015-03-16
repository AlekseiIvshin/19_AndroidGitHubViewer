package com.ivshinaleksei.githubviewer.unit;

import com.ivshinaleksei.githubviewer.network.request.SortedRepositorySearchRequest;

import junit.framework.TestCase;
/**
 * Created by dnss on 16.03.2015.
 */
public class SomeUnitTest extends TestCase {

    public void testShouldCreateNewRequestInstance(){
        SortedRepositorySearchRequest.Builder builder = SortedRepositorySearchRequest.newInstance();
        assertNotNull("Builder should be not null", builder);

        SortedRepositorySearchRequest request = builder.order("asc").query("query").sortBy("sortBy").build();
        assertNotNull("Builder should create not null request", request);
    }
}
