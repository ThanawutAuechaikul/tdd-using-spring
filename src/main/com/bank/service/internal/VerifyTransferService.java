package com.bank.service.internal;

import com.bank.domain.Account;
import com.bank.domain.InsufficientFundsException;
import com.bank.domain.TransferReceipt;
import com.bank.repository.AccountRepository;
import com.bank.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalTime;

@Service
public class VerifyTransferService implements TransferService {

    @Autowired
    private AccountRepository accountRepo;

    @Override
    public TransferReceipt transfer(double amount, String srcAcctNo, String destAcctNo, String remark) throws InsufficientFundsException, InvalidTransferWindow {
        Account srcAccount = accountRepo.findByAccountNumber(srcAcctNo);
        Account desAccount = accountRepo.findByAccountNumber(destAcctNo);
        if (ObjectUtils.isEmpty(srcAccount)) {
            throw new InvalidTransferWindow("Account " + srcAcctNo + " does not exist.");
        } else if (ObjectUtils.isEmpty(desAccount)) {
            throw new InvalidTransferWindow("Account " + destAcctNo + " does not exist.");
        }
        if (srcAccount.getBalance() < amount) {
            throw new InsufficientFundsException(srcAccount, amount);
        }

        TransferReceipt transferReceipt = new TransferReceipt(LocalTime.now());
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

        return transferReceipt;
    }

    private void persistAccount(TransferReceipt transferReceipt) {
        accountRepo.updateBalance(transferReceipt.getFinalSourceAccount());
        accountRepo.updateBalance(transferReceipt.getFinalDestinationAccount());
    }

    @Override
    public void setMinimumTransferAmount(double minimumTransferAmount) {

    }
}
