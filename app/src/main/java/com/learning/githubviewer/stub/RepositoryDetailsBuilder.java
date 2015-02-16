package com.learning.githubviewer.stub;

import com.learning.githubviewer.domain.RepositoryDetails;
import com.learning.githubviewer.domain.RepositoryView;
import com.learning.githubviewer.domain.RepositoryOwner;

import java.util.Date;

/**
 * Created by Aleksei_Ivshin on 13/02/2015.
 */
public class RepositoryDetailsBuilder {
    public static RepositoryDetailsBuilder newRepositoryDetails() {
        return new RepositoryDetailsBuilder();
    }

    private int id = 0;
    private String repoUrl = "";
    private String repoName = "";
    private int stars = 0;
    private Date createdDate;
    private String language = "";
    private String fullName = "";
    private String description="";
    private RepositoryOwner owner;

    private RepositoryDetailsBuilder() {
    }

    public RepositoryDetailsBuilder repositoryUrl(String url) {
        this.repoUrl = url;
        return this;
    }

    public RepositoryDetailsBuilder repositoryName(String name) {
        this.repoName = name;
        return this;
    }

    public RepositoryDetailsBuilder stars(int stars) {
        this.stars = stars;
        return this;
    }

    public RepositoryDetailsBuilder owner(RepositoryOwner owner) {
        this.owner = owner;
        return this;
    }

    public RepositoryDetailsBuilder createdDate(Date createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public RepositoryDetailsBuilder language(String language) {
        this.language = language;
        return this;
    }

    public RepositoryDetailsBuilder fullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public RepositoryDetailsBuilder id(int id){
        this.id=id;
        return this;
    }

    public RepositoryDetailsBuilder description(String description){
        this.description = description;
        return this;
    }

    public RepositoryDetails build() {
        RepositoryView view = RepositoryViewBuilder.newRepositoryView().id(id).repositoryName(repoName).repositoryUrl(repoUrl).stars(stars).description(description).build();
        return new RepositoryDetails(view, owner, createdDate, language, fullName);
    }
}