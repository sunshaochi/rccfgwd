package com.rmwl.rcchgwd.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.util.Locale;

/**
 * Created by Administrator on 2018/7/10.
 */

public class RoncheUtil {
    //截取得到年月日时分
    public static String getRealYMDHMTime(String time){
        if (!TextUtils.isEmpty(time)) {
            time = time.substring(0, 16);
            return time;
        }else {
            return "";
        }
    }

    //截取得到年月日
    public static String getRealTime(String time){
        if (!TextUtils.isEmpty(time)) {
            time = time.substring(0, 10);
            return time;
        }else {
            return "";
        }
    }

    public static String getRealTerm(String term){
        term = term.replaceAll("0+?$", "");//去掉多余的0
        term = term.replaceAll("[.]$", "");//如最后一位是.则去掉
        return term;
    }

    /**
     * 获取版本信息
     *
     * @param context
     */
    public static String getVersionCode(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            return info.versionCode+"";
        } catch (PackageManager.NameNotFoundException e) {
            return "";
        }

    }

    /**
     * 获取版本信息
     *
     * @param context
     */
    public static String getVersionName(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            return info.versionName+"";
        } catch (PackageManager.NameNotFoundException e) {
            return "";
        }

    }



    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前系统上的语言列表(Locale列表)
     *
     * @return  语言列表
     */
    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return  系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return  手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return  手机厂商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取手机IMEI(需要“android.permission.READ_PHONE_STATE”权限)
     *
     * @return  手机IMEI
     */
    @SuppressLint("MissingPermission")
    public static String getIMEI(Context ctx) {
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Activity.TELEPHONY_SERVICE);
        if (tm != null) {
            return tm.getDeviceId();
        }
        return null;
    }

}
