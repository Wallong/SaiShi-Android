package com.twtstudio.coder.saishi_android.bean;

/**
 * Created by clifton on 16-2-19.
 */
public class UserInfo {

    public int result_code;

    public String msg;

    public String token;

    public String id;

    public String username;

    public String phone;

    public String sex;

    public String old;

    public String pwd;

    public String danwei;

    public String zhifu;

    public String icon;

    public String password;

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "danwei='" + danwei + '\'' +
                ", result_code=" + result_code +
                ", msg='" + msg + '\'' +
                ", token='" + token + '\'' +
                ", id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", phone='" + phone + '\'' +
                ", sex='" + sex + '\'' +
                ", old='" + old + '\'' +
                ", pwd='" + pwd + '\'' +
                ", zhifu='" + zhifu + '\'' +
                ", icon='" + icon + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
