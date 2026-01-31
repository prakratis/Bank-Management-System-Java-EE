package com;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class ResetPasswordServlet
 */
@WebServlet("/ResetPasswordServlet")
public class ResetPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int customerId = Integer.parseInt(request.getParameter("customerId"));
        String defaultPass = "Reset@123"; // Realistic default password

        boolean success = AdminDAO.resetCustomerPassword(customerId, defaultPass);
        if (success) {
            response.sendRedirect("ViewAllAccountsServlet?msg=password_reset_success");
        } else {
            response.sendRedirect("ViewAllAccountsServlet?error=reset_failed");
        }
    }
}
