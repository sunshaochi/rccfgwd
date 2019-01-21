package com.rmwl.rcchgwd.fragment;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.OrientationHelper;
//import android.support.v7.widget.RecyclerView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.util.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshRecyclerView;
import com.jaeger.library.StatusBarUtil;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.rmwl.rcchgwd.MyApplication;
import com.rmwl.rcchgwd.R;
//import com.rmwl.rcch.adapter.CpFraAdapter;
import com.rmwl.rcchgwd.Utils.EncryptionTools;
import com.rmwl.rcchgwd.Utils.GsonUtils;
import com.rmwl.rcchgwd.Utils.MyBitmapUtils;
import com.rmwl.rcchgwd.Utils.MyLogUtils;
import com.rmwl.rcchgwd.Utils.MyToastUtils;
import com.rmwl.rcchgwd.Utils.RoncheUtil;
import com.rmwl.rcchgwd.Utils.SpUtils;
import com.rmwl.rcchgwd.Utils.WeiChartUtil;
import com.rmwl.rcchgwd.activity.WebAct;
import com.rmwl.rcchgwd.adapter.CpFraAdapter;
import com.rmwl.rcchgwd.base.BaseFrag;
import com.rmwl.rcchgwd.bean.ProductBean;
import com.rmwl.rcchgwd.bean.UirBean;
import com.rmwl.rcchgwd.bean.UserBean;
import com.rmwl.rcchgwd.okhttp.HttpUrl;
import com.rmwl.rcchgwd.okhttp.OkHttpCallBack;
import com.rmwl.rcchgwd.okhttp.OkHttpUtils;
import com.rmwl.rcchgwd.okhttp.RequestParam;
import com.rmwl.rcchgwd.view.FlyBanner;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by acer on 2018/8/21.
 */

public class CpFragment extends BaseFrag implements CpFraAdapter.ShareListion, FlyBanner.OnItemClickListener {
    @ViewInject(R.id.plv_cp)
    private PullToRefreshRecyclerView plv_cp;
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
    @ViewInject(R.id.banner_1)
    private FlyBanner banner1;


    private CpFraAdapter adapter;
    private RecyclerView recyclerView;

    private boolean booleanlv;
    private boolean booleanqx;

    private String orderBy;
    private int currentPage = 1;
    private int pageSize = 10;
    private List<ProductBean> list;
    private List<ProductBean> datelist = new ArrayList<>();
    private Gson gson;
    private UserBean user;
    private List<String> urllist = new ArrayList<>();
    List<String> noteurllist = new ArrayList<>();

    private IWXAPI api;

    @Override
    public View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fra_cp, null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        api = WXAPIFactory.createWXAPI(getActivity(), MyApplication.APP_ID);
//        api.registerApp(MyApplication.APP_ID);
        registPersimm();//动态添加权限
        if (!TextUtils.isEmpty(SpUtils.getUser(getActivity()))) {
            user = GsonUtils.gsonToBean(SpUtils.getUser(getActivity()), UserBean.class);
        }
        gson = new Gson();
        recyclerView = plv_cp.getRefreshableView();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CpFraAdapter(getActivity(), datelist);
        adapter.setListion(this);
        recyclerView.setAdapter(adapter);


        plv_cp.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                currentPage = 1;
                getDate();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                currentPage++;
                getDate();
            }
        });


        banner1.setOnItemClickListener(this);
        selectColor(0);
