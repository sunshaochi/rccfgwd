package com.rmwl.rcchgwd.okhttp;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/8/22.
 */

public abstract class OKDownLoadCallback implements Callback {

    Activity activity;
    Call call;
    String path;
    Handler handler=new Handler(Looper.getMainLooper());

    public OKDownLoadCallback(Activity activity) {
        this.activity = activity;
    }

    public void setFilePath(String path)
    {
        this.path=path;
    }


    @Override
    public void onFailure(Call call, IOException e) {
        this.call=call;
        final String errorStr;
        if(isNetConn())
            errorStr="网络不可用！";
        else
            errorStr="请检查网络！";
        handler.post(new Runnable() {
            @Override
            public void run() {
                onError(0,errorStr);
            }});


    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        this.call=call;
        int len=0;
        int total=0;
        if(call.isCanceled()) return ;
        if(response.isSuccessful()){
            InputStream inputStream = response.body().byteStream();
            OutputStream outputStream= null;
            try {
                outputStream = new FileOutputStream(path);
                total=inputStream.available();
                byte[] buffer=new byte[1024];
                while((len=inputStream.read(buffer))!=-1)
                {
                    outputStream.write(buffer,0,len);
                    publishProgress(total,len);
                }
                outputStream.flush();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onSuccess(path);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onError(0,"下载失败！");
                    }
                });
            }finally {
                if(inputStream!=null)
                {
                    try {
                        inputStream.close();
                        inputStream=null;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(outputStream!=null)
                {
                    try {
                        outputStream.close();
                        outputStream=null;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        else {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    onError(0, "下载失败！");
                }
            });
        }

    }

    private boolean isNetConn()
    {
        ConnectivityManager manager= (ConnectivityManager) activity.getSystemService(activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=manager.getActiveNetworkInfo();
        if(networkInfo==null||!networkInfo.isAvailable())
            return true;
        else
            return false;
    }

    private void publishProgress(final long total , final long current){
        handler.post(new Runnable() {
            public void run() {
                int progress = (int) (current / (float) total * 100);
                onProgress(progress);
            }
        });
    }


    public abstract void onSuccess(String data);
    public abstract void onError(int code,String error);
    public abstract void onProgress(int current);
}
