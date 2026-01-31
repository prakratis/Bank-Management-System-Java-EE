<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.Customer, com.Account, com.Transaction" %>
<%
    Customer profile = (Customer) request.getAttribute("profile");
    List<Account> accountList = (List<Account>) request.getAttribute("accountList");
    List<Transaction> txList = (List<Transaction>) request.getAttribute("transactionList");
    
    String error = request.getParameter("error");
    String msg = request.getParameter("msg");

    if (profile == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en" data-theme="light">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard | MyBank Modern</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
    <style>
        :root {
            --primary: #4361ee;
            --secondary: #7209b7;
            --bg-body: #f8faff;
            --glass: rgba(255, 255, 255, 0.85);
            --text-main: #2b2d42;
            --card-border: rgba(255, 255, 255, 0.3);
            --input-bg: #f1f3f9;
        }

        [data-theme="dark"] {
            --bg-body: #0b0e14;
            --glass: rgba(20, 24, 33, 0.85);
            --text-main: #f8faff;
            --card-border: rgba(255, 255, 255, 0.08);
            --input-bg: rgba(255, 255, 255, 0.05);
        }

        body { 
            background-color: var(--bg-body); 
            color: var(--text-main);
            font-family: 'Segoe UI', sans-serif; 
            transition: all 0.3s ease;
        }
        
        .glass-card {
            background: var(--glass);
            backdrop-filter: blur(15px);
            -webkit-backdrop-filter: blur(15px);
            border-radius: 20px;
            border: 1px solid var(--card-border);
            box-shadow: 0 8px 32px 0 rgba(0, 0, 0, 0.05);
        }

        .credit-card-ui {
            background: linear-gradient(135deg, #4361ee 0%, #7209b7 100%);
            color: white;
            border-radius: 20px;
            padding: 25px;
            position: relative;
            overflow: hidden;
            box-shadow: 0 15px 35px rgba(67, 97, 238, 0.3);
        }

        .btn-action {
            border-radius: 12px; font-weight: 600; padding: 12px;
            transition: transform 0.2s ease;
        }
        .btn-action:hover { transform: translateY(-3px); }
        
        .transaction-row { border-bottom: 1px solid var(--card-border); transition: background 0.2s; }
        .transaction-row:hover { background: rgba(67, 97, 238, 0.05); }

        .glass-modal {
            background: var(--glass) !important;
            backdrop-filter: blur(20px) !important;
            -webkit-backdrop-filter: blur(20px) !important;
            border: 1px solid var(--card-border) !important;
            border-radius: 25px !important;
            color: var(--text-main);
        }

        .theme-toggle {
            cursor: pointer; width: 40px; height: 40px;
            display: flex; align-items: center; justify-content: center;
            border-radius: 50%; background: var(--input-bg); color: var(--primary);
        }

        .form-control, .form-select {
            background-color: var(--input-bg) !important;
            border: none !important;
            color: var(--text-main) !important;
        }

        .pin-input {
            letter-spacing: 0.5rem;
            text-align: center;
            font-weight: bold;
        }
    </style>
</head>
<body>

<div class="container-fluid py-4">
    <% if(error != null) { %>
        <div class="alert alert-danger glass-card border-0 mb-4 animate__animated animate__fadeIn">
            <i class="bi bi-exclamation-triangle-fill me-2"></i>
            <%= error.equals("wrongPin") ? "Invalid Transaction PIN. Please try again." : "Transaction failed. Please check details." %>
        </div>
    <% } %>
    <% if(msg != null) { %>
        <div class="alert alert-success glass-card border-0 mb-4">
            <i class="bi bi-check-circle-fill me-2"></i> Action completed successfully!
        </div>
    <% } %>

    <div class="row">
        <div class="col-lg-9 ps-lg-4">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <div>
                    <h2 class="fw-bold mb-0">Overview</h2>
                    <p class="text-muted">Welcome back, <%= profile.getFullName() %></p>
                </div>
                <div class="d-flex align-items-center gap-3">
                    <div class="theme-toggle" onclick="toggleTheme()">
                        <i class="bi bi-moon-stars-fill" id="themeIcon"></i>
                    </div>
                    <span class="badge bg-success-subtle text-success p-2 rounded-pill px-3">
                        <i class="bi bi-patch-check-fill"></i> <%= profile.getStatus().toUpperCase() %>
                    </span>
                    <a href="LogoutServlet" class="btn btn-outline-danger btn-sm rounded-pill px-3">Logout</a>
                </div>
            </div>

            <div class="row g-4 mb-4">
                <% if (accountList != null && !accountList.isEmpty()) { 
                    for (Account acc : accountList) { %>
                <div class="col-md-6 col-xl-4">
                    <div class="credit-card-ui">
                        <div class="d-flex justify-content-between mb-4">
                            <span class="small opacity-75"><%= acc.getAccountType().toUpperCase() %> ACCOUNT</span>
                            <i class="bi bi-wifi small"></i>
                        </div>
                        <h2 class="mb-4">$ <%= String.format("%.2f", acc.getBalance()) %></h2>
                        <div class="d-flex justify-content-between align-items-center">
                            <span class="font-monospace">**** <%= acc.getAccountNo().toString().substring(acc.getAccountNo().toString().length() - 4) %></span>
                            <img src="https://upload.wikimedia.org/wikipedia/commons/2/2a/Mastercard-logo.svg" height="25" alt="mastercard">
                        </div>
                    </div>
                </div>
                <% } } %>
            </div>

            <div class="row g-4 mb-4">
                <div class="col-12">
                    <div class="glass-card p-4">
                        <div class="d-flex justify-content-between align-items-center mb-3">
                            <h5 class="fw-bold mb-0">Investment Plans</h5>
                            <div class="d-flex gap-2">
                                <button class="btn btn-primary btn-sm rounded-pill px-3" data-bs-toggle="modal" data-bs-target="#fdModal">
                                    <i class="bi bi-plus-circle me-1"></i> New FD
                                </button>
                                <a href="investment.jsp" class="btn btn-dark btn-sm rounded-pill px-3">
                                    <i class="bi bi-pie-chart-fill me-1"></i> My Portfolio
                                </a>
                            </div>
                        </div>
                        <div class="row text-center">
                            <div class="col-md-6 border-end border-secondary-subtle">
                                <p class="text-muted small mb-1">Fixed Deposit Rate</p>
                                <h4 class="text-primary fw-bold">7.5% p.a.</h4>
                            </div>
                            <div class="col-md-6">
                                <p class="text-muted small mb-1">Recurring Deposit Rate</p>
                                <h4 class="text-secondary fw-bold">6.8% p.a.</h4>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="glass-card p-4">
                <h5 class="fw-bold mb-4">Recent Activity</h5>
                <div class="table-responsive">
                    <table class="table table-borderless align-middle" style="color: inherit;">
                        <thead class="text-muted small">
                            <tr>
                                <th>TRANSACTION</th>
                                <th>DATE</th>
                                <th>REFERENCE</th>
                                <th class="text-end">AMOUNT</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% if (txList != null && !txList.isEmpty()) { 
                                for (Transaction tx : txList) {
                                    boolean isCredit = tx.getTransactionType().contains("deposit") || tx.getTransactionType().contains("in");
                            %>
                            <tr class="transaction-row">
                                <td>
                                    <div class="d-flex align-items-center">
                                        <div class="rounded-circle p-2 me-3 <%= isCredit ? "bg-success-subtle text-success" : "bg-danger-subtle text-danger" %>">
                                            <i class="bi <%= isCredit ? "bi-arrow-down-left" : "bi-arrow-up-right" %>"></i>
                                        </div>
                                        <span class="fw-semibold text-capitalize"><%= tx.getTransactionType() %></span>
                                    </div>
                                </td>
                                <td class="text-muted small"><%= tx.getCreatedAt() %></td>
                                <td class="text-muted small">Ref: <%= tx.getReferenceAccount() != 0 ? tx.getReferenceAccount() : "N/A" %></td>
                                <td class="text-end fw-bold <%= isCredit ? "text-success" : "text-danger" %>">
                                    <%= isCredit ? "+" : "-" %>$<%= String.format("%.2f", tx.getAmount()) %>
                                </td>
                            </tr>
                            <% } } %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <div class="col-lg-3 mt-4 mt-lg-0">
            <div class="glass-card p-4 mb-4">
                <h6 class="fw-bold mb-3">Quick Transfer</h6>
                <form action="TransferServlet" method="post">
                    <div class="mb-3">
                        <label class="small text-muted mb-1">From Account</label>
                        <select name="senderAcc" class="form-select" required>
                            <% if (accountList != null) { for (Account a : accountList) { %>
                                <option value="<%= a.getAccountNo() %>"><%= a.getAccountNo() %></option>
                            <% } } %>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label class="small text-muted mb-1">Recipient Account</label>
                        <input type="text" name="receiverAcc" class="form-control" placeholder="Acc No" required>
                    </div>
                    <div class="mb-3">
                        <label class="small text-muted mb-1">Amount ($)</label>
                        <input type="number" name="amount" step="0.01" class="form-control" placeholder="0.00" required>
                    </div>
                    <div class="mb-3">
                        <label class="small text-muted mb-1">Transaction PIN</label>
                        <input type="password" name="pin" maxlength="4" class="form-control pin-input" placeholder="****" required>
                    </div>
                    <button type="submit" class="btn btn-primary w-100 btn-action shadow-sm">Send Money</button>
                </form>
            </div>

            <div class="d-flex gap-2">
                <button class="btn btn-success-subtle text-success flex-grow-1 btn-action" data-bs-toggle="modal" data-bs-target="#depositModal">Deposit</button>
                <button class="btn btn-danger-subtle text-danger flex-grow-1 btn-action" data-bs-toggle="modal" data-bs-target="#withdrawModal">Withdraw</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="fdModal" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
        <form action="InvestmentServlet" method="post" class="modal-content glass-modal shadow-lg">
            <input type="hidden" name="type" value="FD">
            <div class="modal-header border-0">
                <h5 class="fw-bold">Open Fixed Deposit</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <label class="small fw-bold mb-2">Amount ($)</label>
                <input type="number" name="amount" class="form-control mb-3" placeholder="Min $500" required>
                <label class="small fw-bold mb-2">Duration (Years)</label>
                <select name="duration" class="form-select mb-3">
                    <option value="1">1 Year</option>
                    <option value="3">3 Years</option>
                    <option value="5">5 Years</option>
                </select>
                <label class="small fw-bold mb-2">Transaction PIN</label>
                <input type="password" name="pin" maxlength="4" class="form-control pin-input" placeholder="****" required>
            </div>
            <div class="modal-footer border-0">
                <button type="submit" class="btn btn-primary w-100 btn-action">Create FD</button>
            </div>
        </form>
    </div>
</div>

<div class="modal fade" id="rdModal" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
        <form action="InvestmentServlet" method="post" class="modal-content glass-modal shadow-lg">
            <input type="hidden" name="type" value="RD">
            <div class="modal-header border-0">
                <h5 class="fw-bold">Start RD Plan</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <label class="small fw-bold mb-2">Monthly Contribution ($)</label>
                <input type="number" name="amount" class="form-control mb-3" placeholder="Min $50" required>
                <label class="small fw-bold mb-2">Duration (Months)</label>
                <select name="duration" class="form-select mb-3">
                    <option value="12">12 Months</option>
                    <option value="24">24 Months</option>
                </select>
                <label class="small fw-bold mb-2">Transaction PIN</label>
                <input type="password" name="pin" maxlength="4" class="form-control pin-input" placeholder="****" required>
            </div>
            <div class="modal-footer border-0">
                <button type="submit" class="btn btn-secondary w-100 btn-action">Start RD</button>
            </div>
        </form>
    </div>
</div>

<div class="modal fade" id="depositModal" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
        <form action="TransactionServlet" method="post" class="modal-content glass-modal shadow-lg">
            <input type="hidden" name="action" value="deposit">
            <div class="modal-header border-0">
                <h5 class="fw-bold">Deposit Funds</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <label class="small text-muted mb-2">Account</label>
                <select name="accountNo" class="form-select mb-3">
                    <% if (accountList != null) { for (Account a : accountList) { %>
                        <option value="<%= a.getAccountNo() %>"><%= a.getAccountNo() %></option>
                    <% } } %>
                </select>
                <label class="small text-muted mb-2">Amount ($)</label>
                <input type="number" name="amount" step="0.01" class="form-control mb-3" placeholder="0.00" required>
                <label class="small fw-bold mb-2">Transaction PIN</label>
                <input type="password" name="pin" maxlength="4" class="form-control pin-input" placeholder="****" required>
            </div>
            <div class="modal-footer border-0">
                <button type="submit" class="btn btn-primary w-100 btn-action">Confirm Deposit</button>
            </div>
        </form>
    </div>
</div>

<div class="modal fade" id="withdrawModal" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
        <form action="TransactionServlet" method="post" class="modal-content glass-modal shadow-lg">
            <input type="hidden" name="action" value="withdraw">
            <div class="modal-header border-0">
                <h5 class="fw-bold">Withdraw Funds</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <label class="small text-muted mb-2">Account</label>
                <select name="accountNo" class="form-select mb-3">
                    <% if (accountList != null) { for (Account a : accountList) { %>
                        <option value="<%= a.getAccountNo() %>"><%= a.getAccountNo() %></option>
                    <% } } %>
                </select>
                <label class="small text-muted mb-2">Amount ($)</label>
                <input type="number" name="amount" step="0.01" class="form-control mb-3" placeholder="0.00" required>
                <label class="small fw-bold mb-2">Transaction PIN</label>
                <input type="password" name="pin" maxlength="4" class="form-control pin-input" placeholder="****" required>
            </div>
            <div class="modal-footer border-0">
                <button type="submit" class="btn btn-danger w-100 btn-action">Confirm Withdrawal</button>
            </div>
        </form>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    function toggleTheme() {
        const body = document.documentElement;
        const icon = document.getElementById('themeIcon');
        if (body.getAttribute('data-theme') === 'dark') {
            body.setAttribute('data-theme', 'light');
            icon.classList.replace('bi-sun-fill', 'bi-moon-stars-fill');
        } else {
            body.setAttribute('data-theme', 'dark');
            icon.classList.replace('bi-moon-stars-fill', 'bi-sun-fill');
        }
    }
</script>
</body>
</html>