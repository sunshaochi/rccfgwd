package com.rmwl.rcchgwd.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.rmwl.rcchgwd.activity.LoginAct;
import com.rmwl.rcchgwd.bean.UserBean;

/**
 * Created by wangbin on 17/2/7.
 */
public class SpUtils {

    private final static String CADILLAC_SP = "rcch_sp";
    private final static String PHONE="PHONE";
    private final static String USERJSON="USERJSON";
    private final static String PWD="PWD";
    private final static String CRETIFY="CRETIFY";
    private final static String ISFIRST = "is_first";
    private final static String TOKEN="IS_TOKEN";
    private final static String STATUS="STATUS";
    private final static String USERID="USERID";
    public static SharedPreferences getSp(Context context) {
        return context.getSharedPreferences(CADILLAC_SP, Context.MODE_PRIVATE);
    }

    public static void setPhone(Context context, String phone) {
        getSp(context).edit().putString(PHONE, phone).commit();
    }


    public static String getPhone(Context context) {
        return getSp(context).getString(PHONE, "");
    }

    public static void setPwd(Context context, String pwd) {
        getSp(context).edit().putString(PWD, pwd).commit();
    }

    public static String getPwd(Context context) {
        return getSp(context).getString(PWD, "");
    }



    public static void setUser(Context context, String user) {
        getSp(context).edit().putString(USERJSON, user).commit();
    }


    public static String getUser(Context context) {
        UserBean bean=new UserBean();
        return getSp(context).getString(USERJSON, GsonUtils.bean2Json(bean));
    }


    /**
     * 是否第一次进入app
     *
     * @param context
     * @param isFirst
     */
    public static void setIsFirst(Context context, boolean isFirst) {
        getSp(context).edit().putBoolean(ISFIRST, isFirst).commit();
    }

    public static boolean getIsFirst(Context context) {
        return getSp(context).getBoolean(ISFIRST, true);
    }

    /**
     * 清除sp
     *
     * @param context
     */
    public static void clearSp(Context context) {
//        getSp(context).edit().clear().commit();
          setToken(context,"");
          setPhone(context,"");
          setPwd(context,"");
          setUser(context,"");
          setStatus(context,"");

    }


    public static void setCretify(Context context,String s) {
        getSp(context).edit().putString(CRETIFY, s).commit();
    }

    public static String getCretify(Context context) {
        return getSp(context).getString(CRETIFY, "");
    }

    public static void setToken(Context context, String token) {
        getSp(context).edit().putString(TOKEN, token).commit();
    }

    public static String getToken(Context context) {
        return getSp(context).getString(TOKEN, "");
    }

    public static void setStatus(Context context, String status) {
        getSp(context).edit().putString(STATUS,status).commit();
    }

    public static String  getStatus(Context context) {
        return getSp(context).getString(STATUS, "1");
    }

    public static void setUserId(Context context, String userid) {
        getSp(context).edit().putString(USERID, userid).commit();
    }


    public static String getUserId(Context context) {
        return getSp(context).getString(USERID, "");
    }
}
