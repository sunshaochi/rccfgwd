package com.rmwl.rcchgwd.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/9/11.
 */

public class ProdIntervalBean implements Serializable{

    private String purchaseAmount;
    private String intervalrate;

    public String getPurchaseAmount() {
        return purchaseAmount;
    }

    public void setPurchaseAmount(String purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }

    public String getIntervalrate() {
        return intervalrate;
    }

    public void setIntervalrate(String intervalrate) {
        this.intervalrate = intervalrate;
    }
}
