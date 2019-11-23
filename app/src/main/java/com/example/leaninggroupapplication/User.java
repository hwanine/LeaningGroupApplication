package com.example.leaninggroupapplication;

public class User {

    private String nickname, email;
    private int id;

    public User(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
    }
    public String getNickname() {
        return nickname;
    }
    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }
}
