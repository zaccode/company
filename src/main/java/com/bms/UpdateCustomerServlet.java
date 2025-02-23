package com.bms;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * Servlet based implementation for updating the customer details.
 
*/
@WebServlet("/updateCustomerServlet")
public class UpdateCustomerServlet extends HttpServlet {
    
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int ssnId = Integer.parseInt(request.getParameter("ssnId"));
        String name = request.getParameter("name");
        String accountNumber = request.getParameter("accountNumber");
        int accountBalance = Integer.parseInt(request.getParameter("accountBalance"));
        String aadharNumber = request.getParameter("aadharNumber");
        String panNumber = request.getParameter("panNumber");
        String dob = request.getParameter("dob");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String contactNumber = request.getParameter("contactNumber");

        Customer customer = new Customer(ssnId, name, accountNumber, accountBalance, aadharNumber, panNumber, dob, email, address, contactNumber);
        CustomerDAO customerDAO = new CustomerDAO();
        customerDAO.updateCustomer(customer);
        response.sendRedirect("loggedHomeEmployee.jsp");
    }
}