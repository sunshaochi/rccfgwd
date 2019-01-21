package com.rmwl.rcchgwd.bean;

/**
 * Created by Administrator on 2019/1/7.
 */

public class RecommdBean {

    /**
     * inviteCode : 0
     * inviteNum :  推荐人数
     * inviteAmount : 佣金
     * uuId : asdads
     * totalInvestment : 50,000.00
     */

    private String inviteCode;
    private String inviteNum;
    private String inviteAmount;
    private String uuId;
    private String totalInvestment;

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getInviteNum() {
        return inviteNum;
    }

    public void setInviteNum(String inviteNum) {
        this.inviteNum = inviteNum;
    }

    public String getInviteAmount() {
        return inviteAmount;
    }

    public void setInviteAmount(String inviteAmount) {
        this.inviteAmount = inviteAmount;
    }

    public String getUuId() {
        return uuId;
    }

    public void setUuId(String uuId) {
        this.uuId = uuId;
    }

    public String getTotalInvestment() {
        return totalInvestment;
    }

    public void setTotalInvestment(String totalInvestment) {
        this.totalInvestment = totalInvestment;
    }
}
