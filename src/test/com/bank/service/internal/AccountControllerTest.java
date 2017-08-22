package com.bank.service.internal;

import com.bank.domain.InvalidDepositAmountException;
import com.bank.repository.AccountRepository;
import com.bank.repository.internal.SimpleAccountRepository;
import com.bank.service.DepositService;
import com.bank.service.FeePolicy;
import org.junit.Before;
import org.junit.Test;
import com.bank.domain.Account;

import java.util.List;

import static com.bank.repository.internal.SimpleAccountRepository.Data.*;
import static com.bank.repository.internal.SimpleAccountRepository.Data.U123_UID;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class AccountControllerTest {



    @Test
    public void shouldReturnAllAccountOfUser(){
        AccountRepository accountRepository = new SimpleAccountRepository();
        List<Account> actualAccounts = accountRepository.findAllAccountsByUserId(U123_UID) ;

        assertThat(2, equalTo(actualAccounts.size()));
    }

}
