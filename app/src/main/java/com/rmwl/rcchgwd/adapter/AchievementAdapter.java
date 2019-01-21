package com.rmwl.rcchgwd.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rmwl.rcchgwd.R;
import com.rmwl.rcchgwd.activity.CpInfoAct;
import com.rmwl.rcchgwd.activity.CustomerDetailAct;
import com.rmwl.rcchgwd.bean.AchieveBean;
import com.rmwl.rcchgwd.bean.ProductBean;
import com.rmwl.rcchgwd.view.ShareDialog;

import java.util.List;

/**
 * Created by acer on 2018/8/21.
 */

public class AchievementAdapter extends RecyclerView.Adapter<AchievementAdapter.MyHolder>{
    private Context context;
    private List<AchieveBean> list;

    public AchievementAdapter(Context context,List<AchieveBean> list) {
        this.context = context;
        this.list = list;
    }

    public void notifyData(List<AchieveBean> list){
        this.list = list;
        notifyDataSetChanged();
    }
    @Override
    public AchievementAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_achievement,parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(AchievementAdapter.MyHolder holder, int position) {
        holder.bind(position);
        if (!TextUtils.isEmpty(list.get(position).getUsername())) {
            holder.civ_head.setText(list.get(position).getUsername().substring(0, 1));
        }else {
            holder.civ_head.setText("");
        }
        holder.tv_name.setText(list.get(position).getUsername());
        holder.tv_phone.setText(list.get(position).getMobile());
        String amount = list.get(position).getAmount() + "万元";
        Spannable sp = new SpannableString(amount);
        sp.setSpan(new AbsoluteSizeSpan(18, true), 0, list.get(position).getAmount().length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        sp.setSpan(new AbsoluteSizeSpan(12, true), list.get(position).getAmount().length(), amount.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        holder.tv_invest.setText(sp);
        holder.tv_num.setText(list.get(position).getCount());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }




    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView civ_head;
        private TextView tv_name,tv_phone,tv_invest,tv_num;
        private LinearLayout ll_item;

        public MyHolder(View itemView) {
            super(itemView);
            ll_item=itemView.findViewById(R.id.ll_item);
            tv_name=itemView.findViewById(R.id.tv_name);
            tv_phone=itemView.findViewById(R.id.tv_phone);
            tv_invest=itemView.findViewById(R.id.tv_invest);
            tv_num=itemView.findViewById(R.id.tv_num);
            civ_head=itemView.findViewById(R.id.civ_head);
            civ_head.setText("");
        }

        public void bind(final int position) {
            ll_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                 context.startActivity(new Intent(context,CustomerDetailAct.class).putExtra("userId",list.get(position).getUserid()));
                }
            });

        }
    }

}
