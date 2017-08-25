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
package com.bank.domain;

import java.util.Date;

import static java.lang.String.format;

public class TransferReceipt {

    private double transferAmount;
    private double feeAmount;
    private Account initialSourceAccountCopy;
    private Account initialDestinationAccountCopy;
    private Account finalSourceAccountCopy;
    private Account finalDestinationAccountCopy;

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }

    private Date transactionTime;
    private String srcRemark;
    private String eventId;
    private String desRemark;


    public TransferReceipt(Date transactionTime) {
        this.transactionTime = transactionTime;
    }

    public void setTransferAmount(double transferAmount) {
        this.transferAmount = transferAmount;
    }

    public double getTransferAmount() {
        return transferAmount;
    }

    public void setFeeAmount(double feeAmount) {
        this.feeAmount = feeAmount;
    }

    public double getFeeAmount() {
        return feeAmount;
    }

    public void setInitialSourceAccount(Account account) {
        initialSourceAccountCopy = Account.copy(account);
    }

    public void setFinalSourceAccount(Account account) {
        finalSourceAccountCopy = Account.copy(account);
    }

    public Account getFinalSourceAccount() {
        return finalSourceAccountCopy;
    }

    public void setInitialDestinationAccount(Account account) {
        initialDestinationAccountCopy = Account.copy(account);
    }

    public void setFinalDestinationAccount(Account account) {
        finalDestinationAccountCopy = Account.copy(account);
    }

    public Account getFinalDestinationAccount() {
        return finalDestinationAccountCopy;
    }

    public String getSrcRemark() {
        return srcRemark;
    }

    public void setSrcRemark(String srcRemark) {
        this.srcRemark = srcRemark;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getDesRemark() {
        return desRemark;
    }

    public void setDesRemark(String desRemark) {
        this.desRemark = desRemark;
    }

    @Override
    public String toString() {
        return "TransferReceipt{" +
                "transferAmount=" + transferAmount +
                ", feeAmount=" + feeAmount +
                ", initialSourceAccountCopy=" + initialSourceAccountCopy +
                ", initialDestinationAccountCopy=" + initialDestinationAccountCopy +
                ", finalSourceAccountCopy=" + finalSourceAccountCopy +
                ", finalDestinationAccountCopy=" + finalDestinationAccountCopy +
                ", transactionTime=" + transactionTime +
                ", srcRemark='" + srcRemark + '\'' +
                ", eventId='" + eventId + '\'' +
                ", desRemark='" + desRemark + '\'' +
                '}';
    }
}
