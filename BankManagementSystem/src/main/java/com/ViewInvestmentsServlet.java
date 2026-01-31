package com;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/ViewInvestments")
public class ViewInvestmentsServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    HttpSession session = request.getSession(false);
	   
	    Customer customer = (session != null) ? (Customer) session.getAttribute("user") : null;
	    
	    if (customer == null) {
	        System.out.println("DEBUG: No user found in session, redirecting to login.");
	        response.sendRedirect("login.jsp");
	        return;
	    }

	    try {
	        int userId = customer.getCustomerId(); 
	        List<Investment> list = InvestmentDAO.getInvestmentsByCustomer(userId);
	        
	        request.setAttribute("investmentList", list);
	        request.getRequestDispatcher("investments.jsp").forward(request, response);
	    } catch (Exception e) {
	        e.printStackTrace(); 
	        response.sendRedirect("DashboardServlet?error=data_fetch_failed");
	    }
	}
}