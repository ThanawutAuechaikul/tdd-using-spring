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
package com.bank.config.xml;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import com.bank.domain.DefaultTransferWindow;
import com.bank.domain.LocalTimeWrapper;
import com.bank.repository.internal.SimpleAccountRepository;
import com.bank.service.FeePolicy;
import com.bank.service.internal.DefaultTransferService;
import com.bank.service.internal.InvalidTransferWindow;
import com.bank.service.internal.ZeroFeePolicy;
import org.junit.Test;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.bank.domain.InsufficientFundsException;
import com.bank.repository.AccountRepository;
import com.bank.service.TransferService;

public class IntegrationTests {

    @Test
    public void transferTenDollars() throws InsufficientFundsException, InvalidTransferWindow {
        AccountRepository accountRepository = new SimpleAccountRepository();
        FeePolicy feePolicy = new ZeroFeePolicy();
        LocalTimeWrapper localTimeWrapper = new LocalTimeWrapper();
        DefaultTransferWindow defaultTransferWindow = new DefaultTransferWindow("06:00:00", "22:00:00");


        TransferService transferService = new DefaultTransferService(accountRepository, feePolicy, localTimeWrapper, defaultTransferWindow);

        assertThat(accountRepository.findById("A123").getBalance(), equalTo(100.00));
        assertThat(accountRepository.findById("C456").getBalance(), equalTo(0.00));

        transferService.transfer(10.00, "A123", "C456");

        assertThat(accountRepository.findById("A123").getBalance(), equalTo(90.00));
        assertThat(accountRepository.findById("C456").getBalance(), equalTo(10.00));

    }
}
