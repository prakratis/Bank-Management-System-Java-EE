<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Secure Login | Brick-by-Brick Bank</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
    <style>
        :root { --accent: #4361ee; --gradient: linear-gradient(135deg, #4361ee 0%, #7209b7 100%); }
        body { 
            background: #f0f2f5; 
            min-height: 100vh; 
            display: flex; align-items: center; justify-content: center;
            font-family: 'Inter', sans-serif;
        }
        .login-card {
            background: rgba(255, 255, 255, 0.9);
            backdrop-filter: blur(15px);
            border-radius: 24px;
            border: 1px solid rgba(255, 255, 255, 0.2);
            box-shadow: 0 20px 40px rgba(0,0,0,0.08);
            width: 100%; max-width: 400px;
            overflow: hidden;
        }
        .card-header-gradient {
            background: var(--gradient);
            padding: 30px; text-align: center; color: white;
        }
        .form-control {
            border-radius: 12px; padding: 12px; border: 1px solid #e0e0e0; background: #f9f9f9;
        }
        .form-control:focus { box-shadow: 0 0 0 3px rgba(67, 97, 238, 0.15); border-color: var(--accent); }
        .btn-primary {
            background: var(--gradient); border: none; border-radius: 12px;
            padding: 12px; font-weight: 600; transition: transform 0.2s;
        }
        .btn-primary:hover { transform: translateY(-2px); opacity: 0.9; }
    </style>
</head>
<body>

<div class="login-card">
    <div class="card-header-gradient">
        <i class="bi bi-bank2 fs-1 mb-2"></i>
        <h4 class="mb-0 fw-bold">MyBank Portal</h4>
        <p class="small opacity-75 mb-0">Secure Fintech Access</p>
    </div>
    
    <div class="p-4 pt-5">
        <% 
            String error = (String) request.getAttribute("error");
            if (error != null) { 
        %>
            <div class="alert alert-danger border-0 small rounded-4 py-2 text-center">
                <i class="bi bi-exclamation-circle me-2"></i> <%= error %>
            </div>
        <% } %>

        <form action="LoginServlet" method="post">
            <div class="mb-3">
                <label class="form-label small fw-bold text-muted">Email Address</label>
                <input type="email" name="email" class="form-control" placeholder="name@example.com" required>
            </div>
            <div class="mb-4">
                <label class="form-label small fw-bold text-muted">Password</label>
                <input type="password" name="password" class="form-control" placeholder="••••••••" required>
            </div>
            <button type="submit" class="btn btn-primary w-100 shadow">Sign In</button>
        </form>
    </div>
    
    <div class="text-center pb-5 px-4">
        <p class="small text-muted mb-0">New to MyBank? <a href="register.jsp" class="text-primary text-decoration-none fw-bold">Create Account</a></p>
        <p class="mt-4 text-muted" style="font-size: 10px;">&copy; 2026 Brick-by-Brick Bank Ltd.</p>
    </div>
</div>

</body>
</html>