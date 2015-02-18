package com.ivshinaleksei.githubviewer.domain;

public class RepositoryPreviewImpl implements RepositoryPreview {

    private final String fullName;
    private final String language;
    private final int stargazersCount;

    public RepositoryPreviewImpl(String fullName, String language, int stargazersCount) {
        this.fullName = fullName;
        this.language = language;
        this.stargazersCount = stargazersCount;
    }


    @Override
    public String getFullName() {
        return fullName;
    }

    @Override
    public String getLanguage() {
        return language;
    }

    @Override
    public int getStargazersCount() {
        return stargazersCount;
    }
}
