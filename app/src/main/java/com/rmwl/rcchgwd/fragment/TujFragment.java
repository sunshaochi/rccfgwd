package com.rmwl.rcchgwd.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;

import com.google.gson.Gson;
import com.rmwl.rcchgwd.MainActivity;
import com.rmwl.rcchgwd.MyApplication;
import com.rmwl.rcchgwd.Utils.EncryptionTools;
import com.rmwl.rcchgwd.Utils.ZxingUtils;
import com.rmwl.rcchgwd.bean.RecommdBean;
import com.rmwl.rcchgwd.bean.UserBean;
import com.rmwl.rcchgwd.okhttp.OkHttpCallBack;
import com.rmwl.rcchgwd.okhttp.OkHttpUtils;
import com.rmwl.rcchgwd.okhttp.RequestParam;
import com.tencent.smtt.export.external.interfaces.WebResourceError;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

import android.webkit.JavascriptInterface;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.rmwl.rcchgwd.R;
import com.rmwl.rcchgwd.Utils.GsonUtils;
import com.rmwl.rcchgwd.Utils.MyLogUtils;
import com.rmwl.rcchgwd.Utils.MyToastUtils;
import com.rmwl.rcchgwd.Utils.SpUtils;
import com.rmwl.rcchgwd.activity.ForgetAct;
import com.rmwl.rcchgwd.activity.WebAct;
import com.rmwl.rcchgwd.base.BaseFrag;
import com.rmwl.rcchgwd.okhttp.HttpUrl;
import com.rmwl.rcchgwd.view.ShareDialog;
import com.tencent.smtt.sdk.WebViewClient;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/8/26.
 */

public class TujFragment extends BaseFrag{
    @ViewInject(R.id.tv_code)
    private TextView tv_code;
    @ViewInject(R.id.iv_code)
    private ImageView iv_code;
    @ViewInject(R.id.tv_person_num)
    private TextView tv_person_num;
    @ViewInject(R.id.tv_money_num)
    private TextView tv_money_num;

    @ViewInject(R.id.wb_vv)
    private WebView wb_vv;
    @ViewInject(R.id.pb)
    private ProgressBar pb;

    private String hurl;
    private UserBean user;
    private String strmobile;
    private String suffix;

    private Gson gson;

