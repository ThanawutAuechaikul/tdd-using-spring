package com.bank.domain;

import java.math.BigDecimal;
import java.util.Date;

public class TransactionHistory {

    private String id;
    private String eventId;
    private Date transactionDate;
    private Account account;
    private TransactionType transactionType;
    private BigDecimal amount;
    private BigDecimal balance;
    private String remark;

    public TransactionHistory(String id, String eventId, Date transactionDate, Account account, TransactionType transactionType, BigDecimal amount, BigDecimal balance, String remark) {
        this.id = id;
        this.eventId = eventId;
        this.transactionDate = transactionDate;
        this.account = account;
        this.transactionType = transactionType;
        this.amount = amount;
        this.balance = balance;
        this.remark = remark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}


