package com.bank.domain;

public class TransferRequest {

    private String srcAccount;
    private String destAccount;
    private double amount;
    private String remark;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSrcAccount() {
        return srcAccount;
    }

    public void setSrcAccount(String srcAccount) {
        this.srcAccount = srcAccount;
    }

    public String getDestAccount() {
        return destAccount;
    }

    public void setDestAccount(String destAccount) {
        this.destAccount = destAccount;
    }

    @Override
    public boolean equals(Object obj) {
        TransferRequest request = (TransferRequest) obj;
        return this.srcAccount.equals(request.srcAccount) &&
                this.destAccount.equals(request.destAccount) &&
                this.remark.equals(request.remark) &&
                this.amount == request.getAmount();
    }
}
