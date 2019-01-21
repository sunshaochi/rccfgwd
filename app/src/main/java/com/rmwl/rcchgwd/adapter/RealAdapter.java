package com.rmwl.rcchgwd.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rmwl.rcchgwd.R;
import com.rmwl.rcchgwd.bean.RealBean;

import java.util.List;

/**
 * Created by acer on 2018/8/21.
 */

public class RealAdapter extends RecyclerView.Adapter<RealAdapter.MyHolder>{
    private Context context;
    private List<RealBean> list;

    public RealAdapter(Context context,List<RealBean> list) {
        this.context = context;
        this.list = list;
    }
    public void notifyData(List<RealBean> list){
        this.list = list;
        notifyDataSetChanged();
    }
    @Override
    public RealAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_real,parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(RealAdapter.MyHolder holder, int position) {
        holder.bind(position);
        if (!TextUtils.isEmpty(list.get(position).getRealname())) {
            holder.civ_head.setText(list.get(position).getRealname().substring(0, 1));
        }else {
            holder.civ_head.setText("");
        }
        holder.tv_name.setText(list.get(position).getRealname());
        holder.tv_phone.setText(list.get(position).getMobile());
        holder.tv_birthday.setText(list.get(position).getBirthday());
        holder.tv_register_time.setText(list.get(position).getCreateTime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }




    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView iv_status;
        private TextView civ_head,tv_name,tv_phone,tv_birthday,tv_register_time;
        private LinearLayout ll_item,ll_msg,ll_call;

        public MyHolder(View itemView) {
            super(itemView);
            ll_item=itemView.findViewById(R.id.ll_item);
            ll_msg=itemView.findViewById(R.id.ll_msg);
            ll_call=itemView.findViewById(R.id.ll_call);
            tv_name=itemView.findViewById(R.id.tv_name);
            tv_phone=itemView.findViewById(R.id.tv_phone);
            tv_birthday=itemView.findViewById(R.id.tv_birthday);
            tv_register_time=itemView.findViewById(R.id.tv_register_time);
            civ_head=itemView.findViewById(R.id.civ_head);
            iv_status = itemView.findViewById(R.id.iv_status);
        }

        public void bind(final int position) {
            ll_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    Uri data = Uri.parse("tel:" + list.get(position).getMobile());
                    intent.setData(data);
                    context.startActivity(intent);

                }
            });
            ll_msg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri smsToUri = Uri.parse("smsto:"+list.get(position).getMobile());
                    Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
                    intent.putExtra("sms_body", "");
                    context.startActivity(intent);
                }
            });
        }


    }

}
