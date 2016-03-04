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

    public String tag;

    public void setTag(String tag) {
        this.tag = tag;
    }
}
