package com.reader.books.entify;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/7/1.
 */

public class User extends BmobObject{
    private String userPhoto;
    private String username;
    private String password;
    private String email;

    public User() {
    }

    public User(String userPhoto, String username, String password, String email) {
        this.userPhoto = userPhoto;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "userPhoto='" + userPhoto + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
