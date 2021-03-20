package com.example.cswasm.entity;

import lombok.Data;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class Transaction {
    public enum TypeTransaction {
        RECEIVER,
        TRANSFER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    private Long id;

    private String billCode;
    private Float amount;
    private TypeTransaction typeTransaction;
    private Float fees;
    private String fromAccountNumber;
    private String toAccountNumber;
    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;

    public Transaction() {
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public void setTypeTransaction(TypeTransaction typeTransaction) {
        this.typeTransaction = typeTransaction;
    }

    public void setFees(Float fees) {
        this.fees = fees;
    }

    public void setFromAccountNumber(String fromAccountNumber) {
        this.fromAccountNumber = fromAccountNumber;
    }

    public void setToAccountNumber(String toAccountNumber) {
        this.toAccountNumber = toAccountNumber;
    }
}
