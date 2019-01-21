package com.rmwl.rcchgwd.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.rmwl.rcchgwd.R;
import com.rmwl.rcchgwd.Utils.RoncheUtil;
import com.rmwl.rcchgwd.Utils.TimePareUtil;
import com.rmwl.rcchgwd.base.BaseActivity;
import com.rmwl.rcchgwd.okhttp.RequestParam;

/**
 *
 * Created by Administrator on 2018/11/19.
 */

public class VersionAct extends BaseActivity{
    @ViewInject(R.id.tv_version)
    private TextView tv_version;
    @ViewInject(R.id.tv_company)
    private TextView tv_company;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_version);
    }

    @Override
    public void init(Bundle savedInstanceState) {
       setTopTitle("版本说明");
        tv_company.setText("©"+ TimePareUtil.getCurrentTime("yyyy"));
        tv_version.setText("Version "+ RoncheUtil.getVersionName(VersionAct.this)+" "+"(Build 1)");
    }


}
