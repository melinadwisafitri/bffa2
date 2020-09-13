package com.example.android.usergithub.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SearchResponse{
    @SerializedName("items")
    private ArrayList<Users> items;

    public ArrayList<Users> getItems() {
        return items;
    }

    public void setItems(ArrayList<Users> items) {
        this.items = items;
    }
}
