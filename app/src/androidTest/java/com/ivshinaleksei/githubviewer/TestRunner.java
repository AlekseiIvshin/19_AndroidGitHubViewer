package com.ivshinaleksei.githubviewer;

import android.test.InstrumentationTestRunner;

public class TestRunner extends InstrumentationTestRunner {

    @Override
    public junit.framework.TestSuite getAllTests() {
        return new MainTestSuite();
    }

    @Override
    public ClassLoader getLoader() {
        return TestRunner.class.getClassLoader();
    }
}
