package com.rmwl.rcchgwd;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.rmwl.rcchgwd.Utils.GeneralUtils;
import com.rmwl.rcchgwd.Utils.GsonUtils;
import com.rmwl.rcchgwd.Utils.MyToastUtils;
import com.rmwl.rcchgwd.Utils.PermisionUtils;
import com.rmwl.rcchgwd.Utils.RoncheUtil;
import com.rmwl.rcchgwd.Utils.StatusBarUtils;
import com.rmwl.rcchgwd.activity.RegistAct;
import com.rmwl.rcchgwd.base.AppManager;
import com.rmwl.rcchgwd.base.BaseActivity;
import com.rmwl.rcchgwd.bean.VersionBean;
import com.rmwl.rcchgwd.fragment.CpFragment;
import com.rmwl.rcchgwd.fragment.SyFragment;
import com.rmwl.rcchgwd.fragment.TujFragment;
import com.rmwl.rcchgwd.fragment.WdFragment;
import com.rmwl.rcchgwd.okhttp.HttpUrl;
import com.rmwl.rcchgwd.okhttp.OkHttpCallBack;
import com.rmwl.rcchgwd.okhttp.OkHttpUtils;
import com.rmwl.rcchgwd.okhttp.RequestParam;
import com.umeng.socialize.UMShareAPI;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends BaseActivity {
    @ViewInject(R.id.rg_tab)
    private RadioGroup rg_tab;
    @ViewInject(R.id.rb_sy)
    private RadioButton rb_sy;
    @ViewInject(R.id.rb_cp)
    private RadioButton rb_cp;
    private FragmentManager fragmentManager;

//    private SyFragment syfra;
    private CpFragment syfra;
//    private CpFragment cpfra;
    private TujFragment cpfra;
    private WdFragment wdfra;

    private GeneralUtils generalUtils;
    private String type;


    @Override
    public void setLayout() {
        setContentView(R.layout.activity_main);
        StatusBarUtils.setStatusBarTranslucent(this);
    }

    @Override
    public void init(Bundle savedInstanceState) {
//        generalUtils=new GeneralUtils();
//        generalUtils.toVersion(MainActivity.this);

        AppManager.getAppManager().addActivity(MainActivity.this);
        rg_tab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (radioGroup.getCheckedRadioButtonId()){
                    case R.id.rb_sy:
                        showFrag(0);//显示不同fragmenet
                        break;
                    case R.id.rb_cp:
                        showFrag(1);//显示不同fragmenet
                        break;
                    case R.id.rb_wd:
                        showFrag(2);//显示不同fragmenet
                        break;
                }
            }
        });

        rb_sy.setChecked(true);
//        showFrag(0);

    }

    private void registPersim() {

        RequestParam param = new RequestParam();
        param.add("source","3");
        param.add("versionType","1");//android
        OkHttpUtils.post(HttpUrl.APPVERSION,param,new OkHttpCallBack(MainActivity.this,false) {

            @Override
            public void onSuccess(String data) {
                try {
                    JSONObject jsonObject=new JSONObject(data);
                    JSONObject result=jsonObject.getJSONObject("result");
                    VersionBean bean= GsonUtils.gsonToBean(result.toString(),VersionBean.class);
                    if(Integer.parseInt(bean.getVersionCode()+"")> Integer.parseInt(RoncheUtil.getVersionCode(MainActivity.this))){
                        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
                            if(!PermisionUtils.getPermissState(MainActivity.this,PermisionUtils.TYPEONE).isEmpty()){
                                String[] permissions = PermisionUtils.getPermissState(MainActivity.this,
                                        PermisionUtils.TYPEONE).toArray(new String[PermisionUtils.getPermissState(MainActivity.this,PermisionUtils.TYPEONE).size()]);//将List转为数组
                                requestPermissions(permissions, PermisionUtils.REQUESTSTORAGEPERIMISSINCODE);
                            }else {
                                generalUtils = GeneralUtils.getInstance();
                                generalUtils.toVersion(MainActivity.this);
                            }

                        }

                    }else {
                        generalUtils = GeneralUtils.getInstance();
                        generalUtils.toVersion(MainActivity.this);
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

    private void showFrag(int i) {
        type=i+"";
        fragmentManager=getSupportFragmentManager();//申明
        FragmentTransaction transaction= fragmentManager.beginTransaction();
        hideFragment(transaction);//影长fragment
        switch (i){
            case 0:
                if(syfra==null){
                    syfra=new CpFragment();
                    transaction.add(R.id.fra_main,syfra);
                }else {
                    transaction.show(syfra);
                }
                break;

            case 1:
                if(cpfra==null){
                    cpfra=new TujFragment();
                    transaction.add(R.id.fra_main,cpfra);
                }else {
                    transaction.show(cpfra);
                }
                break;


            case 2:
                if(wdfra==null){
                    wdfra=new WdFragment();
                    transaction.add(R.id.fra_main,wdfra);
                }else {
                    transaction.show(wdfra);
                }
                break;

        }
        transaction.commit();

        registPersim();//动态注册权限并且获取版本更新
    }

    private void hideFragment(FragmentTransaction transaction) {

        if (syfra != null) {
            transaction.hide(syfra);
        }
        if(cpfra!=null){
            transaction.hide(cpfra);
        }
        if (wdfra != null) {
            transaction.hide(wdfra);
        }

    }

    //从其他界面回到主界面更新界面
    @Override
    protected void onResume() {
        super.onResume();
        if(type.equals("0")){
//            syfra.upDate();
        }else if(type.equals("1")){
            cpfra.upDate();
        }else if(type.equals("2")) {
            wdfra.upDate();
        }

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case PermisionUtils.REQUESTSTORAGEPERIMISSINCODE:
                for(int i=0;i<grantResults.length;i++){
                    if(grantResults[i]!=PackageManager.PERMISSION_GRANTED){//拒绝
                        //判断是否勾选禁止后不再询问
                        boolean showRequestPermission = ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permissions[i]);
                        if (showRequestPermission) {//是否被勾选默认
                            MyToastUtils.showShortToast(MainActivity.this,"权限已被禁止");
                            return;
                        }else {//勾选上了
                            MyToastUtils.showShortToast(MainActivity.this,"发现新版本,需要您手动开启读取权限更新最新版本");

                            return;
                        }
                    }

                }

                registPersim();
                break;

        }
    }
}
