package com;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/AdminDashboardServlet")
public class AdminDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        
      
        if (session == null || !"ADMIN".equals(session.getAttribute("role"))) {
            response.sendRedirect("login.jsp");
            return;
        }

        
        int pendingCount = CustomerDAO.getPendingCount(); 
        int totalAccounts = AccountDAO.getTotalAccountCount();

        List<Customer> pendingList = CustomerDAO.getPendingCustomers(); 

        request.setAttribute("pendingCount", pendingCount);
        request.setAttribute("totalAccounts", totalAccounts);
        request.setAttribute("pendingList", pendingList); 
        request.getRequestDispatcher("admindashboard.jsp").forward(request, response);
    }
}