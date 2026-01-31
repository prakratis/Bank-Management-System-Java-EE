Bank Management System (Java EE)
🌟 Advanced Features
Beyond standard banking, this system includes sophisticated enterprise features:

Real-time Investment Portfolio: Users can track and manage assets directly through the investment.jsp interface.

Automated Transaction Histories: Every deposit and transfer is logged via DepositServlet.java to provide a full audit trail.

Admin Approval Workflow: New customer registrations are held in a "Pending" state until an administrator uses ApproveCustomerServlet.java to verify them.

Dynamic Account Controls: Admins can toggle account status (Active/Suspended) using ChangeAccountStatusServlet.java.

Profile Management: Secure update logic for user personal details via SettingsServlet.java and profile.jsp.

🛡️ Security & Integrity Measures
Session-Based Authentication: Prevents unauthorized access to dashboards; users are redirected to login if a session is invalid.

SQL Injection Protection: Uses PreparedStatement within DAO classes (like AdminDAO.java) to ensure safe database interactions.

Password Security: Implements ResetPasswordServlet.java to handle secure credential recovery.

Role-Based Access Control (RBAC): Strict separation between Admin.java and Customer.java entities to protect sensitive administrative functions.

📊 Database Schema Highlights
The system relies on a relational MySQL structure managed by DBConnection.java:

Customers Table: Stores PII (Personally Identifiable Information) and authentication credentials.

Accounts Table: Tracks balances and account types (Savings/Current).

Transactions Table: A relational log linking customers to their financial history.

📁 File Organization
This project follows a clean Package-by-Layer strategy:

com.bank.dao: Data Access Objects (The Database "Engine").

com.bank.servlet: Controllers (The "Brains").

com.bank.model: POJOs/Entities (The "Data").

webapp: JSPs and static assets (The "Face").
