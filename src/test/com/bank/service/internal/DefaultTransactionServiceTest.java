package com.bank.service.internal;

import com.bank.domain.*;
import com.bank.repository.internal.TransactionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DefaultTransactionServiceTest {
    @Mock
    private TransactionRepository transactionRepository;
    @InjectMocks
    private DefaultTransactionService defaultTransactionService;

    @Test
    public void testLogTransaction() {
         String accountId = "1";
         TransactionType transactionType = TransactionType.TRANSFER;
         Double amount = 500.00;
         Double balance = 2000.00;
         String remark = "transfer 500 THB to A123";

        defaultTransactionService.createDefaultTransaction(accountId, transactionType, amount, balance, remark);
        verify(transactionRepository).insertTransaction(isA(String.class), eq(accountId), eq(transactionType.name()), eq(amount), eq(balance), eq(remark));
    }

    @Test
    public void testLogTransferTransaction() {
        String fromAccountId = "1";
        String toAccountId = "2";
        TransactionType transactionType = TransactionType.TRANSFER;
        Double amount = 400.00;
        Double balanceFrom = 2000.00;
        Double balanceTo = 3000.00;
        String remarkFrom = "transfer 400 THB to A123";

        defaultTransactionService.createTransferTransaction(fromAccountId, toAccountId, amount, balanceFrom, balanceTo, remarkFrom);
        verify(transactionRepository).insertTransaction(isA(String.class), eq(fromAccountId), eq(TransactionType.TRANSFER.name()), eq(amount), eq(balanceFrom), eq(remarkFrom));
        verify(transactionRepository).insertTransaction(isA(String.class), eq(toAccountId), eq(TransactionType.DEPOSIT.name()), eq(amount), eq(balanceTo), eq(""));
        verify(transactionRepository).insertTransaction(isA(String.class), eq(fromAccountId), eq(TransactionType.WITHDRAW.name()), eq(amount), eq(balanceFrom), eq("Transfer Fee"));
    }

}
