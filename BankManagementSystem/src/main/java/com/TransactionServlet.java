package com;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Servlet implementation class TransactionServlet
 */
@WebServlet("/TransactionServlet")
public class TransactionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	private TransactionDAO transactionDAO = new TransactionDAO();
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		Customer customer = (session != null) ? (Customer) session.getAttribute("user") : null;

		if (customer == null) {
		    System.out.println("Session invalid - redirecting to login");
		    response.sendRedirect("login.jsp");
		    return;
		}

       
        long accNo = Long.parseLong(request.getParameter("accountNo"));
        double amount = Double.parseDouble(request.getParameter("amount"));
        String type = request.getParameter("type"); 
        
        double currentBalance = Double.parseDouble(request.getParameter("currentBalance"));
        double newBalance = type.equals("deposit") ? currentBalance + amount : currentBalance - amount;

       
        Transaction t = new Transaction();
        t.setAccountNo(accNo);
        t.setTransactionType(type);
        t.setAmount(amount);
        t.setBalanceAfter(newBalance);
        t.setDescription(type + " of $" + amount);
        t.setTransactionStatus("success");

        boolean saved = transactionDAO.addTransaction(t); // Just pass 't'

        if (saved) {
            response.sendRedirect("DashboardServlet?msg=success");
        } else {
            response.sendRedirect("dashboard.jsp?error=failed");
        }
    }
}
