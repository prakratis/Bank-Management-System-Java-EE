<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Join MyBank | New Registration</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
    <style>
        :root { --accent: #4361ee; --gradient: linear-gradient(135deg, #4361ee 0%, #7209b7 100%); }
        body { background: #f0f2f5; font-family: 'Inter', sans-serif; }
        .glass-container {
            background: rgba(255, 255, 255, 0.95);
            backdrop-filter: blur(10px);
            border-radius: 30px;
            border: 1px solid rgba(255, 255, 255, 0.3);
            box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.1);
            margin: 50px auto; max-width: 800px;
        }
        .header-section {
            background: var(--gradient);
            border-radius: 30px 30px 0 0;
            padding: 40px; color: white; text-align: center;
        }
        .form-label { font-size: 0.85rem; font-weight: 600; color: #64748b; }
        .form-control, .form-select {
            border-radius: 12px; border: 1px solid #e2e8f0; padding: 10px 15px; background: #f8fafc;
        }
        .form-control:focus { border-color: var(--accent); box-shadow: 0 0 0 4px rgba(67, 97, 238, 0.1); }
        .btn-register {
            background: var(--gradient); border: none; border-radius: 12px;
            padding: 15px; font-weight: 700; color: white; transition: all 0.3s ease;
        }
        .btn-register:hover { transform: translateY(-2px); box-shadow: 0 10px 20px rgba(67, 97, 238, 0.3); }
    </style>
</head>
<body>

<div class="container">
    <div class="glass-container">
        <div class="header-section">
            <h2 class="fw-bold mb-1">Create Your Account</h2>
            <p class="mb-0 opacity-75 small">Join our secure digital banking network</p>
        </div>

        <div class="p-5">
            <% String error = (String) request.getAttribute("error");
               if(error != null) { %>
                <div class="alert alert-danger border-0 rounded-4 py-2 small mb-4"><i class="bi bi-x-circle me-2"></i><%= error %></div>
            <% } %>

            <form action="RegisterServlet" method="post">
                <div class="row g-3">
                    <div class="col-12 mb-2">
                        <label class="form-label"><i class="bi bi-person me-2"></i>Full Name</label>
                        <input type="text" name="fullName" class="form-control" placeholder="John Doe" required>
                    </div>
                    
                    <div class="col-md-6">
                        <label class="form-label">Email Address</label>
                        <input type="email" name="email" class="form-control" placeholder="john@example.com" required>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">Mobile Number</label>
                        <input type="text" name="mobile" class="form-control" placeholder="1234567890" required>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">Date of Birth</label>
                        <input type="date" name="dob" class="form-control" required>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">Gender</label>
                        <select name="gender" class="form-select" required>
                            <option value="male">Male</option>
                            <option value="female">Female</option>
                            <option value="other">Other</option>
                        </select>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">ID Type</label>
                        <select name="idType" class="form-select" required>
                            <option value="aadhaar">Aadhaar</option>
                            <option value="pan">Pan Card</option>
                            <option value="passport">Passport</option>
                        </select>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">ID Number</label>
                        <input type="text" name="idNumber" class="form-control" placeholder="A1234567" required>
                    </div>

                    <div class="col-12">
                        <label class="form-label">Address (City, State, Pincode)</label>
                        <div class="row g-2">
                            <div class="col-md-4"><input type="text" name="city" class="form-control" placeholder="City" required></div>
                            <div class="col-md-4"><input type="text" name="state" class="form-control" placeholder="State" required></div>
                            <div class="col-md-4"><input type="text" name="pincode" class="form-control" placeholder="Pincode" required></div>
                        </div>
                    </div>

                    <div class="col-md-6 mt-4">
                        <label class="form-label">Create Password</label>
                        <input type="password" name="password" class="form-control" required>
                    </div>

                    <div class="col-md-6 mt-4">
                        <label class="form-label">Confirm Password</label>
                        <input type="password" name="confirmPassword" class="form-control" required>
                    </div>
                </div>

                <button type="submit" class="btn btn-register w-100 mt-5">Open My Account</button>
            </form>
            
            <div class="text-center mt-4">
                <p class="small text-muted">Already have an account? <a href="login.jsp" class="text-primary fw-bold text-decoration-none">Login Here</a></p>
            </div>
        </div>
    </div>
</div>

</body>
</html>