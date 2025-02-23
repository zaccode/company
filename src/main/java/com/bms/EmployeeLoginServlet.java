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
 * This is Employee Login servlet which takes the user Employee ID and password
 * as input and checks from the database if the user exists or not.
 * If the user exists, employee is successfully logged in.
*/
@WebServlet("/EmployeeLoginServlet")
public class EmployeeLoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    public static String EmployeeId;

    public EmployeeLoginServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Retrieve login data from request parameters
        String id = request.getParameter("id");
        String password = request.getParameter("password");

        // Initialize response content type
        response.setContentType("text/html");
        
        // Establish a connection to the database
        try (Connection connection = DBConnection.getConnection()) {

            // Prepare SQL query to validate user credentials
            String sql = "SELECT * FROM Employee WHERE employeeid = ? AND password = ?";
            try (PreparedStatement pst = connection.prepareStatement(sql)) {
                pst.setString(1, id);
                pst.setString(2, password);
                
                // In real applications, hash the password before comparing
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        // Valid credentials, set session attribute and redirect with a success message
                        HttpSession session = request.getSession();
                        session.setMaxInactiveInterval(1800);
                        EmployeeId = rs.getString("employeeid");
                        session.setAttribute("employeeId", rs.getString("employeeid"));
                        session.setAttribute("employeeName", rs.getString("employeeName"));// Assuming 'id' is a column in your database
                        response.sendRedirect("loggedHomeEmployee.jsp");
                    } else {
                        // Invalid credentials, set an attribute and forward to error page
                        request.setAttribute("message", "Invalid credentials. Please try again.");
                        request.getRequestDispatcher("/Login/jsp/employeeLogin.jsp").forward(request, response);
                    }
                }
            }
        } catch (SQLException e) {
            // Handle SQL exceptions
            request.setAttribute("message", "An error occurred while processing your request.");
            request.getRequestDispatcher("./Login/jsp/employeeLogin.jsp").forward(request, response);
            e.printStackTrace();
        }
    }
}