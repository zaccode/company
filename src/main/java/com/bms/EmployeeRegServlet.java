package com.bms;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * This is Employee Registration servlet which takes all required employee details
 * and calls the EmployeeDAO class method for inserting the employee in the database.
*/
@WebServlet("/EmployeeRegServlet")
public class EmployeeRegServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public EmployeeRegServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve employee data from request parameters
        String employeeId = request.getParameter("employeeId");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String employeeName = firstName + " " + lastName;
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String address = request.getParameter("address");
        String contactNumber = request.getParameter("contactNumber");

        // Create Employee object
        Employee employee = new Employee(Integer.parseInt(employeeId), employeeName, email, password, address, contactNumber);

        // Interact with database
        try (Connection connection = DBConnection.getConnection()) {
            if (EmployeeDAO.emailExists(connection, email)) {
                // Redirect to error page with message
                response.sendRedirect("registration-error.jsp?message=Email already exists. Please use a different email.");
            } else {
                EmployeeDAO.insertEmployee(connection, employee);
                // Redirect to success page with message
                response.sendRedirect("Login/jsp/employeeLogin.jsp");
            }
        } catch (SQLException e) {
            // Redirect to error page with message
            response.sendRedirect("registration-error.jsp?message=An error occurred while registering the employee: " + e.getMessage());
        }
    }
}