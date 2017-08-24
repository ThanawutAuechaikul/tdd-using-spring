package com.bank.domain;

public class TransferResponse {
    private TransferReceipt transferReceipt;

    private VerifyTransfer transferSummary;
    private String status;
    private String errorMessage;

    public TransferResponse() {
        transferSummary = new VerifyTransfer();
    }

    public VerifyTransfer getTransferSummary() {
        return transferSummary;
    }

    public void setTransferSummary(TransferReceipt receipt) {
        this.transferSummary.setAmount(receipt.getTransferAmount());
        this.transferSummary.setFromAccount(receipt.getFinalSourceAccount());
        this.transferSummary.setToAccount(receipt.getFinalDestinationAccount());
        this.transferSummary.setFromRemark(receipt.getSrcRemark());
        this.transferSummary.setToRemark(receipt.getDesRemark());
        this.transferSummary.setBalance(receipt.getFinalSourceAccount().getBalance());
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
