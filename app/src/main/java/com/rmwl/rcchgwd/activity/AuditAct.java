package com.rmwl.rcchgwd.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.rmwl.rcchgwd.R;
import com.rmwl.rcchgwd.Utils.CheckUtil;
import com.rmwl.rcchgwd.Utils.EncryptionTools;
import com.rmwl.rcchgwd.Utils.MyBitmapUtils;
import com.rmwl.rcchgwd.Utils.MyToastUtils;
import com.rmwl.rcchgwd.base.BaseActivity;
import com.rmwl.rcchgwd.bean.RegistBean;
import com.rmwl.rcchgwd.okhttp.HttpUrl;
import com.rmwl.rcchgwd.okhttp.OkHttpCallBack;
import com.rmwl.rcchgwd.okhttp.OkHttpUtils;
import com.rmwl.rcchgwd.okhttp.RequestParam;
import com.rmwl.rcchgwd.view.MyAlertDialog;
import com.rmwl.rcchgwd.view.MySelfSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2018/11/23.
 */

public class AuditAct extends BaseActivity {
    @ViewInject(R.id.tv_namet)
    private TextView tv_namet;
    @ViewInject(R.id.tv_zjlxt)
    private TextView tv_zjlxt;
    @ViewInject(R.id.tv_zjnot)
    private TextView tv_zjnot;
    @ViewInject(R.id.tv_phonet)
    private TextView tv_phonet;
    @ViewInject(R.id.tv_ysm)
    private TextView tv_ysm;
    @ViewInject(R.id.tv_btyzm)
    private TextView tv_btyzm;
    @ViewInject(R.id.tv_sf)
    private TextView tv_sf;
    @ViewInject(R.id.iv_photo)
    private ImageView iv_photo;
    @ViewInject(R.id.iv_jt)
    private ImageView iv_jt;
    @ViewInject(R.id.rl_select)
    private RelativeLayout rl_select;
    @ViewInject(R.id.et_yzm)
    private EditText et_yzm;
    @ViewInject(R.id.ck_box)
    private CheckBox ck_box;



    private String notes;
    private String id;
    private Gson gson;
    private RegistBean bean;
    private PopupWindow popupWindow;
    private String atype,idfileUrl,captcha;
    boolean ischeck=true;

    final int REQUESTWRITEPERIMISSINCODE=102;//相机动态请求写入权限
    final int REQUESTPHOTOPERIMISSINCODE=103;//相册动态请求写入权限
    final int CAMERACODE=0;//相机
    final int PHOTOCODE=1;//相册

    private String photoSavePath;
    private String photoSaveName;
    Uri imageUri = null;

    @Override
    public void setLayout() {
        setContentView(R.layout.act_audit);
    }

