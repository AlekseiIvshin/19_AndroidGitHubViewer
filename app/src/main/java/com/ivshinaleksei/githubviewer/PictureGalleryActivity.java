package com.ivshinaleksei.githubviewer;

import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.ivshinaleksei.githubviewer.ui.gallery.MyPagerAdapter;


public class PictureGalleryActivity extends ActionBarActivity {

    private static final String sPreferencesFilename = PictureGalleryActivity.class.getSimpleName()+".prefs";
    private static final String sLastOpenedPicturePosition = PictureGalleryActivity.class.getSimpleName()+".lastOpened";

    private MyPagerAdapter mPagerAdapter;
    private ViewPager mViewPager;
    private int mLastOpenedPicturePosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_gallery);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_picture_gallery, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
