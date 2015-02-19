package com.ivshinaleksei.githubviewer.domain;

import android.os.Parcelable;

public interface RepositoryFullInfo extends RepositoryDetails,Parcelable {
    RepositoryOwner getOwner();
}
