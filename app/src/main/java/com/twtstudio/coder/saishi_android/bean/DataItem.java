package com.twtstudio.coder.saishi_android.bean;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getPaixu() {
        return paixu;
    }

    public void setPaixu(String paixu) {
        this.paixu = paixu;
    }

    public String getIsup() {
        return isup;
    }

    public void setIsup(String isup) {
        this.isup = isup;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getFwl() {
        return fwl;
    }

    public void setFwl(String fwl) {
        this.fwl = fwl;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
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
