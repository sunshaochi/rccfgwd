package com.rmwl.rcchgwd.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/8/25.
 */

public class DicBankinforBean implements Serializable{


    /**
     * id : 5b7a5d42cb881866009c88b6
     * bankname : 大连开发区鑫汇村镇银行
     * bankfullname : 大连开发区鑫汇村镇银行
     * iconUrl : https://static-ubuyche.oss-cn-shanghai.aliyuncs.com/banklogo/slogo/5b7a5d42cb881866009c88b6.png
     * iconLUrl : https://static-ubuyche.oss-cn-shanghai.aliyuncs.com/banklogo/llogo/5b7a5d42cb881866009c88b6.jpg
     * status : 1
     * ifBlack : 0
     * ifDel : 0
     * createUser : 000000000000000000000000
     * createTime : 2018-08-23 16:49:07
     * updateUser : 000000000000000000000000
     * updateTime : 2018-08-23 16:49:07
     */

    private String id;
    private String bankname;
    private String bankfullname;
    private String iconUrl;
    private String iconLUrl;
    private String status;
    private String ifBlack;
    private String ifDel;
    private String createUser;
    private String createTime;
    private String updateUser;
    private String updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getBankfullname() {
        return bankfullname;
    }

    public void setBankfullname(String bankfullname) {
        this.bankfullname = bankfullname;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getIconLUrl() {
        return iconLUrl;
    }

    public void setIconLUrl(String iconLUrl) {
        this.iconLUrl = iconLUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIfBlack() {
        return ifBlack;
    }

    public void setIfBlack(String ifBlack) {
        this.ifBlack = ifBlack;
    }

    public String getIfDel() {
        return ifDel;
    }

    public void setIfDel(String ifDel) {
        this.ifDel = ifDel;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
