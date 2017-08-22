package com.bank.controller;

import com.bank.domain.Account;
import com.bank.repository.AccountRepository;
import com.bank.repository.internal.JdbcAccountRepository;
import com.bank.repository.internal.SimpleAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.util.List;

@RestController
public class AccountController {
    @Autowired
    DataSource dataSource;

    @RequestMapping(value = "/getAccountListByUserId", method = {RequestMethod.GET}, produces = "application/json")
    public @ResponseBody List<Account> getAccountListByUserId(@RequestParam String userId) {
        AccountRepository accountRep = new JdbcAccountRepository(dataSource);
        return accountRep.findAllAccountsByUserId(userId);
    }

}