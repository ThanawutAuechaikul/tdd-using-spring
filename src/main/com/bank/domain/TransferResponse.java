package com.bank.domain;

public class TransferResponse {
    private VerifyTransfer transferSummary;
    private String status;
    private String errorMessage;

    public TransferResponse() {

    }

    public VerifyTransfer getTransferSummary() {
        return transferSummary;
    }

    public void setTransferSummary(VerifyTransfer transferSummary) {
        this.transferSummary = transferSummary;
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
