package com.rmwl.rcchgwd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.rmwl.rcchgwd.PdfAct;
import com.rmwl.rcchgwd.R;
import com.rmwl.rcchgwd.Utils.EncryptionTools;
import com.rmwl.rcchgwd.Utils.GsonUtils;
import com.rmwl.rcchgwd.Utils.MyToastUtils;
import com.rmwl.rcchgwd.Utils.RoncheUtil;
import com.rmwl.rcchgwd.Utils.SpUtils;
import com.rmwl.rcchgwd.base.BaseActivity;
import com.rmwl.rcchgwd.bean.InvestBean;
import com.rmwl.rcchgwd.bean.ProductInfoBean;
import com.rmwl.rcchgwd.bean.UserBean;
import com.rmwl.rcchgwd.okhttp.HttpUrl;
import com.rmwl.rcchgwd.okhttp.OkHttpCallBack;
import com.rmwl.rcchgwd.okhttp.OkHttpUtils;
import com.rmwl.rcchgwd.okhttp.RequestParam;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 产品详情
 * Created by Administrator on 2019/1/4.
 */

public class ProductDetailAct extends BaseActivity {

    @ViewInject(R.id.ll_base)
    private LinearLayout ll_base;//基本信息
    @ViewInject(R.id.ll_message)
    private LinearLayout ll_message;//信息披露
    @ViewInject(R.id.tv_base)
    private TextView tv_base;
    @ViewInject(R.id.view_base)
    private View view_base;
    @ViewInject(R.id.tv_message)
    private TextView tv_message;
    @ViewInject(R.id.view_message)
    private View view_message;


    //基本信息view
    @ViewInject(R.id.base_tv_listing_party)
    private TextView base_tv_listing_party;
    @ViewInject(R.id.base_tv_establish)
    private TextView base_tv_establish;
    @ViewInject(R.id.base_tv_registered_capital)
    private TextView base_tv_registered_capital;
    @ViewInject(R.id.base_tv_industry)
    private TextView base_tv_industry;
    @ViewInject(R.id.base_iv_qualification_certification)
    private ImageView base_iv_qualification_certification;
    @ViewInject(R.id.base_tv_summary)
    private TextView base_tv_summary;
    @ViewInject(R.id.base_tv_underlying_asset)
    private TextView base_tv_underlying_asset;
    @ViewInject(R.id.base_tv_file)
    private TextView base_tv_file;
    @ViewInject(R.id.base_tv_wind_control)
    private TextView base_tv_wind_control;
    @ViewInject(R.id.base_tv_credit_enhancement)
    private TextView base_tv_credit_enhancement;

    //信息披露view
    @ViewInject(R.id.msg_tv_zpf_name)
    private TextView msg_tv_zpf_name;
    @ViewInject(R.id.msg_tv_zpf_register_time)
    private TextView msg_tv_zpf_register_time;
    @ViewInject(R.id.msg_tv_zpf_registered_capital)
    private TextView msg_tv_zpf_registered_capital;
    @ViewInject(R.id.msg_tv_zpf_industry)
    private TextView msg_tv_zpf_industry;
    @ViewInject(R.id.msg_iv_zpf_qualification_certification)
    private ImageView msg_iv_zpf_qualification_certification;
    @ViewInject(R.id.msg_tv_summary)
    private TextView msg_tv_summary;
    @ViewInject(R.id.msg_tv_gpjg_name)
    private TextView msg_tv_gpjg_name;
    @ViewInject(R.id.msg_tv_gpjg_register_time)
    private TextView msg_tv_gpjg_register_time;
    @ViewInject(R.id.msg_tv_gpjg_registered_capital)
    private TextView msg_tv_gpjg_registered_capital;
    @ViewInject(R.id.msg_tv_gpjg_industry)
    private TextView msg_tv_gpjg_industry;
    @ViewInject(R.id.msg_iv_gpjg_qualification_certification)
    private ImageView msg_iv_gpjg_qualification_certification;
    @ViewInject(R.id.msg_tv_gpjg_file)
    private TextView msg_tv_gpjg_file;
    @ViewInject(R.id.msg_iv_gpjg_ownership_structure)
    private ImageView msg_iv_gpjg_ownership_structure;
    @ViewInject(R.id.msg_tv_gpjg_summary)
    private TextView msg_tv_gpjg_summary;
    @ViewInject(R.id.msg_iv_gpjg_transaction_structure)
    private ImageView msg_iv_gpjg_transaction_structure;
    @ViewInject(R.id.msg_tv_company_info)
    private TextView msg_tv_company_info;
    @ViewInject(R.id.msg_tv_glr_name)
    private TextView msg_tv_glr_name;
    @ViewInject(R.id.msg_tv_glr_register_time)
    private TextView msg_tv_glr_register_time;
    @ViewInject(R.id.msg_tv_glr_registered_capital)
    private TextView msg_tv_glr_registered_capital;
    @ViewInject(R.id.msg_tv_glr_industry)
    private TextView msg_tv_glr_industry;
    @ViewInject(R.id.msg_iv_glr_qualification_certification)
    private ImageView msg_iv_glr_qualification_certification;
    @ViewInject(R.id.msg_tv_risk_warning)
    private TextView msg_tv_risk_warning;

