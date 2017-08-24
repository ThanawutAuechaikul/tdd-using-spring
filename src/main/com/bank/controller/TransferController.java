package com.bank.controller;

import com.bank.domain.InsufficientFundsException;
import com.bank.domain.TransactionType;
import com.bank.domain.TransferReceipt;
import com.bank.domain.TransferRequest;
import com.bank.service.TransactionService;
import com.bank.service.TransferService;
import com.bank.service.internal.InvalidTransferWindow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class TransferController {

    @Autowired
    TransferService transferService;

    @Autowired
    TransactionService transactionService;

    @RequestMapping(value = "/transfer", method = RequestMethod.POST, produces = "application/json")
    public TransferReceipt verifyTransfer(@RequestBody TransferRequest request) throws InsufficientFundsException, InvalidTransferWindow {

        return transferService.transfer(request.getAmount(), request.getSrcAccount(), request.getDestAccount(), request.getRemark());
    }
}