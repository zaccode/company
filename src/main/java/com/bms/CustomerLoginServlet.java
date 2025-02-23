package com.bms;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 
 * This is Customer Login servlet which takes the user SSN ID and password
 * as input and checks from the database if the user exists or not.
 * If user exists, customer is successfully logged in.
*/

@WebServlet("/CustomerLoginServlet")
public class CustomerLoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    public static String customerId;
    public static String accountId;

    public CustomerLoginServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Retrieve login data from request parameters
        String ssn = request.getParameter("ssn");
        String password = request.getParameter("password");
        System.out.println(ssn);
        System.out.println(password);
        // Initialize response content type
        response.setContentType("text/html");
        
        // Establish a connection to the database
        try (Connection connection = DBConnection.getConnection()) {

            // Prepare SQL query to validate user credentials
            String sql = "SELECT * FROM Customer WHERE SSN_ID = ? AND password = ? AND isActive = 1";
            try (PreparedStatement pst = connection.prepareStatement(sql)) {
                pst.setString(1, ssn);
                pst.setString(2, password);


                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        // Valid credentials, set session attribute and redirect with a success message
                        HttpSession session = request.getSession();
                        customerId = rs.getString("ssn_id");
                        accountId = rs.getString("Account_Number");
                        System.out.println(rs.getString("ssn_id"));
//                        System.out.println(rs.getString("SSN_ID"));
                        session.setAttribute("customerId", rs.getString("ssn_id")); // Assuming 'emailId' is a column in your database
                        //response.sendRedirect("login-success.jsp?message=Login%20Successful!");
                        session.setAttribute("accountID", rs.getString("Account_Number"));
                        session.setAttribute("customerName", rs.getString("First_Name"));
                        response.sendRedirect("loggedHomeCustomer.jsp");
                    } else {
                        // Invalid credentials, set an attribute and forward to error page
                        request.setAttribute("message", "Invalid credentials. Please try again.");
                        request.getRequestDispatcher("/Login/jsp/customerLogin.jsp").forward(request, response);
                    }
                }
            }
        } catch (SQLException e) {
            // Handle SQL exceptions
            System.out.println(e.getMessage());
            request.setAttribute("message", "An error occurred while processing your request.");
            request.getRequestDispatcher("/Login/jsp/customerLogin.jsp").forward(request, response);
            e.printStackTrace();
        }
    }
}