    @ViewInject(R.id.msg_ll_transaction_structure)
    private LinearLayout msg_ll_transaction_structure;//交易结构


    private ProductInfoBean infoBean;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_product_detail);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("产品详情");
        infoBean = (ProductInfoBean) getIntent().getSerializableExtra("infoBean");
        setData(infoBean);
        selectColor(0);
    }

    private void setData(ProductInfoBean infoBean) {
        setBaseData(infoBean);//基本信息
        setGpfData(infoBean.getGpfOrg());//基本信息挂牌方信息
//        setMsgData(infoBean);//信息披露
        setZpfData(infoBean.getZpfOrg());//信息披露摘牌方
        setGpjgData(infoBean.getGpjgOrg());//信息披露挂牌机构
    }

    private void setGpjgData(ProductInfoBean.GpjgOrgBean gpjgOrg) {
        msg_tv_gpjg_name.setText(gpjgOrg.getCompanyName());
        msg_tv_gpjg_register_time.setText(RoncheUtil.getRealTime(gpjgOrg.getRegistDate()));
        msg_tv_gpjg_registered_capital.setText(gpjgOrg.getRegistCapital()+"万");
        msg_tv_gpjg_industry.setText(gpjgOrg.getIndustry());
        Glide.with(mContext).load(gpjgOrg.getQualificationFile()).into(msg_iv_gpjg_qualification_certification);
        Glide.with(mContext).load(gpjgOrg.getOwnershipFile()).into(msg_iv_gpjg_ownership_structure);
        msg_tv_gpjg_summary.setText(gpjgOrg.getSummary());
        //判断交易结构是否有值
        if (TextUtils.isEmpty(infoBean.getTradeStuctFile())){
            msg_ll_transaction_structure.setVisibility(View.GONE);
        }else {
            msg_ll_transaction_structure.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(infoBean.getTradeStuctFile())&&infoBean.getTradeStuctFile().endsWith(".pdf")){
            msg_tv_gpjg_file.setVisibility(View.VISIBLE);
            msg_iv_gpjg_transaction_structure.setVisibility(View.GONE);
            String [] temp = null;
            if (!TextUtils.isEmpty(infoBean.getTradeStuctFile())) {
                temp = infoBean.getTradeStuctFile().split("/");
                msg_tv_gpjg_file.setText(temp[temp.length - 1]);
            }else {
                msg_tv_gpjg_file.setText("");
            }
        }else if (!TextUtils.isEmpty(infoBean.getTradeStuctFile())){
            msg_tv_gpjg_file.setVisibility(View.GONE);
            msg_iv_gpjg_transaction_structure.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(infoBean.getTradeStuctFile()).into(msg_iv_gpjg_transaction_structure);
        }

    }

    private void setZpfData(ProductInfoBean.ZpfOrgBean zpfOrg) {
        msg_tv_zpf_name.setText(zpfOrg.getCompanyName());
        msg_tv_zpf_register_time.setText(RoncheUtil.getRealTime(zpfOrg.getRegistDate()));
        msg_tv_zpf_registered_capital.setText(zpfOrg.getRegistCapital()+"万");
        msg_tv_zpf_industry.setText(zpfOrg.getIndustry());
        Glide.with(mContext).load(zpfOrg.getQualificationFile()).into(msg_iv_zpf_qualification_certification);
    }

    private void setMsgData(ProductInfoBean infoBean) {
        //公司详情，管理人无
//        msg_tv_company_info.setText(infoBean.getBaseInfor());
//        msg_tv_glr_name.setText(infoBean.getBaseInfor());
//        msg_tv_glr_register_time.setText(infoBean.getBaseInfor());
//        msg_tv_glr_registered_capital.setText(infoBean.getBaseInfor());
//        msg_tv_glr_industry.setText(infoBean.getBaseInfor());
//        msg_iv_glr_qualification_certification;
//        msg_tv_risk_warning.setText(infoBean.getBaseInfor());
    }

    private void setBaseData(ProductInfoBean infoBean) {
        base_tv_underlying_asset.setText(infoBean.getBaseInfor());
        String [] temp = null;
        if (!TextUtils.isEmpty(infoBean.getRegistManageFile())) {
            temp = infoBean.getRegistManageFile().split("/");
            base_tv_file.setText(temp[temp.length - 1]);
        }else {
            base_tv_file.setText("");
        }
        base_tv_wind_control.setText(infoBean.getRiskMeasures());
        base_tv_credit_enhancement.setText(infoBean.getAddMeasures());
    }

    private void setGpfData(ProductInfoBean.GpfOrgBean gpfOrg) {
        base_tv_listing_party.setText(gpfOrg.getCompanyName());
        base_tv_establish.setText(RoncheUtil.getRealTime(gpfOrg.getRegistDate()));
        base_tv_registered_capital.setText(gpfOrg.getRegistCapital()+"万");
        base_tv_industry.setText(gpfOrg.getIndustry());
        Glide.with(mContext).load(gpfOrg.getQualificationFile()).into(base_iv_qualification_certification);
        base_tv_summary.setText(gpfOrg.getSummary());
        msg_tv_summary.setText(gpfOrg.getSummary());
    }

    @OnClick({R.id.rl_base, R.id.rl_message
            ,R.id.base_iv_qualification_certification,R.id.base_tv_file,R.id.msg_iv_zpf_qualification_certification
            ,R.id.msg_iv_gpjg_qualification_certification,R.id.msg_iv_gpjg_ownership_structure,R.id.msg_tv_gpjg_file,R.id.msg_iv_gpjg_transaction_structure})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_base:
                selectColor(0);
                break;

            case R.id.rl_message:
                selectColor(1);
                break;

            case R.id.base_iv_qualification_certification:
                startActivity(new Intent(mContext, PhotoAct.class).putExtra("imgUrl", infoBean.getGpfOrg().getQualificationFile()));
                break;
            case R.id.msg_tv_gpjg_file:
                if (infoBean.getTradeStuctFile().endsWith(".pdf"))
                    startActivity(new Intent(mContext, PdfAct.class).putExtra("url", infoBean.getTradeStuctFile()));
                else
                    startActivity(new Intent(mContext, PhotoAct.class).putExtra("imgUrl", infoBean.getTradeStuctFile()));
                break;
            case R.id.base_tv_file:
                if (infoBean.getRegistManageFile().endsWith(".pdf"))
                    startActivity(new Intent(mContext, PdfAct.class).putExtra("url", infoBean.getRegistManageFile()));
                else
                    startActivity(new Intent(mContext, PhotoAct.class).putExtra("imgUrl", infoBean.getRegistManageFile()));
                break;
            case R.id.msg_iv_zpf_qualification_certification:
                startActivity(new Intent(mContext, PhotoAct.class).putExtra("imgUrl", infoBean.getZpfOrg().getQualificationFile()));
                break;
            case R.id.msg_iv_gpjg_qualification_certification:
                startActivity(new Intent(mContext, PhotoAct.class).putExtra("imgUrl", infoBean.getGpjgOrg().getQualificationFile()));
                break;
            case R.id.msg_iv_gpjg_ownership_structure:
                startActivity(new Intent(mContext, PhotoAct.class).putExtra("imgUrl", infoBean.getGpjgOrg().getOwnershipFile()));
                break;
            case R.id.msg_iv_gpjg_transaction_structure:
                startActivity(new Intent(mContext, PhotoAct.class).putExtra("imgUrl", infoBean.getTradeStuctFile()));
                break;
        }
    }
    private void selectColor(int i) {
        tv_base.setTextColor(getResources().getColor(R.color.black_1));
        view_base.setBackgroundColor(getResources().getColor(R.color.white));
        tv_message.setTextColor(getResources().getColor(R.color.black_1));
        view_message.setBackgroundColor(getResources().getColor(R.color.white));
        if (i == 0) {
            tv_base.setTextColor(getResources().getColor(R.color.bg_end));
            view_base.setBackgroundColor(getResources().getColor(R.color.bg_end));
            ll_base.setVisibility(View.VISIBLE);
            ll_message.setVisibility(View.GONE);
        } else if (i == 1) {
            tv_message.setTextColor(getResources().getColor(R.color.bg_end));
            view_message.setBackgroundColor(getResources().getColor(R.color.bg_end));
            ll_base.setVisibility(View.GONE);
            ll_message.setVisibility(View.VISIBLE);
        }
    }

}
