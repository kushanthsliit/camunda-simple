package com.example.emptask.dto;

public class EmpRequest {
    PurchaseOrder purchaseOrder;
    String businessKey;

    public EmpRequest(PurchaseOrder po, String businessKey) {
        this.purchaseOrder = po;
        this.businessKey = businessKey;
    }

    public PurchaseOrder getPo() {
        return purchaseOrder;
    }

    public void setPo(PurchaseOrder po) {
        this.purchaseOrder = po;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }
}
