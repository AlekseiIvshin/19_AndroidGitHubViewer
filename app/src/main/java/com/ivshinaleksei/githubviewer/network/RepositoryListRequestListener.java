package com.ivshinaleksei.githubviewer.network;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.ivshinaleksei.githubviewer.R;
import com.ivshinaleksei.githubviewer.contracts.RepositoryContract;
import com.ivshinaleksei.githubviewer.domain.RepositoryFullInfo;
import com.ivshinaleksei.githubviewer.domain.RepositoryPreviewImpl;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

public final class RepositoryListRequestListener implements RequestListener<RepositoryList> {
    private final ContentResolver contentResolver;

    // TODO: May be it reduce robospice power
    public RepositoryListRequestListener(ContentResolver resolver){
        super();
        contentResolver = resolver;
    }

    @Override
    public void onRequestFailure(SpiceException spiceException) {
        Log.v("RepositoryPreviewRequestListener","Failure");
    }

    @Override
    public void onRequestSuccess(RepositoryList repositoryPreviews) {
        ContentValues[] data = new ContentValues[repositoryPreviews.items.size()];
        for(int i = 0;i<data.length;i++){
            data[i]=map(repositoryPreviews.items.get(i));
        }
        int count = contentResolver.bulkInsert(RepositoryContract.CONTENT_URI,data);
        Log.v("RepositoryListRequestListener","Inserted: "+count);
    }

    private ContentValues map(RepositoryFullInfo info){
        ContentValues values = new ContentValues();
        values.put(RepositoryContract.Columns.FULL_NAME,info.getFullName());
        values.put(RepositoryContract.Columns.LANGUAGE,info.getLanguage());
        values.put(RepositoryContract.Columns.STARGAZERS_COUNT,info.getStargazersCount());
        values.put(RepositoryContract.Columns.CREATED_DATE,info.getCreatedDate().getTime()/1000);
        values.put(RepositoryContract.Columns.DESCRIPTION,info.getDescription());
        values.put(RepositoryContract.Columns.REPOSITORY_URL,info.getRepositoryUrl());
        values.put(RepositoryContract.Columns.OWNER_LOGIN,info.getOwner().getOwnerLogin());
        values.put(RepositoryContract.Columns.OWNER_AVATAR_URL,info.getOwner().getOwnerAvatarUrl());
        values.put(RepositoryContract.Columns.OWNER_URL,info.getOwner().getOwnerUrl());
        return values;
    }
}