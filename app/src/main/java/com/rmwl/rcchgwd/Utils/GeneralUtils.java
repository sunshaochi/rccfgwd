package com.rmwl.rcchgwd.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.util.TypedValue;
import android.widget.Toast;


import com.rmwl.rcchgwd.bean.VersionBean;
import com.rmwl.rcchgwd.okhttp.HttpUrl;
import com.rmwl.rcchgwd.okhttp.OkHttpCallBack;
import com.rmwl.rcchgwd.okhttp.OkHttpUtils;
import com.rmwl.rcchgwd.okhttp.RequestParam;
import com.rmwl.rcchgwd.view.UpdateDialog;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.CookieSyncManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Util
 *
 * @author wangbin
 */
public class GeneralUtils {

    private static GeneralUtils generalUtils;

    public static GeneralUtils getInstance() {
        if (generalUtils == null) {
            generalUtils = new GeneralUtils();
        }
        return generalUtils;
    }

    // 要判断是否包含特殊字符的目标字符串
    public static boolean compileExChar(Context context, String str) {
        String limitEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern pattern = Pattern.compile(limitEx);
        Matcher m = pattern.matcher(str);
        if (m.find()) {
//            MyToastUtils.showShortToast(context, "不允许输入特殊符号!");
			MyToastUtils.showShortToast(context,"不允许输入特殊符号!");
            return false;
        }
        return true;
    }
    // 要判断密码是否符合规则--由数字和26个英文字母组成的字符串
    public static boolean isTruePaw(Context context, String str) {
        String limitEx = "^[A-Za-z0-9]+$";
        Pattern pattern = Pattern.compile(limitEx);
        Matcher m = pattern.matcher(str);
        if (!m.find()) {
			MyToastUtils.showShortToast(context, "只能是数字或英文字符！");
            return false;
        }
        return true;
    }
    /**
     * 检查版本更新
     */
	public void toVersion(final Context context){
		RequestParam param = new RequestParam();
		param.add("source","3");
		param.add("versionType","1");//android
		OkHttpUtils.post(HttpUrl.APPVERSION,param,new OkHttpCallBack(context) {

			@Override
			public void onSuccess(String data) {
				try {
					JSONObject jsonObject=new JSONObject(data);
					JSONObject result=jsonObject.getJSONObject("result");
					VersionBean bean=GsonUtils.gsonToBean(result.toString(),VersionBean.class);
					if(Integer.parseInt(bean.getVersionCode()+"")> Integer.parseInt(RoncheUtil.getVersionCode(context))){
//						showIsDownLoad(context,HttpUrl.APK_URL,bean.getIsMandatory(),bean.getVersionDesc(),bean.getVersion());
						showIsDownLoad(context,bean.getDownloadUrl(),bean.getIsMandatory(),bean.getVersionDesc(),bean.getVersion());

					}else {
//						MyToastUtils.showShortToast(context,"当前已经是最新版本");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}

			@Override
			public void onError(int code, String error) {

			}
		});
	}
	/*
  * 从服务器中下载APK
  */
	protected void downLoadApk(final Context context, final String url) {
		final ProgressDialog pd;    //进度条对话框
		pd = new ProgressDialog(context);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setMax(100);
		pd.setProgress(0);
		pd.setMessage("正在下载更新");
		pd.show();
		new Thread(){
			@Override
			public void run() {
				try {
					File file = getFileFromServer(url, pd);
					sleep(500);
					installApk(context,file);
					pd.dismiss(); //结束掉进度条对话框
				} catch (Exception e) {
					Message msg = new Message();
					msg.what = DOWN_ERROR;
					pd.dismiss();
					msg.obj=context;
					handler.sendMessage(msg);
					e.printStackTrace();
				}
			}}.start();
	}

	private final int DOWN_ERROR=1;
	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {

				case DOWN_ERROR:
					Context context= (Context) msg.obj;
					//下载apk失败
					Toast.makeText(context, "下载新版本失败", Toast.LENGTH_SHORT).show();
					break;
			}
		}
	};

	//安装apk
	protected void installApk(Context context, File file) {
		if(!file.exists()){
			return;
		}
		Intent intent = new Intent();
		Uri uri ;
		//执行动作
		intent.setAction(Intent.ACTION_VIEW);
		//安装完成后可选择打开或者完成

		//执行的数据类型
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			uri = Uri.fromFile(file);
		}else {
			intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_ACTIVITY_NEW_TASK);
			uri = FileProvider.getUriForFile(context.getApplicationContext(), context.getApplicationContext().getPackageName() + ".provider", file);
		}
		intent.setDataAndType(uri, "application/vnd.android.package-archive");//编者按：此处Android应为android，否则造成安装不了

		context.startActivity(intent);
	}

	private UpdateDialog dialog;

	/**
	 * 是否下载
	 */
	private void showIsDownLoad(final Context context, final String path, String ismandatory, String dec, String name) {
		if(dialog!=null){
			if(dialog.isShowing()){
				dialog.DissMiss();
			}
			dialog=null;
		}
		dialog = new UpdateDialog(context,name,ismandatory,dec).builder();
		dialog.setDeleatClick(new UpdateDialog.OnClickListener() {
			@Override
			public void onClick() {
				downLoadApk(context,path);
			}
		});
		dialog.show();
	}
	public static File getFileFromServer(String path, ProgressDialog pd) throws Exception {
		//如果相等的话表示当前的sdcard挂载在手机上并且是可用的
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			//获取到文件的大小
			pd.setMax(100);
			InputStream is = conn.getInputStream();
			File file = new File(Environment.getExternalStorageDirectory(), "rcchgkd.apk");
			FileOutputStream fos = new FileOutputStream(file);
			BufferedInputStream bis = new BufferedInputStream(is);
			byte[] buffer = new byte[1024];
			int len;
			long total = 0;
			while ((len = bis.read(buffer)) != -1) {
				total += len;
				int prop = (int) (total * 100 / conn.getContentLength());
				//获取当前下载量
				pd.setProgress(prop);
				fos.write(buffer, 0, len);
			}
			fos.close();
			bis.close();
			is.close();
			return file;
		} else {
			return null;
		}
	}


	public static int dip2px(Context context, float dipValue){
		Resources r = context.getResources();
		return (int) TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP, dipValue, r.getDisplayMetrics());
	}

	public static void ClearWebCookier(Context context){
		//清空所有Cookie
		CookieSyncManager.createInstance(context);  //Create a singleton CookieSyncManager within a context
		CookieManager cookieManager = CookieManager.getInstance(); // the singleton CookieManager instance
		cookieManager.removeAllCookie();// Removes all cookies.
		CookieSyncManager.getInstance().sync(); // forces sync manager to sync now


	}
}
