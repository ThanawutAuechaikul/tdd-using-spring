package com.bank.controller;

import com.bank.dto.VerifyTransferRequestDTO;
import com.bank.dto.VerifyTransferResponseDTO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransferController {

    @RequestMapping(value = "/bank/verifyTransfer", method = RequestMethod.POST)
    public String verifyTransfer(@RequestBody VerifyTransferRequestDTO requestDTO) {
        VerifyTransferResponseDTO responseDTO;
        return "Greetings from Spring Boot!";
    }

}