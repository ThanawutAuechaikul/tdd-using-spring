package com.bank.service.internal;

import com.bank.domain.Account;
import com.bank.domain.InsufficientFundsException;
import com.bank.domain.TransferReceipt;
import com.bank.domain.TransferRequest;
import com.bank.repository.AccountRepository;
import com.bank.repository.internal.TransactionRepository;
import com.bank.service.TransactionService;
import com.bank.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalTime;
import java.util.Date;

@Service
public class TransferServiceImpl implements TransferService {

    @Autowired
    private AccountRepository accountRepo;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private DefaultTransactionService transactionService;

    @Override
    public TransferReceipt transfer(double amount, String srcAcctNo, String destAcctNo, String remark) throws InsufficientFundsException, InvalidTransferWindow {
        Account srcAccount = null;
        Account desAccount = null;
        TransferReceipt transferReceipt = null;

        srcAccount = getAccount(srcAcctNo);
        desAccount = getAccount(destAcctNo);
        checkSourceBalanceEnough(amount, srcAccount);

        transferReceipt = new TransferReceipt(new Date());
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

        String eventId = transactionService.createTransferTransaction(transferReceipt);
        transferReceipt.setEventId(eventId);

        return transferReceipt;
    }

    private void checkSourceBalanceEnough(double amount, Account srcAccount) throws InsufficientFundsException {
        if (srcAccount.getBalance() < amount) {
            throw new InsufficientFundsException(srcAccount, amount);
        }
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

        srcAccount = getAccount(transferRequest.getSrcAccount());
        desAccount = getAccount(transferRequest.getDestAccount());

        checkSourceBalanceEnough(amount, srcAccount);

        transferReceipt = new TransferReceipt(new Date());
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

    private Account getAccount(String accountNumber) throws InvalidTransferWindow {
        Account account;
        try {
            account = accountRepo.findByAccountNumber(accountNumber);
        } catch (Exception ex) {
            throw new InvalidTransferWindow("Account " + accountNumber + " does not exist.");
        }
        return account;
    }

    public void complete(String eventId, String remarkTo) {
        transactionRepository.updateTransferRemark(eventId, remarkTo);
    }
}
