package com.ivshinaleksei.githubviewer.contracts;

import android.net.Uri;
import android.provider.BaseColumns;

public class RepositoryContract {

    public static final String AUTHORITY = "com.ivshinaleksei.githubviewer.provider";
    public static final String PATH = "repositories";
    public static final Uri CONTENT_URI =
            Uri.parse("content://" + AUTHORITY + "//" + PATH);

    public static class Columns implements BaseColumns {
        public static final String FULL_NAME = "fullName";
        public static final String LANGUAGE = "language";
        public static final String STARGAZERS_COUNT = "stargazersCount";
        public static final String CREATED_DATE = "createdDate";
        public static final String DESCRIPTION = "description";
        public static final String REPOSITORY_URL = "repositoryUrl";
        public static final String OWNER_LOGIN = "ownerLogin";
        public static final String OWNER_AVATAR_URL = "ownerAvatarUrl";
        public static final String OWNER_URL = "ownerUrl";
    }
}
