package com.example.android.usergithub.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.example.android.usergithub.db.DbContract.UserColumn.TABLE_NAME;

public class UserHelper {
    private static final String DATABASE_TABLE = TABLE_NAME;
    private static DbHelper dbHelper;
    private static UserHelper INSTANCE;
    private static SQLiteDatabase database;

    public UserHelper(Context context){
        dbHelper = new DbHelper(context);
    }
    public static UserHelper getInstance(Context context){
        if (INSTANCE == null){
            synchronized (SQLiteOpenHelper.class){
                if (INSTANCE == null){
                    INSTANCE = new UserHelper(context);
                }
            }
        }
        return INSTANCE;
    }
    public void open() throws SQLException{
        database = dbHelper.getWritableDatabase();
    }
    public void close() {
        dbHelper.close();
        if (database.isOpen()){
            database.close();
        }
    }
    public Cursor queryAll(){
        return database.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC");
    }
    public Cursor queryById(String id){
        return database.query(
                DATABASE_TABLE,
                null,
                _ID + " = ?",
                new String[]{id},
                null,
                null,
                null,
                null
        );
    }
    public long insert(ContentValues values){
        return database.insert(DATABASE_TABLE, null, values);
    }
    public int deleteById(String id){
        return database.delete(DATABASE_TABLE, _ID+"=?", new String[]{id});
    }
    public int deleteByUsername(String username){
        return database.delete(DATABASE_TABLE, DbContract.UserColumn.USERNAME+"=?", new String[]{username});
    }
}
