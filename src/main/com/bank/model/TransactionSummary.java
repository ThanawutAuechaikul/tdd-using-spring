package com.bank.model;

import com.bank.domain.TransactionType;

import java.math.BigDecimal;

public class TransactionSummary {

    private TransactionType type;
    private BigDecimal summaryAmount;

    public TransactionSummary(TransactionType type, BigDecimal summaryAmount) {
        this.type = type;
        this.summaryAmount = summaryAmount;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public BigDecimal getSummaryAmount() {
        return summaryAmount;
    }

    public void setSummaryAmount(BigDecimal summaryAmount) {
        this.summaryAmount = summaryAmount;
    }
}
