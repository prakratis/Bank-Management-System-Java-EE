<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    HttpSession sess = request.getSession(false);
    if (sess == null || !"ADMIN".equals(sess.getAttribute("role"))) {
        response.sendRedirect("login.jsp");
        return;
    }
    Integer pending = (Integer) request.getAttribute("pendingCount");
    Integer accounts = (Integer) request.getAttribute("totalAccounts");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard | Brick-by-Brick Bank</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
    <style>
        body { background-color: #f4f7fe; font-family: 'Inter', sans-serif; overflow-x: hidden; }
        
        /* Sidebar Styling */
        .sidebar { 
            width: 280px; height: 100vh; position: fixed; 
            background: white; border-right: 1px solid #eef2f6;
            padding: 2rem 1.5rem;
        }
        .nav-link { 
            color: #a3afd1; border-radius: 12px; margin-bottom: 5px; 
            padding: 12px 15px; font-weight: 500; transition: 0.3s;
        }
        .nav-link.active { background: #4318ff; color: white; box-shadow: 0 10px 20px rgba(67, 24, 255, 0.2); }
        .nav-link:hover:not(.active) { background: #f4f7fe; color: #4318ff; }

        /* Content Area */
        .main-content { margin-left: 280px; padding: 2.5rem; }

        /* Metric Cards from Reference Image */
        .stat-card {
            border: none; border-radius: 20px; padding: 20px;
            display: flex; align-items: center; background: white;
            box-shadow: 0 4px 25px rgba(0,0,0,0.02);
        }
        .icon-box {
            width: 56px; height: 56px; border-radius: 50%;
            display: flex; align-items: center; justify-content: center;
            font-size: 1.5rem; margin-right: 15px;
        }
        .bg-purple { background: #f4f1ff; color: #4318ff; }
        .bg-blue { background: #e7f0ff; color: #0075ff; }
        .bg-orange { background: #fff5e9; color: #ffb547; }
    </style>
</head>
<body>

<div class="sidebar shadow-sm">
    <h4 class="fw-bold mb-5"><i class="bi bi-bank2 me-2 text-primary"></i> Brick Bank</h4>
    <nav class="nav flex-column">
        <a class="nav-link active" href="AdminDashboardServlet"><i class="bi bi-grid-fill me-2"></i> Dashboard</a>
        <a class="nav-link" href="ViewAllAccountsServlet"><i class="bi bi-people-fill me-2"></i> Customers</a>
        <a class="nav-link" href="PendingCustomersServlet"><i class="bi bi-clock-history me-2"></i> Approvals</a>
        <a class="nav-link" href="SettingsServlet"><i class="bi bi-gear-fill me-2"></i> System Rules</a>
        <hr class="text-light">
        <a class="nav-link text-danger mt-5" href="LogoutServlet"><i class="bi bi-box-arrow-right me-2"></i> Sign Out</a>
    </nav>
</div>

<div class="main-content">
    <div class="d-flex justify-content-between align-items-center mb-5">
        <h2 class="fw-bold">Main Dashboard</h2>
        <div class="bg-white p-2 px-3 rounded-4 shadow-sm small fw-bold">
            <i class="bi bi-calendar3 me-2"></i> <%= new java.util.Date() %>
        </div>
    </div>

    <div class="row g-4 mb-5">
        <div class="col-md-4">
            <div class="stat-card">
                <div class="icon-box bg-purple"><i class="bi bi-person-badge"></i></div>
                <div>
                    <p class="text-muted mb-0 small fw-bold">Total Accounts</p>
                    <h3 class="fw-bold mb-0"><%= (accounts != null) ? accounts : 0 %></h3>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="stat-card">
                <div class="icon-box bg-orange"><i class="bi bi-hourglass-split"></i></div>
                <div>
                    <p class="text-muted mb-0 small fw-bold">Pending Approval</p>
                    <h3 class="fw-bold mb-0 text-warning"><%= (pending != null) ? pending : 0 %></h3>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="stat-card">
                <div class="icon-box bg-blue"><i class="bi bi-shield-check"></i></div>
                <div>
                    <p class="text-muted mb-0 small fw-bold">System Status</p>
                    <h3 class="fw-bold mb-0 text-success">Healthy</h3>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-md-8">
            <div class="card border-0 rounded-5 p-4 shadow-sm mb-4">
                <h5 class="fw-bold mb-4">System Activity Overview</h5>
                <div class="p-5 text-center bg-light rounded-4 border-dashed">
                    <i class="bi bi-graph-up-arrow display-4 text-muted"></i>
                    <p class="mt-3 text-muted">Charts and real-time logs will appear here</p>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card border-0 rounded-5 p-4 shadow-sm bg-primary text-white">
                <h5 class="fw-bold mb-3">Quick Actions</h5>
                <div class="d-grid gap-2">
                    <a href="ExportAuditServlet" class="btn btn-light rounded-pill fw-bold text-primary py-2">
                        <i class="bi bi-download me-2"></i> Export Audit
                    </a>
                    <a href="SettingsServlet" class="btn btn-primary border-white rounded-pill fw-bold py-2">
                        <i class="bi bi-percent me-2"></i> Adjust Rates
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>