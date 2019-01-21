package com.rmwl.rcchgwd.activity;

import android.content.Intent;
import android.os.Bundle;
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
import com.rmwl.rcchgwd.MyApplication;
import com.rmwl.rcchgwd.R;
import com.rmwl.rcchgwd.Utils.CheckUtil;
import com.rmwl.rcchgwd.Utils.EncryptionTools;
import com.rmwl.rcchgwd.Utils.GsonUtils;
import com.rmwl.rcchgwd.Utils.MyToastUtils;
import com.rmwl.rcchgwd.Utils.SpUtils;
import com.rmwl.rcchgwd.base.AppManager;
import com.rmwl.rcchgwd.base.BaseActivity;
import com.rmwl.rcchgwd.bean.UserBean;
import com.rmwl.rcchgwd.okhttp.HttpUrl;
import com.rmwl.rcchgwd.okhttp.OkHttpCallBack;
import com.rmwl.rcchgwd.okhttp.OkHttpUtils;
import com.rmwl.rcchgwd.okhttp.RequestParam;
import com.rmwl.rcchgwd.view.MyAlertDialog;

/**
 * Created by acer on 2018/8/23.
 */

public class UpdatePwdAct extends BaseActivity {
    @ViewInject(R.id.et_oldpwd)
    private EditText et_oldpwd;
    @ViewInject(R.id.et_newpwd)
    private EditText et_newpwd;
    @ViewInject(R.id.et_senewpwd)
    private EditText et_senewpwd;

    @ViewInject(R.id.iv_showorno)
    private ImageView iv_showorno;
    @ViewInject(R.id.iv_newyanjing)
    private ImageView iv_newyanjing;
    @ViewInject(R.id.iv_seneyanjing)
    private ImageView iv_seneyanjing;



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

    private String oldpwd,newpwd,senewpwd;
    private UserBean user;

    private boolean isshow=false;
    private boolean isnewshow=false;
    private boolean issecshow=false;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_updatepwd);
    }

    @Override
    public void init(Bundle savedInstanceState) {
      setTopTitle("修改登录密码");
      if(!TextUtils.isEmpty(SpUtils.getUser(UpdatePwdAct.this))){
          user=GsonUtils.gsonToBean(SpUtils.getUser(UpdatePwdAct.this),UserBean.class);
      }

        et_newpwd.addTextChangedListener(new MyTextChangedListener());
        CheckUtil.showOrHide(false,et_oldpwd);
        CheckUtil.showOrHide(false,et_newpwd);
        CheckUtil.showOrHide(false,et_senewpwd);
    }

    @OnClick({R.id.tv_qd,R.id.iv_showorno,R.id.iv_newyanjing,R.id.iv_seneyanjing})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.tv_qd:
                if(isVisible()){
                    goUpdatePwd();
                }
                break;

            case R.id.iv_showorno:
                if(!isshow){
                    isshow=true;
                    iv_showorno.setImageResource(R.mipmap.bigyanjing);
                    CheckUtil.showOrHide(true,et_oldpwd);
                }else {
                    isshow=false;
                    iv_showorno.setImageResource(R.mipmap.yanjing);
                    CheckUtil.showOrHide(false,et_oldpwd);
                }
                break;

            case R.id.iv_newyanjing:
                if(!isnewshow){
                    isnewshow=true;
                    iv_newyanjing.setImageResource(R.mipmap.bigyanjing);
                    CheckUtil.showOrHide(true,et_newpwd);
                }else {
                    isnewshow=false;
                    iv_newyanjing.setImageResource(R.mipmap.yanjing);
                    CheckUtil.showOrHide(false,et_newpwd);
                }
                break;

            case R.id.iv_seneyanjing:
                if(!issecshow){
                    issecshow=true;
                    iv_seneyanjing.setImageResource(R.mipmap.bigyanjing);
                    CheckUtil.showOrHide(true,et_senewpwd);
                }else {
                    issecshow=false;
                    iv_seneyanjing.setImageResource(R.mipmap.yanjing);
                    CheckUtil.showOrHide(false,et_senewpwd);
                }
                break;
        }
    }

    private void goUpdatePwd() {
        //每次实时申城nonce,signature,nonce
        EncryptionTools.initEncrypMD5(TextUtils.isEmpty(user.getToken())?"":user.getToken());
        RequestParam param=new RequestParam();
        param.add("token", TextUtils.isEmpty(user.getToken())?"":user.getToken());
        param.add("timestamp", EncryptionTools.TIMESTAMP);
        param.add("nonce", EncryptionTools.NONCE);
        param.add("signature", EncryptionTools.SIGNATURE);
        param.add("source","3");
        param.add("password",oldpwd);
        param.add("passwordNew",newpwd);
        param.add("passwordConfirm",senewpwd);
        OkHttpUtils.post(HttpUrl.UPDATEPWD_URL, param, new OkHttpCallBack(UpdatePwdAct.this) {
            @Override
            public void onSuccess(String data) {
//                MyToastUtils.showShortToast(UpdatePwdAct.this,"密码重置成功");
//                finish();
                showDia();//

            }

            @Override
            public void onError(int code, String error) {
                MyToastUtils.showShortToast(UpdatePwdAct.this,error);

            }
        });

    }

    private void showDia() {
        MyAlertDialog dialog=new MyAlertDialog(UpdatePwdAct.this).builder();
        dialog.setTitle("提示");
        dialog.setMsg("密码重置成功,\n请重新登录!");
        dialog.setPositiveButton("确认", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.getAppManager().finishActivity(SettingAct.class);
                startActivity(new Intent( UpdatePwdAct.this,LoginAct.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                SpUtils.clearSp(UpdatePwdAct.this);
                finish();
            }
        });
        dialog.show();
    }

    private boolean isVisible() {
        oldpwd=et_oldpwd.getText().toString().trim();
        newpwd=et_newpwd.getText().toString().trim();
        senewpwd=et_senewpwd.getText().toString().trim();


        if(TextUtils.isEmpty(oldpwd)){
            et_oldpwd.requestFocus();
            MyToastUtils.showShortToast(UpdatePwdAct.this,"请输入旧密码");
            return false;
        }
        if(TextUtils.isEmpty(newpwd)){
            et_newpwd.requestFocus();
            MyToastUtils.showShortToast(UpdatePwdAct.this,"请输入新密码");
            return false;
        }
        if(TextUtils.isEmpty(senewpwd)){
            et_senewpwd.requestFocus();
            MyToastUtils.showShortToast(UpdatePwdAct.this,"请确认密码");
            return false;
        }

        if(!CheckUtil.isPwd(newpwd)){
            et_newpwd.requestFocus();
            et_newpwd.setSelection(newpwd.length());
            MyToastUtils.showShortToast(UpdatePwdAct.this,"请输入6~20位数字和字母");
            return false;
        }

        if(!newpwd.equals(senewpwd)){
            et_senewpwd.requestFocus();
//            et_senewpwd.setSelection(senewpwd.length());
            et_senewpwd.setText("");
            MyToastUtils.showShortToast(UpdatePwdAct.this,"两次输入密码不一致,请重新确认密码");
            return false;
        }

        return true;
    }

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

