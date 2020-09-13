package com.example.android.usergithub.db;

import android.net.Uri;
import android.provider.BaseColumns;

public class DbContract {
    public static final String AUTHORITY = "com.example.android.usergithub";
    public static final String SCHEME = "content";

    public static final class UserColumn implements BaseColumns{
        public static String TABLE_NAME = "user";
        public static String USERNAME = "username";
        public static String HTML_URL = "html_url";
        public static String AVATAR_URL = "avatar_url";
        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();
    }
}
