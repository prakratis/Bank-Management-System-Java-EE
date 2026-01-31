package com;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.Map;

@WebServlet("/SettingsServlet")
public class SettingsServlet extends HttpServlet {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        Map<String, String> configs = SystemConfigDAO.getAllConfigs();
        request.setAttribute("configs", configs);
        
        request.getRequestDispatcher("/settings.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String interest = request.getParameter("interest_rate");
        String limit = request.getParameter("daily_limit");
        String maintenance = request.getParameter("maintenance_mode") != null ? "true" : "false";

        boolean success = SystemConfigDAO.updateSetting("interest_rate", interest) && 
                         SystemConfigDAO.updateSetting("daily_limit", limit) &&
                         SystemConfigDAO.updateSetting("maintenance_mode", maintenance);

        if (success) {
            response.sendRedirect("AdminDashboardServlet?status=success");
        } else {
            response.sendRedirect("SettingsServlet?status=error");
        }
    }
}