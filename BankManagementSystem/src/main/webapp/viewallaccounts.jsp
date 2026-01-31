<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.Account" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Account Ledger | MyBank HQ</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
    <style>
        :root { --admin-navy: #0f172a; --accent: #4318ff; }
        body { background-color: #f4f7fe; font-family: 'Inter', sans-serif; }
        .admin-nav { background: var(--admin-navy); padding: 1rem 2rem; }
        .glass-card { background: white; border-radius: 24px; border: none; box-shadow: 0 4px 20px rgba(0,0,0,0.03); overflow: hidden; }
        .table thead th { background: #f1f5f9; color: #475569; font-size: 0.7rem; text-transform: uppercase; letter-spacing: 0.1em; padding: 1rem; border: none; }
        .acc-number { font-family: 'Courier New', monospace; font-weight: 700; color: var(--admin-navy); letter-spacing: 1px; }
        .status-pill { padding: 6px 14px; border-radius: 50px; font-size: 0.7rem; font-weight: 800; text-transform: uppercase; }
        .status-active { background: #dcfce7; color: #166534; }
        .status-locked { background: #fee2e2; color: #991b1b; }
        .btn-circle { width: 35px; height: 35px; border-radius: 50%; display: flex; align-items: center; justify-content: center; transition: 0.3s; }
    </style>
</head>
<body>

<nav class="navbar navbar-dark admin-nav mb-4 shadow">
    <div class="container-fluid">
        <a class="navbar-brand fw-bold" href="AdminDashboardServlet">🏦 Account Central</a>
        <a href="ExportAuditServlet" class="btn btn-primary rounded-pill btn-sm px-3">
            <i class="bi bi-download me-1"></i> Export CSV
        </a>
    </div>
</nav>

<div class="container-fluid px-lg-5">
    <div class="mb-4">
        <h3 class="fw-bold mb-1">Customer Accounts</h3>
        <p class="text-muted small">Manage balances, security status, and credentials</p>
    </div>

    <div class="card glass-card">
        <div class="table-responsive">
            <table class="table table-hover align-middle mb-0">
                <thead>
                    <tr>
                        <th class="ps-4">Account Number</th>
                        <th>User ID</th>
                        <th>Type</th>
                        <th>Balance</th>
                        <th>Status</th>
                        <th class="text-end pe-4">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                        List<Account> accounts = (List<Account>) request.getAttribute("accountList");
                        if (accounts != null && !accounts.isEmpty()) {
                            for (Account acc : accounts) {
                                String status = acc.getAccountStatus();
                                boolean isActive = "ACTIVE".equalsIgnoreCase(status);
                    %>
                        <tr>
                            <td class="ps-4 py-3"><span class="acc-number"><%= acc.getAccountNo() %></span></td>
                            <td><span class="text-muted">#</span><%= acc.getCustomerId() %></td>
                            <td><span class="badge bg-light text-dark rounded-pill px-3"><%= acc.getAccountType() %></span></td>
                            <td class="fw-bold text-primary">$<%= String.format("%.2f", acc.getBalance()) %></td>
                            <td>
                                <span class="status-pill <%= isActive ? "status-active" : "status-locked" %>">
                                    <%= status %>
                                </span>
                            </td>
                            <td class="text-end pe-4">
                                <div class="d-flex justify-content-end gap-2">
                                    <button class="btn btn-outline-warning btn-circle" data-bs-toggle="modal" data-bs-target="#resetModal<%= acc.getCustomerId() %>">
                                        <i class="bi bi-key"></i>
                                    </button>
                                    <a href="UpdateStatusServlet?id=<%= acc.getCustomerId() %>&currentStatus=<%= status %>" class="btn btn-outline-dark btn-circle">
                                        <i class="bi <%= isActive ? "bi-lock" : "bi-unlock" %>"></i>
                                    </a>
                                </div>
                            </td>
                        </tr>
                    <% } } else { %>
                        <tr><td colspan="6" class="text-center py-5">No records found.</td></tr>
                    <% } %>
                </tbody>
            </table>
        </div>
    </div>
</div>

<% if (accounts != null) { for (Account acc : accounts) { %>
<div class="modal fade" id="resetModal<%= acc.getCustomerId() %>" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content border-0 rounded-4 shadow-lg p-4">
            <form action="ResetPasswordServlet" method="POST">
                <div class="text-center mb-4">
                    <div class="avatar-circle mx-auto mb-3" style="width:60px; height:60px; font-size: 1.5rem;"><i class="bi bi-shield-lock"></i></div>
                    <h5 class="fw-bold">Security Reset</h5>
                    <p class="text-muted small">Customer ID: #<%= acc.getCustomerId() %></p>
                </div>
                <input type="hidden" name="customerId" value="<%= acc.getCustomerId() %>">
                <div class="mb-3">
                    <label class="form-label">New Temporary Password</label>
                    <input type="text" name="newPassword" class="form-control form-control-lg text-center fw-bold" value="Reset@2026">
                </div>
                <div class="d-grid gap-2">
                    <button type="submit" class="btn btn-primary rounded-pill py-2 fw-bold">Update Credentials</button>
                    <button type="button" class="btn btn-link text-muted" data-bs-dismiss="modal">Cancel</button>
                </div>
            </form>
        </div>
    </div>
</div>
<% } } %>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>