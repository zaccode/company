package com.bms;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AddCustomer")
public class AddCustomer extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public AddCustomer() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	    String ssnIdStr = request.getParameter("ssnId");
	    String firstName = request.getParameter("firstName").substring(0, 1).toUpperCase() + request.getParameter("firstName").substring(1).toLowerCase();
	    String lastName = request.getParameter("lastName").substring(0, 1).toUpperCase() + request.getParameter("lastName").substring(1).toLowerCase();
	    String custName = firstName + " " + lastName;
	    String email = request.getParameter("email");
	    String password = request.getParameter("password");
	    String dateOfBirth = request.getParameter("dateOfBirth");
	    String address = request.getParameter("address");
	    String contactNumber = request.getParameter("contactNumber");
	    String panNumber = request.getParameter("panNumber");
	    String aadharNumber = request.getParameter("aadharNumber");
	    int accountBalance = Integer.parseInt(request.getParameter("accountBalance"));
	    String accountNumber = request.getParameter("accountNumber");
	
	    // Create Customer object 
	    Customer customer = new Customer(
	        Integer.parseInt(ssnIdStr), custName, email, password, dateOfBirth, address, contactNumber, aadharNumber, panNumber, accountBalance, accountNumber
	    );
	
	    // Interact with database
	    try (Connection connection = DBConnection.getConnection()) {
	        CustomerDAO customerDAO = new CustomerDAO();
			customerDAO.insertCustomer(connection, customer);
	        response.sendRedirect("customerDetails.jsp" );
	    } catch (SQLException e) {
	        e.printStackTrace();  // Print stack trace for debugging
	        // Redirect to error page with message
	        response.sendRedirect("registration-error.jsp?message=" + URLEncoder.encode("An error occurred while registering the customer: " + e.getMessage(), "UTF-8"));
	    } catch (ParseException e) {
	        e.printStackTrace();  // Print stack trace for debugging
	        // Redirect to error page with message
	        response.sendRedirect("registration-error.jsp?message=" + URLEncoder.encode("An error occurred while parsing the date: " + e.getMessage(), "UTF-8"));
	    }
	    catch(ArithmeticException e) {
	    	response.sendRedirect("registration-error.jsp?message=" + URLEncoder.encode("An error occurred while parsing the date: User Already Exists. " + e.getMessage(), "UTF-8"));
	    }
	}
}