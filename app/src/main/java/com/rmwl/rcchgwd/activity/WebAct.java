package com.rmwl.rcchgwd.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import com.rmwl.rcchgwd.MyApplication;
import com.rmwl.rcchgwd.Utils.GeneralUtils;
import com.tencent.smtt.export.external.interfaces.WebResourceError;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

import android.webkit.JavascriptInterface;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.networkbench.agent.impl.instrumentation.NBSWebChromeClient;
import com.rmwl.rcchgwd.PdfAct;
import com.rmwl.rcchgwd.R;
import com.rmwl.rcchgwd.Utils.EncryptionTools;
import com.rmwl.rcchgwd.Utils.GsonUtils;
import com.rmwl.rcchgwd.Utils.MyLogUtils;
import com.rmwl.rcchgwd.Utils.MyToastUtils;
import com.rmwl.rcchgwd.Utils.SpUtils;
import com.rmwl.rcchgwd.base.BaseActivity;
import com.rmwl.rcchgwd.bean.UserBean;
import com.rmwl.rcchgwd.okhttp.HttpUrl;
import com.rmwl.rcchgwd.view.MyAlertDialog;
import com.tencent.smtt.sdk.WebViewClient;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by acer on 2018/8/23.
 */

public class WebAct extends BaseActivity {
    @ViewInject(R.id.wb_vv)
    private WebView wb_vv;
    @ViewInject(R.id.tv_title)
    private TextView tv_titlev;
    @ViewInject(R.id.pb)
    private ProgressBar pb;


    private String type;
    private String proId;
    private String userId;
    private String hurl;

    private UserBean user;
    private String strmobile;

    boolean fill=false;

    private String suffix;


    @Override
    public void setLayout() {
        setContentView(R.layout.act_web);
    }

    @Override
    public void init(Bundle savedInstanceState) {

//         callPhone();//动态权限
        pb.setMax(100);
        if(!TextUtils.isEmpty(SpUtils.getUser(WebAct.this))){
            user= GsonUtils.gsonToBean(SpUtils.getUser(WebAct.this),UserBean.class);
        }


        EncryptionTools.initEncrypMD5(SpUtils.getToken(MyApplication.getInstance()));
        suffix="&token="+SpUtils.getToken(WebAct.this)+"&timestamp="+EncryptionTools.TIMESTAMP+"&nonce="+EncryptionTools.NONCE+"&signature="+EncryptionTools.SIGNATURE+"&source="+"3";

        type=getIntent().getExtras().getString("type","");
        if(type.equals("1")){
            tv_titlev.setText("产品详情");
            proId=getIntent().getExtras().getString("proId","");
//            hurl= HttpUrl.HCPXQ_URL+"?proId="+proId+"&type=app";
            hurl= HttpUrl.HCPXQ_URL+"?proId="+proId+"&type=app"+suffix;
        }
        else if(type.equals("2")){
            tv_titlev.setText("客户");
            EncryptionTools.initEncrypMD5(TextUtils.isEmpty(user.getToken())?"":user.getToken());
            String timestamp=EncryptionTools.TIMESTAMP;
            String nonce=EncryptionTools.NONCE;
            String signature= EncryptionTools.SIGNATURE;
            hurl= HttpUrl.KH+"?token="+user.getToken()
                    +"&timestamp="+timestamp+"&nonce="+nonce+"&signature="+signature+"&type=app";
        }
        else if(type.equals("3")){
            tv_titlev.setText("业绩");
//            hurl= HttpUrl.YJ+"?adId="+user.getId()+"&type=app";
            hurl= HttpUrl.YJ+"?adId="+user.getId()+"&type=app"+suffix;
        }else if(type.equals("4")){
            tv_titlev.setText("帮助中心");
            hurl= HttpUrl.HELP_URL+"?type=app";
//            hurl= HttpUrl.HELP_URL+"?type=app"+suffix;
        }
        else if(type.equals("5")){
            tv_titlev.setText("待办");
//            hurl=HttpUrl.DB+"?adId="+user.getId()+"&type=app";
            hurl=HttpUrl.DB+"?adId="+user.getId()+"&type=app"+suffix;

        }else if(type.equals("6")){
            tv_titlev.setText("用户注册协议");
            hurl=HttpUrl.HREGIST_XY+"?type=app";
//            hurl=HttpUrl.HREGIST_XY+"?type=app"+suffix;

        }else if(type.equals("10")){//banner图
            tv_titlev.setText("");
            hurl=getIntent().getExtras().getString("hurl","");

        }

          initWebViewSettings();
    }

