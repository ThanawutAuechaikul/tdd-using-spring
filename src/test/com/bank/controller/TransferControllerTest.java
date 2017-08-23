package com.bank.controller;

import com.bank.domain.InsufficientFundsException;
import com.bank.domain.TransferReceipt;
import com.bank.domain.TransferRequest;
import com.bank.repository.AccountRepository;
import com.bank.service.TransferService;
import com.bank.service.internal.InvalidTransferWindow;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalTime;

import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TransferControllerTest {

    public static final int AMOUNT = 15;
    public static final String SRC_ACCOUNT = "1234567891";
    public static final String DEST_ACCOUNT = "1234567897";
    public static final String REMARK = "This is Remark";

    @Mock
    private TransferService transferService;

    @InjectMocks
    private TransferController controller;

    @Autowired
    private AccountRepository accountRepo;

    private TransferRequest request;

    @Before
    public void setUp() throws InsufficientFundsException, InvalidTransferWindow {
        request = new TransferRequest();
        request.setAmount(AMOUNT);
        request.setSrcAccount(SRC_ACCOUNT);
        request.setDestAccount(DEST_ACCOUNT);
        request.setRemark(REMARK);
    }

    @Test
    public void verifyTransfer() throws InsufficientFundsException, InvalidTransferWindow {
        doReturn(new TransferReceipt(LocalTime.now())).when(transferService).transfer(anyDouble(), anyString(), anyString(), anyString());

        controller.transfer(request);
        verify(transferService).transfer(request.getAmount(), request.getSrcAccount(), request.getDestAccount(), request.getRemark());
    }

}