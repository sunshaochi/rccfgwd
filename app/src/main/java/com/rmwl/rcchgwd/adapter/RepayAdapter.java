package com.rmwl.rcchgwd.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.networkbench.com.google.gson.reflect.TypeToken;
import com.rmwl.rcchgwd.R;
import com.rmwl.rcchgwd.Utils.BigDecimalUtil;
import com.rmwl.rcchgwd.Utils.ButtonUtils;
import com.rmwl.rcchgwd.Utils.RoncheUtil;
import com.rmwl.rcchgwd.Utils.SpUtils;
import com.rmwl.rcchgwd.bean.RepayBean;
import com.rmwl.rcchgwd.okhttp.HttpUrl;
import com.rmwl.rcchgwd.okhttp.OkHttpCallBack;
import com.rmwl.rcchgwd.okhttp.OkHttpUtils;
import com.rmwl.rcchgwd.okhttp.RequestParam;
import com.rmwl.rcchgwd.view.DetailDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2019/1/4.
 */

public class RepayAdapter extends RecyclerView.Adapter<RepayAdapter.MyHolder>{
    private Context context;
    private List<RepayBean>list;
    private Gson gson;

    public RepayAdapter(Context context, List<RepayBean>list) {
        this.context = context;
        this.list=list;
        gson=new Gson();
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= View.inflate(context, R.layout.item_repay,null);
        MyHolder holder=new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.setPostion(position);
        holder.tv_qs.setText("期数:"+isNull(list.get(position).getTerm()));

        if(!TextUtils.isEmpty(list.get(position).getStatusName())) {
            if(list.get(position).getStatusName().equals("待兑付")){
                holder.tv_hkr.setText("到期日："+isNull(list.get(position).getExpectPaydate()));
                holder.tv_dfzt.setBackgroundResource(R.drawable.paystate);
                holder.tv_dfkh.setVisibility(View.GONE);
            } else if(list.get(position).getStatusName().equals("已兑付")){
                holder.tv_hkr.setText("实际兑付日期："+isNull(RoncheUtil.getRealTime(list.get(position).getPayDate())));
                holder.tv_dfzt.setBackgroundResource(R.drawable.paydufustate);
                holder.tv_dfkh.setVisibility(View.VISIBLE);
            }else if(list.get(position).getStatusName().equals("已结清")){
                holder.tv_hkr.setText("实际兑付日期："+isNull(list.get(position).getExpireDate()));
                holder.tv_dfzt.setBackgroundResource(R.drawable.payjieqinstate);
                holder.tv_dfkh.setVisibility(View.VISIBLE);
            }

        }else {
            holder.tv_hkr.setText("到期日："+isNull(list.get(position).getExpectPaydate()));
            holder.tv_dfzt.setBackgroundResource(R.drawable.paystate);
        }
        holder.tv_dfzt.setText(list.get(position).getStatusName());
        holder.tv_ze.setText(isNull(list.get(position).getAmount()));
        holder.tv_lx.setText(isNull(list.get(position).getInterest()));
        if (!TextUtils.isEmpty(list.get(position).getCardNo()))
            holder.tv_dfkh.setText(isNull("兑付卡尾号："+list.get(position).getCardNo()));
        if(!TextUtils.isEmpty(list.get(position).getPrinciple())) {
            holder.tv_bj.setText(isNull(RoncheUtil.getRealTerm(BigDecimalUtil.divide(Double.parseDouble(list.get(position).getPrinciple()), Double.parseDouble("10000")).toString())));
        }else {
            holder.tv_bj.setText("0");
        }
    }

    private String isNull(String tex) {
        if(TextUtils.isEmpty(tex)){
            return "--";
        }else {
            return  tex;
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        int dex;
        TextView tv_qs,tv_hkr,tv_dfzt,tv_ze,tv_lx,tv_bj,tv_dfkh;
        ImageView iv_minxi;

        public MyHolder(View itemView) {
            super(itemView);
            tv_qs=itemView.findViewById(R.id.tv_qs);
            tv_hkr=itemView.findViewById(R.id.tv_hkr);
            tv_dfzt=itemView.findViewById(R.id.tv_dfzt);
            tv_ze=itemView.findViewById(R.id.tv_ze);
            tv_lx=itemView.findViewById(R.id.tv_lx);
            tv_bj=itemView.findViewById(R.id.tv_bj);
            iv_minxi=itemView.findViewById(R.id.iv_minxi);
            tv_dfkh=itemView.findViewById(R.id.tv_dfkh);
            tv_dfkh.setVisibility(View.GONE);

            iv_minxi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ButtonUtils.isFastDoubleClick(v.getId());
                    getDetail(dex);//获取明细


                }
            });

        }

        public void setPostion(int position) {
            this.dex=position;
        }
    }

    private void getDetail(int positon) {
        RequestParam param=new RequestParam();
        param.add("termId",list.get(positon).getId());
        param.add("userId", list.get(positon).getUserId());
        OkHttpUtils.post(HttpUrl.QUERYPRODREPAYTERMDETAIL_URL, param, new OkHttpCallBack(context,false) {
            @Override
            public void onSuccess(String data) {
                try {
                    JSONObject object=new JSONObject(data.toString());
                    JSONArray array=object.getJSONArray("result");
                    List<RepayBean>datelist=gson.fromJson(array.toString(),new TypeToken<List<RepayBean>>(){}.getType());
//                    if(datelist!=null&&datelist.size()>0){
                    DetailDialog dialog=new DetailDialog(context,datelist).build();
                    dialog.show();
//                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int code, String error) {

            }
        });
    }


    public void upDate(List<RepayBean>list) {
        this.list=list;
        notifyDataSetChanged();
    }
}
