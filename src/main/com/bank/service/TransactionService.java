package com.bank.service;

import com.bank.domain.TransactionType;
import com.bank.domain.TransferReceipt;

public interface TransactionService {

    void createDefaultTransaction(String accountId, TransactionType transactionType, Double amount, Double balance, String remark);
    String createTransferTransaction(TransferReceipt receipt);

    void updateTransferTransaction(String eventId, String remarkTo);
}
