package com.example.cswasm.DTO;

import java.util.Date;

public class PaymentAccountDTO {
    private String accountNumber;
    private String pinNumber;
    private Date startDate;
    private Date endDate;

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getPinNumber() {
        return pinNumber;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}
