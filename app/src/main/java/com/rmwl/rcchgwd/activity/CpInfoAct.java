package com.rmwl.rcchgwd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
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
import com.rmwl.rcchgwd.bean.UserBean;
import com.rmwl.rcchgwd.okhttp.HttpUrl;
import com.rmwl.rcchgwd.okhttp.OkHttpCallBack;
import com.rmwl.rcchgwd.okhttp.OkHttpUtils;
import com.rmwl.rcchgwd.okhttp.RequestParam;
import com.rmwl.rcchgwd.view.CommonView;
import com.rmwl.rcchgwd.view.InfoView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by acer on 2018/8/22.
 */

public class CpInfoAct extends BaseActivity {
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.tv_sy)
    private TextView tv_sy;
    @ViewInject(R.id.tv_syrs)
    private TextView tv_syrs;
    @ViewInject(R.id.tv_syje)
    private TextView tv_syje;
    @ViewInject(R.id.com_cpqx)
    private CommonView com_cpqx;
    @ViewInject(R.id.com_hkfs)
    private CommonView com_hkfs;
    @ViewInject(R.id.com_cpgm)
    private CommonView com_cpgm;
    @ViewInject(R.id.com_dczcjz)
    private CommonView com_dczcjz;
    @ViewInject(R.id.com_dkks)
    private CommonView com_dkks;
    @ViewInject(R.id.com_dkjz)
    private CommonView com_dkjz;
    @ViewInject(R.id.com_syqsr)
    private CommonView com_syqsr;
    @ViewInject(R.id.com_dqrq)
    private CommonView com_dqrq;
    @ViewInject(R.id.com_rgsm)
    private CommonView com_rgsm;
    @ViewInject(R.id.tv_zjyt)
    private TextView tv_zjyt;
    @ViewInject(R.id.tv_hkly)
    private TextView tv_hkly;
    @ViewInject(R.id.tv_rg)
    private TextView tv_rg;
    @ViewInject(R.id.info_view)
    private InfoView info_view;
    @ViewInject(R.id.tv_msg)
    private TextView tv_msg;//支付结束、计息开始时间说明
    @ViewInject(R.id.tv_cpqc)
    private TextView tv_cpqc;//产品全称


    private String proId;
    private Gson gson;
    private ProductInfoBean bean;
    private UserBean user;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_cpinfo);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.pro_color));
        proId = getIntent().getExtras().getString("proId");
        if(!TextUtils.isEmpty(SpUtils.getUser(CpInfoAct.this))){
            user= GsonUtils.gsonToBean(SpUtils.getUser(CpInfoAct.this),UserBean.class);
        }
        gson = new Gson();
        if (!TextUtils.isEmpty(proId)) {
            getDate();//获取数据
        }
    }

    private void getDate() {
        EncryptionTools.initEncrypMD5(TextUtils.isEmpty(user.getToken())?"":user.getToken());
        RequestParam param=new RequestParam();
        param.add("token", TextUtils.isEmpty(user.getToken())?"":user.getToken());
        param.add("timestamp", EncryptionTools.TIMESTAMP);
        param.add("nonce", EncryptionTools.NONCE);
        param.add("signature", EncryptionTools.SIGNATURE);
        param.add("source","3");
        param.add("proId", proId);
        OkHttpUtils.post(HttpUrl.INFO_URL, param, new OkHttpCallBack(CpInfoAct.this) {
            @Override
            public void onSuccess(String data) {
                try {
                    JSONObject object = new JSONObject(data.toString());
                    JSONObject result = object.getJSONObject("result");
                    bean = gson.fromJson(result.toString(), ProductInfoBean.class);
                    if (bean != null) {
                        setUi(bean);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(int code, String error) {
                MyToastUtils.showShortToast(CpInfoAct.this,error);
            }
        });
    }
    //判断是否加小数点
    private String getValue(String value){
        String str="";
        if (!TextUtils.isEmpty(value)){
            str = "."+value;
        }else {
            str=value;
        }
        return str;
    }
    //判断是否加百分号
    private String getPersent(String value){
        String str="";
        if (!TextUtils.isEmpty(value)){
            str = value+"%";
        }else {
            str=value;
        }
        return str;
    }
    private void setUi(ProductInfoBean bean) {
        if (!TextUtils.isEmpty(bean.getProductName())) {
            tv_title.setText(bean.getProductName());
        }
        if (!TextUtils.isEmpty(bean.getProductFullName()))
            tv_cpqc.setText(bean.getProductFullName());
        String minBaseRateInteger = bean.getMinBaseRateInteger();
        String minBaseRateDecimal = bean.getMinBaseRateDecimal();
        String minAddedRateInteger = bean.getMinAddedRateInteger();
        String minAddedRateDecimal = bean.getMinAddedRateDecimal();
        String maxBaseRateInteger = bean.getMaxBaseRateInteger();
        String maxBaseRateDecimal = bean.getMaxBaseRateDecimal();
        String maxAddedRateInteger = bean.getMaxAddedRateInteger();
        String maxAddedRateDecimal = bean.getMaxAddedRateDecimal();
        //判断要修改字体大小的起始位置和结束位置
        SpannableStringBuilder spannableString = new SpannableStringBuilder();
        String minBeforePart="",minAfterPart="",maxBeforePart="",maxAfterPart="",minPart="",maxPart="";//低利率部分,高利率部分

        minBeforePart = getPersent(minBaseRateInteger + getValue(minBaseRateDecimal) );
        minAfterPart = getPersent(minAddedRateInteger + getValue(minAddedRateDecimal));
        maxBeforePart = getPersent(maxBaseRateInteger + getValue(maxBaseRateDecimal) );
        maxAfterPart = getPersent(maxAddedRateInteger + getValue(maxAddedRateDecimal)) ;
        if (!TextUtils.isEmpty(minAfterPart)){
            minPart = minBeforePart + "+" +minAfterPart;
        }else {
            minPart = minBeforePart;
        }
        if (!TextUtils.isEmpty(maxAfterPart)){
            maxPart = maxBeforePart+"+"+maxAfterPart;
            minPart = minPart+"~";
        }else {
            maxPart = maxBeforePart;
            if (!TextUtils.isEmpty(maxPart))
                minPart = minPart+"~";
        }
        spannableString.append(minPart);
        spannableString.append(maxPart);
        AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan(60);
        if (!TextUtils.isEmpty(minPart))
            spannableString.setSpan(absoluteSizeSpan, minBaseRateInteger.length(), minPart.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        AbsoluteSizeSpan absoluteSizeSpan2 = new AbsoluteSizeSpan(60);
        if (!TextUtils.isEmpty(maxPart))
            spannableString.setSpan(absoluteSizeSpan2, minPart.length()+maxBaseRateInteger.length(), spannableString.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        tv_sy.setText(spannableString);
//        if (!TextUtils.isEmpty(bean.getInterest() + "")) {
//
//
//            tv_sy.setText(bean.getInterest() + "%");
//        }
        if (!TextUtils.isEmpty(bean.getRemcount()+"")) {
            tv_syrs.setText(bean.getRemcount()+"人");
        }
        if (!TextUtils.isEmpty(bean.getRemamount()+"")) {
            tv_syje.setText(bean.getRemamount()+"万元");
        }

        if (!TextUtils.isEmpty(bean.getTerms()+"")) {
            com_cpqx.setNotesText(RoncheUtil.getRealTerm(bean.getTerms())+bean.getTermTypeName());
        }

        if (!TextUtils.isEmpty(bean.getBenefitModeName()+"")) {
            com_hkfs.setNotesText(bean.getBenefitModeName()+"");
        }

        if (!TextUtils.isEmpty(bean.getAmount()+"")) {
            com_cpgm.setNotesText(bean.getAmount()+"万元");
            com_cpgm.setTvNotesBottomVisiable(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(bean.getBaseCost()+"")) {
            com_dczcjz.setNotesText(bean.getBaseCost()+"万元");
        }
        if (!TextUtils.isEmpty(bean.getPayBegindate()+"")) {
            com_dkks.setNotesText(bean.getPayBegindate()+"");
        }
        if (!TextUtils.isEmpty(bean.getPayEdndate()+"")) {
            com_dkjz.setNotesText(bean.getPayEdndate()+"");
        }
        if (!TextUtils.isEmpty(bean.getInterestBegindate()+"")) {
            com_syqsr.setNotesText(bean.getInterestBegindate()+"");
        }

        if (!TextUtils.isEmpty(bean.getInterestEnddate()+"")) {
            com_dqrq.setNotesText(bean.getInterestEnddate()+"");
        }

        if (!TextUtils.isEmpty(bean.getStartAmount()+"")||!TextUtils.isEmpty(bean.getStepAmount()+"")) {
            com_rgsm.setNotesText("单笔"+bean.getvStartAmount()+"万起投"+","+bean.getvStepAmount()+"万递增");
        }

        if (TextUtils.isEmpty(bean.getPayInterNotes())){
            tv_msg.setVisibility(View.GONE);
        }else {
            tv_msg.setVisibility(View.VISIBLE);
            tv_msg.setText(bean.getPayInterNotes());
        }

        if (!TextUtils.isEmpty(bean.getFundsUse()+"")) {
            tv_zjyt.setText(bean.getFundsUse()+"");
        }

        if (!TextUtils.isEmpty(bean.getRepaymentSocuce()+"")) {
            tv_hkly.setText(bean.getRepaymentSocuce()+"");
        }

        if(bean.getProdIntervalratelist()!=null&&bean.getProdIntervalratelist().size()>0) {
            info_view.init(bean.getProdIntervalratelist());//新加的东西
        }

//        if(!TextUtils.isEmpty(bean.getStatus())){
//            if(bean.getStatus().equals("1")){//销售
//                tv_rg.setVisibility(View.VISIBLE);
//            }else {
//                tv_rg.setVisibility(View.GONE);
//            }
//        }



    }

    @OnClick({R.id.iv_back, R.id.com_cpxq,R.id.tv_rg})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.com_cpxq://产品详情
                startActivity(new Intent(CpInfoAct.this,ProductDetailAct.class).putExtra("infoBean",bean));
//               startActivity(new Intent(CpInfoAct.this,WebAct.class).putExtra("type","1").putExtra("proId",proId));
                break;

            case R.id.tv_rg:
                startActivity(new Intent(CpInfoAct.this,RegouAct.class).putExtra("bean",bean));
                break;

        }
    }
}
