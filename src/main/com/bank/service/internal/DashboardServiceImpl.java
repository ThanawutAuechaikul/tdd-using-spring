package com.bank.service.internal;

import com.bank.model.SearchTransactionCriteria;
import com.bank.model.TransactionHistoryResult;
import com.bank.model.TransactionSummaryResult;
import com.bank.repository.internal.TransactionRepository;
import com.bank.service.DashboardService;
import com.bank.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.Date;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private DataSource dataSource;

    @Override
    public SearchTransactionCriteria getDashboardSearchCriteria(String accountId) {
        SearchTransactionCriteria criteria = new SearchTransactionCriteria();
        criteria.setAccountId(accountId);
        criteria.setFromDate( DateUtil.getFirstDate( new Date() ) );
        criteria.setToDate( new Date());
        return criteria;
    }

    SearchTransactionCriteria getDashboardSearchCriteria(String accountId, int offset, int limit) {
        SearchTransactionCriteria criteria = getDashboardSearchCriteria(accountId);
        criteria.setOffset(offset);
        criteria.setLimit(limit);
        return criteria;
    }

    @Override
    public TransactionSummaryResult getPiechartData(String accountId) {
        SearchTransactionCriteria criteria = getDashboardSearchCriteria(accountId);
        TransactionRepository transactionRepository = new TransactionRepository(dataSource);
        return transactionRepository.getSummaryAmountGroupByType( criteria );
    }

    @Override
    public TransactionHistoryResult getTransactionHistory(String accountId, int offset, int limit) {
        SearchTransactionCriteria criteria = getDashboardSearchCriteria(accountId, offset, limit);
        TransactionHistoryResult result = new TransactionHistoryResult();
        TransactionRepository transactionRepository = new TransactionRepository(dataSource);
        result.setTotal(transactionRepository.getTotalTransactionHistoryByAccountId(criteria));
        result.setTransactionHistories(transactionRepository.getTransactionHistory(criteria));
        return result;
    }

    @Override
    public Integer getCountTotalTransactions(String accountId) {
        SearchTransactionCriteria criteria = getDashboardSearchCriteria( accountId );
        TransactionRepository transactionRepository = new TransactionRepository(dataSource);
        return transactionRepository.getTotalTransactionHistoryByAccountId(criteria);
    }
}
