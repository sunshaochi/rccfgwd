package com.rmwl.rcchgwd.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
import com.rmwl.rcchgwd.adapter.AchievementAdapter;
import com.rmwl.rcchgwd.adapter.ArriveAdapter;
import com.rmwl.rcchgwd.adapter.RealAdapter;
import com.rmwl.rcchgwd.base.BaseActivity;
import com.rmwl.rcchgwd.bean.ArriveBean;
import com.rmwl.rcchgwd.bean.RealBean;
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
 * 待办
 * Created by Administrator on 2019/1/4.
 */

public class TodosAct extends BaseActivity {
    @ViewInject(R.id.load_view)
    private LoadingView load_view;
    @ViewInject(R.id.tv_not_real)
    private TextView tv_not_real;
    @ViewInject(R.id.tv_real_num)
    private TextView tv_real_num;
    @ViewInject(R.id.view_real)
    private View view_real;

    @ViewInject(R.id.tv_not_arrive)
    private TextView tv_not_arrive;
    @ViewInject(R.id.tv_arrive_num)
    private TextView tv_arrive_num;
    @ViewInject(R.id.view_arrive)
    private View view_arrive;

    @ViewInject(R.id.plv_cp)
    private PullToRefreshRecyclerView plv_cp;
    private RecyclerView recyclerView;
    private RealAdapter adapterReal;
    private ArriveAdapter adapterArrive;


    private int currentPage = 1;
    private int pageSize = 10;

    private int tab_position = 0;//当前选中的位置 0 未实名 1 资金未到账

    private List<RealBean> listReal=new ArrayList<>();
    private List<RealBean> datalistReal=new ArrayList<>();
    private List<ArriveBean> listArrive=new ArrayList<>();
    private List<ArriveBean> datalistArrive=new ArrayList<>();
    private Gson gson;
    private UserBean user;

    private String noRealNameUserCount = "0";//未实名数
    private String adNoDealUserCount="0";//资金未到账数
    @Override
    public void setLayout() {
        setContentView(R.layout.act_todos);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("待办");
        if (getIntent()!=null){
            noRealNameUserCount = getIntent().getStringExtra("noRealNameUserCount");
            adNoDealUserCount = getIntent().getStringExtra("adNoDealUserCount");
            if (!TextUtils.isEmpty(noRealNameUserCount) && noRealNameUserCount.equals("0")){
                tv_real_num.setVisibility(View.GONE);
            }else {
                tv_real_num.setVisibility(View.VISIBLE);
                tv_real_num.setText(noRealNameUserCount);
            }
            if (!TextUtils.isEmpty(adNoDealUserCount) && adNoDealUserCount.equals("0")){
                tv_arrive_num.setVisibility(View.GONE);
            }else {
                tv_arrive_num.setVisibility(View.VISIBLE);
                tv_arrive_num.setText(adNoDealUserCount);
            }
        }
        if (!TextUtils.isEmpty(SpUtils.getUser(mContext))) {
            user = GsonUtils.gsonToBean(SpUtils.getUser(mContext), UserBean.class);
        }
        gson = new Gson();
        recyclerView = plv_cp.getRefreshableView();
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);

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
    @OnClick({R.id.rl_real, R.id.rl_arrive})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_real:
                selectColor(0);
                break;

