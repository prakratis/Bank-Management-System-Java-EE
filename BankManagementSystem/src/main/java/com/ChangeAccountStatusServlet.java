package com;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/ChangeAccountStatusServlet")
public class ChangeAccountStatusServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

    String idParam = request.getParameter("id");
    String status = request.getParameter("status");

    if (idParam != null && status != null) {
        try {
            int customerId = Integer.parseInt(idParam);
            
            
            boolean success = CustomerDAO.updateStatus(customerId, status);
            
            if (success) {
             
                response.sendRedirect("AdminDashboardServlet");
            } else {
                response.getWriter().println("Error: Could not update status in database.");
            }
        } catch (NumberFormatException e) {
            response.getWriter().println("Error: Invalid Customer ID.");
        }
    }
}
}