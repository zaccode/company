package com.bms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
	 /**
	 * 
	 * This method fetches all the details of a customer by taking
	 * account ID. 
	 */
	public Customer getCustomers() {
	    String customerId = CustomerLoginServlet.customerId;

		System.out.println(customerId + "Hello");
		Customer customer = null;
		try(Connection conn = DBConnection.getConnection()){
			String query = "SELECT * FROM Customer where ssn_id=? AND isActive=1";
			try (PreparedStatement pstmt = conn.prepareStatement(query)) {
				pstmt.setString(1, customerId);
				ResultSet resultSet = pstmt.executeQuery();
				while (resultSet.next()) {
					int ssnId = resultSet.getInt("SSN_ID");
					String firstName = resultSet.getString("First_Name");
					String lastName = resultSet.getString("Last_Name");
					String email = resultSet.getString("Email");
					String password = resultSet.getString("Password");
					String dateOfBirth = resultSet.getString("Date_Of_Birth");
					String address = resultSet.getString("Address");
					String contactNumber = resultSet.getString("Contact_Number");
					String aadharNumber = resultSet.getString("Aadhar_Number");
					String panNumber = resultSet.getString("PAN_Number");
					int accountBalance = resultSet.getInt("Account_Balance");
					String accountNumber = resultSet.getString("Account_Number");
					customer = new Customer(ssnId, firstName + " " + lastName, email, password, dateOfBirth, address, contactNumber, aadharNumber, panNumber, accountBalance, accountNumber);
				}
			} catch (Exception e) {
				System.out.print(e);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println(customer);
		return customer;
		
	}
	
	 /**
		 * 
		 * This method fetches all the details of a customer by taking
		 * SSN ID. 
	 */
	public Customer getCustomerBySSNId(String ssnId) {
	    Customer customer = null;
	    String query = "SELECT * FROM Customer WHERE SSN_ID = ?";

	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(query)) {

	        pstmt.setString(1, ssnId);
	        ResultSet resultSet = pstmt.executeQuery();

	        if (resultSet.next()) {
	            // Use appropriate getters based on your column names
	            int ssnIdValue = resultSet.getInt("SSN_ID");
	            String firstName = resultSet.getString("First_Name");
	            String lastName = resultSet.getString("Last_Name");
	            String email = resultSet.getString("Email");
	            String password = resultSet.getString("Password"); // Consider removing if not needed
	            String dateOfBirth = resultSet.getString("Date_Of_Birth");
	            String address = resultSet.getString("Address");
	            String contactNumber = resultSet.getString("Contact_Number");
	            String aadharNumber = resultSet.getString("Aadhar_Number");
	            String panNumber = resultSet.getString("PAN_Number");
	            int accountBalance = resultSet.getInt("Account_Balance");
	            String accountNumber = resultSet.getString("Account_Number");

	            customer = new Customer(ssnIdValue, firstName + " " + lastName, email, password, dateOfBirth, address, contactNumber, aadharNumber, panNumber, accountBalance, accountNumber);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return customer;
	}

	 /**
		 * 
		 * This method fetches all the details of all customers registered
		 * and are active in the system. 
	  */
	public List<Customer> getAllCustomers() {
	    ArrayList<Customer> customers = new ArrayList<>();
	    
	    try (Connection conn = DBConnection.getConnection()) {
	        String query = "SELECT * FROM Customer where isActive = 1";
	        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
	            ResultSet resultSet = pstmt.e