    @SuppressLint({"JavascriptInterface","SetJavaScriptEnabled","AddJavascriptInterface"})
    private void initWebViewSettings() {
        WebSettings webSettings = wb_vv.getSettings();
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheMaxSize(1024*1024*8);
        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        webSettings.setAppCachePath(appCachePath);
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);

        wb_vv.addJavascriptInterface(new JsInteraction(),"AppModel");
        wb_vv.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                return super.shouldOverrideUrlLoading(view, request);
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                fill=true;
            }


        });
        wb_vv.setWebChromeClient(new WebChromeClient(){

            @Override

            public void onProgressChanged(WebView view, int newProgress) {

//                NBSWebChromeClient.initJSMonitor(view, newProgress);
                pb.setProgress(newProgress);
                if(newProgress==100){
                    pb.setVisibility(View.GONE);
                }
                super.onProgressChanged(view, newProgress);

            }

        });
        wb_vv.loadUrl(hurl);
        MyLogUtils.info("h5地址"+hurl);
    }

    @OnClick({R.id.iv_goback})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.iv_goback:
                if(fill){
                    finish();
                }else {
                    if (wb_vv.canGoBack()) {
                        wb_vv.goBack();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                              if(type.equals("3")){
                                  tv_titlev.setText("业绩");
                              }
                            }
                        });

                    } else {
                        finish();
                    }
                }

                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK&&wb_vv.canGoBack()){
            wb_vv.goBack();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(type.equals("3")){
                        tv_titlev.setText("业绩");
                    }
                }
            });
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GeneralUtils.ClearWebCookier(WebAct.this);
        wb_vv.setWebChromeClient(null);
        wb_vv.setWebViewClient(null);
        wb_vv.getSettings().setJavaScriptEnabled(false);
        wb_vv.clearCache(true);
        if(wb_vv!=null){
            wb_vv=null;
        }
    }

    public class JsInteraction{
        @JavascriptInterface
        public void sendMessage(String mobile) {
            sendMsm(mobile);
        }
        @JavascriptInterface
        public void shareFriend(String shareUrl){

        }

        @JavascriptInterface
        public void ringUp(String mobile){
            strmobile=mobile;
            callPhone();
        }

        @JavascriptInterface
        public void openPdf(String shareUrl){
            if(shareUrl.endsWith(".pdf")) {
                startActivity(new Intent(WebAct.this, PdfAct.class).putExtra("url", shareUrl));
            }
        }

        @JavascriptInterface
        public void H5copy(String text){
//            share(shareUrl);
            if(!TextUtils.isEmpty(text)){
                toTex(text);
            }
        }

        @JavascriptInterface
        public void clientInvestmentDetails(){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                  tv_titlev.setText("客户投资详情");
                }
            });
        }

        @JavascriptInterface
        public void repaymentSchedule(final String productName){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(productName.length()>15){
                        tv_titlev.setText(productName.substring(0,15)+"...");
                    }else {
                        tv_titlev.setText(productName);
                    }
                }
            });
        }



    }

    private void toTex(String tex) {
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
//        ClipData mClipData = ClipData.newPlainText("Label", tex);
        // 将ClipData内容放到系统剪贴板里。
//        cm.setPrimaryClip(mClipData);
        cm.setText(tex);
        MyToastUtils.showShortToast(WebAct.this,"复制成功");
    }

    private void sendMsm(String advisorMobile) {
        Uri smsToUri = Uri.parse("smsto:"+advisorMobile);
        Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
        intent.putExtra("sms_body", "");
        startActivity(intent);
    }

    final int REQUESTCODE=1012;
    private void callPhone() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasPhonePermission = checkSelfPermission(Manifest.permission.CALL_PHONE);
            if (hasPhonePermission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, REQUESTCODE);
                return;
            }
            phone();
        }else {
            phone();
        }

    }

    private void phone() {

        MyAlertDialog dialog=new MyAlertDialog(WebAct.this);
        dialog.builder().setMsg(strmobile);
        dialog.setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }).setPositiveButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(); // 意图对象：动作 + 数据
                intent.setAction(Intent.ACTION_CALL); // 设置动作
                Uri data = Uri.parse("tel:" + strmobile); // 设置数据
                intent.setData(data);
                startActivity(intent); // 激活Activity组件
            }
        }).show();


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case REQUESTCODE:
                if(!permissions[0].equals(Manifest.permission.CALL_PHONE)||grantResults[0]!=PackageManager.PERMISSION_GRANTED)
                {
                    MyToastUtils.showShortToast(this,"请先授权电话权限！");
                    return;
                }
                callPhone();
//                phone();
                break;
        }
    }


}
