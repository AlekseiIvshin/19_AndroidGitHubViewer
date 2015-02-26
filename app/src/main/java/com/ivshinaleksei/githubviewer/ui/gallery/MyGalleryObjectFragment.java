package com.ivshinaleksei.githubviewer.ui.gallery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ivshinaleksei.githubviewer.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Aleksei_Ivshin on 26/02/2015.
 */
public class MyGalleryObjectFragment extends Fragment {

    private static String sImageUrlArgument = MyGalleryObjectFragment.class.getName() + ".imageurl";

    private static DisplayImageOptions sDisplayImageOptions = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.drawable.github_mark)
            .showImageOnFail(R.drawable.github_mark)
            .cacheInMemory(true)
            .cacheOnDisc(true)
            .build();


    public static MyGalleryObjectFragment newInstance(String imageUrl) {
        Bundle args = new Bundle();
        args.putString(sImageUrlArgument, imageUrl);
        MyGalleryObjectFragment fragment = new MyGalleryObjectFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_page_gallery, container, false);
        ImageView imageView = (ImageView) rootView.findViewById(R.id.imageView);
        Bundle args = getArguments();
        if (args != null) {
            String imageUrl = args.getString(sImageUrlArgument);
            ImageLoader.getInstance().displayImage(imageUrl, imageView, sDisplayImageOptions);
        }
        return rootView;
    }
}
