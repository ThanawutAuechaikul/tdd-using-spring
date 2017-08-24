package com.bank.rest;

import com.bank.controller.TransferController;
import com.bank.domain.Account;
import com.bank.domain.TransferReceipt;
import com.bank.domain.TransferRequest;
import com.bank.domain.VerifyTransfer;
import com.bank.dto.TransferDTO;
import com.bank.service.TransferService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalTime;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TransferTest {

    public static final String REMARK = "Hey, test transfer service!";
    public static final String SRC_ACCOUNT = "4558874165";
    public static final String DEST_ACCOUNT = "4558874856";
    public static final double TRANSFER_AMOUNT = 20.00;
    private MockMvc mockMvc;

    @Mock
    private TransferService transferService;

    @Mock
    private TransferDTO dto;

    @InjectMocks
    private TransferController transferController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(transferController)
                .build();
    }

    @Test
    public void itShouldReturnValidJsonWhenVerifySuccess() throws Exception {

        Account Account_1 = new Account("1", "123-456-789-1", "Clark Cent", 20.00);
        Account Account_2 = new Account("2", "456-789-123-1", "Jane Cent", 20.00);

        TransferReceipt receipt = new TransferReceipt(LocalTime.now());
        receipt.setTransferAmount(TRANSFER_AMOUNT);
        receipt.setFinalSourceAccount(Account_1);
        receipt.setFinalDestinationAccount(Account_2);
        receipt.setSrcRemark(REMARK);

        TransferRequest request = new TransferRequest();
        request.setRemark(REMARK);
        request.setSrcAccount(SRC_ACCOUNT);
        request.setDestAccount(DEST_ACCOUNT);
        request.setAmount(TRANSFER_AMOUNT);

        VerifyTransfer verifyTransfer = new VerifyTransfer();
        verifyTransfer.setFromRemark(REMARK);
        verifyTransfer.setFromAccount(Account_1);
        verifyTransfer.setToAccount(Account_2);
        verifyTransfer.setAmount(TRANSFER_AMOUNT);
        verifyTransfer.setBalance(Account_1.getBalance());

        when(transferService.verify(request)).thenReturn(receipt);
        when(dto.transform(receipt)).thenReturn(verifyTransfer);

        mockMvc.perform(
                post("/verify").content("        {\n" +
                        "            \"srcAccount\": \"4558874165\",\n" +
                        "                \"destAccount\": \"4558874856\",\n" +
                        "                \"amount\": 20.00,\n" +
                        "                \"remark\": \"Hey, test transfer service!\"\n" +
                        "        }").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));

        verify(transferService).verify(eq(request));

    }

}
