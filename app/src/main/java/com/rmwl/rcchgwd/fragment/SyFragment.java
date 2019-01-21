package com.rmwl.rcchgwd.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.google.gson.Gson;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.rmwl.rcchgwd.R;
import com.rmwl.rcchgwd.base.BaseFrag;
import com.rmwl.rcchgwd.bean.SyDateBean;
import com.rmwl.rcchgwd.okhttp.HttpUrl;
import com.rmwl.rcchgwd.okhttp.OkHttpCallBack;
import com.rmwl.rcchgwd.okhttp.OkHttpUtils;
import com.rmwl.rcchgwd.okhttp.RequestParam;
import com.rmwl.rcchgwd.view.FlyBanner;
import com.rmwl.rcchgwd.view.LanternRoll.XMarqueeView;
import com.rmwl.rcchgwd.view.SycpView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by acer on 2018/8/21.
 */

public class SyFragment extends BaseFrag {
    @ViewInject(R.id.banner_1)
    private FlyBanner banner_1;
    @ViewInject(R.id.upview2)
    private XMarqueeView upview2;
    @ViewInject(R.id.sycp_view)
    private SycpView sycp_view;
    List<String> list = new ArrayList<>();
    private List<String> reservList = new ArrayList<>();

    private Gson gson;
    private SyDateBean bean;

    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fra_sy, null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        gson = new Gson();
        getDate();
    }

    private void getDate() {
        RequestParam param = new RequestParam();
        OkHttpUtils.post(HttpUrl.HOME_URL, param, new OkHttpCallBack(getActivity()) {
            @Override
            public void onSuccess(String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data.toString());
                    JSONObject object = jsonObject.getJSONObject("result");
                    bean = gson.fromJson(object.toString(), SyDateBean.class);
                    if (bean != null) {
                        initView(bean);
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

    private void initView(SyDateBean bean) {
        //轮播广告
        if (bean.getNotices() != null && bean.getNotices().size() > 0) {//广告
            reservList.clear();
            for (int i = 0; i < bean.getNotices().size(); i++) {
                reservList.add(bean.getNotices().get(i).getContent());
            }
            upview2.setData(reservList);
        }
        //banner图片
        if (bean.getUrls() != null && bean.getUrls().size() > 0) {
            list.clear();
            for (int i = 0; i < bean.getUrls().size(); i++) {
                list.add(bean.getUrls().get(i).getUrl());
            }
            banner_1.setImagesUrl(list);

        }

        //中间产品列表
        if(bean.getPros()!=null&&bean.getPros().size()>0){
            sycp_view.init(bean.getPros());
        }


    }


}
