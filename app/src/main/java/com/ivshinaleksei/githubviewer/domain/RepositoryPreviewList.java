package com.ivshinaleksei.githubviewer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Created by Aleksei_Ivshin on 18/02/2015.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class RepositoryPreviewList{

    @JsonProperty("items")
    public ArrayList<RepositoryPreviewImpl> items;
}
