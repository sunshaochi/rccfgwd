package com.rmwl.rcchgwd.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
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
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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

import com.google.gson.Gson;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.rmwl.rcchgwd.R;
import com.rmwl.rcchgwd.Utils.ButtonUtils;
import com.rmwl.rcchgwd.Utils.CheckUtil;
import com.rmwl.rcchgwd.Utils.EncryptionTools;
import com.rmwl.rcchgwd.Utils.FileUtils;
import com.rmwl.rcchgwd.Utils.GsonUtils;
import com.rmwl.rcchgwd.Utils.MyBitmapUtils;
import com.rmwl.rcchgwd.Utils.MyToastUtils;
import com.rmwl.rcchgwd.Utils.PermisionUtils;
import com.rmwl.rcchgwd.Utils.SpUtils;
import com.rmwl.rcchgwd.base.BaseActivity;
import com.rmwl.rcchgwd.bean.UserBean;
import com.rmwl.rcchgwd.okhttp.HttpUrl;
import com.rmwl.rcchgwd.okhttp.OkHttpCallBack;
import com.rmwl.rcchgwd.okhttp.OkHttpUtils;
import com.rmwl.rcchgwd.okhttp.RequestParam;
import com.rmwl.rcchgwd.view.MySelfSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import static com.umeng.socialize.utils.DeviceConfig.context;


/**
 * Created by Administrator on 2018/8/25.
 */

public class RegistAct extends BaseActivity {
    @ViewInject(R.id.et_lxr)
    private EditText et_lxr;
    @ViewInject(R.id.et_phone)
    private EditText et_phone;
    @ViewInject(R.id.et_mm)
    private EditText et_mm;
    @ViewInject(R.id.et_yzm)
    private EditText et_yzm;
    @ViewInject(R.id.ck_box)
    private CheckBox ck_box;
    @ViewInject(R.id.iv_photo)
    private ImageView iv_photo;
    @ViewInject(R.id.tv_btyzm)
    private TextView tv_btyzm;
    @ViewInject(R.id.tv_sf)
    private TextView tv_sf;
    @ViewInject(R.id.iv_jt)
    private ImageView iv_jt;
    @ViewInject(R.id.rl_select)
    private RelativeLayout rl_select;
    @ViewInject(R.id.iv_showorno)
    private ImageView iv_showorno;
    @ViewInject(R.id.ll_viewline)
    private LinearLayout ll_viewline;
    @ViewInject(R.id.view_one)
    private View view_one;
    @ViewInject(R.id.view_two)
    private View view_two;
    @ViewInject(R.id.view_three)
    private View view_three;
    @ViewInject(R.id.ll_select)
    private LinearLayout ll_select;
    @ViewInject(R.id.iv_zw)
    private ImageView iv_zw;
    @ViewInject(R.id.iv_zwt)
    private ImageView iv_zwt;
    @ViewInject(R.id.iv_zwthre)
    private ImageView iv_zwthre;
    @ViewInject(R.id.tv_kong)
    private TextView tv_kong;
    @ViewInject(R.id.tv_chang)
    private TextView tv_chang;
    @ViewInject(R.id.tv_dxx)
    private TextView tv_dxx;
    @ViewInject(R.id.tv_intensity)
    private TextView tv_intensity;
    @ViewInject(R.id.et_yqm)
    private EditText et_yqm;


    private String realname,idfileUrl,atype,captcha,password,mobile,yqcode;
    boolean ischeck=true;

    final int REQUESTWRITEPERIMISSINCODE=102;//相机动态请求写入权限
    final int REQUESTPHOTOPERIMISSINCODE=103;//相册动态请求写入权限
    final int CAMERACODE=0;//相机
    final int PHOTOCODE=1;//相册

    private String photoSavePath;
    private String photoSaveName;
    Uri imageUri = null;
    private PopupWindow popupWindow;
    private boolean isshow=false;
    private Gson gson;
    private UserBean userBean;



    @Override
    public void setLayout() {
        setContentView(R.layout.act_regist);
    }

    @Override
    public void init(Bundle savedInstanceState) {

        photoSavePath = Environment.getExternalStorageDirectory().getPath() + "/Rcchregist/cache/";
        File tempFile = new File(photoSavePath);
        if (!tempFile.exists()) {
            tempFile.mkdirs();
        }
         gson=new Gson();
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

         et_mm.addTextChangedListener(new MyTextChangedListener());
         CheckUtil.showOrHide(false,et_mm);
    }

    @OnClick({R.id.tv_regist,R.id.rl_select,R.id.iv_photo,R.id.tv_btyzm,R.id.tv_xy,R.id.iv_showorno})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.tv_regist:
                if(isVisible()){
                  goRegist();
                }
                break;
            case R.id.rl_select:
                showPopu();
                break;

