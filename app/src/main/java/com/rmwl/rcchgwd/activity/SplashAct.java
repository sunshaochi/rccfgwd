package com.rmwl.rcchgwd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.WindowManager;

import com.jaeger.library.StatusBarUtil;
import com.rmwl.rcchgwd.MainActivity;
import com.rmwl.rcchgwd.R;
import com.rmwl.rcchgwd.Utils.SpUtils;
import com.rmwl.rcchgwd.base.BaseActivity;
import com.networkbench.agent.impl.NBSAppAgent;

/**
 * Created by Administrator on 2018/8/28.
 */

public class SplashAct extends BaseActivity {
    @Override
    public void setLayout() {
        // 避免从桌面启动程序后，会重新实例化入口类的activity
        if (!this.isTaskRoot()) {
            Intent intent = getIntent();
            if (intent != null) {
                String action = intent.getAction();
                if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) {
                    finish();
                    return;
                }
            }
        }
        setContentView(R.layout.act_splash);
        StatusBarUtil.setColor(this,getResources().getColor(R.color.white));

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);// 去掉Activity上面
        initDate();
    }

    @Override
    public void init(Bundle savedInstanceState) {
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去标题栏
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);// 去掉Activity上面
//        initDate();
    }

    private void initDate() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (SpUtils.getIsFirst(SplashAct.this)) {
                    SpUtils.setIsFirst(getApplicationContext(), false);
                    startActivity(new Intent(SplashAct.this,GuideActivity.class));
                    finish();
                } else {
                    if(TextUtils.isEmpty(SpUtils.getToken(SplashAct.this))) {
                        startActivity(new Intent(SplashAct.this, LoginAct.class));
                        finish();
                    }else {
                        if(SpUtils.getStatus(SplashAct.this).equals("1")){
                            startActivity(new Intent(SplashAct.this, MainActivity.class));
                        }else {
                            startActivity(new Intent(SplashAct.this, LoginAct.class));
                        }
                        finish();
                    }
                }
            }
        }, 2000);
    }
}
