package com.ivshinaleksei.githubviewer.functional;

import android.test.ActivityInstrumentationTestCase2;

import com.ivshinaleksei.githubviewer.MainActivity;
import com.robotium.solo.Solo;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private Solo solo;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }
    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

    public void testCheckActivity() throws Exception {
        solo.assertCurrentActivity("wrong activity", MainActivity.class);
    }

}
