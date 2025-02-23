package com.bms;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * Servlet based implementation for withdrawing money
 * form customer's account.
 * Also applies the condition for minimum balance of 500
 * to withdraw money.
*/
@WebServlet("/WithdrawlServlet")
public class WithdrawlServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException 
    {
        String accountNumber = request.getParameter("accountNumber");
        String withdrawlAmountStr = request.getParameter("withdrawlAmount");
        double withdrawlAmount;

        try {
        	withdrawlAmount = Double.parseDouble(withdrawlAmountStr);
            if (withdrawlAmount < 1.0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid Withdraw amount. It should be greater than zero.");
            request.getRequestDispatcher("withdrawal.jsp").forward(request, response);
            return;
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmtCheck = conn.prepareStatement("SELECT * FROM Customer WHERE Account_Number = ?");
             PreparedStatement pstmtUpdate = conn.prepareStatement("UPDATE Customer SET Account_Balance = ? WHERE Account_Number = ?")) {

            pstmtCheck.setString(1, accountNumber);
            try (ResultSet rs = pstmtCheck.executeQuery()) {
                if (rs.next()) {
                    double currentBalance = rs.getDouble("Account_Balance");
                    if(currentBalance < withdrawlAmount + 500) {
                    	request.setAttribute("errorMessage", "Withdrawal Amount is higher than Current Balance");
                    	request.getRequestDispatcher("withdrawal.jsp").forward(request, response);
                    }
                    else {
                    	double newBalance = currentBalance - withdrawlAmount;

                        pstmtUpdate.setDouble(1, newBalance);
                        pstmtUpdate.setString(2, accountNumber);
                        String transactionId = TransferServlet.generateTransactionId();
                        PreparedStatement psTransaction = conn.prepareStatement(
                                "INSERT INTO Transactions (Transaction_ID, Customer_Name, Account_Number, Aadhar_Number, Transaction_Date, Transaction_Mode, Amount, Credit_Debit) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
                            );
                            psTransaction.setString(1, transactionId);
                            psTransaction.setString(2, rs.getString("FIRST_NAME") + " " + rs.getString("LAST_NAME"));
                            psTransaction.setString(3, rs.getString("Account_Number"));
                            psTransaction.setString(4, rs.getString("Account_Number"));
                            psTransaction.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
                            psTransaction.setString(6, "Transfer");
                            psTransaction.setDouble(7, withdrawlAmount);
                            psTransaction.setString(8, "D"); // Debit from sender
                            psTransaction.executeUpdate();
                            pstmtUpdate.executeUpdate();

                        request.setAttribute("accountNumber", accountNumber);
                        request.setAttribute("withdrawlAmount", withdrawlAmount);
                        request.setAttribute("totalBalance", newBalance);
                    }
                    
                } else {
                    request.setAttribute("errorMessage", "Account number not found.");
                }
            }

            request.getRequestDispatcher("withdrawal.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            request.getRequestDispatcher("withdrawal.jsp").forward(request, response);
        }
    }
}