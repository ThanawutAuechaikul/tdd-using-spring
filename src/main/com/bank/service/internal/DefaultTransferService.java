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
package com.bank.service.internal;

import static java.lang.String.format;

import com.bank.domain.*;
import com.bank.domain.VerifyTransferResponseDTO;
import org.springframework.transaction.annotation.Transactional;

import com.bank.repository.AccountRepository;
import com.bank.service.FeePolicy;
import com.bank.service.TransferService;

import java.time.LocalTime;

public class DefaultTransferService implements TransferService {

    private final AccountRepository accountRepository;
    private final FeePolicy feePolicy;
    private double minimumTransferAmount = 1.00;
    private LocalTimeWrapper localTimeWrapper;
    private DefaultTransferWindow defaultTransferWindow;

    public DefaultTransferService(AccountRepository accountRepository,
                                  FeePolicy feePolicy,
                                  LocalTimeWrapper localTimeWrapper,
                                  DefaultTransferWindow transferWindow) {
        this.accountRepository = accountRepository;
        this.feePolicy = feePolicy;
        this.localTimeWrapper = localTimeWrapper;
        this.defaultTransferWindow = transferWindow;
    }

    @Override
    public void setMinimumTransferAmount(double minimumTransferAmount) {
        this.minimumTransferAmount = minimumTransferAmount;
    }

    @Override
    @Transactional
    public TransferReceipt transfer(double amount, String srcAcctId, String dstAcctId)
            throws InsufficientFundsException, InvalidTransferWindow {

        LocalTime transactionTime = this.localTimeWrapper.getCurrentTime();
        checkEligibility(amount, transactionTime);

        Account srcAcct = accountRepository.findById(srcAcctId);
        Account dstAcct = accountRepository.findById(dstAcctId);

        double fee = feePolicy.calculateFee(amount);
        if (fee > 0) {
            srcAcct.debit(fee);
        }
        srcAcct.debit(amount);
        dstAcct.credit(amount);

        accountRepository.updateBalance(srcAcct);
        accountRepository.updateBalance(dstAcct);

        return getTransferReceipt(amount, transactionTime, srcAcct, dstAcct, fee);
    }

    private TransferReceipt getTransferReceipt(double amount, LocalTime transactionTime, Account srcAcct, Account dstAcct, double fee) {
        TransferReceipt receipt = new TransferReceipt(transactionTime);
        receipt.setInitialSourceAccount(srcAcct);
        receipt.setInitialDestinationAccount(dstAcct);
        receipt.setTransferAmount(amount);
        receipt.setFeeAmount(fee);
        receipt.setFinalSourceAccount(srcAcct);
        receipt.setFinalDestinationAccount(dstAcct);
        return receipt;
    }

    private void checkEligibility(double amount, LocalTime transactionTime) throws InvalidTransferWindow {
        if (amount < minimumTransferAmount) {
            throw new IllegalArgumentException(format("transfer amount must be at least $%.2f", minimumTransferAmount));
        }


        if(!defaultTransferWindow.isValidTimeForTransferMoney(transactionTime)) {
            throw new InvalidTransferWindow("We only allow to transfer between " + defaultTransferWindow.getOpen() + " and " + defaultTransferWindow.getClose());
        }
    }
}
