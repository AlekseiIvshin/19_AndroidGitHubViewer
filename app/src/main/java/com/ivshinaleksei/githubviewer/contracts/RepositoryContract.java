package com.ivshinaleksei.githubviewer.contracts;

import android.net.Uri;
import android.provider.BaseColumns;

public class RepositoryContract {

    public static final String AUTHORITY = "com.ivshinaleksei.githubviewer.provider";
    public static final String DATABASE_NAME = "githubviewer.db";
    public static final int DATABASE_VERSION = 14;

    public static class RepositoryInfo implements BaseColumns {
        public static final String PATH = "repository";
        public static final Uri CONTENT_URI =
                Uri.parse("content://" + AUTHORITY + "//" + PATH);
        public static final String TABLE_NAME = "repository";

        public static final String FULL_NAME = "fullName";
        public static final String LANGUAGE = "language";
        public static final String STARGAZERS_COUNT = "stargazersCount";
        public static final String CREATED_DATE = "createdDate";
        public static final String DESCRIPTION = "description";
    }

    public static class RepositoryOwner implements BaseColumns {
        public static final String PATH = "repositoryOwner";
        public static final Uri CONTENT_URI =
                Uri.parse("content://" + AUTHORITY + "//" + PATH);

        public static final String TABLE_NAME = "repositoryOwner";
        public static final String OWNER_LOGIN = "ownerLogin";
        public static final String OWNER_AVATAR_URL = "ownerAvatarUrl";
    }

}
