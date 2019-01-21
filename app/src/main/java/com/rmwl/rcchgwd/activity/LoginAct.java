package com.rmwl.rcchgwd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.rmwl.rcchgwd.MainActivity;
import com.rmwl.rcchgwd.MyApplication;
import com.rmwl.rcchgwd.R;
import com.rmwl.rcchgwd.Utils.CheckUtil;
import com.rmwl.rcchgwd.Utils.GsonUtils;
import com.rmwl.rcchgwd.Utils.MyToastUtils;
import com.rmwl.rcchgwd.Utils.SpUtils;
import com.rmwl.rcchgwd.base.AppManager;
import com.rmwl.rcchgwd.base.BaseActivity;
import com.rmwl.rcchgwd.bean.UserBean;
import com.rmwl.rcchgwd.jpush.TagAliasOperatorHelper;
import com.rmwl.rcchgwd.okhttp.HttpUrl;
import com.rmwl.rcchgwd.okhttp.OkHttpCallBack;
import com.rmwl.rcchgwd.okhttp.OkHttpUtils;
import com.rmwl.rcchgwd.okhttp.RequestParam;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;

import static com.rmwl.rcchgwd.jpush.TagAliasOperatorHelper.ACTION_SET;
import static com.rmwl.rcchgwd.jpush.TagAliasOperatorHelper.sequence;

/**
 * Created by acer on 2018/8/22.
 */

public class LoginAct extends BaseActivity {
    @ViewInject(R.id.et_phone)
    private EditText et_phone;
    @ViewInject(R.id.et_mm)
    private EditText et_mm;
    @ViewInject(R.id.tv_zc)
    private TextView tv_zc;
    @ViewInject(R.id.iv_showorno)
    private ImageView iv_showorno;

    private String phone, pwd;
    private Gson gson;

    private UserBean userBean;
    private boolean isshow = false;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_login);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);
        gson = new Gson();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.isLogin = true;

        if (!TextUtils.isEmpty(SpUtils.getPhone(LoginAct.this))) {
            et_phone.setText(SpUtils.getPhone(LoginAct.this));
            et_mm.setText(SpUtils.getPwd(LoginAct.this));
            phone = et_phone.getText().toString().trim();
            pwd = et_mm.getText().toString().trim();
            et_phone.setSelection(phone.length());
            et_mm.setSelection(pwd.length());
//            toLogin();
        }
    }



    @OnClick({R.id.tv_login, R.id.tv_wjmm, R.id.tv_zc, R.id.iv_showorno})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                if (isVisible()) {
                    toLogin();
                }
                break;
            case R.id.tv_wjmm:
                openActivity(ForgetAct.class);
                break;
            case R.id.tv_zc:
                openActivity(RegistAct.class);
                break;
            case R.id.iv_showorno:
                if (!isshow) {
                    isshow = true;
                    iv_showorno.setImageResource(R.mipmap.bigyanjing);
                    CheckUtil.showOrHide(true, et_mm);
                } else {
                    isshow = false;
                    iv_showorno.setImageResource(R.mipmap.yanjing);
                    CheckUtil.showOrHide(false, et_mm);
                }
                break;
        }
    }

    private void toLogin() {
        RequestParam param = new RequestParam();
        param.add("source", "3");
        param.add("mobile", phone);
        param.add("password", pwd);
        OkHttpUtils.post(HttpUrl.LOGIN, param, new OkHttpCallBack(LoginAct.this) {
            @Override
            public void onSuccess(String data) {
                try {
                    JSONObject object = new JSONObject(data.toString());
                    JSONObject result = object.getJSONObject("result");
                    userBean = gson.fromJson(result.toString(), UserBean.class);
                    SpUtils.setPhone(LoginAct.this, phone);
                    SpUtils.setPwd(LoginAct.this, pwd);
                    SpUtils.setUser(LoginAct.this, GsonUtils.bean2Json(userBean));
                    SpUtils.setToken(LoginAct.this, userBean.getToken());
                    SpUtils.setStatus(LoginAct.this, userBean.getStatus());
                    SpUtils.setUserId(LoginAct.this,userBean.getId());

//                    if(userBean.getStatus().equals("2")) {//认证失败
//                        openActivity(AuthenticationAct.class);
//                    }else if(userBean.getStatus().equals("0")){
////                        openActivity(MainActivity.class);
////                        finish();
//                        MyToastUtils.showShortToast(LoginAct.this,"等待客服审核员工身份信息");
//                    }else if(userBean.getStatus().equals("1")){//认证成功
//                            openActivity(MainActivity.class);
//                            finish();
//                    }

                    if (userBean.getIfRealname().equals("0")) {//未实名
                        openActivity(AuthenticationAct.class);
                    } else {//实名
                        if (userBean.getStatus().equals("2")) {//认证失败
//                            openActivity(AuthenticationAct.class);
                            startActivity(new Intent(LoginAct.this,AuditAct.class).putExtra("notes",userBean.getNotes()+"").putExtra("id",userBean.getId()));
                        } else if (userBean.getStatus().equals("0")) {//待审核
                            MyToastUtils.showShortToast(LoginAct.this, "等待客服审核员工身份信息");
                        } else if (userBean.getStatus().equals("1")) {//认证成功
                            openActivity(MainActivity.class);
                            finish();
                        }
                    }


//                    openActivity(MainActivity.class);
//                    finish();

//                    setAlias();
                } catch (JSONException e) {
                    e.printStackTrace();
                    MyToastUtils.showShortToast(LoginAct.this, "解析异常");
                }

            }

            @Override
            public void onError(int code, String error) {
                MyToastUtils.showShortToast(LoginAct.this, error);
            }
        });
    }

    boolean isAliasAction = false;
    int action = -1;

    //激光推送设置别名
    private void setAlias() {
        Set<String> tags = null;
        String alias = userBean.getToken();
        isAliasAction = true;
        action = ACTION_SET;
        TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
        tagAliasBean.action = action;
        sequence++;
        if (isAliasAction) {
            tagAliasBean.alias = alias;
        } else {
            tagAliasBean.tags = tags;
        }
        tagAliasBean.isAliasAction = isAliasAction;
        TagAliasOperatorHelper.getInstance().handleAction(getApplicationContext(), sequence, tagAliasBean);
    }


    private boolean isVisible() {
        phone = et_phone.getText().toString().trim();
        pwd = et_mm.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            et_phone.requestFocus();
            MyToastUtils.showShortToast(LoginAct.this, "请输入手机号");
            return false;
        }
        if (TextUtils.isEmpty(pwd)) {
            et_mm.requestFocus();
            MyToastUtils.showShortToast(LoginAct.this, "请输入密码");
            return false;
        }

        if (!CheckUtil.isMobile(phone)) {
            et_phone.requestFocus();
            et_phone.setSelection(phone.length());
            MyToastUtils.showShortToast(LoginAct.this, "请输入11位正确格式手机号");
            return false;
        }

        if (!CheckUtil.isPwd(pwd)) {
            et_mm.requestFocus();
            et_mm.setSelection(pwd.length());
            MyToastUtils.showShortToast(LoginAct.this, "请输入6~20位数字和字母");
            return false;
        }

        return true;
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.isLogin = false;
    }
}
