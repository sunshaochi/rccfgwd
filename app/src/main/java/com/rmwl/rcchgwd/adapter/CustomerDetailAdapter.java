package com.rmwl.rcchgwd.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rmwl.rcchgwd.R;
import com.rmwl.rcchgwd.Utils.RoncheUtil;
import com.rmwl.rcchgwd.activity.RepaymentAct;
import com.rmwl.rcchgwd.bean.InvestBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by acer on 2018/8/21.
 */

public class CustomerDetailAdapter extends RecyclerView.Adapter<CustomerDetailAdapter.MyHolder>{
    private Context context;
    private List<InvestBean> list;
    private String type;
    public CustomerDetailAdapter(Context context,List<InvestBean> list,String type) {
        this.context = context;
        this.list = list;
        this.type=type;
    }

    public void notifyData(List<InvestBean> list,String type){
        this.list = list;
        this.type=type;
        notifyDataSetChanged();
    }
    @Override
    public CustomerDetailAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cus_detail,parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(CustomerDetailAdapter.MyHolder holder, int position) {
        holder.bind(position);

        holder.tv_cp_name.setText(list.get(position).getProductName());
        holder.tv_time.setText(RoncheUtil.getRealTime(list.get(position).getBuyTime()));
        holder.tv_order_money.setText(list.get(position).getAmount());

        if (!TextUtils.isEmpty(list.get(position).getRate())) {
            holder.tv_order_yearly.setText(list.get(position).getRate());
        } else {
            holder.tv_order_yearly.setText("0.00");
        }
        holder.tv_invest_time.setText(RoncheUtil.getRealTerm(list.get(position).getTerm()));

        if (list.get(position).getTermTypeName().contains("月")){
            holder.tv_term.setText("个"+list.get(position).getTermTypeName());
        }else {
            holder.tv_term.setText(list.get(position).getTermTypeName());
        }

        holder.tv_status.setText(isNull(list.get(position).getStatusName()));

        if(type.equals("1")){//预约中
            holder.tv_income.setVisibility(View.GONE);
            holder.tv_next_time.setVisibility(View.GONE);
            holder.ll_botem.setVisibility(View.GONE);
            holder.tv_order_money_name.setText("预约金额");

            if(!TextUtils.isEmpty(list.get(position).getStatus())&&list.get(position).getStatus().equals("1")){//支付成功
                holder.tv_status.setBackgroundResource(R.drawable.paydufustate);
                if(!TextUtils.isEmpty(list.get(position).getAmount())
                        &&!TextUtils.isEmpty(list.get(position).getRealamount())&&list.get(position).getAmount().equals(list.get(position).getRealamount())){
                    holder.rl_sf.setVisibility(View.GONE);
                }else {
                    holder.tv_order_money.setText(isNull(list.get(position).getRealamount()));//实付
                    holder.tv_sf.setText(isNull(list.get(position).getAmount()));//预约
                     holder.tv_sf.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
                     holder.rl_sf.setVisibility(View.VISIBLE);

                }

            }else {
                holder.tv_status.setBackgroundResource(R.drawable.paystate);
                holder.rl_sf.setVisibility(View.GONE);
            }

            holder.tv_status.setVisibility(View.VISIBLE);



        }else {//2收益中 //3已经结清
            holder.tv_status.setVisibility(View.GONE);
            holder.tv_order_money_name.setText("到账金额");
            holder.rl_sf.setVisibility(View.GONE);
            if(type.equals("2")){
                holder.tv_income.setText("已兑付金额："+isNull(list.get(position).getPayAmount())+"元");
                holder.tv_next_time.setText("未兑付金额："+list.get(position).getRemAmount()+"元");
            }else {
                holder.tv_income.setText("结清时间:"+isNull(list.get(position).getCloseTime()));
                holder.tv_next_time.setText("收益："+list.get(position).getHasInterest()+"元");
            }

            holder.tv_income.setVisibility(View.VISIBLE);
            holder.tv_next_time.setVisibility(View.VISIBLE);
            holder.ll_botem.setVisibility(View.VISIBLE);
        }







    }

    private String isNull(String statusName) {
        if(!TextUtils.isEmpty(statusName)){
            return statusName;
        }
        return "";
    }


    @Override
    public int getItemCount() {
        return list.size();
    }




    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView tv_cp_name,tv_time,tv_order_money,tv_order_yearly,tv_invest_time,tv_income,tv_next_time,tv_term,tv_status,tv_order_money_name;
        private LinearLayout ll_item,ll_botem,ll_two;
        RelativeLayout rl_sf;
        TextView tv_sf,tv_wy;
        int dex;
        View view_line;
        public MyHolder(View itemView) {
            super(itemView);
            ll_item=itemView.findViewById(R.id.ll_item);
            ll_botem=itemView.findViewById(R.id.ll_botem);
            tv_cp_name=itemView.findViewById(R.id.tv_cp_name);
            tv_time=itemView.findViewById(R.id.tv_time);
            tv_order_money=itemView.findViewById(R.id.tv_order_money);
            tv_invest_time=itemView.findViewById(R.id.tv_invest_time);
            tv_order_yearly=itemView.findViewById(R.id.tv_order_yearly);
            tv_income=itemView.findViewById(R.id.tv_income);
            tv_next_time=itemView.findViewById(R.id.tv_next_time);
            tv_term=itemView.findViewById(R.id.tv_term);
            tv_status=itemView.findViewById(R.id.tv_state);
            tv_order_money_name = itemView.findViewById(R.id.tv_order_money_name);
            tv_income.setVisibility(View.GONE);
            tv_next_time.setVisibility(View.GONE);

            rl_sf=itemView.findViewById(R.id.rl_sf);
            tv_sf=itemView.findViewById(R.id.tv_sf);
            rl_sf.setVisibility(View.GONE);
            ll_botem.setVisibility(View.GONE);
            view_line=itemView.findViewById(R.id.view_line);
            ll_two=itemView.findViewById(R.id.ll_two);
            tv_wy=itemView.findViewById(R.id.tv_wy);
            tv_wy.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线

            ll_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!type.equals("1"))
                        context.startActivity(new Intent(context,RepaymentAct.class).putExtra("pubId",list.get(dex).getPubId()));
                }
            });

        }

        public void bind(final int position) {
          this.dex=position;
        }
    }

}
