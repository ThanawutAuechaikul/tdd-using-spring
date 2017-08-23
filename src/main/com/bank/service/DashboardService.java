package com.bank.service;

import com.bank.model.SearchTransactionCriteria;
import com.bank.model.TransactionSummaryResult;

public interface DashboardService {

    SearchTransactionCriteria getDashboardSearchCriteria(String accountId);

    TransactionSummaryResult getPiechartData( String accountId );

}
