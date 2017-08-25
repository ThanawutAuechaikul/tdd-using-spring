package com.bank.service.internal;

import com.bank.domain.*;
import com.bank.repository.AccountNotFoundException;
import com.bank.repository.AccountRepository;
import com.bank.repository.internal.TransactionRepository;
import com.bank.service.FeePolicy;
import com.bank.service.TransactionService;
import com.bank.service.TransferService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.bank.repository.internal.SimpleAccountRepository.Data.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DefaultTransactionServiceTest {
    @Mock
    private TransactionRepository transactionRepository;
    @InjectMocks
    private DefaultTransactionService transactionService;

    private TransferReceipt receipt;

    Double trasnsferAmount;
    String remarkFrom;
    Double fee;

    String fromAccountId;
    String fromAccountNumber;
    String fromName;
    Double fromBalance;

    String toAccountId;
    String toAccountNumber;
    String toName;
    Double toBalance;

    Account srcAccount;
    Account destAccount;

    @Before
    public void setUp() {
        trasnsferAmount = 400.00;
        remarkFrom = "Transfer";
        fee = 0.00;

        fromAccountId = "1";
        fromAccountNumber = "1234567890";
        fromName = "John Smith";
        fromBalance = 2000.00;

        toAccountId = "2";
        toAccountNumber = "1234567800";
        toName = "Tom Smith";
        toBalance = 1000.00;

        receipt = new TransferReceipt(new Date());
        srcAccount = new Account(fromAccountId, fromAccountNumber, fromName, fromBalance);
        destAccount = new Account(toAccountId, toAccountNumber, toName, toBalance);
        receipt.setFinalSourceAccount(srcAccount);
        receipt.setFinalDestinationAccount(destAccount);
        receipt.setTransferAmount(trasnsferAmount);
        receipt.setSrcRemark(remarkFrom);
        receipt.setFeeAmount(fee);
    }

    @Test
    public void testCreateDefaultTransaction() {
        transactionService.createDefaultTransaction(fromAccountId, TransactionType.WITHDRAW, trasnsferAmount, fromBalance, remarkFrom);
        verify(transactionRepository).insertTransaction(anyString(), eq(fromAccountId), eq(TransactionType.WITHDRAW.name()), eq(trasnsferAmount), eq(fromBalance), eq(remarkFrom));
    }

    @Test
    public void testCreateTransferTransaction() {
        transactionService.createTransferTransaction(receipt);
        verify(transactionRepository).insertTransaction(anyString(), eq(fromAccountId), eq(TransactionType.TRANSFER.name()), eq(trasnsferAmount), eq(fromBalance), eq(remarkFrom));
        verify(transactionRepository).insertTransaction(anyString(), eq(toAccountId), eq(TransactionType.DEPOSIT.name()), eq(trasnsferAmount), eq(toBalance), anyString());
        verify(transactionRepository).insertTransaction(anyString(), eq(fromAccountId), eq(TransactionType.WITHDRAW.name()), eq(fee), eq(fromBalance), anyString());
    }

    @Test
    public void testUpdateTransferTransaction() {
        String remarkTo = "transfer already";
        String eventId = "20170823161515133";

        transactionService.updateTransferTransaction(eventId, remarkTo);
        verify(transactionRepository).updateTransferRemark(eq(eventId), eq(remarkTo));
    }

}
