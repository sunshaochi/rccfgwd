package com.rmwl.rcchgwd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.rmwl.rcchgwd.bean.AdvisorBean;
import com.rmwl.rcchgwd.R;
import com.rmwl.rcchgwd.Utils.EncryptionTools;
import com.rmwl.rcchgwd.Utils.GsonUtils;
import com.rmwl.rcchgwd.Utils.SpUtils;
import com.rmwl.rcchgwd.base.BaseActivity;
import com.rmwl.rcchgwd.bean.UserBean;
import com.rmwl.rcchgwd.bean.YhkBean;
import com.rmwl.rcchgwd.okhttp.HttpUrl;
import com.rmwl.rcchgwd.okhttp.OkHttpCallBack;
import com.rmwl.rcchgwd.okhttp.OkHttpUtils;
import com.rmwl.rcchgwd.okhttp.RequestParam;
import com.rmwl.rcchgwd.view.MyListview;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by acer on 2018/8/22.
 */

public class YhkAct extends BaseActivity{
    @ViewInject(R.id.lv_yhk)
    private MyListview lv_yhk;
    @ViewInject(R.id.ll_add)
    private LinearLayout ll_add;

    private UserBean user;
    private List<YhkBean>list;
    private Gson gson;
    AdvisorBean bean;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_yhk);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("银行卡");
        gson=new Gson();
        if(!TextUtils.isEmpty(SpUtils.getUser(YhkAct.this))){
            user= GsonUtils.gsonToBean(SpUtils.getUser(YhkAct.this),UserBean.class);
        }
        lv_yhk.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(YhkAct.this,AddBankCarAct.class)
                        .putExtra("type",2).
                                putExtra("bankId",bean.getId())
                               .putExtra("id",bean.getId())
                               .putExtra("list", (Serializable) list));
            }
        });
        getBancklist();
    }

    private void getBancklist() {
        EncryptionTools.initEncrypMD5(TextUtils.isEmpty(user.getToken())?"":user.getToken());
        RequestParam param=new RequestParam();
        param.add("token", TextUtils.isEmpty(user.getToken())?"":user.getToken());
        param.add("timestamp", EncryptionTools.TIMESTAMP);
        param.add("nonce", EncryptionTools.NONCE);
        param.add("signature", EncryptionTools.SIGNATURE);
        param.add("source","3");
        OkHttpUtils.post(HttpUrl.BANKCERTIFYLIST, param, new OkHttpCallBack(YhkAct.this) {
            @Override
            public void onSuccess(String data) {
                try {
                    JSONObject object=new JSONObject(data.toString());
                    JSONObject result=object.getJSONObject("result");
                    JSONObject advisor=result.getJSONObject("advisor");
                     bean=gson.fromJson(advisor.toString(),AdvisorBean.class);
                     if(bean!=null){
                         if(TextUtils.isEmpty(bean.getBankid())){
                             ll_add.setVisibility(View.VISIBLE);
                         }else {
                             lv_yhk.setAdapter(new MyApapter());
                             ll_add.setVisibility(View.GONE);
                         }
                     }else {
                         ll_add.setVisibility(View.VISIBLE);
                     }
                    JSONArray array=result.getJSONArray("dicBankinfors");
                    list=gson.fromJson(array.toString(),new TypeToken<List<YhkBean>>(){}.getType());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int code, String error) {

            }
        });

    }


    @OnClick({R.id.ll_add})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.ll_add://新增加
                startActivity(new Intent(YhkAct.this,AddBankCarAct.class)
                        .putExtra("type",1).putExtra("list", (Serializable) list));

                break;
        }
    }

    private class MyApapter extends BaseAdapter {
        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view=View.inflate(YhkAct.this,R.layout.item_yhk,null);
            ImageView iv_yhklog=view.findViewById(R.id.iv_yhklog);
            TextView tv_yhkname=view.findViewById(R.id.tv_yhkname);
            TextView tv_wh=view.findViewById(R.id.tv_wh);
            TextView tv_kaname=view.findViewById(R.id.tv_kaname);

            tv_yhkname.setText(bean.getBankname());//名字
            tv_wh.setText("尾号"+bean.getCardno());//尾号
            Glide.with(YhkAct.this).load(bean.getIconUrl()).placeholder(R.mipmap.bananone).into(iv_yhklog);
            return view;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getBancklist();
    }
}
