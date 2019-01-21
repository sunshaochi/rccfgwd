package com.rmwl.rcchgwd.bean;

/**
 * Created by Administrator on 2019/1/5.
 */

public class InvestBean {

    /**
     * payAmount : string,已兑付总额
     * proId : string,主键，产品Id
     * accountname : string,账户名称
     * hasInterest : string,一手收益
     * repaysId : string,还款合并Id
     * drawingEndTime : string,划款开放结束时间
     * termTypeName : string,期限名称
     * amount : string,购买金额（万元为单位）
     * closeTime : string,结清时间
     * accountno : string,账户
     * rate : string,年后收益率
     * termType : string,期限类型
     * drawingStatrtTime : string,划款开放开始时间
     * pubId : string,合并Id
     * term : string,期限
     * buyTime : string,认购时间
     * branchname : string,开户行网店名称
     * remAmount : string,未兑付总额
     * purId : string,购买订单Id
     * productName : string,产品名称
     */

    private String payAmount;
    private String proId;
    private String accountname;
    private String hasInterest;
    private String repaysId;
    private String drawingEndTime;
    private String termTypeName;
    private String amount;
    private String closeTime;
    private String accountno;
    private String rate;
    private String termType;
    private String drawingStatrtTime;
    private String pubId;
    private String term;
    private String buyTime;
    private String branchname;
    private String remAmount;
    private String purId;
    private String productName;
    private String nextPayTime;
    private String status;
    private String statusName;
    private String realamount;

    public String getRealamount() {
        return realamount;
    }

    public void setRealamount(String realamount) {
        this.realamount = realamount;
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

    public String getNextPayTime() {
        return nextPayTime;
    }

    public void setNextPayTime(String nextPayTime) {
        this.nextPayTime = nextPayTime;
    }

    public String getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(String payAmount) {
        this.payAmount = payAmount;
    }

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public String getAccountname() {
        return accountname;
    }

    public void setAccountname(String accountname) {
        this.accountname = accountname;
    }

    public String getHasInterest() {
        return hasInterest;
    }

    public void setHasInterest(String hasInterest) {
        this.hasInterest = hasInterest;
    }

    public String getRepaysId() {
        return repaysId;
    }

    public void setRepaysId(String repaysId) {
        this.repaysId = repaysId;
    }

    public String getDrawingEndTime() {
        return drawingEndTime;
    }

    public void setDrawingEndTime(String drawingEndTime) {
        this.drawingEndTime = drawingEndTime;
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

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public String getAccountno() {
        return accountno;
    }

    public void setAccountno(String accountno) {
        this.accountno = accountno;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getTermType() {
        return termType;
    }

    public void setTermType(String termType) {
        this.termType = termType;
    }

    public String getDrawingStatrtTime() {
        return drawingStatrtTime;
    }

    public void setDrawingStatrtTime(String drawingStatrtTime) {
        this.drawingStatrtTime = drawingStatrtTime;
    }

    public String getPubId() {
        return pubId;
    }

    public void setPubId(String pubId) {
        this.pubId = pubId;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(String buyTime) {
        this.buyTime = buyTime;
    }

    public String getBranchname() {
        return branchname;
    }

    public void setBranchname(String branchname) {
        this.branchname = branchname;
    }

    public String getRemAmount() {
        return remAmount;
    }

    public void setRemAmount(String remAmount) {
        this.remAmount = remAmount;
    }

    public String getPurId() {
        return purId;
    }

    public void setPurId(String purId) {
        this.purId = purId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
