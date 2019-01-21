package com.rmwl.rcchgwd

import android.app.Application
import cn.jpush.android.api.JPushInterface
import com.networkbench.agent.impl.NBSAppAgent
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.tencent.smtt.sdk.QbSdk
import com.umeng.commonsdk.UMConfigure
import com.umeng.socialize.PlatformConfig

/**
 * Created by Administrator on 2019/1/14.
 */
class RCApplication : Application(){

    companion object{
        val APP_ID = "wxd04e84116973343d"//正式appid
        private lateinit var api: IWXAPI //定义变量不能为空
        var instance: RCApplication?=null //?= 空安全
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        UMConfigure.init(this, "5b836f9c8f4a9d58cb000017", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "")
        UMConfigure.setLogEnabled(true)
        PlatformConfig.setWeixin("wxd04e84116973343d", "9159640a80059916f4f10d28d9002d19")
        NBSAppAgent.setLicenseKey("ab048e9c7c464ebb963efbfa9ce04b76").withLocationServiceEnabled(true).start(this.applicationContext)//Appkey 请从官网获取

        JPushInterface.setDebugMode(true)//极光推送
        JPushInterface.init(this)
        QbSdk.setDownloadWithoutWifi(true)//非无线网情况下也允许下载X5内核
        //x5内核初始化接口
        QbSdk.initX5Environment(applicationContext, null)

        regToWx()

    }

    /**
     * 像微信终端注册id
     */
    private fun regToWx() {
        api = WXAPIFactory.createWXAPI(this, APP_ID, true)
        api.registerApp(APP_ID)
    }
}