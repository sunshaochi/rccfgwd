package com.rmwl.rcchgwd.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by acer on 2018/8/23.
 */

public class ProductInfoBean implements Serializable{


    /**
     * id : 1
     * productTypeid : 1
     * productName : 金融一号
     * saleArea : 1
     * orgListingId : 1
     * orgDelistingId : 2
     * orgListingOrgId : 3
     * termtype : 1
     * terms : 12
     * amount : 200000.0
     * allcount : 200
     * remamount : 170000.0
     * remcount : 199
     * startAmount : 10000.0
     * stepAmount : 5000.0
     * interest : 7.2
     * benefitMode : 1
     * baseInfor : 按时大多数
     * baseCost : 1.0
     * registManageFile : https://files-ubuyche.oss-cn-shanghai.aliyuncs.com/ubuyche-store/files/brandLogo/7.png
     * addMeasures : 爱上大声大声大声大声道
     * riskMeasures : 爱上大声大声大声对巴萨大
     * fundsUse : asda
     * repaymentSocuce : 1
     * tradeStuctFile : 1dasd
     * aheadBegindate : 2018-08-14 11:26:52
     * aheadEnddate : 2018-08-25 11:26:56
     * payBegindate : 2018-08-22 11:27:00
     * payEdndate : 2018-08-23 11:27:04
     * interestBegindate : 2018-08-24 00:00:00
     * interestEnddate : 2018-08-29 00:00:00
     * ifBlack : 0
     * ifDel : 0
     * status : 1
     * createUser : 1
     * createTime : 2018-08-23 11:27:21
     * updateUser :
     * updateTime :
     * productNote : 好东西
     * benefitModeName : 到期溢价回购
     * statusName : 销售中
     * termTypeName : 月
     * gpfOrg : {"id":"1","companyName":"美丽","registDate":"2018-08-23 00:00:00","registCapital":1,"industry":"100","qualificationFile":"https://files-ubuyche.oss-cn-shanghai.aliyuncs.com/ubuyche-store/files/brandLogo/7.png","busiRole":"1","ownershipFile":"https://files-ubuyche.oss-cn-shanghai.aliyuncs.com/ubuyche-store/files/brandLogo/7.png","risknotice":"adasa","status":"0","ifBlack":"0","ifDel":"0","createUser":"1","createTime":"2018-08-23 11:32:06","updateUser":"","updateTime":"","summary":"asdasdasd"}
     * zpfOrg : {"id":"2","companyName":"asdasd","registDate":"2018-08-25 00:00:00","registCapital":1,"industry":"200","qualificationFile":"https://files-ubuyche.oss-cn-shanghai.aliyuncs.com/ubuyche-store/files/brandLogo/7.png","busiRole":"2","ownershipFile":"https://files-ubuyche.oss-cn-shanghai.aliyuncs.com/ubuyche-store/files/brandLogo/7.png","risknotice":"sa","status":"0","ifBlack":"0","ifDel":"0","createUser":"1","createTime":"2018-08-23 11:33:08","updateUser":"","updateTime":"","summary":"zxc"}
     * gpjgOrg : {"id":"3","companyName":"sczxc","registDate":"2018-08-23 00:00:00","registCapital":1,"industry":"121","qualificationFile":"https://files-ubuyche.oss-cn-shanghai.aliyuncs.com/ubuyche-store/files/brandLogo/7.png","busiRole":"3","ownershipFile":"https://files-ubuyche.oss-cn-shanghai.aliyuncs.com/ubuyche-store/files/brandLogo/7.png","risknotice":"asda","status":"0","ifBlack":"0","ifDel":"0","createUser":"2","createTime":"2018-08-23 11:33:37","updateUser":"","updateTime":"","summary":"asd"}
     */

    private String id;
    private String productTypeid;
    private String productName;
    private String saleArea;
    private String orgListingId;
    private String orgDelistingId;
    private String orgListingOrgId;
    private String termtype;
    private String terms;
    private String amount;
    private String allcount;
    private String remamount;
    private String remcount;
    private String startAmount;
    private String stepAmount;
    private String interest;
    private String benefitMode;
    private String baseInfor;
    private String baseCost;
    private String registManageFile;
    private String addMeasures;
    private String riskMeasures;
    private String fundsUse;
    private String repaymentSocuce;
    private String tradeStuctFile;
    private String aheadBegindate;
    private String aheadEnddate;
    private String payBegindate;
    private String payEdndate;
    private String interestBegindate;
    private String interestEnddate;
    private String ifBlack;
    private String ifDel;
    private String status;
    private String createUser;
    private String createTime;
    private String updateUser;
    private String updateTime;
    private String productNote;
    private String benefitModeName;
    private String statusName;
    private String termTypeName;
    private GpfOrgBean gpfOrg;
    private ZpfOrgBean zpfOrg;
    private GpjgOrgBean gpjgOrg;

