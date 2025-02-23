package com.bms;

public class Customer {

    private int customerSSNId;
    private String customerName;
    private String customerFirstName;
    private String customerLastName;
    private String email;
    private String password;
    private String dateOfBirth;
    private String address;
    private String contactNumber;
    private String aadharNumber;
    private String panNumber;
    private int accountBalance;
    private String accountNumber;

    public Customer(int customerSSNId, String customerName, String email, String password,String dateOfBirth, String address, 
                    String contactNumber, String aadharNumber, String panNumber, int accountBalance,String accountNumber) {
        setCustomerSSNId(customerSSNId);
        setCustomerName(customerName);
        setEmail(email);
        setDateOfBirth(dateOfBirth);
        setPassword(password); 
        setAddress(address);
        setContactNumber(contactNumber);
        setAadharNumber(aadharNumber);
        setPanNumber(panNumber);
        setAccountBalance(accountBalance);
        setAccountNumber(accountNumber);
    }	

    public Customer(String newCustomerName, String newCustomerEmail, String newPassword, 
                    String newCustomerAddress, String newCustomerContactNumber) {
        setCustomerName(newCustomerName);
        setEmail(newCustomerEmail);
        setPassword(newPassword); 
        setAddress(newCustomerAddress);
        setContactNumber(newCustomerContactNumber);
    }

    public Customer(String newCustomerName, String newEmail, String newAddress, String newContactNumber) {
        setCustomerName(newCustomerName);
        setEmail(newEmail);
        setAddress(newAddress);
        setContactNumber(newContactNumber);
    }
    
    public Customer(int ssnId, String firstName, String accountNumber, int accountBalance, String aadharNumber,
			String panNumber, String dob, String email, String address, String contactNumber) {
		// TODO Auto-generated constructor stub
    	setCustomerSSNId(ssnId);
//    	setCustomerName(name);
    	setCustomerFirstName(firstName);
    	setEmail(email);
        setDateOfBirth(dob);
        setAddress(address);
        setContactNumber(contactNumber);
        setAadharNumber(aadharNumber);
        setPanNumber(panNumber);
        setAccountNumber(accountNumber);
        setAccountBalance(accountBalance);
    	}

	public int getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(int accountBalance) {
        validateAccountBalance(accountBalance);
        this.accountBalance = accountBalance;
    }

    public int getCustomerSSNId() {
        return customerSSNId;
    }
    

    public void setCustomerSSNId(int customerSSNId) {
        validateCustomerSSN(customerSSNId);
        this.customerSSNId = customerSSNId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
//        validateName(customerName);
        this.customerName = customerName;
    }
    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String firstName) {
        this.customerFirstName = firstName;
    }

    // Getter and Setter for lastName
    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String lastName) {
        this.customerLastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        validateEmail(email);
        this.email = email;
    }
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        validateDateOfBirth(dateOfBirth);
        this.dateOfBirth = dateOfBirth;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        validateAddress(address);
        this.address = address;
    }

    public String getContactNumber() {
        return co