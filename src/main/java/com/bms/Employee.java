package com.bms;

public class Employee {

    private int employeeId;
    private String employeeName;
    private String email;
    private String password;
    private String address;
    private String contactNumber;

    // Constructor with all fields
    public Employee(int employeeId, String employeeName, String email, String password, String address, String contactNumber) {
        setEmployeeId(employeeId);
        setEmployeeName(employeeName);
        setEmail(email);
        setPassword(password);
        setAddress(address);
        setContactNumber(contactNumber);
    }

    // Constructor with basic fields (excluding ID)
    public Employee(String employeeName, String email, String password, String address, String contactNumber) {
        setEmployeeName(employeeName);
        setEmail(email);
        setPassword(password);
        setAddress(address);
        setContactNumber(contactNumber);
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        validateEmployeeId(employeeId);
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        validateEmployeeName(employeeName);
        this.employeeName = employeeName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        validateEmail(email);
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        validatePassword(password);
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        validateAddress(address);
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        validateContactNumber(contactNumber);
        this.contactNumber = contactNumber;
    }

    
    public String toString() {
        return "Employee{" +
            "employeeId=" + employeeId +
            ", employeeName='" + employeeName + '\'' +
            ", email='" + email + '\'' +
            ", password='" + password + '\'' +
            ", address='" + address + '\'' +
            ", contactNumber='" + contactNumber + '\'' +
            '}';
    }

    // Validation Methods

    public static void validateEmployeeId(int employeeId) {
        if (employeeId <= 0) {
            throw new IllegalArgumentException("Employee ID must be a positive integer.");
        }
    }

    public static void validateEmployeeName(String employeeName) {
    	System.out.println(employeeName);
        if (employeeName == null || employeeName.trim().isEmpty()) {
            throw new IllegalArgumentException("Employee Name cannot be empty and must be up to 100 characters.");
        }
        if (!employeeName.matches("^[A-Za-z\\s]+$")) {
            throw new IllegalArgumentException("Employee Name can only contain letters and spaces.");
        }
    }

    public static void validateEmail(String email) {
        if (email == null || !email.matches("^[^\\s@]+@[\\w-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("Invalid email format.");
        }
    }

    public static void validatePassword(String password) {
        if (password == null || password.length() < 8 || password.length() > 30) {
            throw new IllegalArgumentException("Password must be 8-30 characters long.");
        }
    }

    public static void validateAddress(String address) {
        if (address != null && address.length() > 255) {
            throw new IllegalArgumentException("Address cannot be more than 255 characters.");
        }
    }

    public static void validateContactNumber(String contactNumber) {
        if (contactNumber != null && !contactNumber.matches("\\d{10,15}")) {
            throw new IllegalArgumentException("Contac