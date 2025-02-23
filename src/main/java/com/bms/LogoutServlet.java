package com.bms;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



/**
 * Servlet based implementation for logging out the user.
 * Invalidates the session of user for logging out.
*/
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the current session
        HttpSession session = request.getSession(false); // Use false to avoid creating a new session if one doesn't exist
        if (session != null) {
            // Invalidate the session
            session.invalidate();
        }
        // Redirect to login page or home page after logout
        response.sendRedirect("home.jsp"); // Redirect to login page
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response); // Handles both GET and POST requests

    }

}