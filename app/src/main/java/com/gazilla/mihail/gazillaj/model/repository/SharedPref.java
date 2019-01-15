package com.gazilla.mihail.gazillaj.model.repository;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {

    private static final String APP_PREFERENCES = "myProf";

    private static final String APP_PREF_PRIVATE_KEY = "privateKey";
    private static final String APP_PREF_PUBLIC_KEY = "publicKey";

    private static final String APP_PREF_MY_ID = "myID";
    private static final String APP_PREF_MY_NAME = "myName";
    private static final String APP_PREF_MY_PASS = "myPassword";
    private static final String APP_PREF_MY_PHONE = "myPhone";
    private static final String APP_PREF_MY_EMAIL = "myEmail";

    private static final String APP_PREF_VERSION_MENU_CATEGORY = "versionMenuCategory";
    private static final String APP_PREF_VERSION_MENU_ITEM = "versionMenuItem";
    private static final String APP_PREF_VERSION_PROMO = "versionPromo";


    private SharedPreferences myPref;
    private SharedPreferences.Editor editor;

    public SharedPref(Context context) {
        myPref = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

    }


    public boolean myPreff(){
        return myPref.contains(APP_PREF_MY_ID);
    }


    //_______________________Ключи_______________________________-

    @SuppressLint("CommitPrefEdits")
    public void saveNewPublicKey(String publicKey){
        editor = myPref.edit();
        editor.putString(APP_PREF_PUBLIC_KEY, publicKey);
        editor.commit();
    }

    public String getPublickKeyFromPref(){
        if(myPref.contains(APP_PREF_PUBLIC_KEY)){
            return myPref.getString(APP_PREF_PUBLIC_KEY,  "");
        }
        return "";
    }

    @SuppressLint("CommitPrefEdits")
    public void saveNewPrivateKey(String privateKey){
        editor = myPref.edit();
        editor.putString(APP_PREF_PRIVATE_KEY, privateKey);
        editor.commit();
    }

    public String getPrivateKeyFromPref(){
        if(myPref.contains(APP_PREF_PRIVATE_KEY)){
            return myPref.getString(APP_PREF_PRIVATE_KEY,  "");
        }
        return "";
    }



    //_______________________Инфо о пользователе_____________________

    @SuppressLint("CommitPrefEdits")
    public void saveNewId(int id){
        editor = myPref.edit();
        editor.putInt(APP_PREF_MY_ID, id);
        editor.commit();
    }

    public int getIdFromPref(){
        if(myPref.contains(APP_PREF_MY_ID)){
            return myPref.getInt(APP_PREF_MY_ID,  -1);
        }
        return 0;
    }

    public void saveName(String name){
        editor = myPref.edit();
                editor.putString(APP_PREF_MY_NAME, name).commit();
    }

    public String getNameFromPref(){
        if(myPref.contains(APP_PREF_MY_ID)){
            return myPref.getString(APP_PREF_MY_NAME, "");
        }
        return "";
    }

    public void saveMyPhone(String phone){
        editor = myPref.edit();
        editor.putString(APP_PREF_MY_PHONE, phone).commit();
    }

    public String getPhoneFromPref(){
        if(myPref.contains(APP_PREF_MY_ID)){
            return myPref.getString(APP_PREF_MY_PHONE, "");
        }
        return "";
    }

    public void saveMyEmail(String email){
        editor = myPref.edit();
        editor.putString(APP_PREF_MY_EMAIL, email).commit();
    }

    public String getEmailFromPref(){
        if(myPref.contains(APP_PREF_MY_ID)){
            return myPref.getString(APP_PREF_MY_EMAIL, "");
        }
        return "";
    }

    //_______________________Версии баз данных_______________________


    @SuppressLint("CommitPrefEdits")
    public void saveVersionMenuCategory(String ver){
        editor = myPref.edit();
        editor.putString(APP_PREF_VERSION_MENU_CATEGORY, ver);
        editor.commit();
    }

    public String getVersionMenuCategory(){
        if(myPref.contains(APP_PREF_VERSION_MENU_CATEGORY))
            return myPref.getString(APP_PREF_VERSION_MENU_CATEGORY, "");
        return "";
    }

    @SuppressLint("CommitPrefEdits")
    public void saveVersionMenuItem(String ver){
        editor = myPref.edit();
        editor.putString(APP_PREF_VERSION_MENU_ITEM, ver);
        editor.commit();
    }

    public String getVersionMenuItem(){
        if(myPref.contains(APP_PREF_VERSION_MENU_ITEM))
            return myPref.getString(APP_PREF_VERSION_MENU_ITEM, "");
        return "";
    }

    @SuppressLint("CommitPrefEdits")
    public void saveVersionPromo(String ver){
        editor = myPref.edit();
        editor.putString(APP_PREF_VERSION_PROMO, ver);
        editor.commit();
    }

    public String getVersionPromo(){
        if(myPref.contains(APP_PREF_VERSION_PROMO))
            return myPref.getString(APP_PREF_VERSION_PROMO, "");
        return "";
    }









}
