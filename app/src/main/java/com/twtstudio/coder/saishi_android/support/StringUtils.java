package com.twtstudio.coder.saishi_android.support;

/**
 * Created by clifton on 16-3-1.
 */
public class StringUtils {
    public static String cutString(String string, int tag){
        string = string.replaceAll("-", ".");
        String time = string;
        switch (tag){
            case 0:
                String total  = string.substring(0,16);
                time = total;
                break;
            case 1:
                String date = string.substring(0, 10);
                time = date;
                break;
            case 2:
                String hour = string.substring(11,16);
                time = hour;
                break;
            case 3:
                String chinese = string.substring(0,4) + "年" + string.substring(5,7) + "月" + string.substring(8,10) + "日";
                time = chinese;
                break;
            case 4:
//                String str = "public\\/file\\/wuyun.docx";
                int place = 0;
                for(int i = 0 ; i < string.length(); i++){
                    if(string.charAt(i) == '/'){
                        place = i;
                    }
                }
                String path =  string.substring(place,string.length());
                time = path;
                break;
            case 5:
                if(string.length() == 1){
                    String hexString = "0" + string;
                    time = hexString;
                }
                break;
        }
        return time;
    }
}
