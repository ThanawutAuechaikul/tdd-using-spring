package com.bank.service;

import com.bank.domain.Account;

import java.util.List;

public interface AccountService {
    List<Account> findAllAccountsByUserId(String userId);
}
