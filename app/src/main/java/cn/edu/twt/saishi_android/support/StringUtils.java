package cn.edu.twt.saishi_android.support;

/**
 * Created by clifton on 16-3-1.
 */
public class StringUtils {
    public static String cutString(String string, int tag){
        string = string.replaceAll("-", ".");
        String time = string;
        String total  = string.substring(0,16);
        String date = string.substring(0, 10);
        String hour = string.substring(11,16);
        String chinese = string.substring(0,4) + "年" + string.substring(5,7) + "月" + string.substring(8,10) + "日";

        switch (tag){
            case 0:
                time = total;
                break;
            case 1:
                time = date;
                break;
            case 2:
                time = hour;
                break;
            case 3:
                time = chinese;
                break;
        }
        return time;
    }
}
