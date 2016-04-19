package com.twtstudio.coder.saishi_android.support;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;

/**
 * Created by clifton on 16-3-2.
 */
public class ExitApplication extends Application {

    private List<Activity> activityList = new LinkedList();
    private static ExitApplication instance;
    private int totalSize = -1;

    private ExitApplication()
    {
    }
    //单例模式中获取唯一的ExitApplication实例
    public static ExitApplication getInstance()
    {
        if(null == instance)
        {
            instance = new ExitApplication();
        }
        return instance;

    }
    //添加Activity到容器中
    public void addActivity(Activity activity)
    {
        activityList.add(activity);
    }
    //删除顶部Activity
    public void removeActivity()
    {
        LogHelper.e("activity.size()", "" + activityList.size());
        if(totalSize == 0){
            totalSize = -1;
        }
        if(totalSize == -1 && activityList.size() != 0) {
            activityList.remove(activityList.size() - 1);
        }
    }
    //遍历所有Activity并finish

    public void exit()
    {

        for(Activity activity:activityList)
        {
            activity.finish();
        }

        System.exit(0);

    }
    public void logout() {
        totalSize = activityList.size();
        for(int i = activityList.size()-1; i > -1; i--){
            LogHelper.e("activity.size()", i +"" + activityList.size());
            activityList.get(i).finish();
            activityList.remove(i);
            totalSize = i;
        }
        PrefUtils.setDefaultPrefUserIcon(null);
    }
}
