package com.example.loginandregister;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private final static String PREFS_NAME = "mon_fichierXml";
    private final static int PRIVATE_MODE = Context.MODE_PRIVATE;
    private final static String IS_LOGGED = "isLogged";
    private final static String LOGIN = "CléLogin";
    private final static String ID = "CLéId";
    private final static String EMAIL = "CLéEmail";
    public SessionManager(Context context){
        this.context = context;
        preferences = context.getSharedPreferences(PREFS_NAME,PRIVATE_MODE);
        editor = preferences.edit();
    }
    public Boolean isLogged(){
        return preferences.getBoolean(IS_LOGGED,false);
    }
    public String getLogin(){
        return preferences.getString(LOGIN,null);
    }
    public String  getId(){
        return  preferences.getString(ID,null);
    }
    public  String getEmail(){
        return preferences.getString(EMAIL,null);
    }
    public void insertUser(String id, String login, String email){
        editor.putBoolean(IS_LOGGED,true);
        editor.putString(ID,id);
        editor.putString(EMAIL,email);
        editor.putString(LOGIN,login);
        editor.apply();
    }
    public void logout(){
        editor.clear().apply();
    }
}
