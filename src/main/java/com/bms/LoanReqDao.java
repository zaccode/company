package com.bms;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;


//private String  Request_ID;
//private String Customer_Name;
//private String Account_Number;
//private String Aadhar_Number;
//private String Created_At;
//private String Amount;

public class LoanReqDao {
	
	public LoanReqDao() {
		super();
	}
	 public static  String generateRequestId(Connection conn) throws SQLException {
	        // Get the current date in YYYY-MM-DD format
	        String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

	        // Query to count existing requests for today
	        String query = "SELECT COUNT(*) FROM LoanRequest WHERE ReqId LIKE ?";
	        PreparedStatement ps = conn.prepareStatement(query);
	        ps.setString(1, currentDate + "%");  // Match requests from today's date
	        ResultSet rs = ps.executeQuery();

	        int count = 0;
	        if (rs.next()) {
	            count = rs.getInt(1);
	        }
	        rs.close();
	        ps.close();

	        // Generate new sequence number (increment count)
	        int newSequence = count + 1;

	        // Format the final Request ID
	        return currentDate + "-" + newSequence;  // Example: 2025-02-22-1
	    }
	

	public Loan GetDetail(int ssn) {
		Loan obj = null;
		String customerId = CustomerLoginServlet.customerId;
		try(Connection conn = DBConnection.getConnection()){
			String query = "SELECT * FROM Customer where ssn_id=? AND isActive=1";
			try (PreparedStatement pstmt = conn.prepareStatement(query)) {
				pstmt.setString(1, customerId);
				ResultSet resultSet = pstmt.executeQuery();
				while (resultSet.next()) {
					String ssnId = Integer.toString(resultSet.getInt("SSN_ID"));
					String firstName = resultSet.getString("First_Name");
					String lastName = resultSet.getString("Last_Name");
					String aadharNumb = resultSet.getString("Aadhar_Number");
					String accountNumber = resultSet.getString("Account_Number");
					obj = new Loan(ssnId, firstName + " " + lastName,accountNumber,  aadharNumb);
				}
			} catch (Exception e) {
				System.out.print(e);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return obj;
	}
	
	public static boolean SaveLoan(Loan loan) {
	    boolean res = false;

	    try (Connection conn = DBConnection.getConnection()) {
	        String insertSQL = "INSERT INTO LoanRequest (ReqId, SSNID, Customer_Name, Account_Number, Aadhar_Number, Message, Amount, Status, BankMessage) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

	        try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
	            pstmt.setString(1, generateRequestId(conn));  // Generate Request ID
	            pstmt.setString(2, loan.getSSNID());  // SSN ID
	            pstmt.setString(3, loan.getCustomer_Name());  // Customer Name
	            pstmt.setString(4, loan.getAccount_Number());  // Account Number
	            pstmt.setString(5, loan.getAadhar_Number());  // Aadhar Number
	            pstmt.setString(6, loan.getMessage());  // Message
	            pstmt.setBigDecimal(7, BigDecimal.valueOf(Double.parseDouble(loan.getAmount())));// Amount (Use setBigDecimal)
	            pstmt.setString(8, "Pending");  // Default Status
	            pstmt.setString(9, "Your loan request is under review.");  // Default BankMessage

	            int rowsAffected = pstmt.executeUpdate();
	            if (rowsAffected > 0) {
	                res = true;
	                System.out.println("Loan request submitted successfully!");
	            } else {
	                System.out.println("Failed to submit loan request.");
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();  // Handle exception properly
	        }
	    } catch (SQLException e1) {
	        e1.printStackTrace();
	    }
	    return res;
	}

	
	public static List<Loan> ViewAllLoanByCusomer() {
		List<Loan>arr = new ArrayList<>();
		String customerId = CustomerLoginServlet.customerId;
		try(Connection conn = DBConnection.getConnection()){
			String query = "SELECT * FROM LoanRequest WHERE SSNID = ? ORDER BY ReqId";
			try (PreparedStatement pstmt = conn.prepareStatement(query)) {
				pstmt.setString(1, customerId);
				ResultSet resultSet = pstmt.executeQuery();
				while (resultSet.next()) {
					String reqId = resultSet.getString("ReqId");
					String ssnId = Integer.toString(resultSet.getInt("SSN_ID"));
					String firstName = resultSet.getString("First_Name");
					String lastName = resultSet.getString("Last_Name");
					String aadharNumb = resultSet.getString("Aadhar_Number");
					String accountNumber = resultSet.getString("Account_Number");
					String Message = resultSet.getString("Message");
					Timestamp createdAt = resultSet.getTimestamp("Created_At");
					String createdAtStr = createdAt.toString();
					String Created_At = resultSet.getString("Created_At");
					String Status = resultSet.getString("Status");
					String Amount = Double.toString(resultSet.getDouble("Amount"));
					String BankMessage = resultSet.getString("BankMessage");
//					Loan(String reqId, String sSNID, String customer_Name, String account_Number, String aadhar_Number,
//							String created_At, String amount, String message, String bankMessage, String status)
					arr.add(new Loan(reqId,ssnId, firstName + " " + lastName,accountNumber,  aadharNumb, createdAtStr,  Amount,  Message,  BankMessage,  Status));
				}
			} catch (Exception e) {
				System.out.print(e);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return arr;
	}
	
	public static boolean UpdateStatus(String ReqId, String Reason, String status) {
	    boolean res = false;

	    try (Connection conn = DBConnection.getConnection()) {
	        String updateSQL = "UPDATE LoanRequest SET BankMessage=?, Status=? WHERE ReqId=?";

	        try (PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
	            pstmt.setString(1, Reason);  // Set BankMessage
	            pstmt.setString(2, status);  // Set Status
	            pstmt.setString(3, ReqId);   // Set WHERE condition

	            int rowsAffected = pstmt.executeUpdate();
	            if (rowsAffected > 0) {
	                res = true;
	                System.out.println("Loan request updated successfully!");
	            } else {
	                System.out.println("Failed to update loan request.");
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();  // Handle exception properly
	        }
	    } catch (SQLException e1) {
	        e1.printStackTrace();
	    }
	    return res;
	}

}
