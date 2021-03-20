package com.example.cswasm.DTO;

import com.example.cswasm.entity.Transaction;

public class PaymentDTO {
    private String accountNumber;
    private String accountPaymentNumber;
    private String accountPaymentPIN;
    private String billCode;
    private Float amount;
    private Transaction.TypeTransaction typeTransaction;

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountPaymentNumber() {
        return accountPaymentNumber;
    }

    public String getAccountPaymentPIN() {
        return accountPaymentPIN;
    }

    public String getBillCode() {
        return billCode;
    }

    public Float getAmount() {
        return amount;
    }

    public Transaction.TypeTransaction getTypeTransaction() {
        return typeTransaction;
    }
}
