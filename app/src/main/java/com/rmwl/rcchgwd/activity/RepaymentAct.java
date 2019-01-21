package com.rmwl.rcchgwd.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshRecyclerView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.rmwl.rcchgwd.R;
import com.rmwl.rcchgwd.Utils.MyToastUtils;
import com.rmwl.rcchgwd.adapter.RepayAdapter;
import com.rmwl.rcchgwd.base.BaseActivity;
import com.rmwl.rcchgwd.bean.RepayBean;
import com.rmwl.rcchgwd.okhttp.HttpUrl;
import com.rmwl.rcchgwd.okhttp.OkHttpCallBack;
import com.rmwl.rcchgwd.okhttp.OkHttpUtils;
import com.rmwl.rcchgwd.okhttp.RequestParam;
import com.rmwl.rcchgwd.view.LoadingView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/1/4.
 */

public class RepaymentAct extends BaseActivity {
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.tv_zh)
    private TextView tv_zh;
    @ViewInject(R.id.tv_bank)
    private TextView tv_bank;
    @ViewInject(R.id.plv_repay)
    private PullToRefreshRecyclerView plv_repay;
    @ViewInject(R.id.load_view)
    private LoadingView load_view;

    private RecyclerView recyclerview;
    private RepayAdapter adapter;
    private String pubId;
    private Boolean isshowPro=true;
    private List<RepayBean>list;
    private List<RepayBean>datelist=new ArrayList<>();

    private int currentPage=1;
    private int pageSize=10;
    private Gson gson;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_repayment);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        gson=new Gson();
        recyclerview=plv_repay.getRefreshableView();
        recyclerview.setLayoutManager(new LinearLayoutManager(RepaymentAct.this));
        pubId=getIntent().getExtras().getString("pubId","");


        plv_repay.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                isshowPro=false;
                currentPage=1;
                getDate();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                isshowPro=false;
                currentPage++;
                getDate();
            }
        });

        load_view.setOnRetryListener(new LoadingView.OnRetryListener() {
            @Override
            public void OnRetry() {
//                load_view.loading();
                currentPage=1;
                pageSize=10;
                isshowPro=true;
                getDate();
            }
        });

        if(!TextUtils.isEmpty(pubId)) {
            load_view.loading();
            getDate();
        }


    }

    private void getDate() {
        RequestParam param=new RequestParam();
        param.add("pubId",pubId);
        param.add("currentPage",currentPage+"");
        param.add("pageSize",pageSize+"");
        OkHttpUtils.post(HttpUrl.QUERYREPAYRECORD_URL, param, new OkHttpCallBack(RepaymentAct.this,false) {
            @Override
            public void onSuccess(String data) {
                  plv_repay.onRefreshComplete();
                  load_view.loadComplete();
                  plv_repay.setVisibility(View.VISIBLE);
                try {
                    JSONObject object=new JSONObject(data.toString());
                    JSONObject result=object.getJSONObject("result");
                    String productName=result.getString("productName");
                    String cardno=result.getString("cardno");
                    String realbankname=result.getString("realbankname");
                    String totalSize=result.getString("totalSize");
                    if(!TextUtils.isEmpty(productName)){
//                        tv_title.setText("还款计划:"+productName);
                        tv_title.setText(productName);
                    }

                    if(!TextUtils.isEmpty(cardno)){
                        tv_zh.setText("兑付卡尾号："+cardno);
                    }

                    if(!TextUtils.isEmpty(realbankname)){
                        tv_bank.setText("银行："+realbankname);
                    }


                    JSONArray array=result.getJSONArray("list");
                    list=gson.fromJson(array.toString(),new TypeToken<List<RepayBean>>(){}.getType());
                    if(list!=null&&list.size()>0){
                        if (currentPage==1){
                            datelist.clear();
                        }
                        datelist.addAll(list);
                        if(adapter==null){
                            adapter=new RepayAdapter(RepaymentAct.this,datelist);
                            recyclerview.setAdapter(adapter);
                        }else {
                            adapter.upDate(datelist);
                        }
                    }else {
                        if(currentPage==1){
//                            load_view.noContent();
                            plv_repay.setVisibility(View.GONE);

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    plv_repay.setVisibility(View.GONE);
                }

            }

            @Override
            public void onError(int code, String error) {
                plv_repay.onRefreshComplete();
                load_view.loadError();
                plv_repay.setVisibility(View.GONE);
                MyToastUtils.showShortToast(RepaymentAct.this,error);
            }
        });
    }

    @OnClick({R.id.iv_back})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
