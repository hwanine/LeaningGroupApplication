package com.example.leaninggroupapplication;

public class AttendingUser {

    private String nickname, email, school_number, real_name;
    private Integer warning_count;

    public AttendingUser(String email, String nickname, String school_number, String real_name, Integer warning_count) {
        this.email = email;
        this.nickname = nickname;
        this.school_number = school_number;
        this.real_name = real_name;
        this.warning_count = warning_count;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getSchool_number() {
        return school_number;
    }

    public String getReal_name() {
        return real_name;
    }

    public Integer getWarning_count() {
        return warning_count;
    }

}
