package com.example.cswasm.service;

import com.example.cswasm.DTO.PaymentDTO;
import com.example.cswasm.entity.Transaction;
import com.example.cswasm.repository.TransactionRepository;
import com.example.cswasm.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public void create(PaymentDTO paymentDTO) {
        Transaction transaction = new Transaction();
        transaction.setBillCode(paymentDTO.getBillCode());
        transaction.setAmount(paymentDTO.getAmount());
        transaction.setTypeTransaction(paymentDTO.getTypeTransaction());
        transaction.setFromAccountNumber(paymentDTO.getAccountPaymentNumber());
        transaction.setToAccountNumber(paymentDTO.getAccountNumber());
        transaction.setFees((float) billFeeCalculator(paymentDTO.getAmount()));
        transactionRepository.save(transaction);
    }

    private double billFeeCalculator(Float amount) {
        if(amount > 5000000) {
            return calcPercent(amount, 0.5);
        }
        if(amount > 1000000 && amount <= 5000000) {
            return calcPercent(amount, 1.0);
        }
        if(amount > 500000 && amount <= 1000000) {
            return calcPercent(amount, 1.5);
        }
        if(amount > 100000 && amount <= 500000) {
            return calcPercent(amount, 2);
        }
        return 10000;
    }

    public double calcPercent(Float amount, double percent) {
        return (amount / 100) * percent;
    }

    public List<Transaction> findByFromAccountNumber(String fromAccountNumber, Date startDate, Date endDate) {
        return transactionRepository
                .findByFromAccountNumberAndCreatedAtAfterAndCreatedAtBefore(
                        fromAccountNumber,
                        DateTimeUtil.getStartOfDate(startDate),
                        DateTimeUtil.getStartOfDate(DateTimeUtil.addDate(endDate, 1))
                );
    }

    public List<Transaction> findByToAccountNumbers(List<String> toAccountNumbers, Date startDate, Date endDate) {
        return transactionRepository.findByToAccountNumberInAndCreatedAtAfterAndCreatedAtBefore(
                toAccountNumbers,
                DateTimeUtil.getStartOfDate(startDate),
                DateTimeUtil.getStartOfDate(DateTimeUtil.addDate(endDate, 1))
        );
    }
}
