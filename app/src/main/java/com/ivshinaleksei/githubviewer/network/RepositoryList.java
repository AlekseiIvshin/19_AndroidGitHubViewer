package com.ivshinaleksei.githubviewer.network;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ivshinaleksei.githubviewer.domain.RepositoryFullInfo;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RepositoryList {

    public ArrayList<RepositoryFullInfo> items;

    public RepositoryList(){
        items = new ArrayList<>();
    }
}
