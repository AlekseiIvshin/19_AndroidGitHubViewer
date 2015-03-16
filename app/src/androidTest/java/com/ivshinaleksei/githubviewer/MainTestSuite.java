package com.ivshinaleksei.githubviewer;

import com.ivshinaleksei.githubviewer.functional.FunctionalTestSuite;
import com.ivshinaleksei.githubviewer.unit.UnitTestSuite;

public class MainTestSuite extends junit.framework.TestSuite {

    public MainTestSuite() {
//        addTest(new FunctionalTestSuite());
        addTest(new UnitTestSuite());
    }
}
