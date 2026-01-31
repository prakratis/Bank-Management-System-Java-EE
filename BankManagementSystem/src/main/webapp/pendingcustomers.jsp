<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.Customer" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Verification Queue | MyBank HQ</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
    <style>
        :root { 
            --primary-accent: #4318ff; /* The vibrant purple from your reference */
            --bg-body: #f4f7fe; 
            --success-soft: #dcfce7;
            --success-text: #166534;
            --danger-soft: #fee2e2;
            --danger-text: #991b1b;
        }
        
        body { background-color: var(--bg-body); font-family: 'Inter', sans-serif; color: #2b3674; }
        
        /* Sidebar-aware Navbar matching dashboard theme */
        .admin-nav { 
            background: rgba(255, 255, 255, 0.8); 
            backdrop-filter: blur(10px);
            padding: 1rem 2rem; 
            border-bottom: 1px solid #eef2f6; 
        }

        .glass-card { 
            background: white; 
            border-radius: 20px; 
            border: none; 
            box-shadow: 0 4px 25px rgba(0,0,0,0.05); 
        }

        /* Applicant ID/Avatar Style */
        .avatar-circle { 
            width: 45px; height: 45px; 
            background: #f4f1ff; 
            display: flex; align-items: center; justify-content: center; 
            border-radius: 12px; color: var(--primary-accent); 
            font-weight: 700; 
        }

        /* Clean Table Style */
        .table thead th { 
            background: transparent; 
            color: #a3afd1; 
            font-size: 0.75rem; 
            text-transform: uppercase; 
            letter-spacing: 0.1em; 
            padding: 1.5rem 1rem;
            border-bottom: 1px solid #eef2f6;
        }

        .customer-row td { padding: 1.2rem 1rem; vertical-align: middle; border-bottom: 1px solid #f4f7fe; }

        /* Unified Action Buttons */
        .btn-action { 
            border-radius: 12px; 
            font-weight: 700; 
            padding: 10px 24px; 
            font-size: 0.85rem; 
            transition: 0.3s; 
            border: none;
            display: inline-flex;
            align-items: center;
        }

        .btn-approve { background: var(--success-soft); color: var(--success-text); }
        .btn-approve:hover { background: var(--success-text); color: white; transform: translateY(-2px); }

        .btn-reject { background: var(--danger-soft); color: var(--danger-text); }
        .btn-reject:hover { background: var(--danger-text); color: white; transform: translateY(-2px); }

        .badge-id { background: #f4f7fe; color: #707eae; border: none; font-family: 'Monaco', monospace; }
    </style>
</head>
<body>

<nav class="navbar navbar-light admin-nav mb-4 sticky-top">
    <div class="container-fluid">
        <a class="navbar-brand fw-bold d-flex align-items-center text-dark" href="AdminDashboardServlet">
            <div class="bg-primary rounded-3 p-1 me-2 d-flex align-items-center justify-content-center" style="width: 32px; height: 32px; background: var(--primary-accent) !important;">
                <i class="bi bi-shield-check text-white fs-5"></i>
            </div>
            Verification Hub
        </a>
        <div class="d-flex align-items-center">
            <span class="text-muted small me-3 fw-medium">Admin Session</span>
            <a href="LogoutServlet" class="btn btn-outline-danger btn-sm rounded-pill px-3 fw-bold">Logout</a>
        </div>
    </div>
</nav>

<div class="container">
    <div class="row mb-4 align-items-center px-2">
        <div class="col">
            <h2 class="fw-bold mb-1" style="color: #1b2559;">Pending Approvals</h2>
            <p class="text-muted small mb-0">Identity verification required for new user access</p>
        </div>
        <div class="col-auto">
            <div class="glass-card px-4 py-2 d-flex align-items-center">
                <span class="text-muted small fw-bold me-2">ACTIVE QUEUE:</span>
                <span class="h4 mb-0 fw-bold text-primary">
                    <%= request.getAttribute("pendingList") != null ? ((List)request.getAttribute("pendingList")).size() : 0 %>
                </span>
            </div>
        </div>
    </div>

    <div class="card glass-card border-0">
        <div class="table-responsive">
            <table class="table mb-0">
                <thead>
                    <tr>
                        <th class="ps-4">Applicant Profile</th>
                        <th>Contact info</th>
                        <th>National ID</th>
                        <th class="text-end pe-4">Verification Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<Customer> pendingList = (List<Customer>) request.getAttribute("pendingList");
                        if (pendingList != null && !pendingList.isEmpty()) {
                            for (Customer cust : pendingList) {
                    %>
                        <tr class="customer-row">
                            <td class="ps-4">
                                <div class="d-flex align-items-center">
                                    <div class="avatar-circle me-3">
                                        <%= cust.getFullName().substring(0,1).toUpperCase() %>
                                    </div>
                                    <div>
                                        <div class="fw-bold text-dark" style="font-size: 0.95rem;"><%= cust.getFullName() %></div>
                                        <div class="text-muted extra-small">Ref: #ACC-<%= cust.getCustomerId() %></div>
                                    </div>
                                </div>
                            </td>
                            <td>
                                <div class="small fw-medium text-dark"><%= cust.getEmail() %></div>
                                <div class="small text-muted"><i class="bi bi-phone me-1"></i> <%= cust.getMobile() %></div>
                            </td>
                            <td>
                                <span class="badge badge-id p-2 px-3 rounded-pill fw-bold">
                                    <%= cust.getIdNumber() %>
                                </span>
                            </td>
                            <td class="text-end pe-4">
                                <div class="d-flex justify-content-end gap-2">
                                    <a href="UpdateStatusServlet?id=<%= cust.getCustomerId() %>&status=approved" 
                                       class="btn-action btn-approve text-decoration-none">
                                        <i class="bi bi-check-lg me-2"></i> Approve
                                    </a>
                                    <a href="UpdateStatusServlet?id=<%= cust.getCustomerId() %>&status=rejected" 
                                       class="btn-action btn-reject text-decoration-none">
                                        <i class="bi bi-x-lg me-2"></i> Reject
                                    </a>
                                </div>
                            </td>
                        </tr>
                    <% 
                            } 
                        } else { 
                    %>
                        <tr>
                            <td colspan="4" class="text-center py-5">
                                <div class="py-4">
                                    <i class="bi bi-clipboard-check display-1 text-light mb-3"></i>
                                    <h4 class="text-muted fw-bold">Queue is Empty</h4>
                                    <p class="text-muted small">All new registrations have been processed.</p>
                                </div>
                            </td>
                        </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>