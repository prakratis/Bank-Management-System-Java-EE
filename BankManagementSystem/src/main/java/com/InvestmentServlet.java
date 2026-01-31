package com;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/InvestmentServlet")
public class InvestmentServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        Customer user = (session != null) ? (Customer) session.getAttribute("user") : null;

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int customerId = user.getCustomerId();

        try {
          
            String type = request.getParameter("type");
            double amount = Double.parseDouble(request.getParameter("amount"));
            int duration = Integer.parseInt(request.getParameter("duration"));
            String pin = request.getParameter("pin");

            
            if (!TransactionDAO.verifyPin(customerId, pin)) {
                response.sendRedirect("DashboardServlet?error=wrongPin");
                return;
            }

            
            boolean success = InvestmentDAO.createInvestment(customerId, type, amount, duration);

            if (success) {
                
                request.setAttribute("type", type);
                request.setAttribute("amount", amount);
                request.setAttribute("duration", duration);
                request.getRequestDispatcher("investments.jsp").forward(request, response);
            } else {
                
                response.sendRedirect("DashboardServlet?error=insufficientFunds");
            }

        } catch (NumberFormatException e) {
        
            response.sendRedirect("DashboardServlet?error=invalidInput");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("DashboardServlet?error=processError");
        }
    }
}