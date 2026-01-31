package com;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/PendingCustomersServlet")
public class PendingCustomersServlet extends HttpServlet {
 
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        
        if (session == null || !"ADMIN".equals(session.getAttribute("role"))) {
            response.sendRedirect("login.jsp");
            return;
        }

        List<Customer> pendingList = CustomerDAO.getPendingCustomers();
        request.setAttribute("pendingList", pendingList);
        request.getRequestDispatcher("pendingcustomers.jsp").forward(request, response);
    }
}