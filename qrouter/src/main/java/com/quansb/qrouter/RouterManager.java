package com.quansb.qrouter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * author:quansb
 * Description:
 * Date:2020/5/15
 */
public class RouterManager {

    private final static String ROUTER_APT_ALL_CLASS_PACKAGE_NAME = BuildConfig.router_all_class_package_name;
    private final static String FILE_GROUP_NAME = "QRouter$$Group$$";
    private final static String FILE_PATH_NAME = "QRouter$$PATH$$";

    private static Map<String, RouterPath> groupMap = new HashMap<>();

    private static Map<String, Class<?>> pathMap = new HashMap<>();

    private static volatile RouterManager routerManager;

    private RouterManager() {
    }

    public static RouterManager getInstance() {
        if (routerManager == null) {
            synchronized (RouterManager.class) {
                if (routerManager == null) {
                    routerManager = new RouterManager();
                }
            }
        }
        return routerManager;
    }

    /**
     * @param context      context
     * @param group        所在的module名字
     * @param activityName 在该module中存在的 Activity 名字
     */
    public void startActivity(Context context, String group, String activityName) {
        Log.e("RouterManager", "startActivity.......");

        String groupClassName = ROUTER_APT_ALL_CLASS_PACKAGE_NAME + "." + FILE_GROUP_NAME + group;
        Class<?> activityClass;

        if (null != pathMap.get(activityName)) {
            Log.e("RouterManager", "pathMap not null: ");
            activityClass = pathMap.get(activityName);
            intentJump(context, activityClass, activityName);
            return;
        }
        if (null != groupMap.get(group)) {
            Log.e("RouterManager", "groupMap not null: ");
            RouterPath routerPath = groupMap.get(group);
            activityClass = routerPath.getPathMap().get(activityName);
            intentJump(context, activityClass, activityName);
            return;
        }
        try {
            Log.e("RouterManager", "groupMap is null  use reflect at first times");
            Class<?> groupClass = Class.forName(groupClassName);
            RouterGroup routerGroup = (RouterGroup) groupClass.newInstance();
            Class<?> pathClass = routerGroup.getGroupMap().get(group);
            RouterPath routerPath = (RouterPath) pathClass.newInstance();
            groupMap.put(group, routerPath);
            activityClass = routerPath.getPathMap().get(activityName);
            pathMap.put(activityName, activityClass);
            intentJump(context, activityClass, activityName);
        } catch (ClassNotFoundException e) {
            Log.e("RouterManager", "" + activityName + e.getMessage());
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            Log.e("RouterManager", "" + activityName + e.getMessage());
            e.printStackTrace();
        } catch (InstantiationException e) {
            Log.e("RouterManager", "" + activityName + e.getMessage());
            e.printStackTrace();
        }
    }

    private void intentJump(Context context, Class<?> activityClass, String activityName) {
        if (activityClass == null) {
            Log.e("RouterManager", "get: " + activityName + " failed");
            return;
        }
        Log.e("RouterManager", "get: " + activityName + " success " + activityClass.getName());
        Intent intent;
        if (context instanceof Activity) {
            Log.e("RouterManager", " context is Activity");
            intent = new Intent(context, activityClass);
        } else {
            Log.e("RouterManager", "context is not Activity,------->newTask");
            intent = new Intent(context, activityClass).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    /**
     *
     * @param group      所在的module名字
     * @param className  在该module 存在的 Class名字
     * @return  返回 Class类
     */
    public Class<?> getModuleClass(String group, String className) {
        String groupClassName = ROUTER_APT_ALL_CLASS_PACKAGE_NAME + "." + FILE_GROUP_NAME + group;
        Class<?> clazz = null;
        if (null != pathMap.get(className)) {
            Log.e("RouterManager", "pathMap not null: ");
            clazz = pathMap.get(className);
            return clazz;
        }
        if (null != groupMap.get(group)) {
            Log.e("RouterManager", "groupMap not null: ");
            RouterPath routerPath = groupMap.get(group);
            clazz = routerPath.getPathMap().get(className);
            return clazz;
        }

        try {
            Log.e("RouterManager", "groupMap is null  use reflect at first times");
            Class<?> groupClass = Class.forName(groupClassName);
            RouterGroup routerGroup = (RouterGroup) groupClass.newInstance();
            Class<?> pathClass = routerGroup.getGroupMap().get(group);
            RouterPath routerPath = (RouterPath) pathClass.newInstance();
            groupMap.put(group, routerPath);
            clazz = routerPath.getPathMap().get(className);
            pathMap.put(className, clazz);
            return clazz;
        } catch (ClassNotFoundException e) {
            Log.e("RouterManager", "" + className + e.getMessage());
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            Log.e("RouterManager", "" + className + e.getMessage());
            e.printStackTrace();
        } catch (InstantiationException e) {
            Log.e("RouterManager", "" + className + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }


}
