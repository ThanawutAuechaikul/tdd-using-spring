package com.bank.service.internal;

import com.bank.domain.Account;
import com.bank.domain.InsufficientFundsException;
import com.bank.domain.TransferReceipt;
import com.bank.domain.VerifyTransferBean;
import com.bank.dto.VerifyTransferResponseDTO;
import com.bank.repository.AccountRepository;
import com.bank.repository.internal.JdbcAccountRepository;
import com.bank.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
public class VerifyTransferService implements TransferService{

    @Autowired
    DataSource dataSource;

    @Override
    public VerifyTransferResponseDTO verifyTransfer(VerifyTransferBean transferBean) {
        String accountNumber = transferBean.getToAccount().getAccountNumber();
        AccountRepository accountRep = new JdbcAccountRepository(dataSource);
        Account account = accountRep.findByAccountNumber(accountNumber);
        VerifyTransferResponseDTO responseDTO = new VerifyTransferResponseDTO();
        if (account != null) {
            responseDTO.setValid(true);
            responseDTO.setAmount(transferBean.getAmount());
            responseDTO.setFromRemark(transferBean.getFromRemark());
            responseDTO.setFromAccount(transferBean.getFromAccount());
            responseDTO.setToAccount(account);
        } else {
            responseDTO.setValid(false);
            responseDTO.setErrorMessage("Account Number does not exist.");
        }

        return responseDTO;
    }

    @Override
    public TransferReceipt transfer(double amount, String srcAcctId, String destAcctId) throws InsufficientFundsException, InvalidTransferWindow {
        return null;
    }

    @Override
    public void setMinimumTransferAmount(double minimumTransferAmount) {

    }
}
