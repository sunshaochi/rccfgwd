package com.rmwl.rcchgwd.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.rmwl.rcchgwd.R;
import com.rmwl.rcchgwd.Utils.EncryptionTools;
import com.rmwl.rcchgwd.Utils.GsonUtils;
import com.rmwl.rcchgwd.Utils.MyToastUtils;
import com.rmwl.rcchgwd.Utils.SpUtils;
import com.rmwl.rcchgwd.activity.AchievementAct;
import com.rmwl.rcchgwd.activity.CustomerAct;
import com.rmwl.rcchgwd.activity.SettingAct;
import com.rmwl.rcchgwd.activity.TodosAct;
import com.rmwl.rcchgwd.activity.WebAct;
import com.rmwl.rcchgwd.activity.YhkAct;
import com.rmwl.rcchgwd.base.BaseFrag;
import com.rmwl.rcchgwd.bean.UserBean;
import com.rmwl.rcchgwd.okhttp.HttpUrl;
import com.rmwl.rcchgwd.okhttp.OkHttpCallBack;
import com.rmwl.rcchgwd.okhttp.OkHttpUtils;
import com.rmwl.rcchgwd.okhttp.RequestParam;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by acer on 2018/8/21.
 */

public class WdFragment extends BaseFrag {
    @ViewInject(R.id.iv_msg)
    private ImageView iv_msg;//待办提醒
    @ViewInject(R.id.tv_ysy)
    private TextView tv_ysy;
    @ViewInject(R.id.tv_dsy)
    private TextView tv_dsy;
    @ViewInject(R.id.tv_total_money)
    private TextView tv_total_money;//总佣金
    @ViewInject(R.id.tv_title)
    private TextView tv_title;

    private UserBean user;
    private Gson gson;


    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fra_wd,null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        gson=new Gson();
        if(!TextUtils.isEmpty(SpUtils.getUser(getActivity()))){
            user= GsonUtils.gsonToBean(SpUtils.getUser(getActivity()),UserBean.class);
            String mobile=user.getMobile();
            tv_title.setText(mobile.substring(0,3)+"****"+mobile.substring(7,mobile.length()));
        }
        getDate();

    }
    private String noRealNameUserCount = "0";//未实名数
    private String adNoDealUserCount="0";//资金未到账数
    private void getDate() {
        EncryptionTools.initEncrypMD5(TextUtils.isEmpty(user.getToken())?"":user.getToken());
        RequestParam param=new RequestParam();
        param.add("token", TextUtils.isEmpty(user.getToken())?"":user.getToken());
        param.add("timestamp", EncryptionTools.TIMESTAMP);
        param.add("nonce", EncryptionTools.NONCE);
        param.add("signature", EncryptionTools.SIGNATURE);
        param.add("source","3");
        OkHttpUtils.post(HttpUrl.MYFRAGMENT_URL, param, new OkHttpCallBack(getActivity()) {
            @Override
            public void onSuccess(String data) {
                try {
                    JSONObject object=new JSONObject(data.toString());
                    JSONObject result=object.getJSONObject("result");
                    String hasInterest=result.getString("recommendUserTheMonth");//推荐
                    String remainInterest=result.getString("userRealAmountTheMonth");//投资
                    String totalInterest = result.getString("totalRealAmountTheMonth");//总
                    noRealNameUserCount = result.getString("noRealNameUserCount");//未实名用户数
                    adNoDealUserCount = result.getString("adNoDealUserCount");//资金未到账数
                    if (!TextUtils.isEmpty(noRealNameUserCount) && !TextUtils.isEmpty(adNoDealUserCount)){
                        if (noRealNameUserCount.equals("0")&&adNoDealUserCount.equals("0"))
                            iv_msg.setVisibility(View.GONE);
                        else
                            iv_msg.setVisibility(View.VISIBLE);
                    }else {
                        iv_msg.setVisibility(View.GONE);
                    }
                    if(!TextUtils.isEmpty(hasInterest)){
                        tv_ysy.setText(hasInterest);
                    }
                    if(!TextUtils.isEmpty(remainInterest )){
                        tv_dsy.setText(remainInterest);
                    }
                    if (!TextUtils.isEmpty(totalInterest)){
                        tv_total_money.setText(totalInterest);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(int code, String error) {
                MyToastUtils.showShortToast(getActivity(),error);
            }
        });

    }

    @OnClick({R.id.rl_rg,R.id.rl_jy,R.id.rl_yhk,R.id.rl_yhj,R.id.iv_right})
    public void OnClick(View view){
        switch (view.getId()){
          case R.id.rl_rg://kh
              startActivity(new Intent(getActivity(), CustomerAct.class));//客户原生界面
//              startActivity(new Intent(getActivity(),WebAct.class).putExtra("type","2"));

            break;

            case R.id.rl_jy://yj
                startActivity(new Intent(getActivity(), AchievementAct.class));//业绩原生界面
//                startActivity(new Intent(getActivity(),WebAct.class).putExtra("type","3"));
                break;

            case R.id.rl_yhk://
               openActivity(YhkAct.class);
                break;

            case R.id.rl_yhj://db
                startActivity(new Intent(getActivity(), TodosAct.class).putExtra("noRealNameUserCount",noRealNameUserCount).putExtra("adNoDealUserCount",adNoDealUserCount));//待办原生界面
//                startActivity(new Intent(getActivity(),WebAct.class).putExtra("type","5"));
                break;


            case R.id.iv_right:
                getActivity().startActivity(new Intent(getActivity(),SettingAct.class));
                break;

        }
    }

    public void upDate() {
        getDate();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden){

        }else {
            getDate();
        }
    }
}
