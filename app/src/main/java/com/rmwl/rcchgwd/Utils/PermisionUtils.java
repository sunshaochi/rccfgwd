package com.rmwl.rcchgwd.Utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/1/2.
 */

public class PermisionUtils {


    public static final int TYPEONE=1;
    public static final int TYPETWO=2;
    public static String[] permissions;

    public static final int REQUESTSTORAGEPERIMISSINCODE=102;//文件读取权限
    public static final int REQUESTCAMERPERIMISSINCODE=103;//相机权限


    public static List<String> getPermissState(Context context,int type){
        List<String> mPermissionList = new ArrayList<>();
        mPermissionList.clear();
        if (type==TYPEONE){
            permissions=new String[]{
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE};
        }

        if (type==TYPETWO){
            permissions=new String[]{
                  android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.CAMERA};
        }



        for (int j = 0; j < permissions.length; j++) {
            if (ContextCompat.checkSelfPermission(context, permissions[j]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[j]);
            }
        }
        return mPermissionList;
    }







}
