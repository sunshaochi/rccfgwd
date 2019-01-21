package com.rmwl.rcchgwd.activity;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.rmwl.rcchgwd.MainActivity;
import com.rmwl.rcchgwd.R;
import com.rmwl.rcchgwd.Utils.CheckUtil;
import com.rmwl.rcchgwd.Utils.EncryptionTools;
import com.rmwl.rcchgwd.Utils.GsonUtils;
import com.rmwl.rcchgwd.Utils.MyToastUtils;
import com.rmwl.rcchgwd.Utils.SpUtils;
import com.rmwl.rcchgwd.base.AppManager;
import com.rmwl.rcchgwd.base.BaseActivity;
import com.rmwl.rcchgwd.bean.CretifyBean;
import com.rmwl.rcchgwd.bean.UserBean;
import com.rmwl.rcchgwd.okhttp.HttpUrl;
import com.rmwl.rcchgwd.okhttp.OkHttpCallBack;
import com.rmwl.rcchgwd.okhttp.OkHttpUtils;
import com.rmwl.rcchgwd.okhttp.RequestParam;
import com.rmwl.rcchgwd.view.MyAlertDialog;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by acer on 2018/8/24.
 */

public class AuthenticationAct extends BaseActivity {
    @ViewInject(R.id.et_name)
    private EditText et_name;
    @ViewInject(R.id.tv_zjlx)
    private TextView tv_zjlx;
    @ViewInject(R.id.et_zjhm)
    private EditText et_zjhm;
    @ViewInject(R.id.ll_zjlx)
    private LinearLayout ll_zjlx;
    @ViewInject(R.id.iv_jt)
    private ImageView iv_jt;

    private String name;
    private String idtype="1";//默认等于1
    private String idno;
    private UserBean user;
    private PopupWindow popupWindow;
    private CretifyBean bean;


    @Override
    public void setLayout() {
        setContentView(R.layout.act_authenication);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("实名认证");
        if (!TextUtils.isEmpty(SpUtils.getUser(AuthenticationAct.this))) {
            user = GsonUtils.gsonToBean(SpUtils.getUser(AuthenticationAct.this), UserBean.class);
        }
        ll_zjlx.setEnabled(false);
        getCertify();//获取认证信息
    }

    @OnClick({R.id.tv_qd, R.id.ll_zjlx})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_qd:
                if (isVisible()) {
                    goShiming();
                }
                break;

