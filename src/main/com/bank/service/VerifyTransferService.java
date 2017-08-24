package com.bank.service;

import com.bank.domain.InsufficientFundsException;
import com.bank.domain.TransferReceipt;
import com.bank.domain.TransferRequest;
import com.bank.service.internal.InvalidTransferWindow;

public interface VerifyTransferService {
    TransferReceipt verify(TransferRequest transferRequest) throws InsufficientFundsException, InvalidTransferWindow;
}
