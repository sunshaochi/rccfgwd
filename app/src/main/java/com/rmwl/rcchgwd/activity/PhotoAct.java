package com.rmwl.rcchgwd.activity;

import android.os.Bundle;
import android.view.View;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.rmwl.rcchgwd.R;
import com.rmwl.rcchgwd.base.BaseActivity;

/**
 * 查看大图
 * Created by Administrator on 2019/1/6.
 */

public class PhotoAct extends BaseActivity {
    @ViewInject(R.id.img)
    private PhotoView img;

    private String imgUrl;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_photo);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        imgUrl = getIntent().getStringExtra("imgUrl");
        // 启用图片缩放功能
        img.enable();
        Glide.with(mContext).load(imgUrl).into(img);
    }

    @OnClick({R.id.img})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.img:
                finish();
                break;
        }
    }
}
