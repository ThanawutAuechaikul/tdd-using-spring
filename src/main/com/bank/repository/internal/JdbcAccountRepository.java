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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.bank.domain.Account;
import com.bank.repository.AccountRepository;

public class JdbcAccountRepository implements AccountRepository {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void updateBalance(Account dstAcct) {
        jdbcTemplate.update("update account set balance = ? where id = ?", dstAcct.getBalance(), dstAcct.getId());
    }

    @Override
    public Account findById(String srcAcctId) {
        Account result = jdbcTemplate.queryForObject(
                "SELECT * FROM Account where ID = ?",
                (rs, rowNum) -> new Account(rs.getString("ID"), rs.getString("ACCOUNT_NUMBER"),
                        rs.getString("NAME"), rs.getDouble("BALANCE"))
                , srcAcctId);

        return result;

    }

    @Override
    public List<Account> findAllAccountsByUserId(String userId) {
        List<Account> result = jdbcTemplate.query(
                "SELECT * FROM ACCOUNT where USER_ID = ?",
                (rs, rowNum) -> new Account(rs.getString("ID"),  rs.getString("ACCOUNT_NUMBER"),
                        rs.getString("NAME"), rs.getDouble("BALANCE"))
        , userId);

        return result;

    }


}