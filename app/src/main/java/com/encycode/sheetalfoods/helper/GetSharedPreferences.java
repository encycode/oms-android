package com.encycode.sheetalfoods.helper;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class GetSharedPreferences extends Application  {
    private static Context context;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editorprefs;

    public GetSharedPreferences() {
    }
    @SuppressLint("CommitPrefEdits")
    public GetSharedPreferences(String prefName,Context c) {
        GetSharedPreferences.context = c;
        prefs = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        editorprefs=prefs.edit();
    }

    public void onCreate() {
        super.onCreate();
        GetSharedPreferences.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return GetSharedPreferences.context;
    }

    public String getPrefString(String prefKey) {
        return prefs.getString(prefKey,"");
    }
    public Boolean getPrefBoolean(String prefKey) {
        return prefs.getBoolean(prefKey,false);
    }
    public int getPrefInt(String prefKey) {
        return prefs.getInt(prefKey,0);
    }

    public void setPrefString(String prefKey,String prefValue) {
        editorprefs.putString(prefKey,prefValue);
        editorprefs.apply();
        editorprefs.commit();
    }
    public void setPrefBoolean(String prefKey,Boolean prefValue) {
        editorprefs.putBoolean(prefKey,prefValue);
        editorprefs.apply();
        editorprefs.commit();
    }
    public void setPrefInt(String prefKey,int prefValue) {
        editorprefs.putInt(prefKey,prefValue);
        editorprefs.apply();
        editorprefs.commit();
    }
}
