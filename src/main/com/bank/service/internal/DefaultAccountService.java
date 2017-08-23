package com.bank.service.internal;

import com.bank.domain.Account;
import com.bank.repository.AccountRepository;
import com.bank.repository.internal.JdbcAccountRepository;
import com.bank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

@Service
public class DefaultAccountService implements AccountService{

    @Autowired
    DataSource datasource;


    @Override
    public List<Account> findAllAccountsByUserId(String userId) {
        AccountRepository accountRepository = new JdbcAccountRepository(datasource);
        return accountRepository.findAllAccountsByUserId(userId);
    }
}
