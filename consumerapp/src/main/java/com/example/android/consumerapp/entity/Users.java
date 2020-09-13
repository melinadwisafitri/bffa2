package com.example.android.consumerapp.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Users implements Parcelable {
    @SerializedName("login")
    private String Login;
    @SerializedName("avatar_url")
    private String avatar_url;
    @SerializedName("url")
    private String url;
    @SerializedName("html_url")
    private String html_url;
    @SerializedName("public_gists")
    private String public_gits;
    @SerializedName("name")
    private String name;
    @SerializedName("location")
    private String location;
    @SerializedName("followers")
    private String totalfollowers;
    @SerializedName("following")
    private String totalfollowing;
    @SerializedName("company")
    private String company;
    @SerializedName("public_repos")
    private String repository;
    @SerializedName("blog")
    private String blog;
    private int id;

    protected Users(Parcel in) {
        Login = in.readString();
        avatar_url = in.readString();
        url = in.readString();
        html_url = in.readString();
        public_gits = in.readString();
        name = in.readString();
        location = in.readString();
        totalfollowers = in.readString();
        totalfollowing = in.readString();
        company = in.readString();
        repository = in.readString();
        blog = in.readString();
        id = in.readInt();
    }

    public Users(int id, String login, String html_url, String avatar_url) {
        this.Login = login;
        this.avatar_url = avatar_url;
        this.html_url = html_url;
        this.id = id;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Login);
        dest.writeString(avatar_url);
        dest.writeString(url);
        dest.writeString(html_url);
        dest.writeString(public_gits);
        dest.writeString(name);
        dest.writeString(location);
        dest.writeString(totalfollowers);
        dest.writeString(totalfollowing);
        dest.writeString(company);
        dest.writeString(repository);
        dest.writeString(blog);
        dest.writeInt(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Users> CREATOR = new Creator<Users>() {
        @Override
        public Users createFromParcel(Parcel in) {
            return new Users(in);
        }

        @Override
        public Users[] newArray(int size) {
            return new Users[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public String getPublic_gits() {
        return public_gits;
    }

    public void setPublic_gits(String public_gits) {
        this.public_gits = public_gits;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTotalfollowers() {
        return totalfollowers;
    }

    public void setTotalfollowers(String totalfollowers) {
        this.totalfollowers = totalfollowers;
    }

    public String getTotalfollowing() {
        return totalfollowing;
    }

    public void setTotalfollowing(String totalfollowing) {
        this.totalfollowing = totalfollowing;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    public String getBlog() {
        return blog;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }
}

