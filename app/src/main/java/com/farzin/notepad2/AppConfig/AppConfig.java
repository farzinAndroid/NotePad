package com.farzin.notepad2.AppConfig;

import android.content.Context;
import android.content.SharedPreferences;

public class AppConfig {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public final static String KEY_VALUE_USER = "user";
    public final static String KEY_VALUE_PASSWORD = "password";
    public final static String KEY_VALUE_BOOT = "boot";

    public AppConfig(Context context) {

        sharedPreferences = context.getSharedPreferences("appconfig",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveUserPass(String user, String pass){

        editor.putString(KEY_VALUE_USER,user);
        editor.putString(KEY_VALUE_PASSWORD,pass);
        editor.commit();
    }

    public String saveUser(){

        return sharedPreferences.getString(KEY_VALUE_USER,"");
    }

    public String savePassword(){
        return sharedPreferences.getString(KEY_VALUE_PASSWORD,"");
    }

    public void setBoot(boolean flag){


        editor.putBoolean(KEY_VALUE_BOOT,flag);
        editor.commit();
    }

    public boolean getBoot(){
        return sharedPreferences.getBoolean(KEY_VALUE_BOOT,false);
    }
}
