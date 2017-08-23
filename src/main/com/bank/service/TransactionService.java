package com.bank.service;

import com.bank.domain.TransactionType;

public interface TransactionService {

    void createDefaultTransaction(String accountId, TransactionType transactionType, Double amount, Double balance, String remark);
    String createTransferTransaction(String fromAccountId, String toAccountId, Double amount, Double balanceFrom, Double balanceTo, String remarkFrom);

    void updateTransferTransaction(String eventId, String remarkTo);
}
