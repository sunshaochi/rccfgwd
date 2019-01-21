package com.rmwl.rcchgwd.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.rmwl.rcchgwd.R;


/**
 * Created by Administrator on 2018/7/19.
 */

public class UpdateDialog {
    private Context context;
    private Dialog dialog;
    private Display display;
    String path,isbx,dec,name;
    ImageView iv_deleat,iv_update;
    TextView tv_name,tv_dec1;

    public UpdateDialog(Context context, String name, String isbx, String dec) {
        this.context = context;
        this.name = name;
        this.isbx=isbx;
        this.dec=dec;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }


    public UpdateDialog builder(){
        View view = LayoutInflater.from(context).inflate(R.layout.updatedialog, null);

        iv_deleat= (ImageView)view.findViewById(R.id.iv_deleat);//不更新
        iv_update= (ImageView) view.findViewById(R.id.iv_update);//跟新
        tv_dec1= (TextView)view.findViewById(R.id.tv_dec);//描述
        tv_name= (TextView)view.findViewById(R.id.tv_code);//版本
        iv_deleat.setVisibility(View.INVISIBLE);
        if(isbx.equals("1")){ //强制跟新
           iv_deleat.setVisibility(View.INVISIBLE);
           iv_deleat.setEnabled(false);
        }else {
            iv_deleat.setVisibility(View.VISIBLE);
            iv_deleat.setEnabled(true);
            iv_deleat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        }

        if(!dec.contains("\\n")) {
            tv_dec1.setText(dec);
        }else {
            tv_dec1.setText(dec.replace("\\n","\n"));
        }
        tv_name.setText("v"+name);

        // 定义Dialog布局和参数
        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager manager=dialogWindow.getWindowManager();
        Display d=manager.getDefaultDisplay();
        lp.width=d.getWidth();
//        lp.x = 0;
//        lp.y = 0;
        dialogWindow.setAttributes(lp);
        return this;
    }


    public UpdateDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public UpdateDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public void show() {
        dialog.show();
    }


    public boolean isShowing(){
        return dialog.isShowing();
    }

    public void DissMiss(){
        dialog.dismiss();
    }




    /**
     * 设置不更新
     * @param listener
     */
    public void setDeleatClick(final UpdateDialog.OnClickListener listener)
    {
            iv_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null)
                        listener.onClick();
                }
            });
        }


    public interface OnClickListener{
        public void onClick();
    }

    }






