package com.rmwl.rcchgwd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.rmwl.rcchgwd.R;
import com.rmwl.rcchgwd.base.BaseActivity;


/**
 * Created by Administrator on 2018/6/4.
 */

public class GuideActivity extends BaseActivity {
    @ViewInject(R.id.vp_guide)
    private ViewPager pager;

    private final static int[] guideImages = {R.mipmap.guide1, R.mipmap.guide2, R.mipmap.splash};
    private GuidePagerAdapter adapter;



    @Override
    public void setLayout() {
        setContentView(R.layout.act_guide);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initDate();
    }

    private void initDate() {
        adapter = new GuidePagerAdapter();
        pager.setAdapter(adapter);
    }

    private class GuidePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {

            return guideImages.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {

            return view == obj;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(GuideActivity.this, R.layout.guide_vp_item, null);

            final ImageView iv_goty = (ImageView) view.findViewById(R.id.iv_goty);
            if (position == guideImages.length - 1) {
                iv_goty.setVisibility(View.VISIBLE);
            } else {
                iv_goty.setVisibility(View.GONE);
            }

            view.setBackgroundResource(guideImages[position]);
            iv_goty.setEnabled(true);
            iv_goty.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    iv_goty.setEnabled(false);
                    Intent intent = new Intent(GuideActivity.this, LoginAct.class);
                    startActivity(intent);
                    finish();

                }
            });

            ((ViewPager) container).addView(view);
            return view;
        }

    }
}
