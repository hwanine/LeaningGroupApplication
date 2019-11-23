package com.example.leaninggroupapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class PrefManager {

    private static final String SHARED_PREF_NAME = "pref";
    private static final String KEY_EMAIL = "user_email";
    private static final String KEY_NICKNAME = "user_nickname";
    private static final String KEY_IS_LOGGED_IN = "is_logged-in";
    private static PrefManager instance;
    private static Context ctx;

    private PrefManager(Context context){

        ctx = context;
    }

    public static synchronized  PrefManager getInstance(Context context){

        if(instance ==null){
            instance = new PrefManager(context);
        }

        return instance;
    }

    public void setUserLogin(User user){

        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_NICKNAME, user.getNickname());
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.apply();
    }

    public boolean isLoggedIn(){

        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public User getUser(){

        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getString(KEY_NICKNAME, null),
                sharedPreferences.getString(KEY_EMAIL, null)
        );
    }

    public void logout() {

        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        ctx.startActivity(new Intent(ctx, LoginActivity.class));
    }
}
