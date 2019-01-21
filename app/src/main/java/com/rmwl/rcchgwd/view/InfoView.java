package com.rmwl.rcchgwd.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rmwl.rcchgwd.R;
import com.rmwl.rcchgwd.bean.ProdIntervalBean;

import java.util.List;


/**
 * Created by acer on 2018/8/22.
 */

public class InfoView extends LinearLayout{
    private Context context;
    private TextView tv_proname,tv_lv,tv_days,tv_rg,tv_person,tv_synum,tv_syname,tv_dw;
    private LinearLayout ll_item;
    public InfoView(Context context) {
        super(context);
        this.context=context;
    }

    public InfoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
    }

    public InfoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
    }


    public void init(List<ProdIntervalBean> list){
        if(list!=null&&list.size()>0){
            removeAllViews();
            for(int i=0;i<list.size();i++){
                ProdIntervalBean bean=list.get(i);
                View view= View.inflate(context, R.layout.item_info,null);
                LinearLayout ll_top=view.findViewById(R.id.ll_top);
                TextView tv_left=view.findViewById(R.id.tv_left);
                TextView tv_right=view.findViewById(R.id.tv_right);
                if(i==0){
                    ll_top.setVisibility(VISIBLE);
                }else {
                    ll_top.setVisibility(GONE);
                }
                tv_left.setText(bean.getPurchaseAmount());
                tv_right.setText(bean.getIntervalrate());
                addView(view);
            }
        }

    }
}
