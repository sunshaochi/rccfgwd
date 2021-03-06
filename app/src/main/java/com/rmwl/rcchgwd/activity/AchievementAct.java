package com.rmwl.rcchgwd.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.rmwl.rcchgwd.base.BaseActivity;
import com.rmwl.rcchgwd.bean.AchieveBean;
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
 * 业绩
 * Created by Administrator on 2019/1/4.
 */

public class AchievementAct extends BaseActivity {
    @ViewInject(R.id.load_view)
    private LoadingView load_view;
    @ViewInject(R.id.tv_moren)
    private TextView tv_moren;
    @ViewInject(R.id.tv_lv)
    private TextView tv_lv;
    @ViewInject(R.id.tv_qx)
    private TextView tv_qx;
    @ViewInject(R.id.iv_lv)
    private ImageView iv_lv;
    @ViewInject(R.id.iv_qx)
    private ImageView iv_qx;
    @ViewInject(R.id.plv_cp)
    private PullToRefreshRecyclerView plv_cp;
    private RecyclerView recyclerView;
    private AchievementAdapter adapter;

    private boolean booleanlv;
    private boolean booleanqx;

    private String orderBy;
    private List<AchieveBean> list = new ArrayList<>();
    private List<AchieveBean> datalist = new ArrayList<>();
    private int currentPage = 1;
    private int pageSize = 10;
    private Gson gson;
    private UserBean user;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_achievement);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("业绩");
        if (!TextUtils.isEmpty(SpUtils.getUser(mContext))) {
            user = GsonUtils.gsonToBean(SpUtils.getUser(mContext), UserBean.class);
        }
        gson = new Gson();
        recyclerView = plv_cp.getRefreshableView();
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AchievementAdapter(mContext,datalist);
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
    @OnClick({R.id.ll_moren, R.id.ll_lv, R.id.ll_qx})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.ll_moren:
                selectColor(0);
                break;

            case R.id.ll_lv://累计投资
                selectColor(1);
                break;

            case R.id.ll_qx://笔数
                selectColor(2);
                break;
        }
    }
    private void selectColor(int i) {
        tv_moren.setTextColor(getResources().getColor(R.color.black_1));
        tv_lv.setTextColor(getResources().getColor(R.color.black_1));
        tv_qx.setTextColor(getResources().getColor(R.color.black_1));
        iv_lv.setImageResource(R.mipmap.cpnoxx);
        iv_qx.setImageResource(R.mipmap.cpnoxx);
        if (i == 0) {
            tv_moren.setTextColor(getResources().getColor(R.color.bg_end));
            booleanlv = false;
            booleanqx = false;
            orderBy = "";
        } else if (i == 1) {
            booleanqx = false;
            tv_lv.setTextColor(getResources().getColor(R.color.bg_end));
            if (!booleanlv) {
                iv_lv.setImageResource(R.mipmap.cpxx);
                booleanlv = true;
                orderBy = "1";
            } else {
                iv_lv.setImageResource(R.mipmap.cpxs);
                booleanlv = false;
                orderBy = "2";
            }
        } else if (i == 2) {
            booleanlv = false;
            tv_qx.setTextColor(getResources().getColor(R.color.bg_end));
            if (!booleanqx) {
                iv_qx.setImageResource(R.mipmap.cpxx);
                booleanqx = true;
                orderBy = "3";
            } else {
                iv_qx.setImageResource(R.mipmap.cpxs);
                booleanqx = false;
                orderBy = "4";
            }

        }
        currentPage = 1;
        getData();
        load_view.loading();
    }

    private void getData(){
        EncryptionTools.initEncrypMD5(TextUtils.isEmpty(user.getToken()) ? "" : user.getToken());
        RequestParam param = new RequestParam();
        param.add("token", TextUtils.isEmpty(user.getToken()) ? "" : user.getToken());
        param.add("timestamp", EncryptionTools.TIMESTAMP);
        param.add("nonce", EncryptionTools.NONCE);
        param.add("signature", EncryptionTools.SIGNATURE);
        param.add("source", "3");
        if (!TextUtils.isEmpty(orderBy)) {
            param.add("orderBy", orderBy);
        }
        param.add("currentPage", currentPage + "");
        param.add("pageSize", pageSize + "");
        OkHttpUtils.post(HttpUrl.ACHIEVE_URL, param, new OkHttpCallBack(mContext,false) {
            @Override
            public void onSuccess(String data) {
                load_view.loadComplete();
                plv_cp.onRefreshComplete();
                plv_cp.setVisibility(View.VISIBLE);
                try {
                    JSONObject object = new JSONObject(data.toString());
                    JSONObject result = object.getJSONObject("result");
                    JSONArray array = result.getJSONArray("list");
                    list = gson.fromJson(array.toString(), new TypeToken<List<AchieveBean>>() {
                    }.getType());
                    if (list != null && list.size() > 0) {
                        if (currentPage == 1) {
                            datalist.clear();
                        }
                        datalist.addAll(list);
                        adapter.notifyData(datalist);
                    } else {
                        if (currentPage == 1) {
                            load_view.noContent();
                            load_view.setNoContentIco(R.mipmap.zanwjl);
                            load_view.setNoContentTxt("暂无记录");
                            adapter.notifyData(new ArrayList<AchieveBean>());
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
