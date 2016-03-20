package cn.edu.twt.saishi_android.bean;

/**
 * Created by clifton on 16-3-17.
 */
public class MenuModel {
    private int imageViewIcon;
    private int imageViewGo;
    private String text;

    public MenuModel(int imageViewIcon, int imageViewGo, String text) {
        super();
        this.imageViewIcon = imageViewIcon;
        this.imageViewGo = imageViewGo;
        this.text = text;
    }

    public int getImageViewIcon() {
        return imageViewIcon;
    }

    public void setImageViewIcon(int imageViewIcon) {
        this.imageViewIcon = imageViewIcon;
    }

    public int getImageViewGo() {
        return imageViewGo;
    }

    public void setImageViewGo(int imageViewGo) {
        this.imageViewGo = imageViewGo;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
