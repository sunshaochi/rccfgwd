package com.rmwl.rcchgwd.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.event.OnClick;
import com.rmwl.rcchgwd.R;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

/**
 * Created by Administrator on 2018/8/27.
 */

public class ShareDialog implements View.OnClickListener{
    private Context context;
    private Dialog dialog;
    private Display display;

    TextView tv_name;
    TextView tv_lv;
    TextView tv_time,tv_dec;

    public ShareDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public ShareDialog build(){
        View view = LayoutInflater.from(context).inflate(R.layout.share_dialog, null);
        tv_name=view.findViewById(R.id.tv_name);
        tv_lv=view.findViewById(R.id.tv_lh);
        tv_time=view.findViewById(R.id.tv_time);
        tv_dec=view.findViewById(R.id.tv_dec);
        LinearLayout ll_share=view.findViewById(R.id.ll_share);
        TextView cancle=view.findViewById(R.id.tv_cancle);
        ll_share.setOnClickListener(this);
        cancle.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
     switch (v.getId()){
         case R.id.tv_cancle:
             dialog.dismiss();
             break;

         case R.id.ll_share:
             goshare();
             break;
     }
    }



    private void goshare() {
        todo.share();
    }


    public ShareDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public ShareDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }
    public void dissmiss() {
        dialog.dismiss();
    }

    public ShareDialog setName(String name) {
       tv_name.setText(name);
        return this;
    }

    public ShareDialog setlv(String lv) {
        tv_lv.setText(lv);
        return this;
    }

    public ShareDialog settime(String time) {
        tv_time.setText(time);
        return this;
    }

    public ShareDialog setdec(String dec) {
        tv_dec.setText(dec);
        return this;
    }

    public void show() {
        dialog.show();
    }

    //接口
    private Goshare todo;
    public void setTodo(Goshare todo) {
        this.todo = todo;
    }

    public interface Goshare{
        void share();
    }

}
