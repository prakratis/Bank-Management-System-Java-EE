package com;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Servlet implementation class ExportAuditServlet
 */
@WebServlet("/ExportAuditServlet")
public class ExportAuditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=bank_audit_2026.csv");

        try (PrintWriter writer = response.getWriter()) {
            writer.println("Account No,Customer ID,Type,Balance,Status,Opened At");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "1234");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM accounts");

            while (rs.next()) {
                writer.printf("%s,%d,%s,%.2f,%s,%s\n",
                    rs.getString("account_no"),
                    rs.getInt("customer_id"),
                    rs.getString("account_type"),
                    rs.getDouble("balance"),
                    rs.getString("account_status"),
                    rs.getTimestamp("opened_at"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}