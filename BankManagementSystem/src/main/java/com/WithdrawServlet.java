package com;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/WithdrawServlet")
public class WithdrawServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
      
        HttpSession session = request.getSession(false);
        Customer user = (session != null) ? (Customer) session.getAttribute("user") : null;

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            long accountNo = Long.parseLong(request.getParameter("accountNo"));
            double amount = Double.parseDouble(request.getParameter("amount"));

            if (!TransactionDAO.isAccountValid(accountNo, user.getCustomerId())) {
                response.sendRedirect("DashboardServlet?error=unauthorized");
                return;
            }

            double currentBalance = TransactionDAO.getBalance(accountNo);
            
            if (amount > currentBalance) {
                response.sendRedirect("DashboardServlet?error=insufficientFunds");
                return;
            }

           
            boolean success = TransactionDAO.withdraw(accountNo, amount);

            if (success) {
                response.sendRedirect("DashboardServlet?msg=withdraw_success");
            } else {
                response.sendRedirect("DashboardServlet?error=system_fail");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("DashboardServlet?error=invalidInput");
        }
    }
}