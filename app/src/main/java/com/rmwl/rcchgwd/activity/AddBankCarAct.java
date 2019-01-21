package com.rmwl.rcchgwd.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.rmwl.rcchgwd.R;
import com.rmwl.rcchgwd.Utils.CheckUtil;
import com.rmwl.rcchgwd.Utils.EncryptionTools;
import com.rmwl.rcchgwd.Utils.GetJsonDataUtil;
import com.rmwl.rcchgwd.Utils.GsonUtils;
import com.rmwl.rcchgwd.Utils.MyToastUtils;
import com.rmwl.rcchgwd.Utils.SpUtils;
import com.rmwl.rcchgwd.base.BaseActivity;
import com.rmwl.rcchgwd.bean.BankBean;
import com.rmwl.rcchgwd.bean.DicBankinforBean;
import com.rmwl.rcchgwd.bean.JsonBean;
import com.rmwl.rcchgwd.bean.UserBean;
import com.rmwl.rcchgwd.bean.YhkBean;
import com.rmwl.rcchgwd.okhttp.HttpUrl;
import com.rmwl.rcchgwd.okhttp.OkHttpCallBack;
import com.rmwl.rcchgwd.okhttp.OkHttpUtils;
import com.rmwl.rcchgwd.okhttp.RequestParam;
import com.rmwl.rcchgwd.view.CommonView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by acer on 2018/8/22.
 */

public class AddBankCarAct extends BaseActivity {
    @ViewInject(R.id.com_name)
    private CommonView com_name;
    @ViewInject(R.id.com_zjlx)
    private CommonView com_zjlx;
    @ViewInject(R.id.com_zjhm)
    private CommonView com_zjhm;

    @ViewInject(R.id.com_khh)
    private CommonView com_khh;
    @ViewInject(R.id.com_khd)
    private CommonView com_khd;

    @ViewInject(R.id.et_yhzh)
    private EditText et_yhzh;

    @ViewInject(R.id.et_khhwd)
    private EditText et_khhwd;


    private int type;//1增加2是修改
    private UserBean user;
    private boolean isLoaded = false;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private Thread thread;

    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    private int i1=-10,i2=-10,i3=-10;

    private String bankcardProv,bankcardCity;
    private String bankname,cardno;
    private String branchname;//网点
    private String bankid;
    private String id;
    int BRANDREQUESTCODE =10101;//银行名称
    private Gson gson;

    private BankBean bankBean;
    private List<YhkBean>list;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了
//                        Toast.makeText(VehicleActivity.this, "Begin Parse Data", Toast.LENGTH_SHORT).show();
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 子线程中解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;

                case MSG_LOAD_SUCCESS:
//                    Toast.makeText(VehicleActivity.this, "Parse Succeed", Toast.LENGTH_SHORT).show();
                    showPickerView();
                    isLoaded = true;
                    break;

