package com.example.cswasm.repository;

import com.example.cswasm.entity.Account;
import com.example.cswasm.entity.Partner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Boolean existsAccountByAccountNumber(String accountNumber);
    Account findByAccountNumber(String accountNumber);
    List<Account> findByPartner(Partner partner);
}