    @Override
    public void init(Bundle savedInstanceState) {

        photoSavePath = Environment.getExternalStorageDirectory().getPath()+ "/rcchgetadvisor/cache/";
        File tempFile = new File(photoSavePath);
        if (!tempFile.exists()) {
            tempFile.mkdirs();
        }

        ck_box.setChecked(true);
        ck_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    ischeck=true;
                }else {
                    ischeck=false;
                }
            }
        });

        gson=new Gson();
        notes=getIntent().getExtras().getString("notes","认证失败");
        id=getIntent().getExtras().getString("id","");
        showDia(notes);
        getUserRegist();
    }

    private void getUserRegist() {
        RequestParam param=new RequestParam();
        param.add("advisorId",id);
        OkHttpUtils.post(HttpUrl.GETADVISORINFO_URL, param, new OkHttpCallBack(AuditAct.this) {
            @Override
            public void onSuccess(String data) {
                try {
                    JSONObject object=new JSONObject(data.toString());
                    JSONObject result=object.getJSONObject("result");
                    bean=gson.fromJson(result.toString(),RegistBean.class);
                    appColorDraw();//设置ui界面
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int code, String error) {
                MyToastUtils.showShortToast(AuditAct.this,error);
            }
        });
    }

    /**
     * 渲染ui
     */
    private void appColorDraw() {
        if(bean!=null){
            tv_namet.setText(bean.getRealname()+"");

            if(bean.getIdtype().equals("1")){
                tv_zjlxt.setText("身份证");
            }else {
                tv_zjlxt.setText("护照");
            }

            tv_zjnot.setText(bean.getIdno()+"");

            if(bean.getIfRealname().equals("0")){
                tv_ysm.setText("未实名");
            }else {
                tv_ysm.setText("已实名");
            }

            tv_phonet.setText(bean.getMobile());

            if(bean.getAtype().equals("1")){
                atype="1";
                tv_sf .setText("普通员工");
            }else {
                atype="2";
                tv_sf.setText("理财经理");
            }

            if(!TextUtils.isEmpty(bean.getIdfileUrl())){
                Glide.with(AuditAct.this).load(bean.getIdfileUrl()).placeholder(R.mipmap.phone).into(iv_photo);
                idfileUrl=bean.getIdfileUrl();
            }

        }

    }

    @OnClick({R.id.tv_btyzm,R.id.rl_select,R.id.iv_photo,R.id.tv_regist,R.id.tv_xy})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.tv_btyzm:
                getCaptchaNum();//获取验证码发送次数
                break;
            case R.id.rl_select:
                showPopu();
                break;
            case R.id.iv_photo:
                addPhoto();
                break;
            case R.id.tv_regist:
                if(isVisible()){
                    goSubmit();
                }
                break;

            case R.id.tv_xy:
                startActivity(new Intent(AuditAct.this,WebAct.class).putExtra("type","6"));
                break;
        }
    }

    private boolean isVisible() {
        captcha=et_yzm.getText().toString().trim();
        if(TextUtils.isEmpty(captcha)){
            et_yzm.requestFocus();
            MyToastUtils.showShortToast(AuditAct.this,"请输入验证码");
            return false;
        }
        if(TextUtils.isEmpty(atype)){
            MyToastUtils.showShortToast(AuditAct.this,"请选择身份");
            return false;
        }
        if(TextUtils.isEmpty(idfileUrl)){
            MyToastUtils.showShortToast(AuditAct.this,"请上传图片");
            return false;
        }
        if(!ischeck){
            MyToastUtils.showShortToast(AuditAct.this,"注册提交需要勾选用户协议");
            return false;
        }
        return true;
    }

    private void goSubmit() {
        RequestParam param=new RequestParam();
        param.add("advisorId",id);
        param.add("captcha",captcha);
        param.add("atype",atype);
        param.add("idfileUrl",idfileUrl);
        OkHttpUtils.post(HttpUrl.UPDATEADVISOR_URL, param, new OkHttpCallBack(AuditAct.this) {
            @Override
            public void onSuccess(String data) {
              MyToastUtils.showShortToast(AuditAct.this,"提交成功");
              finish();
            }

            @Override
            public void onError(int code, String error) {
             MyToastUtils.showShortToast(AuditAct.this,error);
            }
        });
    }


    private void getCaptchaNum() {
            RequestParam param = new RequestParam();
            param.add("mobile",bean.getMobile());
            OkHttpUtils.post(HttpUrl.CAPTCHANUM_URL, param, new OkHttpCallBack(AuditAct.this) {
                @Override
                public void onSuccess(String data) {
                    try {
                        JSONObject object=new JSONObject(data.toString());
                        int num=object.getInt("result");
                        if(num>=3){
                            MyToastUtils.showShortToast(AuditAct.this,"今日次数用完");
                        }else {
                            sendYzm();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onError(int code, String error) {
                    MyToastUtils.showShortToast(AuditAct.this,error);
                }
            });

    }

    private void showPopu() {
        iv_jt.setImageResource(R.mipmap.comxx);
        LinearLayout layout = (LinearLayout) LayoutInflater.from(AuditAct.this).inflate(
                R.layout.popuwindowssf, null);
        final TextView tv_sfz = layout.findViewById(R.id.tv_sfz);
        final TextView tv_hz = layout.findViewById(R.id.tv_hz);
        tv_sfz.setTextColor(getResources().getColor(R.color.black_2));
        tv_hz.setTextColor(getResources().getColor(R.color.black_2));
        tv_sfz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_sfz.setTextColor(getResources().getColor(R.color.black_1));
                atype = "1";//普通员工
                tv_sf.setText("普通员工");
                popupWindow.dismiss();
            }
        });

        tv_hz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_hz.setTextColor(getResources().getColor(R.color.black_1));
                atype = "2";//理财经理
                tv_sf.setText("理财经理");
                popupWindow.dismiss();
            }
        });

        // 实例化popupWindow
        popupWindow = new PopupWindow(layout, AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        //控制键盘是否可以获得焦点
        popupWindow.setFocusable(true);
        //设置popupWindow弹出窗体的背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable(null, ""));
        popupWindow.showAsDropDown(rl_select);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                iv_jt.setImageResource(R.mipmap.myfraright);
            }
        });


    }


    private void showDia(String tex) {
        final MyAlertDialog dialog=new MyAlertDialog(AuditAct.this).builder();
            dialog.setTitle("审核失败,请重新提交审核");
            if(!TextUtils.isEmpty(tex)) {
                dialog.setMsg(tex);
            }else {
                dialog.setMsg("信息不匹配");
            }

        dialog.setPositiveButton("确认", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        dialog.show();
    }

    private void sendYzm() {
        RequestParam param=new RequestParam();
        param.add("signature", EncryptionTools.SIGNATURE);
        param.add("source","3");
        param.add("mobile",bean.getMobile());
        param.add("type","1");
        OkHttpUtils.post(HttpUrl.SENDMSG_URL, param, new OkHttpCallBack(AuditAct.this) {
            @Override
            public void onSuccess(String data) {
                MyToastUtils.showShortToast(AuditAct.this,"验证码发送成功");
                i = 60;
                tv_btyzm.setEnabled(false);
                timer = new Timer();
                myTask = new MyTimerTask();
                timer.schedule(myTask, 0, 1000);
            }

            @Override
            public void onError(int code, String error) {
                MyToastUtils.showShortToast(AuditAct.this,error);
            }
        });
    }


    private void addPhoto() {
        MySelfSheetDialog dialog = new MySelfSheetDialog(AuditAct.this);
        dialog.builder().addSheetItem("拍照", MySelfSheetDialog.SheetItemColor.Blue, new MySelfSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
                    int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    int hasReadContactsPermission = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
                    int hasCameraContactsPermission = checkSelfPermission(Manifest.permission.CAMERA);
                    if(hasWriteContactsPermission!= PackageManager.PERMISSION_GRANTED||hasReadContactsPermission!=PackageManager.PERMISSION_GRANTED||hasCameraContactsPermission!=PackageManager.PERMISSION_GRANTED)
                    {
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA},REQUESTWRITEPERIMISSINCODE);
                        return;
                    }else {
                        camera();//拍照相册
                    }
                }else {
                    camera();
                }
