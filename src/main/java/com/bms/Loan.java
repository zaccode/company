package com.bms;
//CREATE TABLE LoanRequest (
//	    ReqId varchar(20) PRIMARY KEY,
//	    SSNID INT NOT NULL,
//	    Customer_Name VARCHAR(100) NOT NULL,
//	    Account_Number VARCHAR(20) NOT NULL,
//	    Aadhar_Number VARCHAR(12) NOT NULL,
//	    Message TEXT NOT NULL,
//	    BankMessage TEXT DEFAULT 'Your loan request is under review.',
//	    Created_At TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
//	    Status VARCHAR(20) DEFAULT 'Pending',
//	    Amount DECIMAL(10, 2) NOT NULL,
//	    FOREIGN KEY (Account_Number) REFERENCES Customer(Account_Number)
//	);

public class Loan {
	private String  ReqId;
	
	private String  SSNID;
	private String Customer_Name;
	private String Account_Number;
	private String Aadhar_Number;
	private String Created_At;
	private String Amount;
	private String Message;
	private String BankMessage;
	private String Status;
	public String getReqId() {
		return ReqId;
	}
	public void setReqId(String reqId) {
		ReqId = reqId;
	}
	public String getSSNID() {
		return SSNID;
	}
	public void setSSNID(String SSNID) {
		SSNID = SSNID;
	}
	public String getCustomer_Name() {
		return Customer_Name;
	}
	public void setCustomer_Name(String customer_Name) {
		Customer_Name = customer_Name;
	}
	public String getAccount_Number() {
		return Account_Number;
	}
	public void setAccount_Number(String account_Number) {
		Account_Number = account_Number;
	}
	public String getAadhar_Number() {
		return Aadhar_Number;
	}
	public void setAadhar_Number(String aadhar_Number) {
		Aadhar_Number = aadhar_Number;
	}
	public String getCreated_At() {
		return Created_At;
	}
	public void setCreated_At(String created_At) {
		Created_At = created_At;
	}
	public String getAmount() {
		return Amount;
	}
	public void setAmount(String amount) {
		Amount = amount;
	}

	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}
	
	
	
	public String getBankMessage() {
		return BankMessage;
	}
	public void setBankMessage(String bankMessage) {
		BankMessage = bankMessage;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	//used at the time fetched the data
	public Loan(String sSNID, String customer_Name, String account_Number, String aadhar_Number) {
		super();
		SSNID = sSNID;
		Customer_Name = customer_Name;
		Account_Number = account_Number;
		Aadhar_Number = aadhar_Number;
	}
	
	//used at the time customer fill the data
	public Loan(String sSNID, String customer_Name, String account_Number, String aadhar_Number, String amount,
			String message) {
		super();
		SSNID = sSNID;
		Customer_Name = customer_Name;
		Account_Number = account_Number;
		Aadhar_Number = aadhar_Number;
		Amount = amount;
		Message = message;
	}
	//used at the time Bank changes the data
	public Loan(String sSNID, String customer_Name, String account_Number, String aadhar_Number,
			String amount, String message, String bankMessage, String status) {
		super();
		SSNID = sSNID;
		Customer_Name = customer_Name;
		Account_Number = account_Number;
		Aadhar_Number = aadhar_Number;
//		Created_At = created_At;
		Amount = amount;
		Message = message;
		BankMessage = bankMessage;
		Status = status;
	}
	public Loan(String reqId, String sSNID, String customer_Name, String account_Number, String aadhar_Number,
			String created_At, String amount, String message, String bankMessage, String status) {
		super();
		ReqId = reqId;
		SSNID = sSNID;
		Customer_Name = customer_Name;
		Account_Number = account_Number;
		Aadhar_Number = aadhar_Number;
		Created_At = created_At;
		Amount = amount;
		Message = message;
		BankMessage = bankMessage;
		Status = status;
	}
	
	
	
}