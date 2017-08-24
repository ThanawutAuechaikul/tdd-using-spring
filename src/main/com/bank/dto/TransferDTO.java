package com.bank.dto;

import com.bank.domain.TransferReceipt;
import com.bank.domain.VerifyTransfer;
import org.springframework.stereotype.Component;

@Component
public class TransferDTO {
    public VerifyTransfer transform(TransferReceipt receipt) {
        VerifyTransfer transferSummary = new VerifyTransfer();
        transferSummary.setAmount(receipt.getTransferAmount());
        transferSummary.setFromAccount(receipt.getFinalSourceAccount());
        transferSummary.setToAccount(receipt.getFinalDestinationAccount());
        transferSummary.setFromRemark(receipt.getSrcRemark());
        transferSummary.setToRemark(receipt.getDesRemark());
        transferSummary.setBalance(receipt.getFinalSourceAccount().getBalance());
        return transferSummary;
    }
}
