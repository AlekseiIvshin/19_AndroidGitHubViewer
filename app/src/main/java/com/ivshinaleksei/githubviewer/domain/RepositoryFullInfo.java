package com.ivshinaleksei.githubviewer.domain;

/**
 * Created by Aleksei_Ivshin on 18/02/2015.
 */
public interface RepositoryFullInfo extends RepositoryDetails {
    RepositoryOwner getOwner();
}
