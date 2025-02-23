package com.Bank;

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
 * Servlet based implementation for transferring the money from
 * sender to receiver.
*/
@WebServlet("/TransferServlet")
public class TransferServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String senderSSN = request.getParameter("senderSSN");
        String recipientSSN = request.getParameter("recipientSSN");
        String transferAmountStr = request.getParameter("transferAmount");
        double transferAmount = Double.parseDouble(transferAmountStr);

        Connection conn = null;
        PreparedStatement psSender = null;
        PreparedStatement psRecipient = null;
        ResultSet rsSender = null;
        ResultSet rsRecipient = null;

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false); // Disable auto-commit

            // Validate sender
            psSender = conn.prepareStatement("SELECT * FROM Customer WHERE SSN_ID = ?");
            psSender.setString(1, senderSSN);
            rsSender = psSender.executeQuery();

            if (!rsSender.next()) {
                request.setAttribute("errorMessage", "Sender not found!");
                request.getRequestDispatcher("sendMoney.jsp").forward(request, response);
                return;
            }

            double senderBalance = rsSender.getDouble("Account_Balance");
            if (senderBalance < transferAmount) {
                request.setAttribute("errorMessage", "Insufficient balance!");
                request.getRequestDispatcher("sendMoney.jsp").forward(request, response);
                return;
            }

            // Validate recipient
            psRecipient = conn.prepareStatement("SELECT * FROM Customer WHERE SSN_ID = ?");
            psRecipient.setString(1, recipientSSN);
            rsRecipient = psRecipient.executeQuery();

            if (!rsRecipient.next()) {
                request.setAttribute("errorMessage", "Recipient not found!");
                request.getRequestDispatcher("sendMoney.jsp").forward(request, response);
                return;
            }

            // Deduct from sender and add to recipient
            psSender = conn.prepareStatement("UPDATE Customer SET Account_Balance = Account_Balance - ? WHERE SSN_ID = ?");
            psSender.setDouble(1, transferAmount);
            psSender.setString(2, senderSSN);
            psSender.executeUpdate();

            psRecipient = conn.prepareStatement("UPDATE Customer SET Account_Balance = Account_Balance + ? WHERE SSN_ID = ?");
            psRecipient.setDouble(1, transferAmount);
            psRecipient.setString(2, recipientSSN);
            psRecipient.executeUpdate();

            // Insert transaction
            String transactionId = generateTransactionId();
            PreparedStatement psTransaction = conn.prepareStatement(
                "INSERT INTO Transactions (Transaction_ID, Customer_Name, Account_Number, Aadhar_Number, Transaction_Date, Transaction_Mode, Amount, Credit_Debit) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
            );
            psTransaction.setString(1, transactionId);
            psTransaction.setString(2, rsSender.getString("First_Name") + " " + rsSender.getString("Last_Name"));
            psTransaction.setString(3, rsSender.getString("Account_Number"));
            psTransaction.setString(4, rsRecipient.getString("Account_Number"));
            psTransaction.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            psTransaction.setString(6, "Transfer");
            psTransaction.setDouble(7, transferAmount);
            psTransaction.setStri