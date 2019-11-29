package com.example.leaninggroupapplication;

public class URLS {

    private static final String URL_ROOT = "http://ec2-13-209-49-31.ap-northeast-2.compute.amazonaws.com/";
    public static final String URL_REGISTER = URL_ROOT + "api.php?action=signup";
    public static final String URL_LOGIN = URL_ROOT + "api.php?action=login";
    public static final String URL_AUTH = URL_ROOT + "phpmail.php";
    public static final String URL_AVAIL_AUTH = URL_ROOT + "api.php?action=authentication";
    public static final String URL_FIND_EMAIL = URL_ROOT + "api.php?action=find_email";
    public static final String URL_CHANGE_PASSWD = URL_ROOT + "api.php?action=change_passwd";
    public static final String URL_NEW_PASSWD = URL_ROOT + "api.php?action=new_passwd";

}
