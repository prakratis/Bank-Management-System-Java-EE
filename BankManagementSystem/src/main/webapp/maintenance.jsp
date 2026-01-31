<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>System Maintenance | MyBank HQ</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    
    <style>
        :root {
            --admin-navy: #0b1437; /* Matches your Admin Panel/Account Central */
            --accent-purple: #4318ff;
            --text-muted: #a3afd1;
        }

        body {
            background-color: var(--admin-navy);
            color: #ffffff;
            font-family: 'Inter', system-ui, -apple-system, sans-serif;
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 0;
            overflow: hidden;
        }

        .maintenance-card {
            background: rgba(255, 255, 255, 0.03);
            backdrop-filter: blur(10px);
            border: 1px solid rgba(255, 255, 255, 0.1);
            border-radius: 32px;
            padding: 4rem 3rem;
            text-align: center;
            max-width: 500px;
            width: 90%;
            box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.5);
        }

        .icon-box {
            width: 80px;
            height: 80px;
            background: var(--accent-purple);
            border-radius: 20px;
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 0 auto 2rem;
            font-size: 2.5rem;
            box-shadow: 0 0 30px rgba(67, 24, 255, 0.4);
            animation: pulse 2s infinite;
        }

        h1 { font-weight: 800; letter-spacing: -1px; margin-bottom: 1rem; }
        p { color: var(--text-muted); line-height: 1.6; margin-bottom: 2.5rem; }

        .btn-admin {
            background: transparent;
            border: 1px solid rgba(255, 255, 255, 0.2);
            color: #ffffff;
            border-radius: 12px;
            padding: 0.8rem 1.5rem;
            text-decoration: none;
            font-weight: 600;
            transition: all 0.3s ease;
        }

        .btn-admin:hover {
            background: rgba(255, 255, 255, 0.05);
            border-color: #ffffff;
        }

        @keyframes pulse {
            0% { transform: scale(1); opacity: 1; }
            50% { transform: scale(1.05); opacity: 0.8; }
            100% { transform: scale(1); opacity: 1; }
        }
    </style>
</head>
<body>

    <div class="maintenance-card">
        <div class="icon-box">
            <i class="bi bi-tools"></i>
        </div>
        
        <h1>Under Maintenance</h1>
        <p>We're currently performing some scheduled upgrades to enhance your banking experience. We'll be back online shortly!</p>
        
        <div class="d-flex flex-column gap-3">
            <a href="login.jsp" class="btn-admin">
                <i class="bi bi-shield-lock me-2"></i> Admin Staff Access
            </a>
            <small class="text-secondary">Expected uptime: ~15 minutes</small>
        </div>
    </div>

</body>
</html>