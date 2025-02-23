package com.bms;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoanReqServlet
 */
@WebServlet(urlPatterns = "/LoanReqServlet")
public class LoanReqServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public LoanReqServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int ssnId = Integer.parseInt(request.getParameter("ssnId"));
		PrintWriter out = response.getWriter();
		LoanReqDao dao = new LoanReqDao();
		Loan obj= dao.GetDetail(ssnId);
		if(obj == null) {
			out.println("<script>alert('Some Error occurred! Please try again later.'); window.location.href='loan_request.html';</script>");
			doGet(request, response);
		}
		HttpSession session = request.getSession();
		session.setAttribute("loan",obj);
		response.sendRedirect("LoanReq.jsp");
//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			PrintWriter out = response.getWriter();
		 	String ssnId = Integer.toString((request.getParameter("ssnId")));
	        String name = request.getParameter("name");
	        String accountNumber = request.getParameter("accountNumber");
	        String accountBalance = Integer.toString(request.getParameter("amount"));
	        String aadharNumber = request.getParameter("aadharNumber");
	        String msg = request.getParameter("loanReason");

	        Loan loan = new Loan(ssnId, name, accountNumber, aadharNumber,accountBalance , msg);
	        LoanReqDao loanDao = new LoanReqDao();
	       boolean res =  loanDao.SaveLoan(loan);
	       if(res == true) {
	    	   out.println("<script>alert('Loan request successfully submitted!'); window.location.href='loan_request.html';</script>");
	    	   
	       }else {
	    	   out.println("<script>alert('Failed to submit loan request! Please try again.'); window.location.href='loan_request.html';</script>");
	    	   doGet(request, response);
	       }
	       
		
	}

}
