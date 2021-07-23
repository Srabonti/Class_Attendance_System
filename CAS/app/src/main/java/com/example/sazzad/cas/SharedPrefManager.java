package com.example.sazzad.cas;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Sazzad on 7/2/2017.
 */

public class SharedPrefManager {
    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private static final String SHRED_PREF_NAME = "mysharedpref12";
    private static final String KEY_USERNAME = "admin_name";
    private static final String KEY_USER_EMAIL = "admin_email";
    private static final String KEY_USER_CONTACT = "admin_contact";
    private static final String KEY_USER_ID = "admin_id";

    private static final String KEY_SEMESTER_ID = "semster_id";
    private static final String KEY_SEMESTER_NAME = "semster_name";

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public boolean userLogin(int id, String username, String email, String contact){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHRED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY_USER_ID,id);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_USER_EMAIL, email);
        editor.putString(KEY_USER_CONTACT, contact);

        editor.apply();

        return true;
    }

    public boolean adminRegistration(String username, String email, String contact){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHRED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //editor.putInt(KEY_USER_ID,id);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_USER_EMAIL, email);
        editor.putString(KEY_USER_CONTACT, contact);

        editor.apply();

        return true;
    }




    /*public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHRED_PREF_NAME, Context.MODE_PRIVATE);
        if(sharedPreferences.getString(KEY_USER_EMAIL, null) != null){
            return true; //user is logged in
        }
        return false;
    }*/

    /*public boolean logout(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHRED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear();
        editor.apply();
        return true;
    }*/

    public String getUsername(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHRED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null);
    }

    public String getUserEmail(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHRED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_EMAIL, null);
    }

    public String getUserContact(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHRED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_CONTACT, null);
    }

    public boolean insertSemesterRecord(String semester_id, String semester_name){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHRED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_SEMESTER_ID,semester_id);
        editor.putString(KEY_SEMESTER_NAME, semester_name);

        editor.apply();

        return true;
    }
}
