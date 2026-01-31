package com;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/DepositServlet")
public class DepositServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            long accountNo = Long.parseLong(request.getParameter("accountNo"));
            double amount = Double.parseDouble(request.getParameter("amount"));

            if (amount <= 0) {
                response.sendRedirect("DashboardServlet?error=invalidAmount");
                return;
            }

            boolean success = TransactionDAO.deposit(accountNo, amount);

            if (success) {
                response.sendRedirect("DashboardServlet?msg=deposit_success");
            } else {
                response.sendRedirect("DashboardServlet?error=system_fail");
            }
        } catch (Exception e) {
            response.sendRedirect("DashboardServlet?error=invalidInput");
        }
    }
}