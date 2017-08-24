package com.bank.service.internal;

import com.bank.domain.Account;
import com.bank.domain.InsufficientFundsException;
import com.bank.domain.TransferReceipt;
import com.bank.domain.TransferRequest;
import com.bank.repository.AccountRepository;
import com.bank.service.TransactionService;
import com.bank.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalTime;

@Service
public class VerifyTransferService implements TransferService {

    @Autowired
    private AccountRepository accountRepo;
    @Autowired
    private TransactionService transactionService;

    @Override
    public TransferReceipt transfer(double amount, String srcAcctNo, String destAcctNo, String remark) throws InsufficientFundsException, InvalidTransferWindow {
        Account srcAccount = null;
        Account desAccount = null;
        TransferReceipt transferReceipt = null;

        try {
            srcAccount = accountRepo.findByAccountNumber(srcAcctNo);
        } catch(Exception ex) {
            throw new InvalidTransferWindow("Account " + srcAcctNo + " does not exist.");
        }

        try {
            desAccount = accountRepo.findByAccountNumber(destAcctNo);
        } catch (Exception ex) {
            throw new InvalidTransferWindow("Account " + destAcctNo + " does not exist.");
        }


        if (srcAccount.getBalance() < amount) {
            throw new InsufficientFundsException(srcAccount, amount);
        }

        transferReceipt = new TransferReceipt(LocalTime.now());
        transferReceipt.setFeeAmount(0.00);
        transferReceipt.setTransferAmount(amount);
        transferReceipt.setInitialSourceAccount(srcAccount);
        transferReceipt.setSrcRemark(remark);

        srcAccount.setBalance(srcAccount.getBalance() - amount);
        transferReceipt.setFinalSourceAccount(srcAccount);

        transferReceipt.setInitialDestinationAccount(desAccount);

        desAccount.setBalance(desAccount.getBalance() + amount);
        transferReceipt.setFinalDestinationAccount(desAccount);
        persistAccount(transferReceipt);

        //transactionService.createTransferTransaction();

        return transferReceipt;
    }

    private void persistAccount(TransferReceipt transferReceipt) {
        accountRepo.updateBalance(transferReceipt.getFinalSourceAccount());
        accountRepo.updateBalance(transferReceipt.getFinalDestinationAccount());
    }

    @Override
    public void setMinimumTransferAmount(double minimumTransferAmount) {

    }

    @Override
    public TransferReceipt verify(TransferRequest transferRequest) throws InsufficientFundsException, InvalidTransferWindow {
        Account srcAccount = null;
        Account desAccount = null;
        TransferReceipt transferReceipt = null;
        double amount = transferRequest.getAmount();

        try {
            srcAccount = accountRepo.findByAccountNumber(transferRequest.getSrcAccount());
        } catch(Exception ex) {
            throw new InvalidTransferWindow("Account " + transferRequest.getSrcAccount() + " does not exist.");
        }

        try {
            desAccount = accountRepo.findByAccountNumber(transferRequest.getDestAccount());
        } catch (Exception ex) {
            throw new InvalidTransferWindow("Account " + transferRequest.getDestAccount() + " does not exist.");
        }


        if (srcAccount.getBalance() < amount) {
            throw new InsufficientFundsException(srcAccount, amount);
        }

        transferReceipt = new TransferReceipt(LocalTime.now());
        transferReceipt.setFeeAmount(0.00);
        transferReceipt.setTransferAmount(amount);
        transferReceipt.setInitialSourceAccount(srcAccount);
        transferReceipt.setSrcRemark(transferRequest.getRemark());

        srcAccount.setBalance(srcAccount.getBalance() - amount);
        transferReceipt.setFinalSourceAccount(srcAccount);

        transferReceipt.setInitialDestinationAccount(desAccount);

        desAccount.setBalance(desAccount.getBalance() + amount);
        transferReceipt.setFinalDestinationAccount(desAccount);

        return transferReceipt;
    }
}
