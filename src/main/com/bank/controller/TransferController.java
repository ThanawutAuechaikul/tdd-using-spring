package com.bank.controller;

import com.bank.domain.InsufficientFundsException;
import com.bank.domain.TransferReceipt;
import com.bank.domain.TransferRequest;
import com.bank.service.TransferService;
import com.bank.service.internal.InvalidTransferWindow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransferController {

    @Autowired
    TransferService transferService;

    @RequestMapping(value = "/transfer", method = RequestMethod.POST, produces = "application/json")
    public TransferReceipt verifyTransfer(@RequestBody TransferRequest request) throws InsufficientFundsException, InvalidTransferWindow {

        return transferService.transfer(request.getAmount(), request.getSrcAccount(), request.getDestAccount(), request.getRemark());
    }

}