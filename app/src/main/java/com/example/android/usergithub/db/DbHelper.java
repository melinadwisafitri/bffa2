package com.example.android.usergithub.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import static com.example.android.usergithub.db.DbContract.UserColumn;

public class DbHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "dbUserGithub";
    public static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_USER = String.format("CREATE TABLE %s"
    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL)",
            UserColumn.TABLE_NAME,
            UserColumn._ID,
            UserColumn.USERNAME,
            UserColumn.HTML_URL,
            UserColumn.AVATAR_URL);

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UserColumn.TABLE_NAME);
        onCreate(db);
    }
}