//        getDate();
        getBanner();//获取首页banantu


    }

    private void getBanner() {
        EncryptionTools.initEncrypMD5(TextUtils.isEmpty(user.getToken()) ? "" : user.getToken());
        RequestParam param = new RequestParam();
        param.add("token", TextUtils.isEmpty(user.getToken()) ? "" : user.getToken());
        param.add("timestamp", EncryptionTools.TIMESTAMP);
        param.add("nonce", EncryptionTools.NONCE);
        param.add("signature", EncryptionTools.SIGNATURE);
        param.add("source", "3");
        OkHttpUtils.post(HttpUrl.BANNER_URL, param, new OkHttpCallBack(getActivity()) {
            @Override
            public void onSuccess(String data) {
                try {
                    JSONObject object = new JSONObject(data.toString());
                    JSONObject result = object.getJSONObject("result");
                    JSONArray array = result.getJSONArray("urls");
                    urllist.clear();
                    noteurllist.clear();
                    List<UirBean> list = gson.fromJson(array.toString(), new TypeToken<List<UirBean>>() {
                    }.getType());
                    if (list != null && list.size() > 0) {
                        for (int i = 0; i < list.size(); i++) {
                            urllist.add(list.get(i).getImageurl());
                            noteurllist.add(list.get(i).getNoteurl());
                        }
                        banner1.setImagesUrl(urllist);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int code, String error) {
                MyToastUtils.showShortToast(getActivity(), error);
            }
        });


    }

    private void registPersimm() {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS, Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions(getActivity(), mPermissionList, 123);
        }
    }

    private void getDate() {
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

        OkHttpUtils.post(HttpUrl.CPLIST_URL, param, new OkHttpCallBack(getActivity(),false) {
            @Override
            public void onSuccess(String data) {
                plv_cp.onRefreshComplete();
                try {
                    JSONObject object = new JSONObject(data.toString());
                    JSONObject result = object.getJSONObject("result");
                    JSONArray array = result.getJSONArray("list");
                    list = gson.fromJson(array.toString(), new TypeToken<List<ProductBean>>() {
                    }.getType());
                    if (list != null && list.size() > 0) {
                        if (currentPage == 1) {
                            datelist.clear();
                        }
                        datelist.addAll(list);
                        adapter.notifyDate(datelist);
                    } else {
                        if (currentPage == 1) {
                            adapter.notifyDate(new ArrayList<ProductBean>());
                        } else {

                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(int code, String error) {
                plv_cp.onRefreshComplete();
                MyToastUtils.showShortToast(getActivity(), error);
            }
        });

    }

    @OnClick({R.id.ll_moren, R.id.ll_lv, R.id.ll_qx})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.ll_moren:
                selectColor(0);
                break;

            case R.id.ll_lv:
                selectColor(1);
                break;

            case R.id.ll_qx:
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
        getDate();
    }

    @Override
    public void goshare(final ProductBean bean) {
        RequestParam param = new RequestParam();
        OkHttpUtils.post(HttpUrl.SHARETYPE_URL, param, new OkHttpCallBack(getActivity()) {
            @Override
            public void onSuccess(String data) {
                try {
                    JSONObject object = new JSONObject(data.toString());
                    String result = object.getString("result");
                    share(bean, result);//分享
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int code, String error) {
                MyToastUtils.showShortToast(getActivity(), error);
            }
        });

    }

    private static final int THUMB_SIZE = 150;
    private int mTargetScene = SendMessageToWX.Req.WXSceneSession;//会话

//    mTargetScene = SendMessageToWX.Req.WXSceneTimeline;//朋友圈
//    mTargetScene = SendMessageToWX.Req.WXSceneFavorite;//收藏

    UMWeb web;
    UMImage image;
    String wxshartitle;
    String shareDesc;
    String shareUrl = HttpUrl.SHAREPRO_URL;


    private void share(ProductBean bean, String type) {
//        image = new UMImage(getActivity(), R.mipmap.share_logo);
//        image.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
        wxshartitle = bean.getProductName();
        shareDesc = "年化" + bean.getInterest() + "%" + "｜" + RoncheUtil.getRealTerm(bean.getTerm())
                + bean.getTermTypeName() + "期"+"｜" + bean.getBenefitModeName();
//
//
//        web = new UMWeb(shareUrl);//分享路径
//        web.setTitle(wxshartitle);//标题
//        web.setThumb(image);  //缩略图
//        web.setDescription(shareDesc);//描述
//
//        new ShareAction(getActivity())
//                .setPlatform(SHARE_MEDIA.WEIXIN)
//                .setCallback(umShareListener)
//                .withMedia(web)
//                .share();

        if (type.equals("1")) {
            //微信api分享h5链接
            WXWebpageObject webpage = new WXWebpageObject();
            webpage.webpageUrl = shareUrl;
            WXMediaMessage msg = new WXMediaMessage(webpage);
            msg.title = wxshartitle;
            msg.description = shareDesc;
            Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.share_logo);
            Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
            bmp.recycle();
            msg.thumbData = WeiChartUtil.bmpToByteArray(thumbBmp, true);

            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = buildTransaction("webpage");
            req.message = msg;
            req.scene = mTargetScene;
            api.sendReq(req);
        } else if (type.equals("2")) {

            //微信api分享小程序

            WXMiniProgramObject miniProgramObj = new WXMiniProgramObject();
            miniProgramObj.webpageUrl = shareUrl; // 兼容低版本的网页链接
            miniProgramObj.miniprogramType = WXMiniProgramObject.MINIPTOGRAM_TYPE_RELEASE;// 正式版:0，测试版:1，体验版:2(正式上线要改成正式版)
//            miniProgramObj.userName ="gh_4d5b04f5ed68";     // 小程序原始id（客户端的id） (预发布正式小程序原始id)
            miniProgramObj.userName ="gh_e97c80956372";     // 小程序原始id（客户端的id）（测试）
            miniProgramObj.path = "/pages/productDescribe/index?proId="+bean.getProId();//小程序页面路径
            WXMediaMessage msg = new WXMediaMessage(miniProgramObj);
//            msg.title = "产融协同 最懂实业的供应链金融专家";// 小程序消息title
            String title="《"+wxshartitle+"》"+shareDesc;
            msg.title=title;
            msg.description = "嵘创财富邀请";// 小程序消息desc这个其实没显示
//            msg.thumbData = getThumb();         // 小程序消息封面图片，小于128k

            Bitmap temp = BitmapFactory.decodeResource(getActivity().getResources(),
                    R.mipmap.zaijcxclogo);
            msg.thumbData = MyBitmapUtils.Bitmap2Bytes(temp);
//            msg.setThumbImage(temp);
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = buildTransaction("webpage");
            req.message = msg;
            req.scene = SendMessageToWX.Req.WXSceneSession;  // 目前支持会话
            api.sendReq(req);
        }

    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }


    private UMShareListener umShareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            if (t.getMessage().contains("没有安装应用")) {
                MyToastUtils.showShortToast(getActivity(), "您未安装微信APP,请先安装");

            } else {
                MyToastUtils.showShortToast(getActivity(), t.getMessage());
            }
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            MyToastUtils.showShortToast(getActivity(), "取消");
        }
    };


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

    }

    public void upDate() {
        selectColor(0);
        getDate();
        getBanner();//获取首页banantu
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        selectColor(0);
//        getDate();
        getBanner();//获取首页banantu
    }

    @Override
    public void onItemClick(int position) {
        if (!TextUtils.isEmpty(noteurllist.get(position))) {
            startActivity(new Intent(getActivity(), WebAct.class).putExtra("type", "10").putExtra("hurl", noteurllist.get(position)));
        }
    }

}
