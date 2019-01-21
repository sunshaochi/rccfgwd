package com.rmwl.rcchgwd.activity;

import android.app.Application;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.rmwl.rcchgwd.MainActivity;
import com.rmwl.rcchgwd.R;
import com.rmwl.rcchgwd.Utils.CacheUtils;
import com.rmwl.rcchgwd.Utils.GeneralUtils;
import com.rmwl.rcchgwd.Utils.MyToastUtils;
import com.rmwl.rcchgwd.Utils.SpUtils;
import com.rmwl.rcchgwd.base.AppManager;
import com.rmwl.rcchgwd.base.BaseActivity;
import com.rmwl.rcchgwd.jpush.TagAliasOperatorHelper;
import com.rmwl.rcchgwd.view.MyAlertDialog;

import java.util.Set;

import static com.rmwl.rcchgwd.jpush.TagAliasOperatorHelper.ACTION_DELETE;
import static com.rmwl.rcchgwd.jpush.TagAliasOperatorHelper.sequence;

/**
 * Created by acer on 2018/8/23.
 */

public class SettingAct extends BaseActivity{
    @ViewInject(R.id.set_cache)
    private TextView cacheSize;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_setting);
    }

    @Override
    public void init(Bundle savedInstanceState) {
         AppManager.getAppManager().addActivity(this);
         setTopTitle("账户设置");
        try {
            cacheSize.setText(CacheUtils.getTotalCacheSize(getApplicationContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }
      }

    @OnClick({R.id.com_xg,R.id.tv_tc,R.id.set_clear_layout,R.id.ll_version})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.com_xg:
                openActivity(UpdatePwdAct.class);
                break;

            case R.id.tv_tc:
                MyAlertDialog dialog=new MyAlertDialog(SettingAct.this);
                dialog.builder().setMsg("确定退出");
                dialog.setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        unregistAlias();
                        SpUtils.clearSp(SettingAct.this);
                        SpUtils.setUser(SettingAct.this,"");
                        openActivity(LoginAct.class);
                        finish();
                        AppManager.getAppManager().finishActivity(MainActivity.class);
                    }
                }).show();
                break;

            case R.id.set_clear_layout:
                clearCache();
                break;

            case R.id.ll_version:
                openActivity(VersionAct.class);
                break;
        }
    }

    private void clearCache()
    {
//                GlideCatchUtil.getInstance().clearCacheMemory();
//                GlideCatchUtil.getInstance().cleanCatchDisk();
//                GlideCatchUtil.getInstance().clearCacheDiskSelf();
        CacheUtils.clearAllCache(getApplicationContext());
        try {
            cacheSize.setText(CacheUtils.getTotalCacheSize(getApplicationContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        GeneralUtils.ClearWebCookier(SettingAct.this);
        MyToastUtils.showShortToast(SettingAct.this,"缓存清除完毕");
    }

    boolean isAliasAction = false;
    int action = -1;
    //激光推送退出注册别名
    private void unregistAlias() {
        Set<String> tags = null;
        String alias ="";
        isAliasAction = true;
        action = ACTION_DELETE;
        TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
        tagAliasBean.action = action;
        sequence++;
        if(isAliasAction){
            tagAliasBean.alias = alias;
        }else{
            tagAliasBean.tags = tags;
        }
        tagAliasBean.isAliasAction = isAliasAction;
        TagAliasOperatorHelper.getInstance().handleAction(getApplicationContext(),sequence,tagAliasBean);
    }
}
