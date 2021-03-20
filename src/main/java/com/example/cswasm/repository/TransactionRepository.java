package com.example.cswasm.repository;

import com.example.cswasm.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByToAccountNumberInAndCreatedAtAfterAndCreatedAtBefore(List<String> toAccountNumber, Date startDate, Date endDate);
    List<Transaction> findByFromAccountNumberAndCreatedAtAfterAndCreatedAtBefore(String fromAccount, Date startDate, Date endDate);
}
