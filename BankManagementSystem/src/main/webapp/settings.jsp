<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Map" %>
<%
    Map<String, String> configs = (Map<String, String>) request.getAttribute("configs");
    String interest = (configs != null) ? configs.get("interest_rate") : "4.50";
    String limit = (configs != null) ? configs.get("daily_limit") : "5000.00";
    String maintenance = (configs != null) ? configs.get("maintenance_mode") : "false";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>System Settings | MyBank HQ</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
    <style>
        :root { --admin-navy: #0f172a; --accent: #4318ff; }
        body { background-color: #f4f7fe; font-family: 'Inter', sans-serif; }
        .admin-nav { background: var(--admin-navy); padding: 1rem 2rem; }
        .glass-card { background: white; border-radius: 24px; border: none; box-shadow: 0 10px 30px rgba(0,0,0,0.03); overflow: hidden; }
        .settings-header { background: var(--admin-navy); color: white; padding: 2rem; }
        .form-control-lg { border-radius: 12px; background: #f8fafc; border: 1px solid #e2e8f0; font-weight: 600; }
        .form-label { font-size: 0.8rem; font-weight: 700; color: #64748b; text-transform: uppercase; }
        .btn-deploy { background: var(--accent); color: white; border-radius: 15px; padding: 15px; font-weight: 700; border: none; transition: 0.3s; }
        .btn-deploy:hover { transform: translateY(-3px); box-shadow: 0 10px 20px rgba(67, 24, 255, 0.2); }
    </style>
</head>
<body>

<nav class="navbar navbar-dark admin-nav mb-5">
    <div class="container-fluid">
        <a class="navbar-brand fw-bold" href="AdminDashboardServlet">🏦 System Rules</a>
    </div>
</nav>

<div class="container">
    <div class="row justify-content-center">
        <div class="col-lg-7">
            <div class="glass-card">
                <div class="settings-header text-center">
                    <i class="bi bi-gear-wide-connected display-4 mb-3"></i>
                    <h4 class="fw-bold">Global Configuration</h4>
                    <p class="opacity-75 small mb-0">Changes take effect immediately across all accounts</p>
                </div>
                <div class="p-5">
                    <form action="SettingsServlet" method="POST">
                        <div class="row g-4">
                            <div class="col-md-6">
                                <label class="form-label">Annual Interest Rate</label>
                                <div class="input-group input-group-lg">
                                    <input type="number" step="0.01" name="interest_rate" class="form-control-lg form-control" value="<%= interest %>">
                                    <span class="input-group-text bg-white border-start-0">%</span>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <label class="form-label">Daily Transfer Limit</label>
                                <div class="input-group input-group-lg">
                                    <span class="input-group-text bg-white border-end-0">$</span>
                                    <input type="number" step="0.01" name="daily_limit" class="form-control-lg form-control" value="<%= limit %>">
                                </div>
                            </div>
                            <div class="col-12 mt-4">
                                <div class="p-4 rounded-4 border bg-danger-subtle d-flex align-items-center">
                                    <div class="form-check form-switch flex-grow-1">
                                        <input class="form-check-input scale-150" type="checkbox" name="maintenance_mode" id="mMode" <%= "true".equals(maintenance) ? "checked" : "" %>>
                                        <label class="form-check-label fw-bold ms-3" for="mMode">Enable Maintenance Mode</label>
                                    </div>
                                    <i class="bi bi-exclamation-octagon text-danger fs-3"></i>
                                </div>
                                <p class="extra-small text-muted mt-2 ps-2">This will temporarily block customer logins.</p>
                            </div>
                        </div>
                        <div class="mt-5 d-flex gap-2">
                            <a href="AdminDashboardServlet" class="btn btn-light px-4 rounded-pill fw-bold py-2">Back</a>
                            <button type="submit" class="btn btn-deploy flex-grow-1">Deploy Changes</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>