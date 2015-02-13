package com.learning.githubviewer.domain;

import java.util.Date;

/**
 * Created by Aleksei_Ivshin on 13/02/2015.
 */
public class RepositoryDetails extends RepositoryView {
    public final RepositoryOwner owner;
    public final Date createdDate;
    public final String language;
    public final String fullName;

    public RepositoryDetails(RepositoryView listView, RepositoryOwner owner, Date createdDate, String language, String fullName) {
        super(listView);
        this.owner = owner;
        this.createdDate = createdDate;
        this.language = language;
        this.fullName = fullName;
    }
}
