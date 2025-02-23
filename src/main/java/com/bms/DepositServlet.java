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
 * Customer Money deposit servlet implementation responsible for
 * depositing (adding) money to the customer's own account.
 * The servlet auto populates the account number of the logged in 
 * customer, the user just has to enter the deposit amount.
*/
@WebServlet("/DepositServlet")
public class DepositServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException 
    {
        String accountNumber = request.getParameter("accountNumber");
        String depositAmountStr = request.getParameter("depositAmount");
        double depositAmount;

        try {
            depositAmount = Double.parseDouble(depositAmountStr);
            if (depositAmount < 1.0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid deposit amount. It should be greater than zero.");
            request.getRequestDispatcher("deposit.jsp").forward(request, response);
            return;
        }

        try (Connection conn = DBConnection.getConnection();
        		
             PreparedStatement pstmtCheck = conn.prepareStatement("SELECT * FROM Customer WHERE Account_Number = ?");
             PreparedStatement pstmtUpdate = conn.prepareStatement("UPDATE Customer SET Account_Balance = ? WHERE Account_Number = ?")) {
            pstmtCheck.setString(1, accountNumber);
            try (ResultSet rs = pstmtCheck.executeQuery()) {
                if (rs.next()) {
                    double currentBalance = rs.getDouble("Account_Balance");
                    double newBalance = currentBalance + depositAmount;

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
                    psTransaction.setDouble(7, depositAmount);
                    psTransaction.setString(8, "C"); // Debit from sender
                    psTransaction.executeUpdate();
                    
                    pstmtUpdate.executeUpdate();

                    request.setAttribute("accountNumber", accountNumber);
                    request.setAttribute("depositAmount", depositAmount);
                    request.setAttribute("totalBalance", newBalance);
                    

                } else {
                    request.setAttribute("errorMessage", "Account number not found.");
                }
            }

            request.getRequestDispatcher("deposit.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            request.getRequestDispatcher