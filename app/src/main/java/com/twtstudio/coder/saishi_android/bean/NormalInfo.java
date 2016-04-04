package com.twtstudio.coder.saishi_android.bean;

/**
 * Created by clifton on 16-3-2.
 */
public class NormalInfo {

    public String result_code;

    public String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    @Override
    public String toString() {
        return "NormalInfo{" +
                "msg='" + msg + '\'' +
                ", result_code='" + result_code + '\'' +
                '}';
    }
}
