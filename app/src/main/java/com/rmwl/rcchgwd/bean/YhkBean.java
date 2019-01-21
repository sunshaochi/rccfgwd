package com.rmwl.rcchgwd.bean;

import java.io.Serializable;

/**
 * Created by acer on 2018/8/24.
 */

public class YhkBean implements Serializable{


    /**
     * ifBlack : string,是否屏蔽：0启用1-禁用
     * id : string,ID
     * bankfullname : string,银行全称
     * iconLUrl : string,大图标文件
     * status : string,状态：0-待启用，1-启用中，2-停用
     * bankname : string,银行名称
     * iconUrl : string,图标文件
     */

    private String ifBlack;
    private String id;
    private String bankfullname;
    private String iconLUrl;
    private String status;
    private String bankname;
    private String iconUrl;

    public String getIfBlack() {
        return ifBlack;
    }

    public void setIfBlack(String ifBlack) {
        this.ifBlack = ifBlack;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBankfullname() {
        return bankfullname;
    }

    public void setBankfullname(String bankfullname) {
        this.bankfullname = bankfullname;
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

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
}
