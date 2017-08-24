package com.bank.rest;

import com.bank.controller.AccountController;
import com.bank.domain.Account;
import com.bank.service.AccountService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class AccountTest {

    private MockMvc mockMvc;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(accountController)
                .build();
    }

    @Test
    public void itShouldReturnValidJsonWhenUserIdIsExist() throws Exception {

        final String USER_ID = "1";

        List accountList = new ArrayList<Account>();
        Account Account_1 = new Account("1", "123-456-789-1", "Clark Cent", 20.00);
        Account Account_2 = new Account("2", "456-789-123-1", "Jane Cent", 20.00);

        accountList.add(Account_1);
        accountList.add(Account_2);

        when(accountService.findAllAccountsByUserId(USER_ID)).thenReturn(accountList);

        mockMvc.perform(get("/user/{id}/accounts", USER_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("[0].id", equalTo(Account_1.getId())))
                .andExpect(jsonPath("[0].accountNumber", equalTo(Account_1.getAccountNumber())))
                .andExpect(jsonPath("[0].fullName", equalTo(Account_1.getFullName())))
                .andExpect(jsonPath("[0].balance", equalTo(Account_1.getBalance())))
                .andExpect(jsonPath("[1].id", equalTo(Account_2.getId())))
                .andExpect(jsonPath("[1].accountNumber", equalTo(Account_2.getAccountNumber())))
                .andExpect(jsonPath("[1].fullName", equalTo(Account_2.getFullName())))
                .andExpect(jsonPath("[1].balance", equalTo(Account_2.getBalance())));
    }

}
