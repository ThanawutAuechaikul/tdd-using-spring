package com.bank.controller;

import com.bank.com.bank.constant.Constant;
import com.bank.domain.*;
import com.bank.dto.TransferDTO;
import com.bank.service.TransferService;
import com.bank.service.internal.InvalidTransferWindow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class TransferController {

    @Autowired
    TransferDTO dto;

    @Autowired
    TransferService transferService;

    @RequestMapping(value = "/transfer", method = RequestMethod.POST, produces = "application/json")
    public TransferResponse transfer(@RequestBody TransferRequest request) throws InsufficientFundsException, InvalidTransferWindow {
        TransferResponse response = new TransferResponse();

        try {
            TransferReceipt receipt = transferService.transfer(request.getAmount(), request.getSrcAccount(), request.getDestAccount(), request.getRemark());
            response.setStatus(Constant.SUCCESS);
            response.setTransferSummary(dto.transform(receipt));
        } catch (Exception ex) {
            response.setStatus(Constant.FAILED);
            response.setErrorMessage(ex.getMessage());
        }
        return response;
    }

    @RequestMapping(value = "/verify", method = RequestMethod.POST, produces = "application/json")
    public TransferResponse verify(@RequestBody TransferRequest request) {
        TransferResponse response = new TransferResponse();

        try {
            TransferReceipt receipt = transferService.verify(request);
            response.setStatus(Constant.SUCCESS);
            response.setTransferSummary(dto.transform(receipt));
        } catch (Exception ex) {
            response.setStatus(Constant.FAILED);
            response.setErrorMessage(ex.getMessage());
        }
        return response;
    }

    @RequestMapping(value = "/complete", method = RequestMethod.POST, produces = "application/json")
    public TransferResponse complete(@RequestBody CompleteRequest request) {
        transferService.complete(request.getEventId(), request.getRemarkTo());
        TransferResponse response = new TransferResponse();
        response.setStatus(Constant.SUCCESS);
        return response;
    }
}