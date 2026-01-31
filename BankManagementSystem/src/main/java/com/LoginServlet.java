package com;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");


        Admin admin = AdminDAO.login(email, password);
        if (admin != null) {
            HttpSession session = request.getSession();
            session.setAttribute("admin", admin);
            session.setAttribute("role", "ADMIN");
           
            response.sendRedirect("AdminDashboardServlet");
            return;
        }

        Customer customer = CustomerDAO.login(email, password);
        if (customer != null) {
         
            if ("approved".equalsIgnoreCase(customer.getStatus())) {
                HttpSession session = request.getSession();
                session.setAttribute("user", customer);
                session.setAttribute("role", "CUSTOMER");
                response.sendRedirect("DashboardServlet"); 
            } else {
                request.setAttribute("error", "Your account is " + customer.getStatus() + ". Please contact Admin.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
            return;
        }

        // 3. Neither found
        request.setAttribute("error", "Invalid Email or Password.");
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}