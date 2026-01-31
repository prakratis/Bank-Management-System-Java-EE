package com;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/TransferServlet")
public class TransferServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
  
        Integer userId = (Integer) session.getAttribute("userId");
        
        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
          
            String enteredPin = request.getParameter("pin");
            long from = Long.parseLong(request.getParameter("senderAcc"));
            long to = Long.parseLong(request.getParameter("receiverAcc"));
            double amt = Double.parseDouble(request.getParameter("amount"));
            boolean isPinValid = TransactionDAO.verifyPin(userId, enteredPin);
            
            if (!isPinValid) {
                response.sendRedirect("DashboardServlet?error=wrongPin");
                return;
            }

      
            if (from == to) {
                response.sendRedirect("DashboardServlet?error=sameAccount");
                return;
            }

         
            boolean success = TransactionDAO.transfer(from, to, amt);

            if (success) {
                response.sendRedirect("DashboardServlet?msg=transferSuccess");
            } else {
                response.sendRedirect("DashboardServlet?error=insufficientFunds");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("DashboardServlet?error=processError");
        }
    }
}