            case R.id.ll_zjlx:
                showPopu();
                break;
        }
    }

    private void showPopu() {
        iv_jt.setImageResource(R.mipmap.comxx);
        LinearLayout layout = (LinearLayout) LayoutInflater.from(AuthenticationAct.this).inflate(
                R.layout.popuwindows, null);
        final TextView tv_sfz = layout.findViewById(R.id.tv_sfz);
        final TextView tv_hz = layout.findViewById(R.id.tv_hz);
        tv_sfz.setTextColor(getResources().getColor(R.color.black_2));
        tv_hz.setTextColor(getResources().getColor(R.color.black_2));
        tv_sfz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_sfz.setTextColor(getResources().getColor(R.color.black_1));
                idtype = "1";
                tv_zjlx.setText("身份证");
                popupWindow.dismiss();
            }
        });

        tv_hz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_hz.setTextColor(getResources().getColor(R.color.black_1));
                idtype = "2";
                tv_zjlx.setText("护照");
                popupWindow.dismiss();
            }
        });

        // 实例化popupWindow
        popupWindow = new PopupWindow(layout, AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        //控制键盘是否可以获得焦点
        popupWindow.setFocusable(true);
        //设置popupWindow弹出窗体的背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable(null, ""));
        popupWindow.showAsDropDown(ll_zjlx);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                iv_jt.setImageResource(R.mipmap.myfraright);
            }
        });


    }

    private boolean isVisible() {
        name = et_name.getText().toString().trim();
        idno = et_zjhm.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            et_name.requestFocus();
            MyToastUtils.showShortToast(AuthenticationAct.this, "请输入姓名");
            return false;
        }
        if (!CheckUtil.isLegalName(name)){
            et_name.requestFocus(name.length());
            MyToastUtils.showShortToast(AuthenticationAct.this,"请输入正确的姓名");
            return false;
        }
        if (TextUtils.isEmpty(idtype)) {
            MyToastUtils.showShortToast(AuthenticationAct.this, "请选择证件类型");
            return false;
        }
        if (TextUtils.isEmpty(idno)) {
            et_zjhm.requestFocus();
            MyToastUtils.showShortToast(AuthenticationAct.this, "请输入证件号码");
            return false;
        }

        if (!CheckUtil.checkIdCard(idno)){
            et_zjhm.requestFocus(idno.length());
            MyToastUtils.showShortToast(AuthenticationAct.this, "请输入正确的证件号码");
            return false;
        }
        return true;
    }

    private void goShiming() {
//        EncryptionTools.initEncrypMD5(TextUtils.isEmpty(user.getToken()) ? "" : user.getToken());
        RequestParam param = new RequestParam();
//        param.add("token", TextUtils.isEmpty(user.getToken()) ? "" : user.getToken());
//        param.add("timestamp", EncryptionTools.TIMESTAMP);
//        param.add("nonce", EncryptionTools.NONCE);
//        param.add("signature", EncryptionTools.SIGNATURE);
        param.add("createSource", "3");
        param.add("idtype", idtype);
        param.add("realname", name);
        param.add("idno", idno);//证件号码
        OkHttpUtils.post(HttpUrl.SAVEREALNAME_URL, param, new OkHttpCallBack(AuthenticationAct.this) {
            @Override
            public void onSuccess(String data) {
//                try {
//                    JSONObject object = new JSONObject(data.toString());
//                    JSONObject result = object.getJSONObject("result");
//                    UserBean userBean = GsonUtils.gsonToBean(result.toString(), UserBean.class);
//                    SpUtils.setUser(AuthenticationAct.this, GsonUtils.bean2Json(userBean));
//                    openActivity(MainActivity.class);
//                    finish();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
                showDia(1,"等待客服审核员工身份信息");//
            }

            @Override
            public void onError(int code, String error) {
//                MyToastUtils.showShortToast(AuthenticationAct.this, error);
                showDia(2,error);//
            }
        });

    }

    private void showDia(final int i, String tex) {
        final MyAlertDialog dialog=new MyAlertDialog(AuthenticationAct.this).builder();
        if(i==1) {
            dialog.setTitle("认证成功");
            dialog.setMsg(tex);
        }else {
            dialog.setTitle("认证失败");
            if(!TextUtils.isEmpty(tex)) {
                dialog.setMsg(tex);
            }else {
                dialog.setMsg("信息不匹配");
            }
        }
        dialog.setPositiveButton("确认", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i==1){
                    finish();
                }else {

                }
            }
        });

        dialog.show();
    }


    private void getCertify() {
        EncryptionTools.initEncrypMD5(TextUtils.isEmpty(user.getToken()) ? "" : user.getToken());
        RequestParam param = new RequestParam();
        param.add("token", TextUtils.isEmpty(user.getToken()) ? "" : user.getToken());
        param.add("timestamp", EncryptionTools.TIMESTAMP);
        param.add("nonce", EncryptionTools.NONCE);
        param.add("signature", EncryptionTools.SIGNATURE);
        param.add("source", "3");
        OkHttpUtils.post(HttpUrl.USERREALNAME_URL, param, new OkHttpCallBack(AuthenticationAct.this) {
            @Override
            public void onSuccess(String data) {
                if(!TextUtils.isEmpty(data)) {
                    try {
                        JSONObject object = new JSONObject(data.toString());
                        JSONObject result = object.getJSONObject("result");
                        if (!TextUtils.isEmpty(result.toString()))
                            bean = GsonUtils.gsonToBean(result.toString(), CretifyBean.class);
                        if (bean.getStatus().equals("0")) {//未认证

                        } else if (bean.getStatus().equals("1")) {//成功
//                        SpUtils.setCretify(AuthenticationAct.this,GsonUtils.bean2Json(bean));
//                        AppManager.getAppManager().finishActivity(LoginAct.class);
//                        finish();
                            showDia(1, "等待客服审核员工身份信息");//
                        } else if (bean.getStatus().equals("2")) {//失败
                            String failreason = bean.getFailreason();
//                     MyToastUtils.showLongToast(AuthenticationAct.this,"认证失败"+"\\n"+failreason);
                            showDia(2, failreason);
                        }
                        setUi(bean);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onError(int code, String error) {
                MyToastUtils.showShortToast(AuthenticationAct.this, error);
            }
        });
    }

    private void setUi(CretifyBean bean) {
        if(!TextUtils.isEmpty(bean.getRealname())) {
            et_name.setText(bean.getRealname());
        }else {
            et_name.setHint("请输入您真实姓名");
        }
        if(!TextUtils.isEmpty(bean.getIdtype())) {
            if(bean.getIdtype().equals("1")){
                tv_zjlx.setText("身份证");
                idtype="1";
            }else {
                tv_zjlx.setText("护照");
                idtype="2";
            }
        }else {
            idtype="1";
            tv_zjlx.setText("身份证");
        }
        if(!TextUtils.isEmpty(bean.getIdno())) {
           et_zjhm.setText(bean.getIdno());
        }else {
            et_zjhm.setHint("请输入您的证件号码");
        }
    }
}
