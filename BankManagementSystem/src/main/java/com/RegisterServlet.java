package com;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String fullName = request.getParameter("fullName");
        String dob = request.getParameter("dob");
        String gender = request.getParameter("gender");
        String email = request.getParameter("email");
        String mobile = request.getParameter("mobile");
        String idType = request.getParameter("idType");
        String idNumber = request.getParameter("idNumber");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String pincode = request.getParameter("pincode");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        if (fullName == null || !password.equals(confirmPassword)) {
            request.setAttribute("error", "Passwords do not match or fields are empty.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        Customer customer = new Customer();
        customer.setFullName(fullName);
        customer.setDob(dob);
        customer.setGender(gender);
        customer.setEmail(email);
        customer.setMobile(mobile);
        customer.setIdType(idType);
        customer.setIdNumber(idNumber);
        customer.setCity(city);
        customer.setState(state);
        customer.setPincode(pincode);
        customer.setPassword(password);
        customer.setAddressLine(city + ", " + state + " - " + pincode);

        if (CustomerDAO.registerCustomer(customer)) {
            response.sendRedirect("login.jsp?msg=registered");
        } else {
            request.setAttribute("error", "Registration failed. See server console for details.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
}
    