    private String minBaseRateInteger;//  最小基本利率-整数位①
    private String minBaseRateDecimal;//  最小基本利率-小数位②
    private String minAddedRateInteger;//  最小附加利率-整数位③
    private String minAddedRateDecimal;// 最小附加利率-小数位④
    private String maxBaseRateInteger;//  最大基本利率-整数位⑤
    private String maxBaseRateDecimal;//  最大基本利率-小数位⑥
    private String maxAddedRateInteger;// 最大附加利率-整数位⑦
    private String maxAddedRateDecimal;//  最大附加利率-小数位⑧

    private String vStartAmount;//起投金额（显示）（万元为单位）
    private String vStepAmount;//第增金额（显示）（万元为单位）
    private String minStartAmount;//起投金额（最小计算）（万元为单位）
    private String minStepAmount;//第增金额（最小计算）（万元为单位）

    private String payInterNotes;//  支付结束、计息开始时间说明
    private String productFullName;//产品全称

    public String getProductFullName() {
        return productFullName;
    }

    public void setProductFullName(String productFullName) {
        this.productFullName = productFullName;
    }

    public String getPayInterNotes() {
        return payInterNotes;
    }

    public void setPayInterNotes(String payInterNotes) {
        this.payInterNotes = payInterNotes;
    }

    public String getvStartAmount() {
        return vStartAmount;
    }

    public void setvStartAmount(String vStartAmount) {
        this.vStartAmount = vStartAmount;
    }

    public String getvStepAmount() {
        return vStepAmount;
    }

    public void setvStepAmount(String vStepAmount) {
        this.vStepAmount = vStepAmount;
    }

    public String getMinStartAmount() {
        return minStartAmount;
    }

    public void setMinStartAmount(String minStartAmount) {
        this.minStartAmount = minStartAmount;
    }

    public String getMinStepAmount() {
        return minStepAmount;
    }

