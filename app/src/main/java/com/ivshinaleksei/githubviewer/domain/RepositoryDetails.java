package com.ivshinaleksei.githubviewer.domain;

import java.util.Date;

/**
 * Created by Aleksei_Ivshin on 18/02/2015.
 */
public interface RepositoryDetails extends RepositoryPreview {

    Date getCreatedDate();
    String getRepositoryUrl();
    String getDescription();

}
