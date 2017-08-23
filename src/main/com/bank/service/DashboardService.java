package com.bank.service;

import com.bank.domain.TransactionHistory;
import com.bank.model.SearchTransactionCriteria;
import com.bank.model.TransactionHistoryResult;
import com.bank.model.TransactionSummary;
import com.bank.model.TransactionSummaryResult;

import java.util.List;

public interface DashboardService {

    SearchTransactionCriteria getDashboardSearchCriteria(String accountId);

    TransactionSummaryResult getPiechartData( String accountId );

    TransactionHistoryResult getTransactionHistory(String accountId, int offset, int limit);



}