                case MSG_LOAD_FAILED:
                    Toast.makeText(AddBankCarAct.this, "Parse Failed", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    public void setLayout() {
        setContentView(R.layout.act_addbankcar);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        gson=new Gson();
        type = getIntent().getExtras().getInt("type", 1);
        id = getIntent().getExtras().getString("id", "");
        bankid= getIntent().getExtras().getString("bankId", "");
        list= (List<YhkBean>) getIntent().getExtras().getSerializable("list");

        if (!TextUtils.isEmpty(SpUtils.getUser(AddBankCarAct.this))) {
            user = GsonUtils.gsonToBean(SpUtils.getUser(AddBankCarAct.this), UserBean.class);
        }

         setTopUi();//绚烂顶部数据从本地

        if (type == 1) {//新增加
            setTopTitle("新增银行卡");
            com_khh.setIvArrow(true);
            com_khh.setcentText("请选择银行");
            com_khh.setCentTextColor(getResources().getColor(R.color.black_2));
            com_khh.setEnabled(true);
            et_yhzh.setText("");
            et_yhzh.setHint("银行账号");
            et_yhzh.setEnabled(true);
            bankid="";
            bankcardProv="";
            bankcardCity="";

        } else {
            setTopTitle("修改银行卡");
            com_khh.setIvArrow(false);
            com_khh.setcentText("选择的银行名称");
            com_khh.setCentTextColor(getResources().getColor(R.color.black_1));
            com_khh.setEnabled(false);
            et_yhzh.setText("");
            et_yhzh.setEnabled(false);

        }

        getInfo();

    }

    private void setTopUi() {
        com_name.setcentText(user.getRealname());
        com_zjlx.setcentText(user.getIdtypeName());
        com_zjhm.setcentText(user.getIdno());
    }

    private void getInfo() {
        EncryptionTools.initEncrypMD5(TextUtils.isEmpty(user.getToken()) ? "" : user.getToken());
        RequestParam param = new RequestParam();
        param.add("token", TextUtils.isEmpty(user.getToken()) ? "" : user.getToken());
        param.add("timestamp", EncryptionTools.TIMESTAMP);
        param.add("nonce", EncryptionTools.NONCE);
        param.add("signature", EncryptionTools.SIGNATURE);
        param.add("source", "3");
        if(TextUtils.isEmpty(bankid)) {//新添加时候是没有的
            param.add("bankId", bankid);
        }
        OkHttpUtils.post(HttpUrl.GETBANKCERTIFYINFO, param, new OkHttpCallBack(AddBankCarAct.this) {
            @Override
            public void onSuccess(String data) {
                try {
                    JSONObject object=new JSONObject(data.toString());
                    JSONObject result=object.getJSONObject("result");
//                    JSONArray array=result.getJSONArray("dicBankinfors");
//                    diclist=gson.fromJson(array.toString(),new TypeToken<List<DicBankinforBean>>(){}.getType());

                    bankBean=GsonUtils.gsonToBean(result.toString(),BankBean.class);
                    if(bankBean!=null) {

                        if(!TextUtils.isEmpty(bankBean.getBankfullname())) {
                            com_khh.setcentText(bankBean.getBankfullname());
                        }
                        et_yhzh.setText(bankBean.getCardno());
                        if(!TextUtils.isEmpty(bankBean.getBankcardProv())||!TextUtils.isEmpty(bankBean.getBankcardCity())) {
                            com_khd.setcentText(bankBean.getBankcardProv() + bankBean.getBankcardCity());
                            com_khd.setCentTextColor(getResources().getColor(R.color.black_1));
                        }
                        et_khhwd.setText(bankBean.getBranchname());

                        bankid=bankBean.getBankid();
                        bankcardProv=bankBean.getBankcardProv();
                        bankcardCity=bankBean.getBankcardCity();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int code, String error) {
             MyToastUtils.showShortToast(AddBankCarAct.this,error);
            }
        });
    }

    @OnClick({R.id.com_khh,R.id.com_khd,R.id.tv_qd})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.com_khh:
                if(list!=null&&list.size()>0) {
                    startActivityForResult(new Intent(AddBankCarAct.this, SelectBankAct.class).putExtra("list", (Serializable) list), BRANDREQUESTCODE);
                }else {
                    MyToastUtils.showShortToast(AddBankCarAct.this,"数据异常");
                }
                break;
            case R.id.com_khd:
                if(!isLoaded) {
                    mHandler.sendEmptyMessage(MSG_LOAD_DATA);
                }else {
                    showPickerView();
                }
                break;
            case R.id.tv_qd:
                if(isVisible()){
                  goHttp();
                }
                break;
        }
    }

    private void goHttp() {
        EncryptionTools.initEncrypMD5(TextUtils.isEmpty(user.getToken()) ? "" : user.getToken());
        RequestParam param = new RequestParam();
        param.add("token", TextUtils.isEmpty(user.getToken()) ? "" : user.getToken());
        param.add("timestamp", EncryptionTools.TIMESTAMP);
        param.add("nonce", EncryptionTools.NONCE);
        param.add("signature", EncryptionTools.SIGNATURE);
        param.add("source", "3");
        if(!TextUtils.isEmpty(id)){
            param.add("id",id);
        }
        param.add("bankcardProv", bankcardProv);
        param.add("bankcardCity", bankcardCity);
        param.add("bankid", bankid);
        param.add("createSource", "3");
        param.add("cardno", cardno);
        param.add("ifDefaultcard", "0");
        param.add("branchname", branchname);
        OkHttpUtils.post(HttpUrl.SAVECARD_URL, param, new OkHttpCallBack(AddBankCarAct.this) {
            @Override
            public void onSuccess(String data) {
                try {
                    JSONObject object = new JSONObject(data.toString());
                    JSONObject result = object.getJSONObject("result");
                    UserBean userBean = GsonUtils.gsonToBean(result.toString(), UserBean.class);
                    SpUtils.setUser(AddBankCarAct.this,GsonUtils.bean2Json(userBean));
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(int code, String error) {
                MyToastUtils.showShortToast(AddBankCarAct.this,error);
            }
        });

    }

    private boolean isVisible() {
        cardno=et_yhzh.getText().toString().trim();
        branchname=et_khhwd.getText().toString();

        if(TextUtils.isEmpty(bankid)){
            MyToastUtils.showShortToast(AddBankCarAct.this,"请选择银行");
            return false;
        }

        if(TextUtils.isEmpty(cardno)){
            et_yhzh.requestFocus();
            MyToastUtils.showShortToast(AddBankCarAct.this,"请输入银行卡号！");
            return false;
        }

//        if(cardno.length()<16){
//            et_yhzh.requestFocus();
//            et_yhzh.setSelection(cardno.length());
//            MyToastUtils.showShortToast(AddBankCarAct.this,"请输入正确格式银行卡账号");
//            return false;
//        }

        if(!CheckUtil.checkBankCard(cardno)){
            et_yhzh.requestFocus();
            et_yhzh.setSelection(cardno.length());
            MyToastUtils.showShortToast(AddBankCarAct.this,"请输入正确格式银行卡账号");
            return false;
        }

        if(TextUtils.isEmpty(bankcardProv)||TextUtils.isEmpty(bankcardCity)){
            MyToastUtils.showShortToast(AddBankCarAct.this,"请选择开户地");
            return false;
        }

        if(TextUtils.isEmpty(branchname)){
            et_khhwd.requestFocus();
            MyToastUtils.showShortToast(AddBankCarAct.this,"请输入开户行网点");
            return false;
        }
        return true;
    }


    private void showPickerView() {// 弹出选择器

        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置

                bankcardProv= options1Items.get(options1).getPickerViewText();
                bankcardCity=  options2Items.get(options1).get(options2);
                com_khd.setcentText(bankcardProv+bankcardCity);
                com_khd.setCentTextColor(getResources().getColor(R.color.black_1));
                i1=options1;
                i2=options2;
//                i3=options3;

//                Toast.makeText(JsonDataActivity.this, tx, Toast.LENGTH_SHORT).show();
            }
        })

                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

//        pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器
//        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        if(i1!=-10&&i2!=-10&&i3!=-10) {
            pvOptions.setSelectOptions(i1,i2,i3);
        }
        pvOptions.show();
    }

    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市
                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {
                    City_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }

        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }


    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data==null){
            return;
        }else {
            bankid=data.getExtras().getString("bankid","");
            bankname=data.getExtras().getString("bankname","");
            com_khh.setcentText(bankname);
            com_khh.setCentTextColor(getResources().getColor(R.color.black_1));
        }
    }
}
