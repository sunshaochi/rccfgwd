package com.rmwl.rcchgwd;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


import com.networkbench.agent.impl.NBSAppAgent;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.smtt.sdk.QbSdk;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2018/3/23.
 */

public class MyApplication extends Application {

    static MyApplication instance;
    public static boolean isLogin=false;//false不在

    public static final String APP_ID = "wxd04e84116973343d";//正式appid
    public IWXAPI api;

    public static Application getInstance() {
        return instance;
    }

    public void onCreate() {
        super.onCreate();
        instance = this;
        UMConfigure.init(this, "5b836f9c8f4a9d58cb000017", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
        UMConfigure.setLogEnabled(true);
        PlatformConfig.setWeixin("wxd04e84116973343d", "9159640a80059916f4f10d28d9002d19");
        NBSAppAgent.setLicenseKey("ab048e9c7c464ebb963efbfa9ce04b76").withLocationServiceEnabled(true).start(this.getApplicationContext());//Appkey 请从官网获取

        JPushInterface.setDebugMode(true);//激光推送
        JPushInterface.init(this);
        QbSdk.setDownloadWithoutWifi(true);//非无线网情况下也允许下载X5内核
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(),  null);

        regToWx();

    }

    /**
     * 像微信终端注册id
     */
    private void regToWx() {
        api = WXAPIFactory.createWXAPI(this,APP_ID, true);
        api.registerApp(APP_ID);
    }

}




