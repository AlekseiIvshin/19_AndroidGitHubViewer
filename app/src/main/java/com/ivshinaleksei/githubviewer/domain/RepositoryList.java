package com.ivshinaleksei.githubviewer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ivshinaleksei.githubviewer.contracts.RepositoryContract;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.Collection;

@Root
@JsonIgnoreProperties(ignoreUnknown = true)
public class RepositoryList {
    @DatabaseField(generatedId = true)
    public int id;

    @ForeignCollectionField(eager = false)
    @ElementList(inline = true, entry = RepositoryContract.RepositoryInfo.TABLE_NAME,required = false)
    private Collection<RepositoryInfo> items;

    public Collection<RepositoryInfo> getItems() {
        return items;
    }

    public void setItems(Collection<RepositoryInfo> items) {
        this.items = items;
    }
}
