package com.bank.controller;

import com.bank.domain.Account;
import com.bank.model.AccountSummary;
import com.bank.repository.AccountRepository;
import com.bank.repository.internal.JdbcAccountRepository;
import com.bank.repository.internal.SimpleAccountRepository;
import com.bank.service.AccountService;
import com.bank.service.internal.DefaultAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.util.List;

@RestController
@CrossOrigin
public class AccountController {
    @Autowired
    DataSource dataSource;

    @Autowired
    AccountService accountService;

    @RequestMapping(value = "user/{userId}/accounts", method = {RequestMethod.GET}, produces = "application/json")
    public @ResponseBody List<Account> getAccountListByUserId(@PathVariable String userId) {
        return accountService.findAllAccountsByUserId(userId);
    }

    @RequestMapping(value = "account/summary/{accountId}", method = {RequestMethod.GET}, produces = "application/json")
    public @ResponseBody AccountSummary getAccountSummary(@PathVariable String accountId) {
        AccountRepository accountRep = new JdbcAccountRepository(dataSource);
        return accountRep.getAccountSummaryByAccountId(accountId);
    }

}