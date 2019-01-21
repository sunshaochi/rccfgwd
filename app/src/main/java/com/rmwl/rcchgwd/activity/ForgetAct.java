package com.rmwl.rcchgwd.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.rmwl.rcchgwd.R;
import com.rmwl.rcchgwd.Utils.ButtonUtils;
import com.rmwl.rcchgwd.Utils.CheckUtil;
import com.rmwl.rcchgwd.Utils.EncryptionTools;
import com.rmwl.rcchgwd.Utils.GsonUtils;
import com.rmwl.rcchgwd.Utils.MyToastUtils;
import com.rmwl.rcchgwd.Utils.SpUtils;
import com.rmwl.rcchgwd.base.BaseActivity;
import com.rmwl.rcchgwd.bean.UserBean;
import com.rmwl.rcchgwd.okhttp.HttpUrl;
import com.rmwl.rcchgwd.okhttp.OkHttpCallBack;
import com.rmwl.rcchgwd.okhttp.OkHttpUtils;
import com.rmwl.rcchgwd.okhttp.RequestParam;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by acer on 2018/8/22.
 */

public class ForgetAct extends BaseActivity{
    @ViewInject(R.id.et_phone)
    private EditText et_phone;
    @ViewInject(R.id.et_yzm)
    private EditText et_yzm;
    @ViewInject(R.id.et_newpwd)
    private EditText et_newpwd;
    @ViewInject(R.id.et_secnewpwd)
    private EditText et_secnewpwd;
    @ViewInject(R.id.tv_btyzm)
    private TextView tv_btyzm;
    @ViewInject(R.id.iv_showorno)
    private ImageView iv_showorno;
    @ViewInject(R.id.iv_secyanjin)
    private ImageView iv_secyanjin;

    @ViewInject(R.id.ll_viewline)
    private LinearLayout ll_viewline;
    @ViewInject(R.id.view_one)
    private View view_one;
    @ViewInject(R.id.view_two)
    private View view_two;
    @ViewInject(R.id.view_three)
    private View view_three;
    @ViewInject(R.id.ll_select)
    private LinearLayout ll_select;
    @ViewInject(R.id.iv_zw)
    private ImageView iv_zw;
    @ViewInject(R.id.iv_zwt)
    private ImageView iv_zwt;
    @ViewInject(R.id.iv_zwthre)
    private ImageView iv_zwthre;
    @ViewInject(R.id.tv_kong)
    private TextView tv_kong;
    @ViewInject(R.id.tv_chang)
    private TextView tv_chang;
    @ViewInject(R.id.tv_dxx)
    private TextView tv_dxx;
    @ViewInject(R.id.tv_intensity)
    private TextView tv_intensity;

    private UserBean user;
    private String phone,yzm,newpwd,secnewpwd;

