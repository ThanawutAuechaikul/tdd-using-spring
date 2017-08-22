package com.bank.controller;

import com.bank.domain.Account;
import com.bank.model.AccountSummary;
import com.bank.repository.AccountRepository;
import com.bank.repository.internal.SimpleAccountRepository;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static com.bank.repository.internal.SimpleAccountRepository.Data.U123_UID;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class AccountControllerTest {

    private AccountRepository accountRepository;

    @Before
    public void setup () {
        accountRepository = new SimpleAccountRepository();
    }

    @Test
    public void shouldReturnAllAccountOfUser(){
        List<Account> actualAccounts = accountRepository.findAllAccountsByUserId(U123_UID) ;

        assertThat(2, equalTo(actualAccounts.size()));
    }

    @Test
    public void getAccountSummaryByAccountIdShouldReturnAccountNumber1AndBalance100WhenInputIs123() {
        AccountSummary actualResult = accountRepository.getAccountSummaryByAccountId(SimpleAccountRepository.Data.A123_ID);
        assertEquals(actualResult.getAccountNumber(), SimpleAccountRepository.Data.A123_ACCOUNTNO);
        assertEquals(actualResult.getBalance(), new BigDecimal(SimpleAccountRepository.Data.A123_INITIAL_BAL));
    }
}
