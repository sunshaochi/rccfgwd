package com.rmwl.rcchgwd.adapter;

import android.content.Context;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rmwl.rcchgwd.R;
import com.rmwl.rcchgwd.Utils.BigDecimalUtil;
import com.rmwl.rcchgwd.Utils.MyToastUtils;
import com.rmwl.rcchgwd.Utils.RoncheUtil;
import com.rmwl.rcchgwd.activity.CpInfoAct;
import com.rmwl.rcchgwd.bean.ProductBean;
import com.rmwl.rcchgwd.view.ShareDialog;

import java.util.List;

/**
 * Created by acer on 2018/8/21.
 */

public class CpFraAdapter extends RecyclerView.Adapter<CpFraAdapter.MyHolder>{
    private Context context;
    private List<ProductBean>list;

    public CpFraAdapter(Context context,List<ProductBean>list) {
        this.context = context;
        this.list=list;
    }

    @Override
    public CpFraAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= View.inflate(context, R.layout.item_cpfra,null);
        MyHolder holder=new MyHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(CpFraAdapter.MyHolder holder, int position) {
        holder.bind(position);
        holder.tv_proname.setText(list.get(position).getProductName());

//        if(!TextUtils.isEmpty(list.get(position).getProgressVal())){
//            holder.tv_pb.setText(list.get(position).getProgressVal()+"%");
//            holder.pb.setProgress(Integer.parseInt(list.get(position).getProgressVal()));
//        }else {
//            holder.tv_pb.setText("0%");
//            holder.pb.setProgress(0);
//        }

        //上面是后台返回的百分比等接口出来直接取上面代码下面注释
        if(!TextUtils.isEmpty(list.get(position).getAmount())
                &&!list.get(position).getAmount().equals("0")
                &&!list.get(position).getAmount().equals("0.0")
                &&!list.get(position).getAmount().equals("0.00")){
            //总额度-剩余除以总的
            String amount=list.get(position).getAmount().contains(",")==true?list.get(position).getAmount().replace(",",""):list.get(position).getAmount();
            String remamount=list.get(position).getRemamount().contains(",")==true?list.get(position).getRemamount().replace(",",""):list.get(position).getRemamount();
            String subtract=BigDecimalUtil.subtract(amount,remamount);
            String bfb=BigDecimalUtil.divideNum(subtract,amount);
            String tet=BigDecimalUtil.multiply(bfb,"100");
            holder.tv_pb.setText(tet+"%");
            holder.pb.setProgress(Integer.parseInt(tet));
        }else {
            holder.tv_pb.setText(0+"%");
            holder.pb.setProgress(0);
        }

        String minBaseRateInteger = list.get(position).getMinBaseRateInteger();
        String minBaseRateDecimal = list.get(position).getMinBaseRateDecimal();
        String minAddedRateInteger = list.get(position).getMinAddedRateInteger();
        String minAddedRateDecimal = list.get(position).getMinAddedRateDecimal();
        String maxBaseRateInteger = list.get(position).getMaxBaseRateInteger();
        String maxBaseRateDecimal = list.get(position).getMaxBaseRateDecimal();
        String maxAddedRateInteger = list.get(position).getMaxAddedRateInteger();
        String maxAddedRateDecimal = list.get(position).getMaxAddedRateDecimal();
        //判断要修改字体大小的起始位置和结束位置
        SpannableStringBuilder spannableString = new SpannableStringBuilder();
        String minBeforePart="",minAfterPart="",maxBeforePart="",maxAfterPart="",minPart="",maxPart="";//低利率部分,高利率部分

        minBeforePart = getPersent(minBaseRateInteger + getValue(minBaseRateDecimal) );
        minAfterPart = getPersent(minAddedRateInteger + getValue(minAddedRateDecimal));
        maxBeforePart = getPersent(maxBaseRateInteger + getValue(maxBaseRateDecimal) );
        maxAfterPart = getPersent(maxAddedRateInteger + getValue(maxAddedRateDecimal)) ;
        if (!TextUtils.isEmpty(minAfterPart)){
            minPart = minBeforePart + "+" +minAfterPart;
        }else {
            minPart = minBeforePart;
        }
        if (!TextUtils.isEmpty(maxAfterPart)){
            maxPart = maxBeforePart+"+"+maxAfterPart;
            minPart = minPart+"~";
        }else {
            maxPart = maxBeforePart;
            if (!TextUtils.isEmpty(maxPart))
                minPart = minPart+"~";
        }
        spannableString.append(minPart);
        spannableString.append(maxPart);
        AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan(32);
        if (!TextUtils.isEmpty(minPart))
            spannableString.setSpan(absoluteSizeSpan, minBaseRateInteger.length(), minPart.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        AbsoluteSizeSpan absoluteSizeSpan2 = new AbsoluteSizeSpan(32);
        if (!TextUtils.isEmpty(maxPart))
            spannableString.setSpan(absoluteSizeSpan2, minPart.length()+maxBaseRateInteger.length(), spannableString.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        holder.tv_lv.setText(spannableString);
//        holder.tv_lv.setText(list.get(position).getInterest());
//        holder.tv_days.setText(list.get(position).getDays());
//        holder.tv_rg.setText(list.get(position).getStatusName());
        holder.tv_days.setText(RoncheUtil.getRealTerm(list.get(position).getTerm()));
        holder.tv_dw.setText(list.get(position).getTermTypeName());


        if(list.get(position).getStatus().equals("1")){//销售中
            holder.tv_rg.setBackgroundResource(R.drawable.jjbutton);
            holder.tv_rg.setTextColor(context.getResources().getColor(R.color.white));
            holder.tv_rg.setText("分享");
            holder.tv_rg.setVisibility(View.VISIBLE);
            holder.tv_xsz.setVisibility(View.VISIBLE);
            holder.tv_person.setText("剩余"+list.get(position).getRemcount()+"人");
            holder.tv_synum.setText("剩余"+list.get(position).getRemamount()+"万元");
            holder.tv_zt.setVisibility(View.VISIBLE);
            holder.tv_sjtime.setVisibility(View.GONE);
            holder.tv_rg.setEnabled(true);

        }else if(list.get(position).getStatus().equals("2")){
//            if(list.get(position).getStatus().equals("2")){//售罄
//            holder.tv_rg.setBackgroundResource(R.drawable.nomobutton);
//            holder.tv_rg.setText("已售罄");
            holder.tv_rg.setVisibility(View.GONE);
            holder.tv_xsz.setVisibility(View.GONE);
//            holder.tv_person.setText("预约"+ BigDecimalUtil.subtract(list.get(position).getAllcount(),list.get(position).getRemcount())+"人");
//            holder.tv_synum.setText("预约"+BigDecimalUtil.subtract(list.get(position).getAmount(),list.get(position).getRemamount())+"万");//剩余额度

//            holder.tv_person.setText("预约"+ list.get(position).getHascount()+"人");
//            holder.tv_synum.setText("预约"+list.get(position).getHascount()+"万");//剩余额度
            holder.tv_person.setText("剩余"+list.get(position).getRemcount()+"人");
            holder.tv_synum.setText("剩余"+list.get(position).getRemamount()+"万元");

            holder.tv_zt.setVisibility(View.GONE);
            holder.tv_sjtime.setVisibility(View.GONE);
            holder.tv_rg.setEnabled(true);
        }else if(list.get(position).getStatus().equals("0")){//待上架
            holder.tv_rg.setBackgroundResource(R.drawable.jjbutton);
            holder.tv_rg.setText("即将上架");
            holder.tv_rg.setTextColor(context.getResources().getColor(R.color.white));
            holder.tv_rg.setVisibility(View.VISIBLE);
            holder.tv_xsz.setVisibility(View.GONE);
            holder.tv_person.setText("剩余"+list.get(position).getRemcount()+"人");
            holder.tv_synum.setText("剩余"+list.get(position).getRemamount()+"万元");
            holder.tv_zt.setVisibility(View.GONE);
            holder.tv_sjtime.setText("上架时间："+list.get(position).getAheadBegindate());
            holder.tv_sjtime.setVisibility(View.VISIBLE);
            holder.tv_rg.setEnabled(false);
        }

        if(!TextUtils.isEmpty(list.get(position).getBenefitModeName())) {
            holder.tv_syname.setText(list.get(position).getBenefitModeName());//收益名称
        }else {
            holder.tv_syname.setText("--");//收益名称

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }




    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView tv_proname,tv_lv,tv_days,tv_rg,tv_person,tv_synum,tv_syname,tv_dw,tv_sjtime;
        private LinearLayout ll_item;
        private TextView tv_zt,tv_xsz,tv_pb;
        private ProgressBar pb;

        public MyHolder(View itemView) {
            super(itemView);
            ll_item=itemView.findViewById(R.id.ll_item);
            tv_proname=itemView.findViewById(R.id.tv_proname);
            tv_lv=itemView.findViewById(R.id.tv_lv);
            tv_days=itemView.findViewById(R.id.tv_days);
            tv_rg=itemView.findViewById(R.id.tv_rg);
            tv_dw=itemView.findViewById(R.id.tv_dw);
            tv_person=itemView.findViewById(R.id.tv_person);
            tv_synum=itemView.findViewById(R.id.tv_synum);
            tv_syname=itemView.findViewById(R.id.tv_syname);
            ll_item=itemView.findViewById(R.id.ll_item);
            tv_zt=itemView.findViewById(R.id.tv_zt);
            tv_sjtime=itemView.findViewById(R.id.tv_sjtime);
            tv_xsz=itemView.findViewById(R.id.tv_xsz);
            tv_pb=itemView.findViewById(R.id.tv_pb);
            pb=itemView.findViewById(R.id.pb);
        }

        public void bind(final int position) {
            ll_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                 context.startActivity(new Intent(context,CpInfoAct.class).putExtra("proId",list.get(position).getProId()));
                }
            });


            tv_rg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final ShareDialog dialog=new ShareDialog(context).build();
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.setName(list.get(position).getProductName());
                    dialog.setlv("年化"+list.get(position).getInterest()+"%");
                    dialog.settime(RoncheUtil.getRealTerm(list.get(position).getTerm())+list.get(position).getTermTypeName()+"期");
                    dialog.setdec(list.get(position).getBenefitModeName());
                    dialog.show();
                    dialog.setTodo(new ShareDialog.Goshare() {
                        @Override
                        public void share() {
                         listion.goshare(list.get(position));//通知界面开始分享
                         dialog.dissmiss();
                        }
                    });
                }
            });
        }
    }


    public void notifyDate(List<ProductBean>list) {
        this.list=list;
        notifyDataSetChanged();
    }


    //接口
    private ShareListion listion;

    public void setListion(ShareListion listion) {
        this.listion = listion;
    }

    public interface ShareListion{
        void goshare(ProductBean bean);
    }

    //判断是否加小数点
    private String getValue(String value){
        String str="";
        if (!TextUtils.isEmpty(value)){
            str = "."+value;
        }else {
            str=value;
        }
        return str;
    }
    //判断是否加百分号
    private String getPersent(String value){
        String str="";
        if (!TextUtils.isEmpty(value)){
            str = value+"%";
        }else {
            str=value;
        }
        return str;
    }
}
