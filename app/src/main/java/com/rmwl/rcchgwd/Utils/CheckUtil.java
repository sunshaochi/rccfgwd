package com.rmwl.rcchgwd.Utils;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by acer on 2018/8/23.
 */

public class CheckUtil {
    /**
     * 验证输入的名字是否为“中文”或者是否包含“·”
     */
    public static boolean isLegalName(String name){
        if (name.contains("·") || name.contains("•")){
            if (name.matches("^[\\u4e00-\\u9fa5]+[·•][\\u4e00-\\u9fa5]+$")){
                return true;
            }else {
                return false;
            }
        }else {
            if (name.matches("^[\\u4e00-\\u9fa5]+$")){
                return true;
            }else if (name.matches("^[a-zA-Z]+$")){
                return true;
            }else {
                return false;
            }
        }
    }
    /**
     * 正则表达式:验证身份证
     */
    public static final String REGEX_ID_CARD = "(^\\d{15}$)|(^\\d{17}([0-9]|X)$)";
    /**
     * 验证身份证号码
     * @param idCard 居民身份证号码15位或18位，最后一位可能是数字或字母
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkIdCard(String idCard) {
        //这里只支持18位身份证
        if (idCard.length()<18){
            return false;
        }
        return Pattern.matches(REGEX_ID_CARD,idCard);
    }

    public static boolean isMobile(String mobiles)
    {
        String phone="^1[3|4|5|6|7|8|9][0-9]\\d{8}$";
        Pattern p = Pattern.compile(phone);
        Matcher m = p.matcher(mobiles);
        Log.e("Reg",String.valueOf(m.matches()));
        return m.matches();
    }

    public static boolean isPwd(String pwd)
    {
//        String strpwd="^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,12}$";
//        String strpwd="^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$";//上面是之前的
        String strpwd="^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])[A-Za-z\\d]{6,}$";//至少6个字符，至少1个大写字母，1个小写字母和1个数字,不能包含特殊字符（非数字字母）：
        Pattern p = Pattern.compile(strpwd);
        Matcher m = p.matcher(pwd);
        Log.e("Reg",String.valueOf(m.matches()));
        return m.matches();
    }


    /**
     * 必须大写字母小写字母和数字同时出现
     * @param pwd
     * @return
     */
    public static boolean isBigandSmailAndNum(String pwd)
    {
//        String strpwd="^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,12}$";
//        String strpwd="^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$";
        String strpwd="^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{3,}$";//至少3个字符，至少1个大写字母，1个小写字母和1个数字,不能包含特殊字符（非数字字母）：
        Pattern p = Pattern.compile(strpwd);
        Matcher m = p.matcher(pwd);
        Log.e("Reg",String.valueOf(m.matches()));
        return m.matches();
    }






    /**
     * 显示或隐藏密码眼睛
     * @param isShow
     */
    public static void showOrHide(boolean isShow, EditText etPassword ){
        //记住光标开始的位置
        int pos = etPassword.getSelectionStart();
        if(isShow){
            etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }else{
            etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

        }
        etPassword.setSelection(pos);
    }


    /**
     * 校验银行卡卡号
     *
     * @param cardId
     * @return
     */
    public static boolean checkBankCard(String cardId) {
        char bit = getBankCardCheckCode(cardId
                .substring(0, cardId.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return cardId.charAt(cardId.length() - 1) == bit;
    }


    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     *
     * @param nonCheckCodeCardId
     * @return
     */
    public static char getBankCardCheckCode(String nonCheckCodeCardId) {
        if (nonCheckCodeCardId == null
                || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
            // 如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }


}
