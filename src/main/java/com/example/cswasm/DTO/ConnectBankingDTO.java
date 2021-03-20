package com.example.cswasm.DTO;

public class ConnectBankingDTO {
    private String accountNumber;
    private String accountPINNumber;
    private String partnerAccountNumber;
    private String partnerAccountPassword;
    private Integer step;

    public ConnectBankingDTO() {}

    public ConnectBankingDTO(String accountNumber, String accountPINNumber, String partnerAccountNumber, String partnerAccountPassword) {
        this.accountNumber = accountNumber;
        this.accountPINNumber = accountPINNumber;
        this.partnerAccountNumber = partnerAccountNumber;
        this.partnerAccountPassword = partnerAccountPassword;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountPINNumber() {
        return accountPINNumber;
    }

    public void setAccountPINNumber(String accountPINNumber) {
        this.accountPINNumber = accountPINNumber;
    }

    public String getPartnerAccountNumber() {
        return partnerAccountNumber;
    }

    public void setPartnerAccountNumber(String partnerAccountNumber) {
        this.partnerAccountNumber = partnerAccountNumber;
    }

    public String getPartnerAccountPassword() {
        return partnerAccountPassword;
    }

    public void setPartnerAccountPassword(String partnerAccountPassword) {
        this.partnerAccountPassword = partnerAccountPassword;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }
}
