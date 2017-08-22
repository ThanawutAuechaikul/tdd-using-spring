/*
 * Copyright 2002-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bank.repository.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bank.domain.Account;
import com.bank.repository.AccountNotFoundException;
import com.bank.repository.AccountRepository;

public class SimpleAccountRepository implements AccountRepository {

    public static class Data {
        public static final String U123_UID = "123";

        public static final String A123_ID = "A123";
        public static final String A123_ACCOUNTNO = "1234567890";
        public static final String A123_FULLNAME =  "John Smith";

        public static final String C456_ID = "C456";
        public static final String C456_ACCOUNTNO = "1234567800";
        public static final String C456_FULLNAME =  "John Smith";

        public static final String Z999_ID = "Z999"; // NON EXISTENT ID
        public static final double A123_INITIAL_BAL = 100.00;
        public static final double C456_INITIAL_BAL = 0.00;
    }
    @SuppressWarnings("serial")
    private final Map<String, Account> accountsById = new HashMap<String, Account>() {

        {
            put(Data.A123_ID, new Account(Data.A123_ID, Data.A123_ACCOUNTNO, Data.A123_FULLNAME, Data.A123_INITIAL_BAL));
            put(Data.C456_ID, new Account(Data.C456_ID, Data.C456_ACCOUNTNO, Data.C456_FULLNAME, Data.C456_INITIAL_BAL));
        }
    };

    @Override
    public Account findById(String acctId) {
        return Account.copy(nullSafeAccountLookup(acctId));
    }

    @Override
    public void updateBalance(Account account) {
        Account actualAccount = nullSafeAccountLookup(account.getId());
        actualAccount.setBalance(account.getBalance());
    }

    @Override
    public List<Account> findAllAccountsByUserId(String userId) {
        if (userId.equals(Data.U123_UID)) {
            ArrayList<Account> accounts = new ArrayList<>();
            accounts.add(new Account(Data.A123_ID, Data.A123_ACCOUNTNO, Data.A123_FULLNAME, Data.A123_INITIAL_BAL));
            accounts.add(new Account(Data.C456_ID, Data.C456_ACCOUNTNO, Data.C456_FULLNAME, Data.C456_INITIAL_BAL));
            return accounts;
        }
        else {
            return new ArrayList<>();
        }
    }

    private Account nullSafeAccountLookup(String acctId) {
        Account account = accountsById.get(acctId);
        if (account == null) {
            throw new AccountNotFoundException(acctId);
        }
        return account;
    }

}
