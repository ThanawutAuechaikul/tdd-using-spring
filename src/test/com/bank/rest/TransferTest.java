package com.bank.rest;

import com.bank.controller.TransferController;
import com.bank.domain.*;
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
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TransferTest {

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

//        TransferReceipt receipt = new TransferReceipt(LocalTime.now());
//        receipt.setTransferAmount(5.00);
//        receipt.setFinalSourceAccount();
//        receipt.setFinalDestinationAccount();
//        receipt.setSrcRemark();

        TransferRequest request = new TransferRequest();
        request.setRemark("Hey, test transfer service!");
        request.setSrcAccount("4558874165");
        request.setDestAccount("4558874856");
        request.setAmount(20.0);

        when(transferService.verify(any(TransferRequest.class))).thenReturn(new TransferReceipt(LocalTime.now()));
        when(dto.transform(any(TransferReceipt.class))).thenReturn(new VerifyTransfer());

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
