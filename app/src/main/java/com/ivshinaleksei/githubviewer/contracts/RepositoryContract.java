package com.ivshinaleksei.githubviewer.contracts;

import android.net.Uri;
import android.provider.BaseColumns;

public class RepositoryContract {

    public static final String AUTHORITY = "com.ivshinaleksei.githubviewer.provider";
    public static final String DATABASE_NAME = "githubviewer.db";
    public static final int DATABASE_VERSION = 17;

    public static class RepositoryInfo implements BaseColumns {
        public static final String PATH = "repository";
        public static final Uri CONTENT_URI =
                Uri.parse("content://" + AUTHORITY + "//" + PATH);
        public static final String TABLE_NAME = "repository";

        public static final String DIR_TYPE = "vnd.cursor.dir/"+AUTHORITY+"/"+PATH;

        public static final String FULL_NAME = "fullName";
        public static final String LANGUAGE = "language";
        public static final String STARGAZERS_COUNT = "stargazersCount";
        public static final String CREATED_DATE = "createdDate";
        public static final String DESCRIPTION = "description";
        public static final String OWNER_ID = "repositoryowner_id";
    }

    public static class RepositoryOwner implements BaseColumns {
        public static final String PATH = "repositoryOwner";
        public static final Uri CONTENT_URI =
                Uri.parse("content://" + AUTHORITY + "//" + PATH);

        public static final String DIR_TYPE = "vnd.cursor.dir/"+AUTHORITY+"/"+PATH;

        public static final String TABLE_NAME = RepositoryInfo.TABLE_NAME;
        public static final String OWNER_LOGIN = "ownerLogin";
        public static final String OWNER_AVATAR_URL = "ownerAvatarUrl";
    }

    public static class Comment implements BaseColumns {
        public static final String PATH = "comment";
        public static final Uri CONTENT_URI =
                Uri.parse("content://" + AUTHORITY + "//" + PATH);
        public static final String TABLE_NAME = "comment";

        public static final String DIR_TYPE = "vnd.cursor.dir/"+AUTHORITY+"/"+PATH;

        public static final String TITLE = "title";
        public static final String MESSAGE = "message";
        public static final String CREATED_DATE = "createdDate";

    }


}