//                jump2Camera();
            }
        }).addSheetItem("从相册选取", MySelfSheetDialog.SheetItemColor.Blue, new MySelfSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
                    int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    int hasReadContactsPermission = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
                    if(hasWriteContactsPermission!= PackageManager.PERMISSION_GRANTED||hasReadContactsPermission!=PackageManager.PERMISSION_GRANTED)
                    {//如果没有授权
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},REQUESTPHOTOPERIMISSINCODE);
                        return;
                    }else {
                        showPhoto();
                    }
                }else {
                    showPhoto();
                }
//                startActionFile();

            }
        }).show();

    }

    /**
     * 调取相机
     */
    private void camera() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            photoSaveName = String.valueOf(System.currentTimeMillis()) + ".png";
            Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            imageUri = Uri.fromFile(new File(photoSavePath, photoSaveName));
            imageUri = FileProvider.getUriForFile(AuditAct.this, AuditAct.this.getApplicationContext().getPackageName() + ".provider", new File(photoSavePath, photoSaveName));
            openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(openCameraIntent, CAMERACODE);
        } else {
            MyToastUtils.showLongToast(AuditAct.this,"存储卡不存在！");
        }
    }

    /**
     * 调取相册
     */
    private void showPhoto() {
//        Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);//打开所有类容，oppo5.1手机图片会乱序换成下面相册
        Intent openAlbumIntent = new Intent(Intent.ACTION_PICK);
        openAlbumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(openAlbumIntent, PHOTOCODE);
    }

    private int i = 60;
    private Timer timer;
    private MyTimerTask myTask;
    /**
     * 倒计时
     *
     * @author wangbin
     *
     */
    class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            handler.sendEmptyMessage(i--);
        }

    }


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                tv_btyzm.setEnabled(true);
                tv_btyzm.setText("重新发送");
                timer.cancel();
                myTask.cancel();
            } else {
                tv_btyzm.setText("重新获取"+"("+msg.what + "秒"+")");
            }
        }

    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case PHOTOCODE://相册
                if (resultCode != RESULT_OK||data == null) {
                    idfileUrl="";
                    return;
                }
                try {
                    imageUri = data.getData();//路径
                    Log.i("路径",imageUri.getPath()+"==");//content://com.android.providers.media.documents/document/image%3A84763
                    saveAndupload(imageUri);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case CAMERACODE://相机
                try {
                    saveAndupload(imageUri);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;



        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void saveAndupload(Uri imageUri) {
        Bitmap bit = null;
        try {
            bit = MyBitmapUtils.getBitmapFormUri(AuditAct.this, imageUri);//比例压缩质量压缩
            File file = MyBitmapUtils.saveBitmap(bit, String.valueOf(System.currentTimeMillis())+"advisorinfo.png");//把mitmap转成file文件保存在本地

            iv_photo.setImageBitmap(bit);
            uploadPhoto(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void uploadPhoto(File file) {
        RequestParam param=new RequestParam();
        param.addFile("f1",file);
        OkHttpUtils.post(HttpUrl.ADDPHOTO_URL, param, new OkHttpCallBack(AuditAct.this) {
            @Override
            public void onSuccess(String data) {
                try {
                    JSONObject object=new JSONObject(data.toString());
                    JSONArray array=object.getJSONArray("result");
                    if(array!=null&&array.length()>0){
                        JSONObject key=array.getJSONObject(0);
                        idfileUrl=key.getString("imageUrlPath");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int code, String error) {
//                MyToastUtils.showShortToast(RegistAct.this,error);
                MyToastUtils.showShortToast(AuditAct.this,"图片上传失败,请重新上传");
                iv_photo.setImageResource(R.mipmap.phone);
                idfileUrl="";
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case REQUESTWRITEPERIMISSINCODE:
                if(!permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)||grantResults[0]!=PackageManager.PERMISSION_GRANTED)
                {
                    MyToastUtils.showLongToast(AuditAct.this,"请手动开启文件写入权限！");
                    return;
                }
                else if(!permissions[1].equals(Manifest.permission.READ_EXTERNAL_STORAGE)||grantResults[1]!=PackageManager.PERMISSION_GRANTED)
                {
                    MyToastUtils.showLongToast(AuditAct.this,"请手动开启文件读取权限！");
                    return;
                }
                else if(!permissions[2].equals(Manifest.permission.CAMERA)||grantResults[2]!=PackageManager.PERMISSION_GRANTED)
                {
                    MyToastUtils.showLongToast(AuditAct.this,"请手动开启相机权限！");

                    return;
                }
                camera();
                break;
            case REQUESTPHOTOPERIMISSINCODE:
                if(!permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)||grantResults[0]!=PackageManager.PERMISSION_GRANTED)
                {
                    MyToastUtils.showLongToast(AuditAct.this,"请手动开启文件访问权限！");

                    return;
                }
                else if(!permissions[1].equals(Manifest.permission.READ_EXTERNAL_STORAGE)||grantResults[1]!=PackageManager.PERMISSION_GRANTED)
                {
                    MyToastUtils.showLongToast(AuditAct.this,"请手动开启文件读取权限！");
                    return;
                }
                showPhoto();
                break;
        }
    }

}
