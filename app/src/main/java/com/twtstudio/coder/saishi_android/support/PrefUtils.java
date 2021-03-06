package com.twtstudio.coder.saishi_android.support;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.twtstudio.coder.saishi_android.ContestApp;
import com.twtstudio.coder.saishi_android.bean.UserInfo;

/**
 * Created by clifton on 16-2-20.
 */
public class PrefUtils {
    private static final String PREF_IS_LOGIN = "is_login";

    private static final String PREF_PHONE = "phone";

    private static final String PREF_PWD = "pwd";

    private static final String PREF_TOKEN = "token";

    private static final String PREF_DANWEI = "danwei";

    private static final String PREF_ZHIWU = "zhifu";

    private static final String PREF_ICON = "icon";

    private static final String PREF_USERNAME = "username";

    private static final String PREF_ICON_URL = "iconurl";

    private static final String PREF_PASSWORD = "password";

    private static final String PREF_HEADER = "header";

    private static final String PREF_UPDATE = "update";

    public static SharedPreferences getDefaultSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(ContestApp.getContext());
    }

    public static void setDefaultPrefUserInfo(UserInfo userInfo){
        getDefaultSharedPreferences().edit()
                .putString(PREF_TOKEN, userInfo.token)
                .putString(PREF_PHONE, userInfo.phone)
                .putString(PREF_PWD, userInfo.pwd)
                .putString(PREF_DANWEI, userInfo.danwei)
                .putString(PREF_ICON, userInfo.icon)
                .putString(PREF_USERNAME, userInfo.username)
                .putString(PREF_PASSWORD, userInfo.password)
                .apply();
    }

    public static void setDefaultPrefHeader(int header){
        getDefaultSharedPreferences().edit().putString(PREF_HEADER, Integer.toString(header)).apply();
    }

    public static String getPrefHeader() {
        return getDefaultSharedPreferences().getString(PREF_HEADER, null);
    }

    public static void setDefaultPrefUpdate(String update){
        getDefaultSharedPreferences().edit().putString(PREF_UPDATE, update).apply();
    }

    public static String getPrefUpdate(){
        return getDefaultSharedPreferences().getString(PREF_UPDATE, null);
    }

    public static void setDefaultPrefUserIcon(String iconUrl){
        getDefaultSharedPreferences().edit().putString(PREF_ICON_URL, iconUrl).apply();
    }

    public static boolean isLogin() {
        return getDefaultSharedPreferences().getBoolean(PREF_IS_LOGIN, false);
    }

    public static void setLogin(boolean isLogin){
        getDefaultSharedPreferences().edit().putBoolean(PREF_IS_LOGIN, isLogin).apply();
    }

    public static String getPrefPassword(){
        return getDefaultSharedPreferences().getString(PREF_PASSWORD, null);
    }

    public static void setPrePassword(String password){
        getDefaultSharedPreferences().edit().putString(PREF_PASSWORD, password).apply();
    }

    public static String getToken() {
        return getDefaultSharedPreferences().getString(PREF_TOKEN, null);
    }

    public static String getPrefUsername() {
        return getDefaultSharedPreferences().getString(PREF_USERNAME, null);
    }

    public static String getPrefPwd() {
        return getDefaultSharedPreferences().getString(PREF_PWD, null);
    }

    public static void setPrefPwd(String pwd) {
        getDefaultSharedPreferences().edit().putString(PREF_IS_LOGIN, pwd).apply();
    }

    public static String getPrefDanwei() {
        return getDefaultSharedPreferences().getString(PREF_DANWEI, null);
    }

    public static String getPrefZhiwu() {
        return getDefaultSharedPreferences().getString(PREF_ZHIWU, null);
    }

    public static String getPrefIcon() {
        return getDefaultSharedPreferences().getString(PREF_ICON, null);
    }

    public static String getPrefPhone() {
        return getDefaultSharedPreferences().getString(PREF_PHONE, null);
    }

    public static String getPrefIconUrl() {
        return getDefaultSharedPreferences().getString(PREF_ICON_URL, null);
    }

}
