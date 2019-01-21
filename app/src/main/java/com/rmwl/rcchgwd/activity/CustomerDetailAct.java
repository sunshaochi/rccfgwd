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
import com.rmwl.rcchgwd.Utils.EncryptionTools;
import com.rmwl.rcchgwd.Utils.GsonUtils;
import com.rmwl.rcchgwd.Utils.MyToastUtils;
import com.rmwl.rcchgwd.Utils.SpUtils;
import com.rmwl.rcchgwd.adapter.ArriveAdapter;
import com.rmwl.rcchgwd.adapter.CustomerDetailAdapter;
import com.rmwl.rcchgwd.adapter.RealAdapter;
import com.rmwl.rcchgwd.base.BaseActivity;
import com.rmwl.rcchgwd.bean.InvestBean;
import com.rmwl.rcchgwd.bean.UserBean;
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
 * 客户投资详情
 * Created by Administrator on 2019/1/4.
 */

public class CustomerDetailAct extends BaseActivity {
    @ViewInject(R.id.load_view)
    private LoadingView load_view;

    @ViewInject(R.id.tv_order)
    private TextView tv_order;
    @ViewInject(R.id.view_order)
    private View view_order;

    @ViewInject(R.id.tv_settle)
    private TextView tv_settle;
    @ViewInject(R.id.view_settle)
    private View view_settle;

    @ViewInject(R.id.tv_income)
    private TextView tv_income;
    @ViewInject(R.id.view_income)
    private View view_income;

    @ViewInject(R.id.plv_cp)
    private PullToRefreshRecyclerView plv_cp;
    private RecyclerView recyclerView;
    private CustomerDetailAdapter adapter;


    private int currentPage = 1;
    private int pageSize = 10;

    private List<InvestBean> list=new ArrayList<>();
    private List<InvestBean> datalist=new ArrayList<>();
    private Gson gson;
    private UserBean user;
    private String type = "1";//1 预约中 2 收益中 3 已结清
    private String userId = "";
    @Override
    public void setLayout() {
        setContentView(R.layout.act_cus_detail);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("客户投资详情");
        userId = getIntent().getStringExtra("userId");
        if (!TextUtils.isEmpty(SpUtils.getUser(mContext))) {
            user = GsonUtils.gsonToBean(SpUtils.getUser(mContext), UserBean.class);
        }
        gson = new Gson();
        recyclerView = plv_cp.getRefreshableView();
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CustomerDetailAdapter(mContext,datalist,type);
        recyclerView.setAdapter(adapter);
        selectColor(0);
        plv_cp.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                currentPage = 1;
                getData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                currentPage++;
                getData();
            }
        });

        load_view.setOnRetryListener(new LoadingView.OnRetryListener() {
            @Override
            public void OnRetry() {
                getData();
            }
        });
    }
    @OnClick({R.id.rl_order, R.id.rl_income,R.id.rl_settle})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_order:
                selectColor(0);
                break;

            case R.id.rl_income:
                selectColor(1);
                break;
            case R.id.rl_settle:
                selectColor(2);
                break;

        }
    }
    private void selectColor(int i) {
        //type 查询类型 1-认购中 2-收益中 3-已结清
        tv_order.setTextColor(getResources().getColor(R.color.black_1));
        view_order.setBackgroundColor(getResources().getColor(R.color.white));
        tv_income.setTextColor(getResources().getColor(R.color.black_1));
        view_income.setBackgroundColor(getResources().getColor(R.color.white));
        tv_settle.setTextColor(getResources().getColor(R.color.black_1));
        view_settle.setBackgroundColor(getResources().getColor(R.color.white));
        if (i == 0) {
            type = "1";
            tv_order.setTextColor(getResources().getColor(R.color.bg_end));
            view_order.setBackgroundColor(getResources().getColor(R.color.bg_end));
        } else if (i == 1) {
            type = "2";
            tv_income.setTextColor(getResources().getColor(R.color.bg_end));
            view_income.setBackgroundColor(getResources().getColor(R.color.bg_end));
        }else if (i == 2) {
            type = "3";
            tv_settle.setTextColor(getResources().getColor(R.color.bg_end));
            view_settle.setBackgroundColor(getResources().getColor(R.color.bg_end));
        }
        currentPage = 1;
        getData();
        load_view.loading();
    }

    private void getData(){
        EncryptionTools.initEncrypMD5(TextUtils.isEmpty(user.getToken()) ? "" : user.getToken());
        RequestParam param = new RequestParam();
        param.add("token", TextUtils.isEmpty(user.getToken()) ? "" : user.getToken());
        param.add("userId",userId );
        param.add("timestamp", EncryptionTools.TIMESTAMP);
        param.add("nonce", EncryptionTools.NONCE);
        param.add("signature", EncryptionTools.SIGNATURE);
        param.add("source", "3");
        if (!TextUtils.isEmpty(type)) {
            param.add("type", type);
        }
        param.add("currentPage", currentPage + "");
        param.add("pageSize", pageSize + "");
        OkHttpUtils.post(HttpUrl.CUSTOMER_INVEST_URL, param, new OkHttpCallBack(mContext,false) {
            @Override
            public void onSuccess(String data) {
                load_view.loadComplete();
                plv_cp.onRefreshComplete();
                plv_cp.setVisibility(View.VISIBLE);
                try {
                    JSONObject object = new JSONObject(data.toString());
                    JSONObject result = object.getJSONObject("result");
                    JSONArray array = result.getJSONArray("list");
                    list = gson.fromJson(array.toString(), new TypeToken<List<InvestBean>>() {
                    }.getType());
                    if (list != null && list.size() > 0) {
                        if (currentPage == 1) {
                            datalist.clear();
                        }
                        datalist.addAll(list);
                        adapter.notifyData(datalist,type);
                    } else {
                        if (currentPage == 1) {
                            adapter.notifyData(new ArrayList<InvestBean>(),type);
                            load_view.noContent();
                            load_view.setNoContentIco(R.mipmap.zanwjl);
                            load_view.setNoContentTxt("暂无记录");
                            plv_cp.setVisibility(View.GONE);

                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    plv_cp.setVisibility(View.GONE);
                }

            }

            @Override
            public void onError(int code, String error) {
                load_view.loadError();
                plv_cp.onRefreshComplete();
                plv_cp.setVisibility(View.GONE);
                MyToastUtils.showShortToast(mContext, error);
            }
        });
    }
}
