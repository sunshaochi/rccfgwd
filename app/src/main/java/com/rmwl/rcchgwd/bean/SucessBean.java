package com.rmwl.rcchgwd.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/8/25.
 */

public class SucessBean implements Serializable{

    /**
     * payBegindate : 2018年08月25日 13:49
     * amount : 5
     * allAmount : 0.00
     * accountname :
     * endDate : 2018年08月25日 13:49
     * interest : 7530.82
     * accountno :
     * show : 1
     * allInter : 0.00
     * productName : whltest
     * branchname :
     */

    private String payBegindate;
    private String amount;
    private String allAmount;
    private String accountname;
    private String endDate;
    private String interest;
    private String accountno;
    private String show;
    private String allInter;
    private String productName;
    private String branchname;

    public String getPayBegindate() {
        return payBegindate;
    }

    public void setPayBegindate(String payBegindate) {
        this.payBegindate = payBegindate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAllAmount() {
        return allAmount;
    }

    public void setAllAmount(String allAmount) {
        this.allAmount = allAmount;
    }

    public String getAccountname() {
        return accountname;
    }

    public void setAccountname(String accountname) {
        this.accountname = accountname;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getAccountno() {
        return accountno;
    }

    public void setAccountno(String accountno) {
        this.accountno = accountno;
    }

    public String getShow() {
        return show;
    }

    public void setShow(String show) {
        this.show = show;
    }

    public String getAllInter() {
        return allInter;
    }

    public void setAllInter(String allInter) {
        this.allInter = allInter;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBranchname() {
        return branchname;
    }

    public void setBranchname(String branchname) {
        this.branchname = branchname;
    }
}
