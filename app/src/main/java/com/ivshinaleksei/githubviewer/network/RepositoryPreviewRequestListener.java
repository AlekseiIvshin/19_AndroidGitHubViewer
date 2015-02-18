package com.ivshinaleksei.githubviewer.network;

import android.util.Log;
import android.widget.Toast;

import com.ivshinaleksei.githubviewer.R;
import com.ivshinaleksei.githubviewer.domain.RepositoryPreviewList;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

public final class RepositoryPreviewRequestListener implements RequestListener<RepositoryPreviewList> {
    @Override
    public void onRequestFailure(SpiceException spiceException) {
        Log.v("RepositoryPreviewRequestListener","Failure");
    }

    @Override
    public void onRequestSuccess(RepositoryPreviewList repositoryPreviews) {
        Log.v("RepositoryPreviewRequestListener","Return result is null: "+(repositoryPreviews==null));
        if(repositoryPreviews!=null) {
            Log.v("RepositoryPreviewRequestListener", "Return result size: " + repositoryPreviews.items.size());
        }
    }
}