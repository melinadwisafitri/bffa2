package com.example.android.usergithub.helper;

import android.database.Cursor;

import com.example.android.usergithub.entity.Users;
import java.util.ArrayList;
import static com.example.android.usergithub.db.DbContract.UserColumn;


public class MappingHelper {
    public static ArrayList<Users> mapCursorArrayList(Cursor userCursor){
        ArrayList<Users> userslist = new ArrayList<>();
        while (userCursor.moveToNext()){
            int id = userCursor.getInt(userCursor.getColumnIndexOrThrow(UserColumn._ID));
            String Login = userCursor.getString(userCursor.getColumnIndexOrThrow(UserColumn.USERNAME));
            String html_url = userCursor.getString(userCursor.getColumnIndexOrThrow(UserColumn.HTML_URL));
            String avatar_url = userCursor.getString(userCursor.getColumnIndexOrThrow(UserColumn.AVATAR_URL));
            userslist.add(new Users(id, Login, html_url, avatar_url));
        }
        return userslist;
    }
    }

