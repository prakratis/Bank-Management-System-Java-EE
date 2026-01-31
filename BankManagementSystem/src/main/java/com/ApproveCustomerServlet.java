package com;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@SuppressWarnings("serial")
@WebServlet("/ApproveCustomerServlet")
public class ApproveCustomerServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        
        if (session == null || !"ADMIN".equals(session.getAttribute("role"))) {
            response.sendRedirect("login.jsp");
            return;
        }

        int customerId = Integer.parseInt(request.getParameter("id"));
        String action = request.getParameter("action"); 
   
        String newStatus = action.equals("approve") ? "approved" : "rejected";

        boolean success = CustomerDAO.updateStatus(customerId, newStatus);

        if (success && "approve".equals(action)) {
            
            AccountDAO.createDefaultAccount(customerId);
        }

        response.sendRedirect("PendingCustomersServlet?msg=" + newStatus);
    }
}