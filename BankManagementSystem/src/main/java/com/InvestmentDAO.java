package com;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvestmentDAO {

    /**
     * Creates a new Investment (FD/RD) and deducts the amount from the customer's savings account.
     * Fixed: Added case-insensitive 'savings' check and proper resource management.
     */
    public static boolean createInvestment(int customerId, String type, double amount, int duration) {

        String deductSql = "UPDATE accounts SET balance = balance - ? " +
                           "WHERE customer_id = ? AND LOWER(account_type) = 'savings' AND balance >= ?";
        
        String investSql = "INSERT INTO investments (customer_id, type, principal_amount, " +
                           "interest_rate, duration_months, status) VALUES (?, ?, ?, ?, ?, 'ACTIVE')";
        
        double rate = type.equalsIgnoreCase("FD") ? 7.5 : 6.8;


        try (Connection con = DBConnection.getConnection()) {
            if (con == null) return false;
            
            con.setAutoCommit(false); 

            try (PreparedStatement psDeduct = con.prepareStatement(deductSql);
                 PreparedStatement psInvest = con.prepareStatement(investSql)) {

      
                psDeduct.setDouble(1, amount);
                psDeduct.setInt(2, customerId);
                psDeduct.setDouble(3, amount);
                
                int rowsAffected = psDeduct.executeUpdate();

                
                if (rowsAffected == 0) {
                    con.rollback();
                    System.out.println("Investment Failed: Insufficient funds or no savings account found for ID: " + customerId);
                    return false; 
                }

                psInvest.setInt(1, customerId);
                psInvest.setString(2, type.toUpperCase()); 
                psInvest.setDouble(3, amount);
                psInvest.setDouble(4, rate);
                psInvest.setInt(5, duration);
                psInvest.executeUpdate();

                con.commit(); 
                return true;

            } catch (SQLException e) {
                if (con != null) con.rollback();
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Fetches all investments for a customer.
     * Fixed: Removed con.rollback() which was causing red errors in SELECT.
     */
    public static List<Investment> getInvestmentsByCustomer(int customerId) {
        List<Investment> list = new ArrayList<>();
        String sql = "SELECT * FROM investments WHERE customer_id = ? ORDER BY start_date DESC";
        

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            if (con == null) 
            	return list;
            ps.setInt(1, customerId);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Investment inv = new Investment();
                    inv.setId(rs.getInt("id")); 
                    inv.setCustomerId(rs.getInt("customer_id")); 
                    inv.setType(rs.getString("type"));
                    inv.setPrincipalAmount(rs.getDouble("principal_amount"));
                    inv.setInterestRate(rs.getDouble("interest_rate"));
                    inv.setDurationMonths(rs.getInt("duration_months"));
                    inv.setStartDate(rs.getTimestamp("start_date"));
                    inv.setStatus(rs.getString("status"));
                    list.add(inv);
                }
            }
        } catch (SQLException e) {
            
            e.printStackTrace();
        }
        return list;
    }
}
   