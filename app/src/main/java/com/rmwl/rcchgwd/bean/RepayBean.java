package com.rmwl.rcchgwd.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/1/5.
 */

public class RepayBean implements Serializable{


    /**还款计划
     * id : 5b9a2308d13d6cb2d56b6917
     * term : 3
     * userId : 5b8c99d6d765b0e4e00c40bd
     * amount : 1303.56
     * principle : 0.00
     * interest : 1303.56
     * expireDate : 2018-12-18
     * expectPaydate : 2018-12-13
     * payDate : 2018-12-13 00:00:00.0
     * status : 1
     * statusName : 待兑付
     */

    private String id;
    private String term;
    private String userId;
    private String amount;
    private String principle;
    private String interest;
    private String expireDate;
    private String expectPaydate;
    private String payDate;
    private String status;
    private String statusName;
    private String cardNo;

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    /**明细
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     */
    private String type;
    private String subsidyKind;
    private String periodName;
    private String interestBegindate;
    private String interestEnddate;
    private String realamount;
//    private String interest;
    private String realinterest;
    private String days;
    private String realamountView;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubsidyKind() {
        return subsidyKind;
    }

    public void setSubsidyKind(String subsidyKind) {
        this.subsidyKind = subsidyKind;
    }

    public String getPeriodName() {
        return periodName;
    }

    public void setPeriodName(String periodName) {
        this.periodName = periodName;
    }

    public String getInterestBegindate() {
        return interestBegindate;
    }

    public void setInterestBegindate(String interestBegindate) {
        this.interestBegindate = interestBegindate;
    }

    public String getInterestEnddate() {
        return interestEnddate;
    }

    public void setInterestEnddate(String interestEnddate) {
        this.interestEnddate = interestEnddate;
    }

    public String getRealamount() {
        return realamount;
    }

    public void setRealamount(String realamount) {
        this.realamount = realamount;
    }

    public String getRealinterest() {
        return realinterest;
    }

    public void setRealinterest(String realinterest) {
        this.realinterest = realinterest;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getRealamountView() {
        return realamountView;
    }

    public void setRealamountView(String realamountView) {
        this.realamountView = realamountView;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPrinciple() {
        return principle;
    }

    public void setPrinciple(String principle) {
        this.principle = principle;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getExpectPaydate() {
        return expectPaydate;
    }

    public void setExpectPaydate(String expectPaydate) {
        this.expectPaydate = expectPaydate;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
