package com.example.android.usergithub.api;

import com.example.android.usergithub.entity.SearchResponse;
import com.example.android.usergithub.entity.Users;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("search/users")
        // @Headers("Authorization: ")
    Call<SearchResponse> getSearchResult(@Query("q") String q);
    @GET("users/{username}")
    Call<Users> getDetail(@Path("username") String username);
    @GET("users/{username}/followers")
    Call<List<Users>> getFollowers(@Path("username") String username);
    @GET("users/{username}/following")
    Call<List<Users>> getFollowing(@Path("username") String username);
}
