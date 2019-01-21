package com.rmwl.rcchgwd.okhttp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;


import com.rmwl.rcchgwd.MyApplication;
import com.rmwl.rcchgwd.R;
import com.rmwl.rcchgwd.Utils.JsonTool;
import com.rmwl.rcchgwd.Utils.MyLogUtils;
import com.rmwl.rcchgwd.Utils.MyToastUtils;
import com.rmwl.rcchgwd.Utils.SpUtils;
import com.rmwl.rcchgwd.activity.LoginAct;
import com.rmwl.rcchgwd.view.LoadingDialog;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/**
 * Created by Administrator on 2017/7/24.
 */

public abstract class OkHttpCallBack implements Callback {

    Context context;
    Call call;
    LoadingDialog dialog;
    boolean isshow;//是否要显示请求弹框

    Handler handler=new Handler(Looper.getMainLooper());

    public OkHttpCallBack(Context context) {
        this.context= context;
        this.isshow=true;//显示
        onBefore();
    }

    public OkHttpCallBack(Context context, boolean isshow) {
        this.context = context;
        this.isshow = isshow;
        if(isshow){//
            onBefore();
        }
    }

    /**
     * 请求前
     *
     */
    public void onBefore() {
        if (dialog!=null){
            if(dialog.isShowing()){
                dialog.dismiss();
                dialog=null;
            }
        }
        showDialog();

    }

    /**
     * 请求完成
     */
    public void onAfter() {
        if (dialog!=null&&dialog.isShowing()){
            dialog.dismiss();
            dialog=null;
        }
    }



    @Override
    public void onFailure(Call call, IOException e) {
        this.call=call;
        final String errorStr;
        if(isNetConn())
            errorStr="网络不可用！";
        else
            errorStr="请求超时！";
        handler.post(new Runnable() {
            @Override
            public void run() {
                onError(0,errorStr);
            }});

        onAfter();
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        this.call=call;
        final String bodyString = response.body().string();
        String header = response.header("Set-Cookie");
        if(!TextUtils.isEmpty(header)){

        }
        MyLogUtils.info("bodyString"+bodyString);
        final int code = response.code();
        Log.i("返回结果", bodyString);
        if(code==200) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        if(JsonTool.getHttpCode(bodyString)==200) {
                            onSuccess(bodyString);
                        }
                        else if (JsonTool.getHttpCode(bodyString)==1){//1--当前操作需要先登录
                            if(!MyApplication.isLogin) {
                                goLoginAct(1, JsonTool.getResultMsg(bodyString));
                            }else {
                                onError(JsonTool.getHttpCode(bodyString), JsonTool.getResultMsg(bodyString));

                            }
                        }else if (JsonTool.getHttpCode(bodyString)==2){//2--用户签名错误
                            if(!MyApplication.isLogin) {
                                goLoginAct(2, JsonTool.getResultMsg(bodyString));
                            }else {
                                onError(JsonTool.getHttpCode(bodyString), JsonTool.getResultMsg(bodyString));

                            }
                        }else if (JsonTool.getHttpCode(bodyString)==3){//3--账号已被冻结，请联系客服
                            if(!MyApplication.isLogin) {
                                goLoginAct(3, JsonTool.getResultMsg(bodyString));
                            }else {
                                onError(JsonTool.getHttpCode(bodyString), JsonTool.getResultMsg(bodyString));

                            }
                        }
                        else {
                            onError(JsonTool.getHttpCode(bodyString), JsonTool.getResultMsg(bodyString));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        else {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    onError(code, JsonTool.getResultMsg(bodyString));
                }
            });
        }

        onAfter();
    }


    public abstract void onSuccess(String data);
    public abstract void onError(int code,String error);

    private void goLoginAct(int i,String tex) {
//        if(i==1){
//            MyToastUtils.showShortToast(activity,"当前操作需要先登录");
//        }else if(i==2){
//            MyToastUtils.showShortToast(activity,"用户签名错误");
//        }else if(i==3){
//            MyToastUtils.showShortToast(activity,"账号已被冻结，请联系客服 ");
//        }
        MyToastUtils.showShortToast(context,tex);
//        SpUtils.setPhone(MyApplication.getInstance(),"");
//        SpUtils.setPwd(MyApplication.getInstance(),"");
//        SpUtils.setUser(MyApplication.getInstance(),"");
//        SpUtils.setToken(MyApplication.getInstance(),"");
        SpUtils.clearSp(MyApplication.getInstance());
//          AppManager.getAppManager().finishAllActivity();
        MyApplication.getInstance().getApplicationContext().startActivity(new Intent( MyApplication.getInstance().getApplicationContext(),LoginAct.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    private boolean isNetConn()
    {
        ConnectivityManager manager= (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=manager.getActiveNetworkInfo();
        if(networkInfo==null||!networkInfo.isAvailable())
            return true;
        else
            return false;
    }


    private void showDialog()
    {
        dialog=new LoadingDialog(context, R.style.LoadingDialogStyle);
        dialog.setText("加载中...");
        dialog.setCancelable(false);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
               /* if(keyCode== KeyEvent.KEYCODE_BACK&&event.getKeyCode()==KeyEvent.ACTION_DOWN)*/
                if(keyCode== KeyEvent.KEYCODE_BACK)
                {
                    dialog.dismiss();
                    if(call!=null)
                        call.cancel();
                    return true;
                }
                return false;
            }
        });

        dialog.show();
    }


}

