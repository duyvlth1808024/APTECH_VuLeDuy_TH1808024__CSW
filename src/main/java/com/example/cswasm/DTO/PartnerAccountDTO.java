package com.example.cswasm.DTO;

import java.util.List;

public class PartnerAccountDTO {
    private Long accountId;
    private String accountNumber;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public PartnerAccountDTO(Long accountId, String accountNumber) {
        this.accountId = accountId;
        this.accountNumber = accountNumber;
    }
}
