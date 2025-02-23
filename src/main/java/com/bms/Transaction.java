package com.bms;

import java.math.BigDecimal;

public class Transaction {
    private String transactionId;
    private String customerName;
    private String accountId;
    private String aadhaarCardNo;
    private String date;
    private String modeOfTransaction;
    private BigDecimal amountOfTransaction;
    private String creditDebit;

    // Getters and setters for each field
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAadhaarCardNo() {
        return aadhaarCardNo;
    }

    public void setAadhaarCardNo(String aadhaarCardNo) {
        this.aadhaarCardNo = aadhaarCardNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getModeOfTransaction() {
        return modeOfTransaction;
    }

    public void setModeOfTransaction(String modeOfTransaction) {
        this.modeOfTransaction = modeOfTransaction;
    }

    public BigDecimal getAmountOfTransaction() {
        return amountOfTransaction;
    }

    public void setAmountOfTransaction(BigDecimal amountOfTransaction) {
        this.amountOfTransaction = amountOfTransaction;
    }

    public String getCreditDebit() {
        return creditDebit;
    }

    public void setCreditDebit(String creditDebit) {
        this.creditDebit = creditDebit;
    }
}