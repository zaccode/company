package com.bms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {
	
	/**
	 * 
	 * Retrieves all the transactions of all the customers from the  
	 * transaction table.
  */


    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        String query = "SELECT * FROM TRANSACTIONS"; // Ensure table name is in lowercase as per the schema

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setTransactionId(rs.getString("TRANSACTION_ID"));
                transaction.setCustomerName(rs.getString("CUSTOMER_NAME"));
                transaction.setAccountId(rs.getString("ACCOUNT_NUMBER"));
                transaction.setAadhaarCardNo(rs.getString("AADHAR_NUMBER"));
                transaction.setDate(rs.getString("TRANSACTION_DATE"));
                transaction.setModeOfTransaction(rs.getString("TRANSACTION_MODE"));
                transaction.setAmountOfTransaction(rs.getBigDecimal("AMOUNT"));
                transaction.setCreditDebit(rs.getString("CREDIT_DEBIT"));
                
                transactions.add(transaction);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }
    
    /**
	 * 
	 * Retrieves all the transactions of a specific customer (logged in customer) from the  
	 * transaction table.
  */
    public List<Transaction> getAllTransactionsCustomer() {
        List<Transaction> transactions = new ArrayList<>();
        String customerId = CustomerLoginServlet.accountId;
        String query = "SELECT * FROM TRANSACTIONS WHERE ACCOUNT_NUMBER=?"; // Ensure table name is in lowercase as per the schema

        try (Connection conn = DBConnection.getConnection()){
             PreparedStatement pstmt = conn.prepareStatement(query);
             pstmt.setString(1, customerId);
             ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setTransactionId(rs.getString("TRANSACTION_ID"));
                transaction.setCustomerName(rs.getString("CUSTOMER_NAME"));
                transaction.setAccountId(rs.getString("ACCOUNT_NUMBER"));
                transaction.setAadhaarCardNo(rs.getString("AADHAR_NUMBER"));
                transaction.setDate(rs.getString("TRANSACTION_DATE"));
                transaction.setModeOfTransaction(rs.getString("TRANSACTION_MODE"));
                transaction.setAmountOfTransaction(rs.getBigDecimal("AMOUNT"));
                transaction.setCreditDebit(rs.getString("CREDIT_DEBIT"));
                
                transactions.add(transaction);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }

}