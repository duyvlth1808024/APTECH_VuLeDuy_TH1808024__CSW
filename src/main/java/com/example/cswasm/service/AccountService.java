package com.example.cswasm.service;

import com.example.cswasm.entity.Account;
import com.example.cswasm.entity.Partner;
import com.example.cswasm.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public boolean isAccountNumberExisted(String accountNumber) {
        System.out.println("accountNumber " + accountNumber);
        return accountRepository.existsAccountByAccountNumber(accountNumber);
    }

    public Account create(Account account) {
        return accountRepository.save(account);
    }

    public Account get(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }

    public void createConnect(Account account, Partner partner) {
        account.setPartner(partner);
        accountRepository.save(account);
    }

    public List<Account> accounts(Partner partner) {
        return accountRepository.findByPartner(partner);
    }
}
