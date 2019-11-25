package com.example.leaninggroupapplication;

public class GroupList {
    private String group_num;
    private String group_name;
    private String group_type;
    private String group_date;
    private String group_starttime;
    private String group_endtime;

    public GroupList(String group_num, String group_name, String group_type, String group_date, String group_starttime, String group_endtime) {
        this.group_num = group_num;
        this.group_name = group_name;
        this.group_type = group_type;
        this.group_date = group_date;
        this.group_starttime = group_starttime;
        this.group_endtime = group_endtime;
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

    public String getGroup_starttime() {
        return group_starttime;
    }
    public String getGroup_endtime() {
        return group_endtime;
    }

    public void setGroup_num(String group_num) {
        this.group_num = group_num;
    }

    public void setGroup_name(String group_name) { this.group_name = group_name; }

    public void setGroup_type(String group_type) {
        this.group_type = group_type;
    }

    public void setGroup_date(String group_date) {
        this.group_date = group_date;
    }

    public void setGroup_starttime(String group_starttime) {
        this.group_starttime = group_starttime;
    }
    public void setGroup_endtime(String group_endtime) {
        this.group_endtime = group_endtime;
    }

    @Override
    public String toString() {
        return "Group{" +
                "group_num='" + group_num + '\'' +
                ", group_name='" + group_name + '\'' +
                ", group_type='" + group_type + '\'' +
                ", group_date='" + group_date + '\'' +
                ", group_starttime='" + group_starttime + '\'' +
                ", group_endtime='" + group_endtime + '\'' +
                '}';
    }
}