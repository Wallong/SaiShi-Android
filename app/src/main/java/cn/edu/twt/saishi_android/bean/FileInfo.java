package cn.edu.twt.saishi_android.bean;

import java.io.Serializable;

/**
 * Created by clifton on 16-2-27.
 */
public class FileInfo implements Serializable {

    public String id;

    public String title;

    public String descrp;

    public String createtime;

    public String file;

    public String type;

    public String r;

    public String g;

    public String b;

    public String tag;

    public String content;

    public String mark;

    public void setMark(String mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", descrp='" + descrp + '\'' +
                ", createtime='" + createtime + '\'' +
                ", file='" + file + '\'' +
                ", type='" + type + '\'' +
                ", r='" + r + '\'' +
                ", g='" + g + '\'' +
                ", b='" + b + '\'' +
                ", tag='" + tag + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
