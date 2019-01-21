package com.rmwl.rcchgwd;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;


import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.rmwl.rcchgwd.Utils.MyLogUtils;
import com.rmwl.rcchgwd.base.BaseActivity;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by Administrator on 2018/8/31.
 */

public class PdfAct extends BaseActivity implements OnPageChangeListener, OnLoadCompleteListener,
        OnPageErrorListener {
    private String url;
    private PDFView pdfView;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_pdf);
    }

    @Override
    public void init(Bundle savedInstanceState) {
//        setTopTitle("产品协议");
        pdfView = findViewById(R.id.pdfView);
        url=getIntent().getExtras().getString("url","");
        MyLogUtils.error("===="+url);
        if (!TextUtils.isEmpty(url)) {
            pdfView.setBackgroundColor(Color.LTGRAY);
            new RetrivePDFStream().execute(url);
//            String [] temp = null;
//            temp = url.split("/");
//            displayFromFile1(url, temp[temp.length-1]);
        }
    }

    @Override
    public void loadComplete(int nbPages) {

    }

    @Override
    public void onPageChanged(int page, int pageCount) {

    }

    @Override
    public void onPageError(int page, Throwable t) {

    }

//    private void displayFromFile1( String fileUrl ,String fileName) {
//        PDFView pdfView =  findViewById(R.id.pdfView);
//        pdfView.setVisibility(View.VISIBLE);
//        pdfView.fileFromLocalStorage(this,this,this,fileUrl,fileName); //设置pdf文件地址
//    }

    class RetrivePDFStream extends AsyncTask<String, Void, InputStream> {
        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;
            try {
                URL uri = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) uri.openConnection();
                if (urlConnection.getResponseCode() == 200) {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }
            } catch (IOException e) {
                return null;
            }
            return inputStream;
        }
        @Override
        protected void onPostExecute(InputStream inputStream) {
            pdfView.fromStream(inputStream).load();
        }
    }
}