    private boolean isshow=false;
    private boolean secisshow=false;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_forget);
    }

    @Override
    public void init(Bundle savedInstanceState) {
     setTopTitle("找回密码");
        if(!TextUtils.isEmpty(SpUtils.getUser(ForgetAct.this))){
            user= GsonUtils.gsonToBean(SpUtils.getUser(ForgetAct.this),UserBean.class);
        }else {
            user=new UserBean();
        }

        et_newpwd.addTextChangedListener(new MyTextChangedListener());
        CheckUtil.showOrHide(false,et_secnewpwd);
        CheckUtil.showOrHide(false,et_newpwd);

    }

    @OnClick({R.id.tv_qd,R.id.tv_btyzm,R.id.iv_showorno,R.id.iv_secyanjin})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.tv_qd:
                if(isVisible()){
                    goForgot();
                }
                break;

            case R.id.tv_btyzm:
                if (!ButtonUtils.isFastDoubleClick(R.id.tv_btyzm))
                   getCaptchaNum();//获取验证码发送次数
                  break;

            case R.id.iv_showorno:
                if(!isshow){
                    isshow=true;
                    iv_showorno.setImageResource(R.mipmap.bigyanjing);
                    CheckUtil.showOrHide(true,et_newpwd);
                }else {
                    isshow=false;
                    iv_showorno.setImageResource(R.mipmap.yanjing);
                    CheckUtil.showOrHide(false,et_newpwd);
                }
                break;

            case R.id.iv_secyanjin:
                if(!secisshow){
                    secisshow=true;
                    iv_secyanjin.setImageResource(R.mipmap.bigyanjing);
                    CheckUtil.showOrHide(true,et_secnewpwd);
                }else {
                    secisshow=false;
                    iv_secyanjin.setImageResource(R.mipmap.yanjing);
                    CheckUtil.showOrHide(false,et_secnewpwd);
                }
                break;
        }
    }

    private void getCaptchaNum() {
        phone=et_phone.getText().toString();
        if(TextUtils.isEmpty(phone)||!CheckUtil.isMobile(phone)){
            if(TextUtils.isEmpty(phone)) {
                et_phone.requestFocus();
                MyToastUtils.showShortToast(ForgetAct.this, "请输入手机号");
            }else {
                et_phone.requestFocus();
                MyToastUtils.showShortToast(ForgetAct.this, "请输入正确格式手机号");
            }
        }else {
            RequestParam param = new RequestParam();
            param.add("mobile",phone);
            OkHttpUtils.post(HttpUrl.CAPTCHANUM_URL, param, new OkHttpCallBack(ForgetAct.this) {
                @Override
                public void onSuccess(String data) {
                    try {
                        JSONObject object=new JSONObject(data.toString());
//                        JSONObject result=object.getJSONObject("result");
//                        int num=GsonUtils.gsonToBean(result.toString(),int.class);
                        int num=object.getInt("result");
                        if(num>=3){
                            MyToastUtils.showShortToast(ForgetAct.this,"今日次数用完");
                        }else {
                            sendYzm();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onError(int code, String error) {
                 MyToastUtils.showShortToast(ForgetAct.this,error);
                }
            });
        }
    }

    private void sendYzm() {
        EncryptionTools.initEncrypMD5(TextUtils.isEmpty(user.getToken())?"":user.getToken());
        RequestParam param=new RequestParam();
        param.add("token", TextUtils.isEmpty(user.getToken())?"":user.getToken());
        param.add("timestamp", EncryptionTools.TIMESTAMP);
        param.add("nonce", EncryptionTools.NONCE);
        param.add("signature", EncryptionTools.SIGNATURE);
        param.add("source","3");
        param.add("mobile",phone);
        param.add("type","2");
        OkHttpUtils.post(HttpUrl.SENDMSG_URL, param, new OkHttpCallBack(ForgetAct.this) {
            @Override
            public void onSuccess(String data) {
                MyToastUtils.showShortToast(ForgetAct.this,"验证码发送成功");
                i = 60;
                tv_btyzm.setEnabled(false);
                timer = new Timer();
                myTask = new MyTimerTask();
                timer.schedule(myTask, 0, 1000);
            }

            @Override
            public void onError(int code, String error) {
             MyToastUtils.showShortToast(ForgetAct.this,error);
            }
        });
    }


    private boolean isVisible() {
        phone=et_phone.getText().toString().trim();
        yzm=et_yzm.getText().toString().trim();
        newpwd=et_newpwd.getText().toString().trim();
        secnewpwd=et_secnewpwd.getText().toString().trim();


        if(TextUtils.isEmpty(phone)){
            et_phone.requestFocus();
            MyToastUtils.showShortToast(ForgetAct.this,"请输入手机号");
            return false;
        }
        if(TextUtils.isEmpty(yzm)){
            et_yzm.requestFocus();
            MyToastUtils.showShortToast(ForgetAct.this,"请输入验证码");
            return false;
        }
        if(TextUtils.isEmpty(newpwd)){
            et_newpwd.requestFocus();
            MyToastUtils.showShortToast(ForgetAct.this,"请输入新密码");
            return false;
        }

        if(TextUtils.isEmpty(secnewpwd)){
            et_secnewpwd.requestFocus();
            MyToastUtils.showShortToast(ForgetAct.this,"请确认新密码");
            return false;
        }

        if(!CheckUtil.isPwd(newpwd)){
            et_newpwd.requestFocus();
            et_newpwd.setSelection(newpwd.length());
            MyToastUtils.showShortToast(ForgetAct.this,"请输入6~20位数字和字母");
            return false;
        }

        if(!newpwd.equals(secnewpwd)){
            et_secnewpwd.requestFocus();
            et_secnewpwd.setText("");
            MyToastUtils.showShortToast(ForgetAct.this,"两次输入密码不一致,请重新确认密码");
            return false;
        }

        return true;

    }

    private void goForgot() {
        RequestParam param=new RequestParam();
        param.add("source","3");
        param.add("mobile",phone);
        param.add("captcha",yzm);
        param.add("password",newpwd);
        param.add("passwordConfirm",secnewpwd);
        OkHttpUtils.post(HttpUrl.FORGPWD_URL, param, new OkHttpCallBack(ForgetAct.this) {
            @Override
            public void onSuccess(String data) {
                MyToastUtils.showShortToast(ForgetAct.this,"密码重置成功");
                finish();
            }

            @Override
            public void onError(int code, String error) {
             MyToastUtils.showShortToast(ForgetAct.this,error);
            }
        });

    }

    private int i = 60;
    private Timer timer;
    private MyTimerTask myTask;


    /**
     * 倒计时
     *
     * @author wangbin
     *
     */
    class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            handler.sendEmptyMessage(i--);
        }

    }


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                tv_btyzm.setEnabled(true);
                tv_btyzm.setText("重新发送");
                timer.cancel();
                myTask.cancel();
            } else {
                tv_btyzm.setText("重新获取"+"("+msg.what + "秒"+")");
            }
        }

    };


    private class MyTextChangedListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            judgeTex(s.toString());
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            judgeTex(s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    private void judgeTex(String s) {
        ll_viewline.setVisibility(View.GONE);
        ll_select.setVisibility(View.GONE);
        view_one.setBackgroundColor(getResources().getColor(R.color.line_intensity));
        view_two.setBackgroundColor(getResources().getColor(R.color.line_intensity));
        view_three.setBackgroundColor(getResources().getColor(R.color.line_intensity));
        iv_zw.setVisibility(View.INVISIBLE);
        iv_zwt.setVisibility(View.INVISIBLE);
        iv_zwthre.setVisibility(View.INVISIBLE);
        tv_kong.setTextColor(getResources().getColor(R.color.text_intensity));
        tv_chang.setTextColor(getResources().getColor(R.color.text_intensity));
        tv_dxx.setTextColor(getResources().getColor(R.color.text_intensity));
        tv_intensity.setVisibility(View.GONE);
        tv_intensity.setText("密码强度低");
        if(TextUtils.isEmpty(s)){
            ll_viewline.setVisibility(View.GONE);
            ll_select.setVisibility(View.GONE);
        }else {
            if(s.length()>=6&&s.length()<8){
                view_one.setBackgroundColor(getResources().getColor(R.color.line_one));
                tv_intensity.setText("密码强度低");
                tv_intensity.setVisibility(View.VISIBLE);
            }else if(s.length()>=8&&s.length()<14){
                view_one.setBackgroundColor(getResources().getColor(R.color.line_two));
                view_two.setBackgroundColor(getResources().getColor(R.color.line_two));
                tv_intensity.setText("密码强度中");
                tv_intensity.setVisibility(View.VISIBLE);
            }else if(s.length()>=14&&s.length()<=20){
                view_one.setBackgroundColor(getResources().getColor(R.color.line_three));
                view_two.setBackgroundColor(getResources().getColor(R.color.line_three));
                view_three.setBackgroundColor(getResources().getColor(R.color.line_three));
                tv_intensity.setText("密码强度高");
                tv_intensity.setVisibility(View.VISIBLE);
            }
            ll_viewline.setVisibility(View.VISIBLE);

            if(!s.toString().contains(" ")){
                iv_zw.setVisibility(View.VISIBLE);
                tv_kong.setTextColor(getResources().getColor(R.color.black_1));
            }

            if(s.length()>=6){
                iv_zwt.setVisibility(View.VISIBLE);
                tv_chang.setTextColor(getResources().getColor(R.color.black_1));
            }
            if(CheckUtil.isBigandSmailAndNum(s.toString().trim())){
                iv_zwthre.setVisibility(View.VISIBLE);
                tv_dxx.setTextColor(getResources().getColor(R.color.black_1));
            }
            ll_select.setVisibility(View.VISIBLE);

        }

    }
}
