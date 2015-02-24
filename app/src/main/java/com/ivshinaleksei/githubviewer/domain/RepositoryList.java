package com.ivshinaleksei.githubviewer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RepositoryList {

    public ArrayList<RepositoryInfo> items;

    public RepositoryList() {
        items = new ArrayList<>();
    }
}
