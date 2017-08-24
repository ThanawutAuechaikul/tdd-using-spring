package com.bank.service.internal;

import com.bank.domain.TransactionType;
import com.bank.domain.TransferReceipt;
import com.bank.repository.internal.TransactionRepository;
import com.bank.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class DefaultTransactionService implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public void createDefaultTransaction(String accountId, TransactionType transactionType, Double amount, Double balance, String remark) {
        String eventId = generateEventId();

        transactionRepository.insertTransaction(eventId, accountId, transactionType.name(), amount, balance, remark);
    }

    public static String generateEventId() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMddHHmmssSSS");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }

    @Override
    public String createTransferTransaction(TransferReceipt receipt) {
        String fromAccountId = receipt.getFinalSourceAccount().getId();
        String toAccountId = receipt.getFinalDestinationAccount().getId();
        Double amount = receipt.getTransferAmount();
        Double balanceFrom = receipt.getFinalSourceAccount().getBalance();
        Double balanceTo = receipt.getFinalDestinationAccount().getBalance();
        String remarkFrom = receipt.getSrcRemark();

        String eventId = generateEventId();

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
