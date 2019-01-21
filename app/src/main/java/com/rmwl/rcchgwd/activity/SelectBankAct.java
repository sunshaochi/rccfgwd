package com.rmwl.rcchgwd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.rmwl.rcchgwd.R;
import com.rmwl.rcchgwd.base.BaseActivity;
import com.rmwl.rcchgwd.bean.DicBankinforBean;
import com.rmwl.rcchgwd.bean.YhkBean;

import java.util.List;

/**
 * Created by acer on 2018/8/25.
 */

public class SelectBankAct extends BaseActivity {
    @ViewInject(R.id.lv_bank)
    private ListView lv_bank;
    private List<YhkBean> list;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_act_selectbank);
    }

    @Override
    public void init(Bundle savedInstanceState) {
     setTopTitle("选择银行");
     list= (List<YhkBean>) getIntent().getExtras().getSerializable("list");
     lv_bank.setAdapter(new MyAdapter());
     lv_bank.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             setResult(RESULT_OK,new Intent().putExtra("bankid",list.get(position).getId()).
                     putExtra("bankname",list.get(position).getBankname()));
                     finish();
         }
     });
    }

    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view=View.inflate(SelectBankAct.this,R.layout.item_bank,null);
            ImageView iv_login=(ImageView) view.findViewById(R.id.iv_login);
            TextView tv_name=(TextView) view.findViewById(R.id.tv_name);
            Glide.with(SelectBankAct.this).load(list.get(i).getIconUrl()).placeholder(R.mipmap.bananone).into(iv_login);
            tv_name.setText(list.get(i).getBankfullname());
            return view;
        }
    }
}
