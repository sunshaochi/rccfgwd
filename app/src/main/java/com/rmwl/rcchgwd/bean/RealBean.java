package com.rmwl.rcchgwd.bean;

/**
 * 未实名
 * Created by Administrator on 2019/1/4.
 */

public class RealBean {

    /**
     * advisorId : string,理财顾问ID
     * id : string,客户主键ID
     * createTime : string,创建时间
     * birthday : string,出生日期
     * loginTime : string,最后登录时间
     * status : string,状态：1-手机认证，2-实名认证，3-银行卡绑定
     * idtype : string,证件类型：1-身份证,2-护照
     * realname : string,真实姓名
     * idno : string,证件号码
     * mobile : string,手机号
     */

    private String advisorId;
    private String id;
    private String createTime;
    private String birthday;
    private String loginTime;
    private String status;
    private String idtype;
    private String realname;
    private String idno;
    private String mobile;

    public String getAdvisorId() {
        return advisorId;
    }

    public void setAdvisorId(String advisorId) {
        this.advisorId = advisorId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdtype() {
        return idtype;
    }

    public void setIdtype(String idtype) {
        this.idtype = idtype;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
