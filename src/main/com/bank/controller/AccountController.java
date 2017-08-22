package com.bank.controller;

import com.bank.domain.Account;
import com.bank.repository.AccountRepository;
import com.bank.repository.internal.JdbcAccountRepository;
import com.bank.repository.internal.SimpleAccountRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.util.List;

@Controller
public class AccountController {

    @RequestMapping(value = "/getAccountListByUserId", method = {RequestMethod.GET}, produces = "application/json")
    public @ResponseBody List<Account> getAccountListByUserId(@RequestParam String userId) {
        AccountRepository accountRep = new SimpleAccountRepository();
        return accountRep.findAllAccountsByUserId(userId);
    }

}