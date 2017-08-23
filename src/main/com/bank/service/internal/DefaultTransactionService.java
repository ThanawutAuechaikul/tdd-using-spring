package com.bank.service.internal;

import com.bank.domain.TransactionType;
import com.bank.repository.internal.TransactionRepository;
import com.bank.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class DefaultTransactionService implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public void createDefaultTransaction(String accountId, TransactionType transactionType, Double amount, Double balance, String remark) {
        String eventId = getEventId();

        transactionRepository.insertTransaction(eventId, accountId, transactionType.name(), amount, balance, remark);
    }

    public static String getEventId() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMddHHmmssSSS");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }

    @Override
    public String createTransferTransaction(String fromAccountId, String toAccountId, Double amount, Double balanceFrom, Double balanceTo, String remarkFrom) {
        String eventId = getEventId();

        transactionRepository.insertTransaction(eventId, fromAccountId, TransactionType.TRANSFER.name(), amount, balanceFrom, remarkFrom);
        transactionRepository.insertTransaction(eventId, toAccountId, TransactionType.DEPOSIT.name(), amount, balanceTo, "");
        transactionRepository.insertTransaction(eventId, fromAccountId, TransactionType.WITHDRAW.name(), amount, balanceFrom, "Transfer Fee");

        return eventId;
    }

    @Override
    public void updateTransferTransaction(String eventId, String remarkTo) {
        transactionRepository.updateTransferRemark(eventId, remarkTo);
    }

}
