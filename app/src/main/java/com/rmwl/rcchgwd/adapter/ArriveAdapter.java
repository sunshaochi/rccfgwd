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
import com.rmwl.rcchgwd.Utils.RoncheUtil;
import com.rmwl.rcchgwd.bean.ArriveBean;

import java.util.List;

/**
 * Created by acer on 2018/8/21.
 */

public class ArriveAdapter extends RecyclerView.Adapter<ArriveAdapter.MyHolder>{
    private Context context;
    private List<ArriveBean> list;

    public ArriveAdapter(Context context,List<ArriveBean> list) {
        this.context = context;
        this.list = list;
    }

    public void notifyData(List<ArriveBean> list){
        this.list = list;
        notifyDataSetChanged();
    }
    @Override
    public ArriveAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_arrive,parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(ArriveAdapter.MyHolder holder, int position) {
        holder.bind(position);
        holder.iv_status.setText(list.get(position).getStatusName());
        if (!TextUtils.isEmpty(list.get(position).getUserName())) {
            holder.civ_head.setText(list.get(position).getUserName().substring(0, 1));
        }else {
            holder.civ_head.setText("");
        }
        holder.tv_name.setText(list.get(position).getUserName());
        holder.tv_phone.setText(list.get(position).getMobile());
        holder.tv_cp_name.setText(list.get(position).getProductName());
        holder.tv_invest_time.setText(RoncheUtil.getRealYMDHMTime(list.get(position).getPurchaseDate()));
        holder.tv_invest.setText(list.get(position).getAmount());
        holder.tv_top_time.setText("划款开始时间："+RoncheUtil.getRealYMDHMTime(list.get(position).getPayBegindate()));
        holder.tv_below_time.setText("划款结束时间："+RoncheUtil.getRealYMDHMTime(list.get(position).getPayEdndate()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }




    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView iv_status;
        private TextView civ_head,tv_name,tv_phone,tv_cp_name,tv_invest_time,tv_invest,tv_top_time,tv_below_time;
        private LinearLayout ll_item,ll_msg,ll_call;

        public MyHolder(View itemView) {
            super(itemView);
            ll_item=itemView.findViewById(R.id.ll_item);
            ll_msg=itemView.findViewById(R.id.ll_msg);
            ll_call=itemView.findViewById(R.id.ll_call);
            tv_name=itemView.findViewById(R.id.tv_name);
            tv_cp_name=itemView.findViewById(R.id.tv_cp_name);
            tv_phone=itemView.findViewById(R.id.tv_phone);
            tv_invest=itemView.findViewById(R.id.tv_invest);
            tv_invest_time=itemView.findViewById(R.id.tv_invest_time);
            civ_head=itemView.findViewById(R.id.civ_head);
            iv_status = itemView.findViewById(R.id.iv_status);
            tv_top_time=itemView.findViewById(R.id.tv_top_time);
            tv_below_time = itemView.findViewById(R.id.tv_below_time);
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
