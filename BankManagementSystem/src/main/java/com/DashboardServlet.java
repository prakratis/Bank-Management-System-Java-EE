package com;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            Customer user = (Customer) session.getAttribute("user");
            int customerId = user.getCustomerId();


            Customer profile = CustomerDAO.getCustomerById(customerId);
            List<Account> accounts = AccountDAO.getAccountsByCustomerId(customerId);
            List<Investment> investments = InvestmentDAO.getInvestmentsByCustomer(customerId);
            
            List<Transaction> transactions = new ArrayList<>();
            if (accounts != null && !accounts.isEmpty()) {
                long accNo = Long.parseLong(accounts.get(0).getAccountNo()); 
                transactions = TransactionDAO.getRecentTransactions(accNo);
            }


            request.setAttribute("profile", profile);
            request.setAttribute("accountList", accounts);
            request.setAttribute("transactionList", transactions);
            request.setAttribute("investmentList", investments);

    
            String target = request.getParameter("view"); 


            String destination = "dashboard.jsp"; 

  
            if ("portfolio".equals(target)) {
                destination = "investment.jsp"; 
            }


            request.getRequestDispatcher(destination).forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("login.jsp?error=exception");
        }
    }
}