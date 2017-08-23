package com.bank.repository.internal;

import com.bank.domain.TransactionHistory;
import com.bank.domain.TransactionType;
import com.bank.model.SearchTransactionCriteria;
import com.bank.model.TransactionSummary;
import com.bank.model.TransactionSummaryResult;
import com.bank.repository.TransactionHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public class TransactionRepository implements TransactionHistoryRepository {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public TransactionRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insertTransaction(String eventId, LocalDateTime transactionDateTime, String accountId, TransactionType transactionType, Double amount, Double balance, String remark) {

    }

    @Override
    public TransactionSummaryResult getSummaryAmountGroupByType(SearchTransactionCriteria criteria) {

        TransactionSummaryResult result = new TransactionSummaryResult();

        List<TransactionSummary> summaryList = jdbcTemplate.query(
                "select TRANSACTION_TYPE, sum(AMOUNT) as SUM_AMOUNT from TRANSACTION_HISTORY where ACCOUNT_ID = ? and TRANSACTION_DATE between ? and ? group by TRANSACTION_TYPE",
                (rs, rowNum) -> new TransactionSummary(TransactionType.valueOf(rs.getString("TRANSACTION_TYPE")),  rs.getBigDecimal("SUM_AMOUNT"))
                , criteria.getAccountId(), criteria.getFromDate(), criteria.getToDate());

        result.setTransactionTypes(summaryList);

        return result;
    }

    @Override
    public List<TransactionHistory> getTransactionHistory(SearchTransactionCriteria criteria) {
        List<TransactionHistory> result = jdbcTemplate.query("select * from TRANSACTION_HISTORY where ACCOUNT_ID = ? and TRANSACTION_DATE between ? and ? limit ? offset ?",
                (rs, rowNum) -> new TransactionHistory(

                        rs.getString("ID"),
                        rs.getString("EVENT_ID"),
                        new Date(rs.getTimestamp("TRANSACTION_DATE").getTime()), null,
                        TransactionType.valueOf(rs.getString("TRANSACTION_TYPE")),
                        rs.getBigDecimal("AMOUNT"), rs.getBigDecimal("BALANCE"), rs.getString("REMARK")
                )
                , criteria.getAccountId(), criteria.getFromDate(), criteria.getToDate(),criteria.getLimit(),criteria.getOffset());
        return  result;
    }

    @Override
    public int getTotalTransactionHistoryByAccountId(SearchTransactionCriteria criteria) {
        int result = jdbcTemplate.queryForObject("select count(*) from TRANSACTION_HISTORY where ACCOUNT_ID = ? and TRANSACTION_DATE between ? and ? ",
                (rs, rowNum) -> rs.getInt(1)

                , criteria.getAccountId(), criteria.getFromDate(), criteria.getToDate());
        return  result;
    }

    public void insertTransaction(String eventId, String accountId, String transactionType, Double amount, Double balance, String remark) {
        jdbcTemplate.update("insert into TRANSACTION_HISTORY (EVENT_ID,TRANSACTION_DATE, ACCOUNT_ID, TRANSACTION_TYPE, AMOUNT, BALANCE, REMARK)" +
                                 " values(?, ?, ?, ?, ?, ?, ?)", eventId, new Date(), accountId, transactionType, amount, balance, remark);
    }


}
