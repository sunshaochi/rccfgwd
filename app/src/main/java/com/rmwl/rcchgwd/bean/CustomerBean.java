package com.rmwl.rcchgwd.bean;

/**
 * 客户列表实体
 * Created by Administrator on 2019/1/4.
 */

public class CustomerBean {

    /**
     * birthday : string,出生日期
     * createTime : string,创建时间
     * loginTime : string,最后登录时间
     * idtype : string,证件类型：1-身份证,2-护照
     * status : string,状态：0-待审核，1-审核通过，2-审核失败
     * allRealamount : string,累计投资金额（顾问APP客户列表字段）
     * id : string,客户主键ID
     * advisorId : string,理财顾问ID
     * onRealamount : string,在投金额（顾问APP客户列表字段）
     * realname : string,真实姓名
     * idno : string,证件号码
     * ifRealname : string,是否实名0-否，1-是
     * mobile : string,手机号
     */

    private String birthday;
    private String createTime;
    private String loginTime;
    private String idtype;
    private String status;
    private String allRealamount;
    private String id;
    private String advisorId;
    private String onRealamount;
    private String realname;
    private String idno;
    private String ifRealname;
    private String mobile;

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getIdtype() {
        return idtype;
    }

    public void setIdtype(String idtype) {
        this.idtype = idtype;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAllRealamount() {
        return allRealamount;
    }

    public void setAllRealamount(String allRealamount) {
        this.allRealamount = allRealamount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdvisorId() {
        return advisorId;
    }

    public void setAdvisorId(String advisorId) {
        this.advisorId = advisorId;
    }

    public String getOnRealamount() {
        return onRealamount;
    }

    public void setOnRealamount(String onRealamount) {
        this.onRealamount = onRealamount;
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

    public String getIfRealname() {
        return ifRealname;
    }

    public void setIfRealname(String ifRealname) {
        this.ifRealname = ifRealname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
