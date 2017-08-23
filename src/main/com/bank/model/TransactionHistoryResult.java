package com.bank.model;

import com.bank.domain.TransactionHistory;

import java.util.ArrayList;
import java.util.List;

public class TransactionHistoryResult {

    private List<TransactionHistory> transactionHistories = new ArrayList<TransactionHistory>();
    private int total;

    public List<TransactionHistory> getTransactionHistories() {
        return transactionHistories;
    }

    public void setTransactionHistories(List<TransactionHistory> transactionHistories) {
        this.transactionHistories = transactionHistories;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
