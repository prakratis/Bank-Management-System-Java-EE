# 🏦 Bank Management System (Java EE)

A professional, full-stack banking application developed using **Java Enterprise Edition (JEE)**. This project implements a comprehensive **Model-View-Controller (MVC)** architecture to facilitate secure financial transactions, multi-role authentication, and persistent data management.



[Image of MVC architecture diagram]


## 🌟 Advanced Features
Beyond standard banking, this system includes sophisticated enterprise features:
* **Real-time Investment Portfolio**: Users can track and manage assets directly through the `investment.jsp` interface.
* **Automated Transaction Histories**: Every deposit and transfer is logged via `DepositServlet.java` to provide a full audit trail.
* **Admin Approval Workflow**: New customer registrations are held in a "Pending" state until an administrator uses `ApproveCustomerServlet.java` to verify them.
* **Dynamic Account Controls**: Admins can toggle account status (Active/Suspended) using `ChangeAccountStatusServlet.java`.
* **Profile Management**: Secure update logic for user personal details via `SettingsServlet.java` and `profile.jsp`.

## 🛡️ Security & Integrity Measures
* **Session-Based Authentication**: Prevents unauthorized access to dashboards; users are redirected to login if a session is invalid.
* **SQL Injection Protection**: Uses `PreparedStatement` within DAO classes (like `AdminDAO.java`) to ensure safe database interactions.
* **Role-Based Access Control (RBAC)**: Strict separation between `Admin.java` and `Customer.java` entities to protect sensitive administrative functions.

## 💻 Tech Stack
| Component | Technology |
| :--- | :--- |
| **Backend Language** | Java 17 |
| **Web Server** | Apache Tomcat 10.1 |
| **Database** | MySQL |
| **Framework** | Java EE (Servlets & JSP) |
| **Version Control** | Git / GitHub |

## 📁 File Organization
This project follows a clean **Package-by-Layer** strategy:
* `com.bank.dao`: Data Access Objects (The Database "Engine").
* `com.bank.servlet`: Controllers (The "Brains").
* `com.bank.model`: POJOs/Entities (The "Data").
* `webapp`: JSPs and static assets (The "Face").

---
### 🚀 Setup & Installation
1. **Clone the Repository**: `git clone https://github.com/prakratis/Bank-Management-System-Java-EE.git`
2. **Database Configuration**: Import the provided SQL schema and update `DBConnection.java` with your local MySQL credentials.
3. **Deployment**: Import the project into Eclipse and run on **Apache Tomcat 10.1**.
