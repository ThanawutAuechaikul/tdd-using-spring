package com.bank.domain;

public class TransferResponse {
    private TransferReceipt transferReceipt;
    private String status;
    private String errorMessage;

    public TransferReceipt getTransferReceipt() {
        return transferReceipt;
    }

    public void setTransferReceipt(TransferReceipt transferReceipt) {
        this.transferReceipt = transferReceipt;
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
