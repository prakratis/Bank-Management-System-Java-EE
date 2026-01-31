<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>System Error</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light d-flex align-items-center" style="height: 100vh;">

    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-6 text-center">
                <div class="card shadow border-0 p-5">
                    <div class="mb-4">
                        <h1 class="display-1 text-danger">⚠️</h1>
                    </div>
                    
                    <h2 class="fw-bold text-dark">Something went wrong</h2>
                    <p class="text-muted">
                        We're sorry, but the bank's system encountered an unexpected error. 
                        Our team has been notified.
                    </p>

                    <%-- For development only: show the error message --%>
                    <div class="alert alert-secondary small text-start mt-3">
                        <strong>Error Details:</strong> ${pageContext.exception.message != null ? pageContext.exception.message : "Unexpected Server Error"}
                    </div>

                    <div class="mt-4 d-grid gap-2 d-sm-flex justify-content-sm-center">
                        <a href="DashboardServlet" class="btn btn-primary px-4">Back to Dashboard</a>
                        <a href="login.jsp" class="btn btn-outline-secondary px-4">Login Again</a>
                    </div>
                </div>
            </div>
        </div>
    </div>

</body>
</html>