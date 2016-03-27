package com.twtstudio.coder.saishi_android.bean;

/**
 * Created by clifton on 16-3-17.
 */
public class UpdateInfo {

    public String result_code;

    public String msg;

    public String version;

    public String detail;

    public String url;

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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "UpdateInfo{" +
                "result_code='" + result_code + '\'' +
                ", msg='" + msg + '\'' +
                ", version='" + version + '\'' +
                ", detail='" + detail + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
