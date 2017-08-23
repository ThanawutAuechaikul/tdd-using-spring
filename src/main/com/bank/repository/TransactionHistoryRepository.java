package com.bank.repository;

import com.bank.domain.TransactionHistory;
import com.bank.domain.TransactionType;
import com.bank.model.SearchTransactionCriteria;
import com.bank.model.TransactionSummaryResult;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionHistoryRepository {

    public TransactionSummaryResult getSummaryAmountGroupByType(SearchTransactionCriteria criteria );
    public List<TransactionHistory> getTransactionHistory(SearchTransactionCriteria criteria);
    public int getTotalTransactionHistoryByAccountId(SearchTransactionCriteria criteria);
}
