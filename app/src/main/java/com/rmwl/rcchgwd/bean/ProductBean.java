package com.rmwl.rcchgwd.bean;

import java.io.Serializable;

/**
 * Created by acer on 2018/8/23.
 */

public class ProductBean implements Serializable{


    /**
     * proId : 2
     * productName : 景荣二号
     * interest : 9.2000
     * term : 12
     * termType : 1
     * termTypeName : 月
     * amount : 100000.00
     * allcount : 100
     * remamount : 0.00
     * remcount : 99
     * startAmount : 10000.00
     * stepAmount : 10000.00
     * benefitMode : 1
     * benefitModeName : 到期溢价回购
     * status : 2
     * statusName : 售罄
     * days : 0
     *
     * "minBaseRateInteger":"8",  最小基本利率-整数位①
     "minBaseRateDecimal":"",   最小基本利率-小数位②
     "minAddedRateInteger":"0",  最小附加利率-整数位③
     "minAddedRateDecimal":"3",  最小附加利率-小数位④
     "maxBaseRateInteger":"9",  最大基本利率-整数位⑤
     "maxBaseRateDecimal":"6",  最大基本利率-小数位⑥
     "maxAddedRateInteger":"",  最大附加利率-整数位⑦
     "maxAddedRateDecimal":""  最大附加利率-小数位⑧

     */

    private String progressVal;


    private String proId;
    private String productName;
    private String interest;
    private String term;
    private String termType;
    private String termTypeName;
    private String amount;
    private String allcount;
    private String remamount;
    private String remcount;
    private String startAmount;
    private String stepAmount;
    private String benefitMode;
    private String benefitModeName;
    private String status;
    private String statusName;
    private String days;

    private String hasAmount;
    private String hascount;
    private String minBaseRateInteger;//  最小基本利率-整数位①
    private String minBaseRateDecimal;//  最小基本利率-小数位②
    private String minAddedRateInteger;//  最小附加利率-整数位③
    private String minAddedRateDecimal;// 最小附加利率-小数位④
    private String maxBaseRateInteger;//  最大基本利率-整数位⑤
    private String maxBaseRateDecimal;//  最大基本利率-小数位⑥
    private String maxAddedRateInteger;// 最大附加利率-整数位⑦
    private String maxAddedRateDecimal;//  最大附加利率-小数位⑧

    private String aheadBegindate;//预约上架时间

    public String getProgressVal() {
        return progressVal;
    }

    public void setProgressVal(String progressVal) {
        this.progressVal = progressVal;
    }

    public String getAheadBegindate() {
        return aheadBegindate;
    }

    public void setAheadBegindate(String aheadBegindate) {
        this.aheadBegindate = aheadBegindate;
    }

    public String getMinBaseRateInteger() {
        return minBaseRateInteger;
    }

    public void setMinBaseRateInteger(String minBaseRateInteger) {
        this.minBaseRateInteger = minBaseRateInteger;
    }

    public String getMinBaseRateDecimal() {
        return minBaseRateDecimal;
    }

    public void setMinBaseRateDecimal(String minBaseRateDecimal) {
        this.minBaseRateDecimal = minBaseRateDecimal;
    }

    public String getMinAddedRateInteger() {
        return minAddedRateInteger;
    }

    public void setMinAddedRateInteger(String minAddedRateInteger) {
        this.minAddedRateInteger = minAddedRateInteger;
    }

    public String getMinAddedRateDecimal() {
        return minAddedRateDecimal;
    }

    public void setMinAddedRateDecimal(String minAddedRateDecimal) {
        this.minAddedRateDecimal = minAddedRateDecimal;
    }

    public String getMaxBaseRateInteger() {
        return maxBaseRateInteger;
    }

    public void setMaxBaseRateInteger(String maxBaseRateInteger) {
        this.maxBaseRateInteger = maxBaseRateInteger;
    }

    public String getMaxBaseRateDecimal() {
        return maxBaseRateDecimal;
    }

    public void setMaxBaseRateDecimal(String maxBaseRateDecimal) {
        this.maxBaseRateDecimal = maxBaseRateDecimal;
    }

    public String getMaxAddedRateInteger() {
        return maxAddedRateInteger;
    }

    public void setMaxAddedRateInteger(String maxAddedRateInteger) {
        this.maxAddedRateInteger = maxAddedRateInteger;
    }

    public String getMaxAddedRateDecimal() {
        return maxAddedRateDecimal;
    }

    public void setMaxAddedRateDecimal(String maxAddedRateDecimal) {
        this.maxAddedRateDecimal = maxAddedRateDecimal;
    }

    public String getHasAmount() {
        return hasAmount;
    }

    public void setHasAmount(String hasAmount) {
        this.hasAmount = hasAmount;
    }

    public String getHascount() {
        return hascount;
    }

    public void setHascount(String hascount) {
        this.hascount = hascount;
    }

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
         this.term = term;
    }

    public String getTermType() {
        return termType;
    }

    public void setTermType(String termType) {
        this.termType = termType;
    }

    public String getTermTypeName() {
        return termTypeName;
    }

    public void setTermTypeName(String termTypeName) {
        this.termTypeName = termTypeName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAllcount() {
        return allcount;
    }

    public void setAllcount(String allcount) {
        this.allcount = allcount;
    }

    public String getRemamount() {
        return remamount;
    }

    public void setRemamount(String remamount) {
        this.remamount = remamount;
    }

    public String getRemcount() {
        return remcount;
    }

    public void setRemcount(String remcount) {
        this.remcount = remcount;
    }

    public String getStartAmount() {
        return startAmount;
    }

    public void setStartAmount(String startAmount) {
        this.startAmount = startAmount;
    }

    public String getStepAmount() {
        return stepAmount;
    }

    public void setStepAmount(String stepAmount) {
        this.stepAmount = stepAmount;
    }

    public String getBenefitMode() {
        return benefitMode;
    }

    public void setBenefitMode(String benefitMode) {
        this.benefitMode = benefitMode;
    }

    public String getBenefitModeName() {
        return benefitModeName;
    }

    public void setBenefitModeName(String benefitModeName) {
        this.benefitModeName = benefitModeName;
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

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }
}
