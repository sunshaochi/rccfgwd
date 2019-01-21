package com.rmwl.rcchgwd.view;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rmwl.rcchgwd.R;
import com.rmwl.rcchgwd.Utils.BigDecimalUtil;
import com.rmwl.rcchgwd.activity.CpInfoAct;
import com.rmwl.rcchgwd.bean.ProductBean;

import java.util.List;

/**
 * Created by acer on 2018/8/22.
 */

public class SycpView extends LinearLayout{
    private Context context;
    private TextView tv_proname,tv_lv,tv_days,tv_rg,tv_person,tv_synum,tv_syname,tv_dw;
    private LinearLayout ll_item;
    public SycpView(Context context) {
        super(context);
        this.context=context;
    }

    public SycpView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
    }

    public SycpView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
    }


    public void init(final List<ProductBean>list){
        if(list!=null&&list.size()>0){
            removeAllViews();
            for(int i=0;i<list.size();i++){
                final ProductBean bean=list.get(i);
                View view= View.inflate(context, R.layout.item_sycp,null);
                tv_proname=view.findViewById(R.id.tv_proname);
                tv_lv=view.findViewById(R.id.tv_lv);
                tv_days=view.findViewById(R.id.tv_days);
                tv_rg=view.findViewById(R.id.tv_rg);
                tv_person=view.findViewById(R.id.tv_person);
                tv_synum=view.findViewById(R.id.tv_synum);
                tv_syname=view.findViewById(R.id.tv_syname);
                ll_item=view.findViewById(R.id.ll_item);
                tv_dw=view.findViewById(R.id.tv_dw);
                tv_proname.setText(list.get(i).getProductName());
                tv_lv.setText(list.get(i).getInterest());
                tv_days.setText(list.get(i).getTerm());
                tv_dw.setText(list.get(i).getTermTypeName());

                if(list.get(i).getStatus().equals("1")){//销售中
                tv_rg.setBackgroundResource(R.drawable.button);
                tv_rg.setText("立即预约");
                tv_person.setText("剩余"+list.get(i).getRemcount()+"人");
                tv_synum.setText("剩余"+list.get(i).getRemamount()+"万元");

                }else if(list.get(i).getStatus().equals("2")){//售罄
                    tv_rg.setBackgroundResource(R.drawable.nomobutton);
                    tv_rg.setText("已售罄");
                    tv_person.setText("预约"+ BigDecimalUtil.subtract(list.get(i).getAllcount(),list.get(i).getRemcount())+"人");
                    tv_synum.setText("预约"+BigDecimalUtil.subtract(list.get(i).getAmount(),list.get(i).getRemamount())+"万元");
                }

                tv_syname.setText(list.get(i).getBenefitModeName());
                ll_item.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        context.startActivity(new Intent(context,CpInfoAct.class).putExtra("proId",bean.getProId()));
                    }
                });
                addView(view);
            }
        }

    }
}
