package com.rmwl.rcchgwd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.rmwl.rcchgwd.R;
import com.rmwl.rcchgwd.Utils.EncryptionTools;
import com.rmwl.rcchgwd.Utils.GsonUtils;
import com.rmwl.rcchgwd.Utils.MyToastUtils;
import com.rmwl.rcchgwd.Utils.RoncheUtil;
import com.rmwl.rcchgwd.Utils.SpUtils;
import com.rmwl.rcchgwd.base.BaseActivity;
import com.rmwl.rcchgwd.bean.ProductInfoBean;
import com.rmwl.rcchgwd.bean.SucessBean;
import com.rmwl.rcchgwd.bean.UserBean;
import com.rmwl.rcchgwd.okhttp.HttpUrl;
import com.rmwl.rcchgwd.okhttp.OkHttpCallBack;
import com.rmwl.rcchgwd.okhttp.OkHttpUtils;
import com.rmwl.rcchgwd.okhttp.RequestParam;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/8/25.
 */

public class RegouAct extends BaseActivity{
    private ProductInfoBean bean;
    @ViewInject(R.id.tv_name)
    private TextView tv_name;
    @ViewInject(R.id.tv_yqsyl)
    private TextView tv_yqsyl;
    @ViewInject(R.id.tv_tzqx)
    private TextView tv_tzqx;
    @ViewInject(R.id.tv_syrg)
    private TextView tv_syrg;
    @ViewInject(R.id.tv_yqsy)
    private TextView tv_yqsy;

    @ViewInject(R.id.tv_zrg)
    private TextView tv_zrg;
    @ViewInject(R.id.tv_zyq)
    private TextView tv_zyq;
    private UserBean user;
    @ViewInject(R.id.et_phone)
    private TextView et_phone;
    @ViewInject(R.id.ch_box)
    private CheckBox ch_box;

    private String amount;
    private SucessBean sucessbean;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_regou);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("预约");
        bean= (ProductInfoBean) getIntent().getExtras().getSerializable("bean");
        if(bean!=null){
            setUi();
        }

        if(!TextUtils.isEmpty(SpUtils.getUser(RegouAct.this))){
            user= GsonUtils.gsonToBean(SpUtils.getUser(RegouAct.this),UserBean.class);
        }

        getHistoty();

        et_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!TextUtils.isEmpty(s.toString())){
                    getLv(s.toString());
                }else {
                    tv_yqsy.setText("0");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void getLv(String str) {
        EncryptionTools.initEncrypMD5(TextUtils.isEmpty(user.getToken())?"":user.getToken());
        RequestParam param=new RequestParam();
        param.add("token", TextUtils.isEmpty(user.getToken())?"":user.getToken());
        param.add("timestamp", EncryptionTools.TIMESTAMP);
        param.add("nonce", EncryptionTools.NONCE);
        param.add("signature", EncryptionTools.SIGNATURE);
        param.add("source","3");
        param.add("proId",bean.getId());
        param.add("amount",str);
        OkHttpUtils.post(HttpUrl.QUERYINTEREST, param, new OkHttpCallBack(RegouAct.this) {
            @Override
            public void onSuccess(String data) {
                try {
                    JSONObject object=new JSONObject(data.toString());
                    JSONObject result=object.getJSONObject("result");
                    String amount=result.getString("amount");
                    String interest=result.getString("interest");
                    if(!TextUtils.isEmpty(interest)){
                        tv_yqsy.setText(interest);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int code, String error) {

            }
        });
    }

    private void getHistoty() {
        EncryptionTools.initEncrypMD5(TextUtils.isEmpty(user.getToken())?"":user.getToken());
        RequestParam param=new RequestParam();
        param.add("token", TextUtils.isEmpty(user.getToken())?"":user.getToken());
        param.add("timestamp", EncryptionTools.TIMESTAMP);
        param.add("nonce", EncryptionTools.NONCE);
        param.add("signature", EncryptionTools.SIGNATURE);
        param.add("source","3");
        param.add("proId",bean.getId());
        OkHttpUtils.post(HttpUrl.PURCHASE_URL, param, new OkHttpCallBack(RegouAct.this) {
            @Override
            public void onSuccess(String data) {
                try {
                    JSONObject object=new JSONObject(data.toString());
                    JSONObject result=object.getJSONObject("result");
                    String amount=result.getString("amount");
                    String interest=result.getString("interest");
                    if(!TextUtils.isEmpty(amount)){
                        tv_zrg.setText(amount);
                    }else {
                        tv_zrg.setText("0.00");
                    }
                    if(!TextUtils.isEmpty(interest)){
                        tv_zyq.setText(amount);
                    }else {
                        tv_zrg.setText("0.00");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int code, String error) {

            }
        });


    }

    private void setUi() {

        if (!TextUtils.isEmpty(bean.getProductName())) {
            tv_name.setText(bean.getProductName());
        }
        if (!TextUtils.isEmpty(bean.getInterest() + "")) {
            tv_yqsyl.setText("预期收益率"+bean.getInterest() + "%");
        }
        if (!TextUtils.isEmpty(bean.getRemcount()+"")) {
            tv_syrg.setText("(剩余预约"+bean.getRemcount()+"万元)");
        }

        if (!TextUtils.isEmpty(bean.getTerms()+"")) {
            tv_tzqx.setText("投资期限"+ RoncheUtil.getRealTerm(bean.getTerms())+bean.getTermTypeName());
        }


    }

    @OnClick({R.id.tv_yy})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.tv_yy:

                amount=et_phone.getText().toString().trim();
                if(TextUtils.isEmpty(amount)){
                    MyToastUtils.showShortToast(RegouAct.this,"请输入");
                    et_phone.requestFocus();
                    return;
                }
                if(!ch_box.isChecked()){
                    MyToastUtils.showShortToast(RegouAct.this,"需同意产品协议");
                    return;
                }

                goBuy();
                break;
        }
    }

    private void goBuy() {
        EncryptionTools.initEncrypMD5(TextUtils.isEmpty(user.getToken())?"":user.getToken());
        RequestParam param=new RequestParam();
        param.add("token", TextUtils.isEmpty(user.getToken())?"":user.getToken());
        param.add("timestamp", EncryptionTools.TIMESTAMP);
        param.add("nonce", EncryptionTools.NONCE);
        param.add("signature", EncryptionTools.SIGNATURE);
        param.add("source","3");
        param.add("proId",bean.getId());
        param.add("amount",amount);

        OkHttpUtils.post(HttpUrl.BUYPRODUCT, param, new OkHttpCallBack(RegouAct.this) {
            @Override
            public void onSuccess(String data) {
                try {
                    JSONObject object=new JSONObject(data.toString());
                    JSONObject result=object.getJSONObject("result");
                    sucessbean=GsonUtils.gsonToBean(result.toString(),SucessBean.class);
                    startActivity(new Intent(RegouAct.this,SucessAct.class).putExtra("bean",sucessbean));
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onError(int code, String error) {
             MyToastUtils.showShortToast(RegouAct.this,error);
            }
        });

    }


}
