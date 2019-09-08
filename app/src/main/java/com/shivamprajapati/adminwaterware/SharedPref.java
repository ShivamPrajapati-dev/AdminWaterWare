package com.shivamprajapati.adminwaterware;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    public  static  String filename="helper";

    public static String readSavedSettingsPartnesList(Context context,String settingName,String defaultValue){
        SharedPreferences sharedPreferences= context.getSharedPreferences(filename ,Context.MODE_PRIVATE);
        return sharedPreferences.getString(settingName,defaultValue);
    }
    public static String readSavedSettingsCleaningCharges(Context context,String settingName,String defaultValue){
        SharedPreferences sharedPreferences= context.getSharedPreferences(filename ,Context.MODE_PRIVATE);
        return sharedPreferences.getString(settingName,defaultValue);
    }
    public static String readSavedSettingsVisitingCharges(Context context,String settingName,String defaultValue){
        SharedPreferences sharedPreferences= context.getSharedPreferences(filename ,Context.MODE_PRIVATE);
        return sharedPreferences.getString(settingName,defaultValue);
    }

    public static void saveSharedSettingsPartnersList(Context context,String settingName,String settingValue){
        SharedPreferences sharedPreferences=context.getSharedPreferences(filename,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(settingName,settingValue).commit();
    }
    public static void saveSharedSettingsCleaningCharges(Context context,String settingName,String settingValue){
        SharedPreferences sharedPreferences=context.getSharedPreferences(filename,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(settingName,settingValue).commit();
    }
    public static void saveSharedSettingsVisitingCharges(Context context,String settingName,String settingValue){
        SharedPreferences sharedPreferences=context.getSharedPreferences(filename,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(settingName,settingValue).commit();
    }

    public static String readSavedSettingsNotificationList(Context context,String settingName,String defaultValue){
        SharedPreferences sharedPreferences= context.getSharedPreferences(filename ,Context.MODE_PRIVATE);
        return sharedPreferences.getString(settingName,defaultValue);
    }

    public static void saveSharedSettingsNotificationList(Context context,String settingName,String settingValue){
        SharedPreferences sharedPreferences=context.getSharedPreferences(filename,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(settingName,settingValue).commit();
    }


    public static String readSavedSettingsHistoryList(Context context,String settingName,String defaultValue){
        SharedPreferences sharedPreferences= context.getSharedPreferences(filename ,Context.MODE_PRIVATE);
        return sharedPreferences.getString(settingName,defaultValue);
    }

    public static void saveSharedSettingsHistoryList(Context context,String settingName,String settingValue){
        SharedPreferences sharedPreferences=context.getSharedPreferences(filename,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(settingName,settingValue).commit();
    }



}
