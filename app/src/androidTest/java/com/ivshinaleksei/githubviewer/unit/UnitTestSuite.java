package com.ivshinaleksei.githubviewer.unit;

import junit.framework.TestSuite;

/**
 * Created by dnss on 16.03.2015.
 */
public class UnitTestSuite extends TestSuite {
    public UnitTestSuite(){
        addTest(new SomUnitTest());
    }
}
