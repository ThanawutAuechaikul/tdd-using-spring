package com.bank.repository;

import com.bank.domain.TransactionType;
import com.bank.model.SearchTransactionCriteria;
import com.bank.model.TransactionSummaryResult;

import java.time.LocalDateTime;

public interface TransactionHistoryRepository {

    public void insertTransaction(String eventId, LocalDateTime transactionDateTime, String accountId, TransactionType transactionType, Double amount, Double balance, String remark);
    public TransactionSummaryResult getSummaryAmountGroupByType(SearchTransactionCriteria criteria );

}
