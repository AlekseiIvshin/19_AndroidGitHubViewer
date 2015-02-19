package com.ivshinaleksei.githubviewer.domain;

import java.util.Date;

public interface RepositoryDetails extends RepositoryPreview {

    Date getCreatedDate();
    String getRepositoryUrl();
    String getDescription();

}
