<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.Investment, com.Customer" %>
<%
    // 1. Get the user session from the dashboard context
    Customer profile = (Customer) session.getAttribute("user");
    
    // 2. Suppress the unchecked cast warning found in your IDE
    @SuppressWarnings("unchecked")
    List<Investment> investmentList = (List<Investment>) request.getAttribute("investmentList");

    // 3. Safety Check: If user hits this URL directly, redirect to Servlet to pull DB data
    if (profile == null) {
        response.sendRedirect("login.jsp");
        return;
    } else if (investmentList == null) {
        // This ensures the records (like the $500.00 FD) are fetched
        response.sendRedirect("DashboardServlet"); 
        return;
    }
%>
<!DOCTYPE html>
<html lang="en" data-theme="light">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Portfolio | MyBank Modern</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
    <style>
        :root {
            --bg-body: #f8faff;
            --glass: rgba(255, 255, 255, 0.85);
            --card-border: rgba(255, 255, 255, 0.3);
        }
        body { background-color: var(--bg-body); font-family: 'Segoe UI', sans-serif; }
        
        .glass-card {
            background: var(--glass);
            backdrop-filter: blur(15px);
            border-radius: 20px;
            border: 1px solid var(--card-border);
            box-shadow: 0 8px 32px 0 rgba(0, 0, 0, 0.05);
            padding: 30px;
            margin-top: 50px;
        }
        .status-badge { border-radius: 50px; padding: 5px 15px; font-size: 0.8rem; font-weight: 600; }
        .table thead th { border: none; color: #6c757d; font-size: 0.85rem; text-transform: uppercase; }
    </style>
</head>
<body>

<div class="container py-4">
    <div class="glass-card animate__animated animate__fadeIn">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <div>
                <h2 class="fw-bold mb-0">Investment Portfolio</h2>
                <p class="text-muted">Viewing active plans for <strong><%= profile.getFullName() %></strong></p>
            </div>
            <a href="DashboardServlet" class="btn btn-outline-primary rounded-pill px-4 shadow-sm">
                <i class="bi bi-house-door me-2"></i> Dashboard
            </a>
        </div>

        <div class="table-responsive">
            <table class="table table-hover align-middle">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Investment Type</th>
                        <th>Principal Amount</th>
                        <th>Interest Rate</th>
                        <th>Term</th>
                        <th>Current Status</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                        // Iterating through records found in your MySQL terminal
                        if (investmentList != null && !investmentList.isEmpty()) {
                            for (Investment inv : investmentList) {
                    %>
                    <tr>
                        <td class="fw-bold text-primary">#<%= inv.getId() %></td>
                        <td>
                            <span class="badge bg-primary-subtle text-primary px-3 py-2">
                                <i class="bi bi-piggy-bank me-1"></i> <%= inv.getType() %>
                            </span>
                        </td>
                        <td class="fw-bold text-dark">$ <%= String.format("%.2f", inv.getPrincipalAmount()) %></td>
                        <td class="text-success fw-semibold"><%= inv.getInterestRate() %>% p.a.</td>
                        <td class="text-muted"><%= inv.getDurationMonths() %> Months</td>
                        <td>
                            <span class="status-badge <%= inv.getStatus().equalsIgnoreCase("ACTIVE") ? "bg-success-subtle text-success" : "bg-secondary-subtle text-muted" %>">
                                <i class="bi bi-circle-fill me-1 small"></i> <%= inv.getStatus() %>
                            </span>
                        </td>
                    </tr>
                    <% 
                            }
                        } else { 
                    %>
                    <tr>
                        <td colspan="6" class="text-center py-5">
                            <i class="bi bi-inbox text-muted display-4 mb-3 d-block"></i>
                            <p class="text-muted fw-semibold">No active investments found.</p>
                            <a href="DashboardServlet" class="btn btn-sm btn-primary rounded-pill">Open New Deposit</a>
                        </td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
    </div>
</div>

</body>
</html>