package com.example.leaninggroupapplication;

public class GroupList {
    private String group_num;
    private String group_name;
    private String group_type;
    private String group_date;
    private String group_time;

    public GroupList(String group_num, String group_name, String group_type, String group_date, String group_time) {
        this.group_num = group_num;
        this.group_name = group_name;
        this.group_type = group_type;
        this.group_date = group_date;
        this.group_time = group_time;
    }

    public String getGroup_num() {
        return group_num;
    }

    public String getGroup_name() {
        return group_name;
    }

    public String getGroup_type() {
        return group_type;
    }

    public String getGroup_date() {
        return group_date;
    }

    public String getGroup_time() {
        return group_time;
    }

    public void setGroup_num(String group_num) {
        this.group_num = group_num;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public void setGroup_type(String group_type) {
        this.group_type = group_type;
    }

    public void setGroup_date(String group_date) {
        this.group_date = group_date;
    }

    public void setGroup_time(String group_time) {
        this.group_time = group_time;
    }

    @Override
    public String toString() {
        return "Group{" +
                "group_num='" + group_num + '\'' +
                ", group_name='" + group_name + '\'' +
                ", group_type='" + group_type + '\'' +
                ", group_date='" + group_date + '\'' +
                ", group_time='" + group_time + '\'' +
                '}';
    }
}