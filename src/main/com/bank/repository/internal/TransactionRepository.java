package com.bank.repository.internal;

import com.bank.domain.TransactionType;

import java.time.LocalDateTime;

public class TransactionRepository {

    public void insertTransaction(String eventId, LocalDateTime transactionDateTime, String accountId, TransactionType transactionType, Double amount, Double balance, String remark) {
    }
}
