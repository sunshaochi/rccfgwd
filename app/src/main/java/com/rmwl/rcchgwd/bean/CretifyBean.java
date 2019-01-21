package com.rmwl.rcchgwd.bean;

import java.io.Serializable;

/**
 * Created by acer on 2018/8/24.
 */

public class CretifyBean implements Serializable{

    /**
     * id : string,认证信息主键ID
     * status : string,状态：0-待认证，1-认证成功，2-认证失败
     * remark : string,备注
     * idtype : string,证件类型1身份证2护照
     * userId : string,用户主键ID
     * failreason : string,失败原因
     * realname : string,真实姓名
     * idno : string,证件号码
     */

    private String id;
    private String status;
    private String remark;
    private String idtype;
    private String userId;
    private String failreason;
    private String realname;
    private String idno;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIdtype() {
        return idtype;
    }

    public void setIdtype(String idtype) {
        this.idtype = idtype;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFailreason() {
        return failreason;
    }

    public void setFailreason(String failreason) {
        this.failreason = failreason;
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
}
