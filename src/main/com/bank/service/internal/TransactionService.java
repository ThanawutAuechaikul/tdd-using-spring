package com.bank.service.internal;

import com.bank.domain.TransactionType;
import com.bank.repository.internal.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    public void createDefaultTransaction(String accountId, TransactionType transactionType, Double amount, Double balance, String remark) {
        String eventId = new Timestamp(System.currentTimeMillis()).toString();
        LocalDateTime transactionDateTime = LocalDateTime.now();

        transactionRepository.insertTransaction(eventId, transactionDateTime, accountId, transactionType, amount, balance, remark);
    }

    public void createTransferTransaction(String fromAccountId, String toAccountId, Double amount, Double balanceFrom, Double balanceTo, String remarkFrom) {
        String eventId = new Timestamp(System.currentTimeMillis()).toString();
        LocalDateTime transactionDateTime = LocalDateTime.now();

        transactionRepository.insertTransaction(eventId, transactionDateTime, fromAccountId, TransactionType.TRANSFER, amount, balanceFrom, remarkFrom);
        transactionRepository.insertTransaction(eventId, transactionDateTime, toAccountId, TransactionType.DEPOSIT, amount, balanceTo, "");
        transactionRepository.insertTransaction(eventId, transactionDateTime, toAccountId, TransactionType.WITHDRAW, amount, balanceTo, "Transfer Fee");
    }
}
