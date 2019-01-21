package com.rmwl.rcchgwd.view;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.rmwl.rcchgwd.R;
import com.rmwl.rcchgwd.bean.RepayBean;

import java.util.List;

/**
 * Created by Administrator on 2019/1/5.
 */

public class DetailDialog {
    private Context context;
    private Dialog dialog;
    private Display display;
    private TextView tv_lx,tv_close;
    private ListView lv_list;
    private List<RepayBean> list;



    public DetailDialog(Context context, List<RepayBean> list) {
        this.context = context;
        this.list=list;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public DetailDialog build(){
        View view = LayoutInflater.from(context).inflate(R.layout.detaildialog, null);

        tv_close=view.findViewById(R.id.tv_close);
        lv_list=view.findViewById(R.id.lv_list);

        lv_list.setAdapter(new MyDetailAdapter());

        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialog=null;
            }
        });

        // 定义Dialog布局和参数
        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager manager=dialogWindow.getWindowManager();
        Display d=manager.getDefaultDisplay();
        lp.width=d.getWidth();
//        lp.x = 0;
//        lp.y = 0;
        dialogWindow.setAttributes(lp);
        return this;
    }


    public DetailDialog setLv(String  text) {
        tv_lx.setText(text);
        return this;
    }

    public void show() {
        dialog.show();
    }

    private class MyDetailAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View item= View.inflate(context,R.layout.item_detaildialog,null);
            LinearLayout ll_one=item.findViewById(R.id.ll_one);
            LinearLayout ll_two=item.findViewById(R.id.ll_two);
            TextView tv_jdlx=item.findViewById(R.id.tv_jdlx);
            TextView tv_je=item.findViewById(R.id.tv_je);
            TextView tv_ll=item.findViewById(R.id.tv_ll);
            TextView tv_time=item.findViewById(R.id.tv_time);
            TextView tv_jdname=item.findViewById(R.id.tv_jdname);
            TextView tv_lx=item.findViewById(R.id.tv_lx);
            ll_one.setVisibility(View.GONE);
            ll_two.setVisibility(View.GONE);

           if(list.get(position).getType().equals("1")){
               ll_one.setVisibility(View.VISIBLE);
               tv_lx.setText(isNull(list.get(position).getRealinterest())+"元");
           }else {
               ll_two.setVisibility(View.VISIBLE);
               tv_jdname.setText(isNull(list.get(position).getPeriodName()));
               tv_jdlx.setText(isNull(list.get(position).getRealinterest())+"元");
               tv_je.setText(isNull(list.get(position).getRealamountView()+"万元"));
               tv_ll.setText(isNull(list.get(position).getInterest())+"%");
               tv_time.setText(isNull(list.get(position).getInterestBegindate())+"至"+isNull(list.get(position).getInterestEnddate()));

           }

            return item;
        }
    }

    private String isNull(String tex) {
        if(TextUtils.isEmpty(tex)){
            return "--";
        }else {
            return tex;
        }
    }
}
