package com.bank.model;

import java.util.ArrayList;
import java.util.List;

public class TransactionSummaryResult {

    private List<TransactionSummary> transactionTypes = new ArrayList<TransactionSummary>();

    public List<TransactionSummary> getTransactionTypes() {
        return transactionTypes;
    }

    public void setTransactionTypes(List<TransactionSummary> transactionTypes) {
        this.transactionTypes = transactionTypes;
    }
}
