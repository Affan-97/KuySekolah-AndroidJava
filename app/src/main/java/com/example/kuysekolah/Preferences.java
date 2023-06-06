package com.example.kuysekolah;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preferences {

    static final String KEY_USER_REGISTERED ="user", KEY_PASS_REGISTERED="pass";
    static final String KEY_EMAIL_LOGGEDIN = "Email_logged_in";
    static final String KEY_STATUS_LOGGEDIN = "Status_logged_in";
    static final String KEY_NAME_REGISTERED ="name";
    static final String KEY_NAME_lOGGEDIN ="name_logged_in";
    static final String KEY_KELAS_REGISTERED="kelas";
    static final String sharedPrefFile = "com.example.kuysekolah";


    public static SharedPreferences getSharedPreference(Context context) {
        return context.getSharedPreferences(sharedPrefFile,Context.MODE_PRIVATE);
    }


    public static void setRegisteredUser(Context context, String user){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_USER_REGISTERED, user);
        editor.apply();
    }

    public static String getRegisteredUser(Context context){
        return getSharedPreference(context).getString(KEY_USER_REGISTERED,"");
    }


    public static void setRegisteredPass(Context context, String password){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_PASS_REGISTERED, password);
        editor.apply();
    }

    public static String getRegisteredPass(Context context){
        return getSharedPreference(context).getString(KEY_PASS_REGISTERED,"");
    }
    public static void  setRegisteredName(Context context,String nama){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_NAME_REGISTERED, nama);
        editor.apply();
    }
    public static String getRegisteredName(Context context){
        return getSharedPreference(context).getString(KEY_NAME_REGISTERED,"");
    }
    public static void  setRegisteredClass(Context context,String kelas){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_KELAS_REGISTERED,kelas);
        editor.apply();
    }

  public static String getRegisteredClass(Context context){
        return getSharedPreference(context).getString(KEY_KELAS_REGISTERED,"");
    }



    public static void setLoggedInUser(Context context, String username){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_EMAIL_LOGGEDIN, username);
        editor.apply();
    }

    public static String getLoggedInUser(Context context){
        return getSharedPreference(context).getString(KEY_EMAIL_LOGGEDIN,"");
    }


    public static void setLoggedInStatus(Context context, boolean status){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putBoolean(KEY_STATUS_LOGGEDIN,status);
        editor.apply();
    }

    public static boolean getLoggedInStatus(Context context){
        return getSharedPreference(context).getBoolean(KEY_STATUS_LOGGEDIN,false);
    }


    public static void clearLoggedInUser (Context context){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.remove(KEY_EMAIL_LOGGEDIN);
        editor.remove(KEY_STATUS_LOGGEDIN);
        editor.apply();
    }
}