    public void setMinStepAmount(String minStepAmount) {
        this.minStepAmount = minStepAmount;
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

    private List<ProdIntervalBean> prodIntervalratelist;

    public List<ProdIntervalBean> getProdIntervalratelist() {
        return prodIntervalratelist;
    }

    public void setProdIntervalratelist(List<ProdIntervalBean> prodIntervalratelist) {
        this.prodIntervalratelist = prodIntervalratelist;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductTypeid() {
        return productTypeid;
    }

    public void setProductTypeid(String productTypeid) {
        this.productTypeid = productTypeid;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSaleArea() {
        return saleArea;
    }

    public void setSaleArea(String saleArea) {
        this.saleArea = saleArea;
    }

    public String getOrgListingId() {
        return orgListingId;
    }

    public void setOrgListingId(String orgListingId) {
        this.orgListingId = orgListingId;
    }

    public String getOrgDelistingId() {
        return orgDelistingId;
    }

    public void setOrgDelistingId(String orgDelistingId) {
        this.orgDelistingId = orgDelistingId;
    }

    public String getOrgListingOrgId() {
        return orgListingOrgId;
    }

    public void setOrgListingOrgId(String orgListingOrgId) {
        this.orgListingOrgId = orgListingOrgId;
    }

    public String getTermtype() {
        return termtype;
    }

    public void setTermtype(String termtype) {
        this.termtype = termtype;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
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

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getBenefitMode() {
        return benefitMode;
    }

    public void setBenefitMode(String benefitMode) {
        this.benefitMode = benefitMode;
    }

    public String getBaseInfor() {
        return baseInfor;
    }

    public void setBaseInfor(String baseInfor) {
        this.baseInfor = baseInfor;
    }

    public String getBaseCost() {
        return baseCost;
    }

    public void setBaseCost(String baseCost) {
        this.baseCost = baseCost;
    }

    public String getRegistManageFile() {
        return registManageFile;
    }

    public void setRegistManageFile(String registManageFile) {
        this.registManageFile = registManageFile;
    }

    public String getAddMeasures() {
        return addMeasures;
    }

    public void setAddMeasures(String addMeasures) {
        this.addMeasures = addMeasures;
    }

    public String getRiskMeasures() {
        return riskMeasures;
    }

    public void setRiskMeasures(String riskMeasures) {
        this.riskMeasures = riskMeasures;
    }

    public String getFundsUse() {
        return fundsUse;
    }

    public void setFundsUse(String fundsUse) {
        this.fundsUse = fundsUse;
    }

    public String getRepaymentSocuce() {
        return repaymentSocuce;
    }

    public void setRepaymentSocuce(String repaymentSocuce) {
        this.repaymentSocuce = repaymentSocuce;
    }

    public String getTradeStuctFile() {
        return tradeStuctFile;
    }

    public void setTradeStuctFile(String tradeStuctFile) {
        this.tradeStuctFile = tradeStuctFile;
    }

    public String getAheadBegindate() {
        return aheadBegindate;
    }

    public void setAheadBegindate(String aheadBegindate) {
        this.aheadBegindate = aheadBegindate;
    }

    public String getAheadEnddate() {
        return aheadEnddate;
    }

    public void setAheadEnddate(String aheadEnddate) {
        this.aheadEnddate = aheadEnddate;
    }

    public String getPayBegindate() {
        return payBegindate;
    }

    public void setPayBegindate(String payBegindate) {
        this.payBegindate = payBegindate;
    }

    public String getPayEdndate() {
        return payEdndate;
    }

    public void setPayEdndate(String payEdndate) {
        this.payEdndate = payEdndate;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getProductNote() {
        return productNote;
    }

    public void setProductNote(String productNote) {
        this.productNote = productNote;
    }

    public String getBenefitModeName() {
        return benefitModeName;
    }

    public void setBenefitModeName(String benefitModeName) {
        this.benefitModeName = benefitModeName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getTermTypeName() {
        return termTypeName;
    }

    public void setTermTypeName(String termTypeName) {
        this.termTypeName = termTypeName;
    }

    public GpfOrgBean getGpfOrg() {
        return gpfOrg;
    }

    public void setGpfOrg(GpfOrgBean gpfOrg) {
        this.gpfOrg = gpfOrg;
    }

    public ZpfOrgBean getZpfOrg() {
        return zpfOrg;
    }

    public void setZpfOrg(ZpfOrgBean zpfOrg) {
        this.zpfOrg = zpfOrg;
    }

    public GpjgOrgBean getGpjgOrg() {
        return gpjgOrg;
    }

    public void setGpjgOrg(GpjgOrgBean gpjgOrg) {
        this.gpjgOrg = gpjgOrg;
    }

    public static class GpfOrgBean implements Serializable{
        /**
         * id : 1
         * companyName : 美丽
         * registDate : 2018-08-23 00:00:00
         * registCapital : 1.0
         * industry : 100
         * qualificationFile : https://files-ubuyche.oss-cn-shanghai.aliyuncs.com/ubuyche-store/files/brandLogo/7.png
         * busiRole : 1
         * ownershipFile : https://files-ubuyche.oss-cn-shanghai.aliyuncs.com/ubuyche-store/files/brandLogo/7.png
         * risknotice : adasa
         * status : 0
         * ifBlack : 0
         * ifDel : 0
         * createUser : 1
         * createTime : 2018-08-23 11:32:06
         * updateUser :
         * updateTime :
         * summary : asdasdasd
         */

        private String id;
        private String companyName;
        private String registDate;
        private String registCapital;
        private String industry;
        private String qualificationFile;
        private String busiRole;
        private String ownershipFile;
        private String risknotice;
        private String status;
        private String ifBlack;
        private String ifDel;
        private String createUser;
        private String createTime;
        private String updateUser;
        private String updateTime;
        private String summary;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getRegistDate() {
            return registDate;
        }

        public void setRegistDate(String registDate) {
            this.registDate = registDate;
        }

        public String getRegistCapital() {
            return registCapital;
        }

        public void setRegistCapital(String registCapital) {
            this.registCapital = registCapital;
        }

        public String getIndustry() {
            return industry;
        }

        public void setIndustry(String industry) {
            this.industry = industry;
        }

        public String getQualificationFile() {
            return qualificationFile;
        }

        public void setQualificationFile(String qualificationFile) {
            this.qualificationFile = qualificationFile;
        }

        public String getBusiRole() {
            return busiRole;
        }

        public void setBusiRole(String busiRole) {
            this.busiRole = busiRole;
        }

        public String getOwnershipFile() {
            return ownershipFile;
        }

        public void setOwnershipFile(String ownershipFile) {
            this.ownershipFile = ownershipFile;
        }

        public String getRisknotice() {
            return risknotice;
        }

        public void setRisknotice(String risknotice) {
            this.risknotice = risknotice;
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

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }
    }

    public static class ZpfOrgBean implements Serializable{
        /**
         * id : 2
         * companyName : asdasd
         * registDate : 2018-08-25 00:00:00
         * registCapital : 1.0
         * industry : 200
         * qualificationFile : https://files-ubuyche.oss-cn-shanghai.aliyuncs.com/ubuyche-store/files/brandLogo/7.png
         * busiRole : 2
         * ownershipFile : https://files-ubuyche.oss-cn-shanghai.aliyuncs.com/ubuyche-store/files/brandLogo/7.png
         * risknotice : sa
         * status : 0
         * ifBlack : 0
         * ifDel : 0
         * createUser : 1
         * createTime : 2018-08-23 11:33:08
         * updateUser :
         * updateTime :
         * summary : zxc
         */

        private String id;
        private String companyName;
        private String registDate;
        private String registCapital;
        private String industry;
        private String qualificationFile;
        private String busiRole;
        private String ownershipFile;
        private String risknotice;
        private String status;
        private String ifBlack;
        private String ifDel;
        private String createUser;
        private String createTime;
        private String updateUser;
        private String updateTime;
        private String summary;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getRegistDate() {
            return registDate;
        }

        public void setRegistDate(String registDate) {
            this.registDate = registDate;
        }

        public String getRegistCapital() {
            return registCapital;
        }

        public void setRegistCapital(String registCapital) {
            this.registCapital = registCapital;
        }

        public String getIndustry() {
            return industry;
        }

        public void setIndustry(String industry) {
            this.industry = industry;
        }

        public String getQualificationFile() {
            return qualificationFile;
        }

        public void setQualificationFile(String qualificationFile) {
            this.qualificationFile = qualificationFile;
        }

        public String getBusiRole() {
            return busiRole;
        }

        public void setBusiRole(String busiRole) {
            this.busiRole = busiRole;
        }

        public String getOwnershipFile() {
            return ownershipFile;
        }

        public void setOwnershipFile(String ownershipFile) {
            this.ownershipFile = ownershipFile;
        }

        public String getRisknotice() {
            return risknotice;
        }

        public void setRisknotice(String risknotice) {
            this.risknotice = risknotice;
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

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }
    }

    public static class GpjgOrgBean implements Serializable{
        /**
         * id : 3
         * companyName : sczxc
         * registDate : 2018-08-23 00:00:00
         * registCapital : 1.0
         * industry : 121
         * qualificationFile : https://files-ubuyche.oss-cn-shanghai.aliyuncs.com/ubuyche-store/files/brandLogo/7.png
         * busiRole : 3
         * ownershipFile : https://files-ubuyche.oss-cn-shanghai.aliyuncs.com/ubuyche-store/files/brandLogo/7.png
         * risknotice : asda
         * status : 0
         * ifBlack : 0
         * ifDel : 0
         * createUser : 2
         * createTime : 2018-08-23 11:33:37
         * updateUser :
         * updateTime :
         * summary : asd
         */

        private String id;
        private String companyName;
        private String registDate;
        private String registCapital;
        private String industry;
        private String qualificationFile;
        private String busiRole;
        private String ownershipFile;
        private String risknotice;
        private String status;
        private String ifBlack;
        private String ifDel;
        private String createUser;
        private String createTime;
        private String updateUser;
        private String updateTime;
        private String summary;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getRegistDate() {
            return registDate;
        }

        public void setRegistDate(String registDate) {
            this.registDate = registDate;
        }

        public String getRegistCapital() {
            return registCapital;
        }

        public void setRegistCapital(String registCapital) {
            this.registCapital = registCapital;
        }

        public String getIndustry() {
            return industry;
        }

        public void setIndustry(String industry) {
            this.industry = industry;
        }

        public String getQualificationFile() {
            return qualificationFile;
        }

        public void setQualificationFile(String qualificationFile) {
            this.qualificationFile = qualificationFile;
        }

        public String getBusiRole() {
            return busiRole;
        }

        public void setBusiRole(String busiRole) {
            this.busiRole = busiRole;
        }

        public String getOwnershipFile() {
            return ownershipFile;
        }

        public void setOwnershipFile(String ownershipFile) {
            this.ownershipFile = ownershipFile;
        }

        public String getRisknotice() {
            return risknotice;
        }

        public void setRisknotice(String risknotice) {
            this.risknotice = risknotice;
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

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }
    }
}
