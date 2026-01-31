package com;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

 @WebFilter("/*")
public class MaintenanceFilter implements Filter {
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri = req.getRequestURI();

        String isMaint = SystemConfigDAO.getSetting("maintenance_mode");
        
        System.out.println("DEBUG: Maintenance Mode Value is: " + isMaint);

        boolean isMaintenancePage = uri.endsWith("maintenance.jsp");
        boolean isAdminPath = uri.contains("/Admin") || uri.contains("SettingsServlet") || uri.contains("Login");
        boolean isStaticResource = uri.endsWith(".css") || uri.endsWith(".js") || uri.contains("/images/");

        if ("true".equalsIgnoreCase(isMaint) && !isMaintenancePage && !isAdminPath && !isStaticResource) {
            res.sendRedirect(req.getContextPath() + "/maintenance.jsp");
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}