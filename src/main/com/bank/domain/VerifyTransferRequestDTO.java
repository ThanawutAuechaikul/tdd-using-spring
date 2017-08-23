package com.bank.domain;

import com.bank.domain.Account;

public class VerifyTransferRequestDTO {

    private String fromRemark;
    private double amount;
    private Account fromAccount;
    private Account toAccount;

    public String getFromRemark() {
        return fromRemark;
    }

    public void setFromRemark(String fromRemark) {
        this.fromRemark = fromRemark;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Account getToAccount() {
        return toAccount;
    }

    public void setToAccount(Account fromAccount) {
        this.toAccount = fromAccount;
    }

    public Account getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(Account fromAccount) {
        this.fromAccount = fromAccount;
    }
}
