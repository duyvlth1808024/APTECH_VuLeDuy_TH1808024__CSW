package com.example.cswasm.DTO;

import java.util.Date;

public class PaymentPartnerDTO {
    private String accountNumber;
    private String password;
    private Date startDate;
    private Date endDate;

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getPassword() {
        return password;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}
