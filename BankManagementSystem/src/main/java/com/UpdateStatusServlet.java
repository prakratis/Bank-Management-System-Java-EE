package com;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/UpdateStatusServlet")
public class UpdateStatusServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String idParam = request.getParameter("id");
        String status = request.getParameter("status"); 

        if (idParam != null) {
            int customerId = Integer.parseInt(idParam);
            
         
            if (status != null) {
                boolean success = CustomerDAO.updateStatus(customerId, status);
                if (success && "approved".equalsIgnoreCase(status)) {
                 
                    Account newAcc = new Account();
                    newAcc.setCustomerId(customerId);
                    newAcc.setAccountNo("1000" + (1000 + customerId)); 
                    newAcc.setAccountType("SAVINGS");
                    newAcc.setBalance(1000.0);
                    newAcc.setAccountStatus("active");
                    AccountDAO.createAccount(newAcc);
                }
                response.sendRedirect("PendingCustomersServlet"); 
            } 
      
            else {
                boolean success = AccountDAO.toggleAccountStatus(customerId);
                response.sendRedirect("ViewAllAccountsServlet?msg=" + (success ? "updated" : "error"));
            }
        }
    }
}