    private RecommdBean recommdBean;
    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fra_web,null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        gson = new Gson();
        registPersimm();//动态添加友盟分享权限
        if(!TextUtils.isEmpty(SpUtils.getUser(getActivity()))){
            user= GsonUtils.gsonToBean(SpUtils.getUser(getActivity()),UserBean.class);
        }
//        pb.setMax(100);
//        EncryptionTools.initEncrypMD5(SpUtils.getToken(MyApplication.getInstance()));
//        suffix="&token="+SpUtils.getToken(getActivity())+"&timestamp="+EncryptionTools.TIMESTAMP+"&nonce="+EncryptionTools.NONCE+"&signature="+EncryptionTools.SIGNATURE+"&source="+"3";
//
//        hurl=HttpUrl.HTUJ_URL+"?adId="+user.getId()+"&type=app"+suffix;
//        initWebViewSettings();
        getData();
    }

    @SuppressLint({"JavascriptInterface","SetJavaScriptEnabled","AddJavascriptInterface"})
    private void initWebViewSettings() {
        WebSettings webSettings = wb_vv.getSettings();
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheMaxSize(1024*1024*8);
        String appCachePath = getActivity().getCacheDir().getAbsolutePath();
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

    private void registPersimm() {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS, Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions(getActivity(), mPermissionList, 123);
        }
    }




    @Override
    public void onDestroy() {
        super.onDestroy();
        if(wb_vv!=null){
            wb_vv=null;
        }
    }



    public class JsInteraction{
        @JavascriptInterface
        public void sendMessage(String mobile) {
            doSendSMSTo(mobile);
        }
        @JavascriptInterface
        public void shareFriend(String shareUrl){
            share(shareUrl);
        }

        @JavascriptInterface
        public void ringUp(String mobile){
            strmobile=mobile;
            callPhone();
        }

    }

    final int REQUESTCODE=1012;
    private void callPhone() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasPhonePermission = getActivity().checkSelfPermission(Manifest.permission.CALL_PHONE);
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
        Intent intent = new Intent(); // 意图对象：动作 + 数据
        intent.setAction(Intent.ACTION_CALL); // 设置动作
        Uri data = Uri.parse("tel:" + strmobile); // 设置数据
        intent.setData(data);
        startActivity(intent); // 激活Activity组件
    }


    UMWeb web;
    UMImage image;
    String wxshartitle;
    String shareDesc;

    private void share(String url) {
        image = new UMImage(getActivity(), R.mipmap.share_logo);
        image.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
        wxshartitle ="好友邀请您来注册";
        shareDesc ="你的好友邀请您注册【嵘创财富】。小额理财稳健收益";


        web = new UMWeb(url);//分享路径
        web.setTitle(wxshartitle);//标题
        web.setThumb(image);  //缩略图
        web.setDescription(shareDesc);//描述

        new ShareAction(getActivity())
                .setDisplayList(SHARE_MEDIA.WEIXIN)//传入平台
//                .withMedia(web)
                .setShareboardclickCallback(shareBoardlistener)//面板点击监听器
                .open();

    }

    private void doSendSMSTo(String mobile) {
            Uri smsToUri = Uri.parse("smsto:"+mobile);
            Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
            intent.putExtra("sms_body", "");
            startActivity(intent);
    }


    private ShareBoardlistener shareBoardlistener = new  ShareBoardlistener() {
        @Override
        public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
            if (share_media==null){
                //根据key来区分自定义按钮的类型，并进行对应的操作
                if (snsPlatform.mKeyword.equals("umeng_sharebutton_custom")){
                    Toast.makeText(getActivity(),"add button  success",Toast.LENGTH_LONG).show();
                }
            } else {//社交平台的分享行为
                if (share_media == SHARE_MEDIA.WEIXIN) {
                    new ShareAction(getActivity())
                            .setPlatform(share_media)
                            .setCallback(umShareListener)
                            .withMedia(web)
                            .share();
                }
            }
        }
    };

    private UMShareListener umShareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            if (t.getMessage().contains("没有安装应用")) {
                MyToastUtils.showShortToast(getActivity(), "您未安装微信APP,请先安装");

            } else {
                MyToastUtils.showShortToast(getActivity(), t.getMessage());
            }
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            MyToastUtils.showShortToast(getActivity(), "取消");
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode)
        {
            case REQUESTCODE:
                if(!permissions[0].equals(Manifest.permission.CALL_PHONE)||grantResults[0]!=PackageManager.PERMISSION_GRANTED)
                {
                    MyToastUtils.showShortToast(getActivity(),"请先授权电话权限！");
                    return;
                }
//                callPhone();
                phone();
                break;
        }
    }

    public void upDate() {
//        pb.setVisibility(View.VISIBLE);
//        pb.setMax(100);
//        EncryptionTools.initEncrypMD5(SpUtils.getToken(MyApplication.getInstance()));
//        suffix="&token="+SpUtils.getToken(getActivity())+"&timestamp="+EncryptionTools.TIMESTAMP+"&nonce="+EncryptionTools.NONCE+"&signature="+EncryptionTools.SIGNATURE+"&source="+"3";
//        hurl=HttpUrl.HTUJ_URL+"?adId="+user.getId()+"&type=app"+suffix;
//        initWebViewSettings();
        getData();
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden){

        }else {
//            pb.setVisibility(View.VISIBLE);
//            pb.setMax(100);
//            wb_vv.reload(); //刷新
//            EncryptionTools.initEncrypMD5(SpUtils.getToken(MyApplication.getInstance()));
//            suffix="&token="+SpUtils.getToken(getActivity())+"&timestamp="+EncryptionTools.TIMESTAMP+"&nonce="+EncryptionTools.NONCE+"&signature="+EncryptionTools.SIGNATURE+"&source="+"3";
//            hurl=HttpUrl.HTUJ_URL+"?adId="+user.getId()+"&type=app"+suffix;
//            initWebViewSettings();
            getData();
        }
    }


    private void getData(){
        RequestParam param = new RequestParam();
        OkHttpUtils.post(HttpUrl.RECOMMD_URL, param, new OkHttpCallBack(getActivity()) {
            @Override
            public void onSuccess(String data) {
                try {
                    JSONObject object = new JSONObject(data.toString());
                    JSONObject result = object.getJSONObject("result");
                    recommdBean = gson.fromJson(result.toString(), RecommdBean.class);

                    setData(recommdBean);
                } catch (JSONException e) {
                    e.printStackTrace();
                    MyToastUtils.showShortToast(getActivity(), "解析异常");
                }

            }

            @Override
            public void onError(int code, String error) {
                MyToastUtils.showShortToast(getActivity(), error);
            }
        });
    }

    private void setData(RecommdBean recommdBean){
        tv_code.setText(recommdBean.getInviteCode());
        Bitmap bitmap = ZxingUtils.createBitmap(HttpUrl.RECOMMD_CODE_URL + "?InvitationCode="+recommdBean.getInviteCode()+"&uuId="+recommdBean.getUuId());
        iv_code.setImageBitmap(bitmap);
        tv_person_num.setText(recommdBean.getInviteNum());
        tv_money_num.setText(recommdBean.getTotalInvestment());
    }

    @OnClick({R.id.tv_share})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_share:
                share(HttpUrl.RECOMMD_CODE_URL + "?InvitationCode="+recommdBean.getInviteCode()+"&uuId="+recommdBean.getUuId());
                break;
        }
    }
}