            case R.id.rl_arrive://资金未到账
                selectColor(1);
                break;

        }
    }
    private void selectColor(int i) {
        adapterReal = null;
        adapterArrive = null;
        tab_position = i;
        currentPage = 1;
        tv_not_real.setTextColor(getResources().getColor(R.color.black_1));
        view_real.setBackgroundColor(getResources().getColor(R.color.white));
        tv_not_arrive.setTextColor(getResources().getColor(R.color.black_1));
        view_arrive.setBackgroundColor(getResources().getColor(R.color.white));
        if (i == 0) {
            tv_not_real.setTextColor(getResources().getColor(R.color.bg_end));
            view_real.setBackgroundColor(getResources().getColor(R.color.bg_end));
        } else if (i == 1) {
            tv_not_arrive.setTextColor(getResources().getColor(R.color.bg_end));
            view_arrive.setBackgroundColor(getResources().getColor(R.color.bg_end));
        }
        getData();
    }

    private void getData(){
        if (tab_position == 0) {
            getRealData();
        }else if (tab_position == 1){
            getArriveData();
        }
        load_view.loading();
    }

    private void getRealData(){
        EncryptionTools.initEncrypMD5(TextUtils.isEmpty(user.getToken()) ? "" : user.getToken());
        RequestParam param = new RequestParam();
        param.add("token", TextUtils.isEmpty(user.getToken()) ? "" : user.getToken());
        param.add("timestamp", EncryptionTools.TIMESTAMP);
        param.add("nonce", EncryptionTools.NONCE);
        param.add("signature", EncryptionTools.SIGNATURE);
        param.add("source", "3");
        param.add("currentPage", currentPage + "");
        param.add("pageSize", pageSize + "");
        OkHttpUtils.post(HttpUrl.REAL_URL, param, new OkHttpCallBack(mContext,false) {
            @Override
            public void onSuccess(String data) {
                load_view.loadComplete();
                plv_cp.onRefreshComplete();
                plv_cp.setVisibility(View.VISIBLE);
                if (TextUtils.isEmpty(data))
                    return;
                try {
                    JSONObject object = new JSONObject(data.toString());
                    JSONObject result = object.getJSONObject("result");
                    String totalSize = result.getString("totalSize");
                    if (!TextUtils.isEmpty(totalSize) && totalSize.equals("0")){
                        tv_real_num.setVisibility(View.GONE);
                    }else if (!TextUtils.isEmpty(totalSize)){
                        tv_real_num.setVisibility(View.VISIBLE);
                        tv_real_num.setText(totalSize);
                    }
                    JSONArray array = result.getJSONArray("list");
                    listReal = gson.fromJson(array.toString(), new TypeToken<List<RealBean>>() {
                    }.getType());
                    if (listReal != null && listReal.size() > 0) {
                        if (currentPage == 1) {
                            datalistReal.clear();
                        }
                        datalistReal.addAll(listReal);
                        if (adapterReal==null) {
                            adapterReal = new RealAdapter(mContext,datalistReal);
                            recyclerView.setAdapter(adapterReal);
                        }else {
                            adapterReal.notifyData(datalistReal);
                        }

                    } else {
                        if (currentPage == 1) {
                            if (adapterReal==null) {
                                adapterReal = new RealAdapter(mContext,new ArrayList<RealBean>());
                                recyclerView.setAdapter(adapterReal);
                            }else {
                                adapterReal.notifyData(new ArrayList<RealBean>());
                            }

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
                plv_cp.onRefreshComplete();
                MyToastUtils.showShortToast(mContext, error);
                if (adapterReal==null) {
                    adapterReal = new RealAdapter(mContext,datalistReal);
                    recyclerView.setAdapter(adapterReal);
                }else {
                    adapterReal.notifyData(datalistReal);
                }
                load_view.loadError();
                plv_cp.setVisibility(View.GONE);
            }
        });
    }

    private void getArriveData(){
        load_view.loading();
        EncryptionTools.initEncrypMD5(TextUtils.isEmpty(user.getToken()) ? "" : user.getToken());
        RequestParam param = new RequestParam();
        param.add("token", TextUtils.isEmpty(user.getToken()) ? "" : user.getToken());
        param.add("adId", TextUtils.isEmpty(user.getId()) ? "" : user.getId());
        param.add("timestamp", EncryptionTools.TIMESTAMP);
        param.add("nonce", EncryptionTools.NONCE);
        param.add("signature", EncryptionTools.SIGNATURE);
        param.add("source", "3");
        param.add("type","2");
        param.add("currentPage", currentPage + "");
        param.add("pageSize", pageSize + "");
        OkHttpUtils.post(HttpUrl.ARRIVE_URL, param, new OkHttpCallBack(mContext,false) {
            @Override
            public void onSuccess(String data) {
                load_view.loadComplete();
                plv_cp.onRefreshComplete();
                plv_cp.setVisibility(View.VISIBLE);
                if (TextUtils.isEmpty(data))
                    return;
                try {
                    JSONObject object = new JSONObject(data.toString());
                    JSONObject result = object.getJSONObject("result");
                    String totalSize = result.getString("totalSize");
                    if (!TextUtils.isEmpty(totalSize) && totalSize.equals("0")){
                        tv_arrive_num.setVisibility(View.GONE);
                    }else if (!TextUtils.isEmpty(totalSize)){
                        tv_arrive_num.setVisibility(View.VISIBLE);
                        tv_arrive_num.setText(totalSize);
                    }
                    JSONArray array = result.getJSONArray("list");
                    listArrive = gson.fromJson(array.toString(), new TypeToken<List<ArriveBean>>() {
                    }.getType());
                    if (listArrive != null && listArrive.size() > 0) {
                        if (currentPage == 1) {
                            datalistArrive.clear();
                        }
                        datalistArrive.addAll(listArrive);
                        if (adapterArrive==null) {
                            adapterArrive = new ArriveAdapter(mContext,datalistArrive);
                            recyclerView.setAdapter(adapterArrive);
                        }else {
                            adapterArrive.notifyData(datalistArrive);
                        }

                    } else {
                        if (currentPage == 1) {
                            if (adapterArrive==null) {
                                adapterArrive = new ArriveAdapter(mContext,new ArrayList<ArriveBean>());
                                recyclerView.setAdapter(adapterArrive);
                            }else {
                                adapterArrive.notifyData(new ArrayList<ArriveBean>());
                            }

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
                plv_cp.onRefreshComplete();
                MyToastUtils.showShortToast(mContext, error);
                if (adapterArrive==null) {
                    adapterArrive = new ArriveAdapter(mContext,datalistArrive);
                    recyclerView.setAdapter(adapterArrive);
                }else {
                    adapterArrive.notifyData(datalistArrive);
                }
                load_view.loadError();
                plv_cp.setVisibility(View.GONE);
            }
        });
    }
}
