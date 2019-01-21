package com.rmwl.rcchgwd.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.rmwl.rcchgwd.MainActivity;
import com.rmwl.rcchgwd.R;
import com.rmwl.rcchgwd.Utils.GsonUtils;
import com.rmwl.rcchgwd.Utils.MyToastUtils;
import com.rmwl.rcchgwd.Utils.SpUtils;
import com.rmwl.rcchgwd.base.BaseActivity;
import com.rmwl.rcchgwd.bean.SucessBean;
import com.rmwl.rcchgwd.bean.UserBean;

/**
 * Created by Administrator on 2018/8/25.
 */

public class SucessAct extends BaseActivity{
    @ViewInject(R.id.tv_name)
    private TextView tv_name;
    @ViewInject(R.id.tv_rg)
    private TextView tv_rg;
    @ViewInject(R.id.tv_yqsy)
    private TextView tv_yqsy;
    @ViewInject(R.id.tv_zrg)
    private TextView tv_zrg;
    @ViewInject(R.id.tv_zyq)
    private TextView tv_zyq;
    @ViewInject(R.id.ll_isshow)
    private LinearLayout ll_isshow;
    @ViewInject(R.id.tv_time)
    private TextView tv_time;
    @ViewInject(R.id.tv_zhname)
    private TextView tv_zhname;
    @ViewInject(R.id.tv_kh)
    private TextView tv_kh;
    @ViewInject(R.id.tv_bankname)
    private TextView tv_bankname;

    private SucessBean bean;
    private UserBean user;
    ClipboardManager myClipboard;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_sucess);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        myClipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);

        bean= (SucessBean) getIntent().getExtras().getSerializable("bean");
        if(!TextUtils.isEmpty(SpUtils.getUser(SucessAct.this))){
            user= GsonUtils.gsonToBean(SpUtils.getUser(SucessAct.this),UserBean.class);
        }
        if(bean!=null){
            setUi();
        }

    }

    private void setUi() {
        if(bean.getShow().equals("0")){
            ll_isshow.setVisibility(View.GONE);
        }else {
            ll_isshow.setVisibility(View.VISIBLE);
        }
        if(!TextUtils.isEmpty(bean.getProductName())){
            tv_name.setText(bean.getProductName());
        }
        if(!TextUtils.isEmpty(bean.getAmount())){
            tv_rg.setText(bean.getAmount());
        }
        if(!TextUtils.isEmpty(bean.getInterest())){
            tv_yqsy.setText(bean.getInterest());
        }
        if(!TextUtils.isEmpty(bean.getAllAmount())){
            tv_zrg.setText(bean.getAllAmount());
        }
        if(!TextUtils.isEmpty(bean.getAllInter())){
            tv_zyq.setText(bean.getAllInter());
        }

        if(!TextUtils.isEmpty(bean.getAccountname())){
            tv_zhname.setText(bean.getAccountname());
        }
        if(!TextUtils.isEmpty(bean.getAccountno())){
            tv_kh.setText(bean.getAccountno());
        }
        if(!TextUtils.isEmpty(bean.getBranchname())){
            tv_bankname.setText(bean.getBranchname());
        }

    }

    @OnClick({R.id.tv_qd,R.id.tv_ck,R.id.tv_fzyh,R.id.tv_fzkh,R.id.tv_fzname})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.tv_qd:
//                sendBroadcast(new Intent(MainActivity.BRODCAST));
                break;

            case R.id.tv_ck:
                startActivity(new Intent(SucessAct.this,WebAct.class).putExtra("type","2").putExtra("userId",user.getId()));
                finish();
                break;

            case R.id.tv_fzyh://银行
                toTex(bean.getBranchname());
                break;
            case R.id.tv_fzkh://卡号
                toTex(bean.getAccountno());
                break;
            case R.id.tv_fzname://账号名称
                toTex(bean.getAccountname());
                break;
        }
    }

    private void toTex(String tex) {
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", tex);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
        MyToastUtils.showShortToast(SucessAct.this,"复制成功");
    }



}
