package com.bms;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@WebServlet("/DeleteCustomerServlet")
public class DeleteCustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int ssnId = Integer.parseInt(request.getParameter("ssnId"));
		CustomerDAO customerDAO = new CustomerDAO();
		customerDAO.deleteCustomer(ssnId);
		response.sendRedirect("loggedHomeEmployee.jsp"); // Redirect to the home page or wherever appropriate
		
    }
}