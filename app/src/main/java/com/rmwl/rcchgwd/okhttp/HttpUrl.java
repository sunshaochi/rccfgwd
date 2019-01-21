package com.rmwl.rcchgwd.okhttp;

/**
 * Created by Administrator on 2018/3/27.
 */

public class HttpUrl {

    /**
     * 生产
     **/
    static final String IP = "https://manage.shrccf.com";//生产服务
    static final String H5IP = "https://website.shrccf.com/";//生产h5地址

    /**预发布**/
//        static final String IP="https://prelaunch.shrccf.com";//预发布环境
//        static final String H5IP="https://prelaunchh5.shrccf.com/";//预发布地址h5

    /**开发**/

//        static final String IP="http://192.168.2.239:8080/rccf-front-manage";//开发服务
//        static final String H5IP="http://192.168.2.239/";//h5开发地址

//    static final String IP="http://192.168.6.244:8080/rccf-front-manage";//xu


    /**测试**/
    //      static final String IP="http://192.168.2.248:8080";//测试
//          static final String IP="https://developmanage.shrccf.com";//内部测试环境&
    //      static final String H5IP="http://192.168.2.248/#/";//h5测试地址
//          static final String H5IP="http://develop.shrccf.com/";//内部测试环境

    /**
     * 其他本机
     **/
//          static final String IP="http://192.168.3.210:8090/rccf-front-manage";//海龙
    //      static final String H5IP="http://192.168.2.239:8091/#/";//熊样地址
    //      static final String H5IP="http://192.168.4.251:8001/#/";
    //      static final String H5IP="https://rccf-bucket.oss-cn-shanghai.aliyuncs.com/";

    public static final String key = "545b3dadb9e0b0e48aa3d21d";   //签名秘钥


    //首页
    public static final String HOME_URL = HttpUrl.IP + "/app/product/queryIndex.do";
    public static final String INFO_URL = HttpUrl.IP + "/app/advisor/product/queryProductDetail.do";
    public static final String CPLIST_URL = HttpUrl.IP + "/app/advisor/product/queryProductList.do";
    public static final String MYFRAGMENT_URL = HttpUrl.IP + "/app/chieve/queryIndexAchieve.do";
    public static final String UPDATEPWD_URL = HttpUrl.IP + "/app/advisor/updPassword.do";

    public static final String FORGPWD_URL = HttpUrl.IP + "/app/advisor/public/retrievePasswordByAdvisor.do";
    public static final String CAPTCHANUM_URL = HttpUrl.IP + "/app/advisor/public/getSmsSendNumberByAdvisor.do";
    public static final String SENDMSG_URL = HttpUrl.IP + "/app/advisor/public/sendMsgByAdvisor.do";
    public static final String SAVEREALNAME_URL = HttpUrl.IP + "/app/advisor/realnameCertify/saveRealnameCertify.do";
    public static final String LOGIN = HttpUrl.IP + "/app/advisor/public/advisorLogin.do";
    public static final String REGIST_URL = HttpUrl.IP + "/app/advisor/public/registerAdvisor.do";
    public static final String USERREALNAME_URL = HttpUrl.IP + "/app/advisor/realnameCertify/getAdvisorRealnameCertify.do";

    public static final String BANKCERTIFYLIST = HttpUrl.IP + "/app/advisor/bank/getAdvisorBankInfo.do";
    public static final String GETBANKCERTIFYINFO = HttpUrl.IP + "/app/advisor/bank/getAdvisorBank.do";
    public static final String SAVECARD_URL = HttpUrl.IP + "/app/advisor/bank/saveAdvisorBank.do";
    public static final String PURCHASE_URL = HttpUrl.IP + "/app/purchase/ queryUserHasPurchase.do";
    public static final String QUERYINTEREST = HttpUrl.IP + "/app/product/ queryInterest.do";
    public static final String BUYPRODUCT = HttpUrl.IP + "/app/purchase/buyProduct.do";
    public static final String APPVERSION = HttpUrl.IP + "/app/advisor/public/getAdvisorAppVersion.do";

    public static final String ADDPHOTO_URL = HttpUrl.IP + "/app/advisor/upload/uploadUserLicenseImg.do";
    public static final String BANNER_URL = HttpUrl.IP + "/app/chieve/queryIndex.do";
    public static final String SHARETYPE_URL = HttpUrl.IP + "/app/advisor/public/getProductShareType.do";

    public static final String GETADVISORINFO_URL = HttpUrl.IP + "/app/advisor/public/getAdvisorInfo.do";
    public static final String UPDATEADVISOR_URL = HttpUrl.IP + "/app/advisor/public/updateAdvisor.do";

    public static final String APK_URL = "https://files.shrccf.com/client/rcchgwd.apk";

    //业绩
    public static final String ACHIEVE_URL= HttpUrl.IP +"/app/chieve/queryChieveList.do";
    //客户
    public static final String CUSTOMER_URL= HttpUrl.IP +"/app/advisor/listUserByAdvisor.do";
    //客户投资列表
    public static final String CUSTOMER_INVEST_URL = HttpUrl.IP +"/app/advisor/listUserPurchaseByUser.do";
    //未实名
    public static final String REAL_URL= HttpUrl.IP +"/app/advisor/listNoRealNameByAdvisor.do";
    //资产未到账
    public static final String ARRIVE_URL= HttpUrl.IP +"/app/advisor/queryAdNotDeal.do";
    //推荐
    public static final String RECOMMD_URL = HttpUrl.IP + "/app/chieve/queryAdRecommd.do";

    //还款计划
    public static final String QUERYREPAYRECORD_URL =HttpUrl.IP + "/app/advisor/product/queryRepayRecord.do";
    public static final String QUERYPRODREPAYTERMDETAIL_URL =HttpUrl.IP + "/app/advisor/product/queryProdRepayTermDetail.do";


    public static String HCPXQ_URL = H5IP + "productDetail";
    public static String HRGLB_URL = H5IP + "subscription";
    public static String HJYJL_URL = H5IP + "tradingRecord";
    public static String HELP_URL = H5IP + "helpCenter";
    public static String HYHJ_URL = H5IP + "discountCoupon";
    public static String HTUJ_URL = H5IP + "recommend";
    public static final String KH = H5IP + "client";
    //    public static final String KH ="https://baidu.com";
    public static final String YJ = H5IP + "performance";
    public static final String DB = H5IP + "agenda";
    public static final String HREGIST_XY = H5IP + "agreement";
    public static final String SHAREPRO_URL = H5IP + "productShare";

    public static final String RECOMMD_CODE_URL = H5IP + "active";

}
