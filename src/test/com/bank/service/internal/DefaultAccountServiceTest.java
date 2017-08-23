package com.bank.service.internal;

import com.bank.domain.Account;
import com.bank.repository.internal.JdbcAccountRepository;
import com.bank.service.AccountService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultAccountServiceTest {

    @Mock
    JdbcAccountRepository jdbcAccountRepository;

    @InjectMocks
    private DefaultAccountService accountService;

    List<Account> accountList = new ArrayList<Account>();

    @Before
    public void setUp() {
        accountList.add(new Account("1", "1234567890", "John Smith1", 500.00));
        accountList.add(new Account("2", "1234567800", "John Smith2", 1000.00));
    }

    @Test
    public void testFindAccountByUserId() {
        when(jdbcAccountRepository.findAllAccountsByUserId(anyString())).thenReturn(accountList);
        List<Account> actualAccounts = accountService.findAllAccountsByUserId("1");
        Assert.assertEquals(actualAccounts, accountList);
        Assert.assertEquals(actualAccounts.get(1).getId(), "2");
    }
}
