package com.ivshinaleksei.githubviewer.functional;

import junit.framework.TestSuite;

public class FunctionalTestSuite extends TestSuite {

    public FunctionalTestSuite() {
        addTestSuite(MainActivityTest.class);
    }
}
