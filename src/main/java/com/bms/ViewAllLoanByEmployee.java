package com.bms;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ViewAllLoanByEmployee
 */
@WebServlet(urlPatterns ="/ViewAllLoanByEmployee")
public class ViewAllLoanByEmployee extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public ViewAllLoanByEmployee() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LoanReqDao dao = new LoanReqDao();
		List<Loan> arr  = dao.ViewAllLoanByCusomer();
		Collections.reverse(arr);
		request.setAttribute("accountList", arr);
		 request.getRequestDispatcher("ViewAllLoanByEmployee.jsp").forward(request, response);

		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String ReqId = request.getParameter("reqId");
		String Reason = request.getParameter("reason");
		String status = request.getParameter("status"); 
		if(ReqId != null) {
			LoanReqDao dao = new LoanReqDao();
			boolean res = dao.UpdateStatus(ReqId,Reason,status);
			if(res == true) {
		    	   out.println("<script>alert('Loan Status Update successfully!'); window.location.href='loan_request.html';</script>");
		    	   
		       }else {
		    	   out.println("<script>alert('Failed to Update loan Status! Please try again.'); window.location.href='loan_request.html';</script>");
		    	   doGet(request, response);
		       }
		}
		doGet(request, response);
	}

}
