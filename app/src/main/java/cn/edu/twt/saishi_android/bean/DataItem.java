package cn.edu.twt.saishi_android.bean;

import java.io.Serializable;

/**
 * Created by clifton on 16-2-27.
 */
public class DataItem implements Serializable {

    public String id;

    public String title;

    public String content;

    public String icon;

    public String createtime;

    public String type;

    public String isup;

    public String subtitle;

    public String paixu;

    public String fwl;

    public String url;

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "DataItem{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", icon='" + icon + '\'' +
                ", createtime='" + createtime + '\'' +
                ", type='" + type + '\'' +
                ", isup='" + isup + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", paixu='" + paixu + '\'' +
                ", fwl='" + fwl + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
