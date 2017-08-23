package com.bank.controller;

import com.bank.domain.Account;
import com.bank.model.SearchTransactionCriteria;
import com.bank.model.TransactionHistoryResult;
import com.bank.model.TransactionSummaryResult;
import com.bank.repository.AccountRepository;
import com.bank.repository.TransactionHistoryRepository;
import com.bank.repository.internal.JdbcAccountRepository;
import com.bank.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.util.List;

@RestController
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;


    @CrossOrigin
    @RequestMapping(value = "/dashboard/piechart/{accountId}", method = {RequestMethod.GET}, produces = "application/json")
    public @ResponseBody TransactionSummaryResult getPieChartData(@PathVariable String accountId) {
        return dashboardService.getPiechartData(accountId);
    }

    @CrossOrigin
    @RequestMapping(value = "/dashboard/transaction/{accountId}", method = {RequestMethod.GET}, produces = "application/json")
    public @ResponseBody TransactionHistoryResult getTransactionHistory(@PathVariable String accountId, @RequestParam("offset") int offset, @RequestParam("limit") int limit){
        return dashboardService.getTransactionHistory(accountId,offset,limit);
    }

}