            case R.id.iv_photo:
                addPhoto();
                break;
            case R.id.tv_btyzm:
                if (!ButtonUtils.isFastDoubleClick(R.id.tv_btyzm))
                  getCaptchaNum();//获取验证码发送次数
                 break;
            case R.id.tv_xy:
                startActivity(new Intent(RegistAct.this,WebAct.class).putExtra("type","6"));
                break;

            case R.id.iv_showorno:
                if(!isshow){
                    isshow=true;
                    iv_showorno.setImageResource(R.mipmap.bigyanjing);
                    CheckUtil.showOrHide(true,et_mm);
                }else {
                    isshow=false;
                    iv_showorno.setImageResource(R.mipmap.yanjing);
                    CheckUtil.showOrHide(false,et_mm);
                }
                break;
        }
    }

    private void goRegist() {
        atype = "2";//理财经理
        RequestParam param=new RequestParam();
        param.add("source","3");
        param.add("mobile",mobile);
        param.add("password",password);
        param.add("captcha",captcha);
        param.add("atype",atype);
        param.add("idfileUrl",idfileUrl);
        param.add("realname",realname);
//        if(!TextUtils.isEmpty(yqcode)){
//            param.add("invitecode",yqcode);
//        }

        OkHttpUtils.post(HttpUrl.REGIST_URL, param, new OkHttpCallBack(RegistAct.this) {
            @Override
            public void onSuccess(String data) {
//               MyToastUtils.showShortToast(RegistAct.this,"注册成功");

                try {
                    JSONObject object = new JSONObject(data.toString());
                    JSONObject result = object.getJSONObject("result");
                    userBean = gson.fromJson(result.toString(), UserBean.class);
                    SpUtils.setUser(RegistAct.this, GsonUtils.bean2Json(userBean));
                    SpUtils.setToken(RegistAct.this, userBean.getToken());
                    SpUtils.setStatus(RegistAct.this, userBean.getStatus());

                    SpUtils.setPhone(RegistAct.this, mobile);
                    SpUtils.setPwd(RegistAct.this, password);
                    openActivity(AuthenticationAct.class);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(int code, String error) {
                MyToastUtils.showShortToast(RegistAct.this,error);

            }
        });

    }


    private boolean isVisible() {
        realname=et_lxr.getText().toString().trim();
        mobile=et_phone.getText().toString().trim();
        password=et_mm.getText().toString().trim();
        captcha=et_yzm.getText().toString().trim();
        yqcode=et_yqm.getText().toString().trim();
        if(TextUtils.isEmpty(realname)){
            et_lxr.requestFocus();
            MyToastUtils.showShortToast(RegistAct.this,"请输入真实姓名");
            return false;
        }

        if (!CheckUtil.isLegalName(realname)){
            et_lxr.requestFocus();
            MyToastUtils.showShortToast(RegistAct.this,"请输入真实姓名");
            return false;
        }
        if(TextUtils.isEmpty(mobile)){
            et_phone.requestFocus();
            MyToastUtils.showShortToast(RegistAct.this,"请输入手机号");
            return false;
        }

        if(!CheckUtil.isMobile(mobile)){
            et_phone.requestFocus();
            et_phone.setSelection(mobile.length());
            MyToastUtils.showShortToast(RegistAct.this,"请输入正确格式手机号码");
            return false;
        }

        if(TextUtils.isEmpty(password)){
            et_mm.requestFocus();
            MyToastUtils.showShortToast(RegistAct.this,"请输入新密码");
            return false;
        }
        if(TextUtils.isEmpty(captcha)){
            et_yzm.requestFocus();
            MyToastUtils.showShortToast(RegistAct.this,"请输入验证码");
            return false;
        }


        if(!CheckUtil.isPwd(password)){
            et_mm.requestFocus();
            et_mm.setSelection(password.length());
            MyToastUtils.showShortToast(RegistAct.this,"请输入6~20位数字和字母");
            return false;
        }

//        if(TextUtils.isEmpty(atype)){
//            MyToastUtils.showShortToast(RegistAct.this,"请选择身份");
//            return false;
//        }

        if(TextUtils.isEmpty(idfileUrl)){
            MyToastUtils.showShortToast(RegistAct.this,"请上传图片");
            return false;
        }

        if(!ischeck){
            MyToastUtils.showShortToast(RegistAct.this,"注册提交需要勾选用户协议");
            return false;
        }

        return true;

    }


    private void getCaptchaNum() {
        mobile=et_phone.getText().toString();
        if(TextUtils.isEmpty(mobile)||!CheckUtil.isMobile(mobile)){
            if(TextUtils.isEmpty(mobile)) {
                et_phone.requestFocus();
                MyToastUtils.showShortToast(RegistAct.this, "请输入手机号");
            }else {
                et_phone.requestFocus();
                MyToastUtils.showShortToast(RegistAct.this, "请输入正确格式手机号");
            }
        }else {
            RequestParam param = new RequestParam();
            param.add("mobile",mobile);
            OkHttpUtils.post(HttpUrl.CAPTCHANUM_URL, param, new OkHttpCallBack(RegistAct.this) {
                @Override
                public void onSuccess(String data) {
                    try {
                        JSONObject object=new JSONObject(data.toString());
                        int num=object.getInt("result");
                        if(num>=3){
                            MyToastUtils.showShortToast(RegistAct.this,"今日次数用完");
                        }else {
                            sendYzm();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onError(int code, String error) {
                    MyToastUtils.showShortToast(RegistAct.this,error);
                }
            });
        }
    }


    private void showPopu() {
        iv_jt.setImageResource(R.mipmap.comxx);
        LinearLayout layout = (LinearLayout) LayoutInflater.from(RegistAct.this).inflate(
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


    private void sendYzm() {
        RequestParam param=new RequestParam();
        param.add("signature", EncryptionTools.SIGNATURE);
        param.add("source","3");
        param.add("mobile",mobile);
        param.add("type","1");
        OkHttpUtils.post(HttpUrl.SENDMSG_URL, param, new OkHttpCallBack(RegistAct.this) {
            @Override
            public void onSuccess(String data) {
                MyToastUtils.showShortToast(RegistAct.this,"验证码发送成功");
                i = 60;
                tv_btyzm.setEnabled(false);
                timer = new Timer();
                myTask = new MyTimerTask();
                timer.schedule(myTask, 0, 1000);
            }

            @Override
            public void onError(int code, String error) {
                MyToastUtils.showShortToast(RegistAct.this,error);
            }
        });
    }


    private void addPhoto() {
        MySelfSheetDialog dialog = new MySelfSheetDialog(RegistAct.this);
        dialog.builder().addSheetItem("拍照", MySelfSheetDialog.SheetItemColor.Blue, new MySelfSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
                    if(!PermisionUtils.getPermissState(RegistAct.this,PermisionUtils.TYPETWO).isEmpty()){
                        String[] permissions = PermisionUtils.getPermissState(RegistAct.this,
                                PermisionUtils.TYPETWO).toArray(new String[PermisionUtils.getPermissState(RegistAct.this,PermisionUtils.TYPETWO).size()]);//将List转为数组
                        requestPermissions(permissions, PermisionUtils.REQUESTCAMERPERIMISSINCODE);
                    }else {
                        camera();
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

                    if(!PermisionUtils.getPermissState(RegistAct.this,PermisionUtils.TYPEONE).isEmpty()) {
                        String[] permissions = PermisionUtils.getPermissState(RegistAct.this,
                                PermisionUtils.TYPEONE).toArray(new String[PermisionUtils.getPermissState(RegistAct.this, PermisionUtils.TYPEONE).size()]);//将List转为数组
                        requestPermissions(permissions, PermisionUtils.REQUESTSTORAGEPERIMISSINCODE);
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



    /**
     * 调取相机
     */
    private void camera() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            photoSaveName = String.valueOf(System.currentTimeMillis()) + ".png";
            Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            imageUri = Uri.fromFile(new File(photoSavePath, photoSaveName));
             imageUri = FileProvider.getUriForFile(RegistAct.this, RegistAct.this.getApplicationContext().getPackageName() + ".provider", new File(photoSavePath, photoSaveName));
            openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(openCameraIntent, CAMERACODE);
        } else {
            MyToastUtils.showLongToast(RegistAct.this,"存储卡不存在！");
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
                    String type = data.getType();
                    Log.i("路径",imageUri.getPath()+"==");//content://com.android.providers.media.documents/document/image%3A84763
                    if (type == null) {
                        String realPath = imageUri.getEncodedPath();//获取图片真实路径
                        boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);// 判断sd卡是否存在
                        if (sdCardExist) {
                            String replaceStr = Environment.getExternalStorageDirectory().getPath();
                            if (realPath.contains("external_storage_root")) {
                                realPath = realPath.replaceFirst("/external_storage_root", replaceStr);
                                Log.e("realpath:", realPath);
                                imageUri = MyBitmapUtils.pathToUri(this, realPath);
                            }

                        }
                    }
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
            bit = MyBitmapUtils.getBitmapFormUri(RegistAct.this, imageUri);//比例压缩质量压缩
            File file = MyBitmapUtils.saveBitmap(bit, "regist.png");//把mitmap转成file文件保存在本地

            iv_photo.setImageBitmap(bit);
            uploadPhoto(file);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void uploadPhoto(File file) {
        RequestParam param=new RequestParam();
        param.addFile("f1",file);
        OkHttpUtils.post(HttpUrl.ADDPHOTO_URL, param, new OkHttpCallBack(RegistAct.this) {
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
                MyToastUtils.showShortToast(RegistAct.this,"图片上传失败,请重新上传");
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
            case PermisionUtils.REQUESTCAMERPERIMISSINCODE:

                for(int i=0;i<grantResults.length;i++){
                    if(grantResults[i]!=PackageManager.PERMISSION_GRANTED){//拒绝
                        //判断是否勾选禁止后不再询问
                        boolean showRequestPermission = ActivityCompat.shouldShowRequestPermissionRationale(RegistAct.this, permissions[i]);
                        if (showRequestPermission) {//是否够
                          MyToastUtils.showShortToast(RegistAct.this,"权限已被禁止");
                            return;
                        }else {//勾选上了
                            MyToastUtils.showShortToast(RegistAct.this,"获取相机,需要您手动开启文件读取权限和相机权限");
                            return;
                        }
                    }

                }
                camera();
                break;
            case PermisionUtils.REQUESTSTORAGEPERIMISSINCODE:

                for(int i=0;i<grantResults.length;i++){
                    if(grantResults[i]!=PackageManager.PERMISSION_GRANTED){//拒绝
                        //判断是否勾选禁止后不再询问
                        boolean showRequestPermission = ActivityCompat.shouldShowRequestPermissionRationale(RegistAct.this, permissions[i]);
                        if (showRequestPermission) {//是否被勾选默认
                            MyToastUtils.showShortToast(RegistAct.this,"权限已被禁止");
                            return;
                        }else {//勾选上了
                            MyToastUtils.showShortToast(RegistAct.this,"获取相册,需要您手动开启读取权限");

                            return;
                        }
                    }

                }
                showPhoto();
                break;
        }
    }

    private class MyTextChangedListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            judgeTex(s.toString());
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            judgeTex(s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    private void judgeTex(String s) {
        ll_viewline.setVisibility(View.GONE);
        ll_select.setVisibility(View.GONE);
        view_one.setBackgroundColor(getResources().getColor(R.color.line_intensity));
        view_two.setBackgroundColor(getResources().getColor(R.color.line_intensity));
        view_three.setBackgroundColor(getResources().getColor(R.color.line_intensity));
        iv_zw.setVisibility(View.INVISIBLE);
        iv_zwt.setVisibility(View.INVISIBLE);
        iv_zwthre.setVisibility(View.INVISIBLE);
        tv_kong.setTextColor(getResources().getColor(R.color.text_intensity));
        tv_chang.setTextColor(getResources().getColor(R.color.text_intensity));
        tv_dxx.setTextColor(getResources().getColor(R.color.text_intensity));
        tv_intensity.setVisibility(View.GONE);
        tv_intensity.setText("密码强度低");
        if(TextUtils.isEmpty(s)){
            ll_viewline.setVisibility(View.GONE);
            ll_select.setVisibility(View.GONE);
        }else {
            if(s.length()>=6&&s.length()<8){
               view_one.setBackgroundColor(getResources().getColor(R.color.line_one));
                tv_intensity.setText("密码强度低");
                tv_intensity.setVisibility(View.VISIBLE);
            }else if(s.length()>=8&&s.length()<14){
                view_one.setBackgroundColor(getResources().getColor(R.color.line_two));
                view_two.setBackgroundColor(getResources().getColor(R.color.line_two));
                tv_intensity.setText("密码强度中");
                tv_intensity.setVisibility(View.VISIBLE);
            }else if(s.length()>=14&&s.length()<=20){
                view_one.setBackgroundColor(getResources().getColor(R.color.line_three));
                view_two.setBackgroundColor(getResources().getColor(R.color.line_three));
                view_three.setBackgroundColor(getResources().getColor(R.color.line_three));
                tv_intensity.setText("密码强度高");
                tv_intensity.setVisibility(View.VISIBLE);
            }
            ll_viewline.setVisibility(View.VISIBLE);

            if(!s.toString().contains(" ")){
                iv_zw.setVisibility(View.VISIBLE);
                tv_kong.setTextColor(getResources().getColor(R.color.black_1));
            }

            if(s.length()>=6){
                iv_zwt.setVisibility(View.VISIBLE);
                tv_chang.setTextColor(getResources().getColor(R.color.black_1));
            }
            if(CheckUtil.isBigandSmailAndNum(s.toString().trim())){
                iv_zwthre.setVisibility(View.VISIBLE);
                tv_dxx.setTextColor(getResources().getColor(R.color.black_1));
            }
            ll_select.setVisibility(View.VISIBLE);


        }

    }
}
