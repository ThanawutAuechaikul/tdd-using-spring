package com.bank.service.internal;

import com.bank.domain.*;
import com.bank.repository.AccountNotFoundException;
import com.bank.repository.AccountRepository;
import com.bank.repository.internal.TransactionRepository;
import com.bank.service.FeePolicy;
import com.bank.service.TransferService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.time.LocalDateTime;

import static com.bank.repository.internal.SimpleAccountRepository.Data.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DefaultTransactionServiceTests {
    @Mock
    private TransactionRepository transactionRepository;
    @InjectMocks
    private TransactionService transactionService;

    @Test
    public void testLogTransaction() {
         String accountId = "1";
         TransactionType transactionType = TransactionType.TRANSFER;
         Double amount = 500.00;
         Double balance = 2000.00;
         String remark = "transfer 500 THB to A123";

        transactionService.createDefaultTransaction(accountId, transactionType, amount, balance, remark);
        verify(transactionRepository, times(1)).insertTransaction(isA(String.class), isA(LocalDateTime.class), eq(accountId), eq(transactionType), eq(amount), eq(balance), eq(remark));
    }

    @Test
    public void testLogTransferTransaction() {

        /*
         ID

        EVENT_ID J
                TRANSACTION_DATE
        ACCOUNT_ID
                TRANSACTION_TYPE
        AMOUNT
                BALANCE
        REMARK
*/
        String eventId = new Timestamp(System.currentTimeMillis()).toString();
        LocalDateTime transactionDateTime = LocalDateTime.now();
        String accountId = "1";
        TransactionType transactionType = TransactionType.TRANSFER;
        Double amount = 500.00;
        Double balance = 2000.00;
        String remark = "transfer 500 THB to A123";

      //  transactionService.createTransferTransaction(eventId, transactionDateTime, accountId, transactionType, amount, balance, remark);
      //  verify(transactionRepository, times(3)).insertTransaction(eventId, transactionDateTime, accountId, transactionType, amount, balance, remark);
    }

}
