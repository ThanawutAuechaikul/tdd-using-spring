package com.bank.domain;

import javax.validation.constraints.Digits;

public class VerifyTransferBean {

    private String fromRemark;

    private String toRemark;

    @Digits(integer = 15, fraction = 2)
    private double amount;

    private Account toAccount;

    private Account fromAccount;

    public String getFromRemark() {
        return fromRemark;
    }

    public void setFromRemark(String fromRemark) {
        this.fromRemark = fromRemark;
    }

    public String getToRemark() {
        return toRemark;
    }

    public void setToRemark(String toRemark) {
        this.toRemark = toRemark;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Account getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(Account fromAccount) {
        this.fromAccount = fromAccount;
    }

    public Account getToAccount() {
        return toAccount;
    }

    public void setToAccount(Account toAccount) {
        this.toAccount = toAccount;
    }
}
