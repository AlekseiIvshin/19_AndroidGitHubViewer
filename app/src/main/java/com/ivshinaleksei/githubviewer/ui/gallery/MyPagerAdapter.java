package com.ivshinaleksei.githubviewer.ui.gallery;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ivshinaleksei.githubviewer.domain.RepositoryOwner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aleksei_Ivshin on 26/02/2015.
 */
public class MyPagerAdapter extends FragmentStatePagerAdapter {

    List<RepositoryOwner> owners = new ArrayList<>();

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
        owners.add(new RepositoryOwner("login0","avatarUrl0"));
        owners.add(new RepositoryOwner("login1","avatarUrl1"));
        owners.add(new RepositoryOwner("login2","avatarUrl2"));
        owners.add(new RepositoryOwner("login3","avatarUrl3"));
        owners.add(new RepositoryOwner("login4","avatarUrl4"));
    }

    @Override
    public Fragment getItem(int position) {
        return MyGalleryObjectFragment.newInstance(owners.get(position).avatarUrl);
    }

    @Override
    public int getCount() {
        // TODO: stub
        return owners.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // TODO: get picture name
        return owners.get(position).login;
    }

    public boolean isEmpty(){
        return getCount() == 0;
    }
}
