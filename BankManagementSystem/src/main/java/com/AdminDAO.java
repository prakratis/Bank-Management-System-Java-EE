package com;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {

  
    public static Admin login(String email, String password) {
        Admin admin = null;
        String sql = "SELECT * FROM admins WHERE email = ? AND password = ?";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, email);
            ps.setString(2, password);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    admin = new Admin();
                    admin.setAdminId(rs.getInt("admin_id"));
                    admin.setFullName(rs.getString("full_name"));
                    admin.setEmail(rs.getString("email"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return admin;
    }


    public static int getPendingRegistrationCount() {
        String sql = "SELECT COUNT(*) FROM customers WHERE status = 'PENDING'";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) { 
            e.printStackTrace();
            return 0; 
        }
    }

  
    public static int getActiveAccountCount() {
        
        String sql = "SELECT COUNT(*) FROM accounts";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) { 
            e.printStackTrace();
            return 0; 
        }
    }

    
    public static List<Customer> getPendingCustomers() {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM customers WHERE status = 'PENDING' ORDER BY created_at DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Customer c = new Customer();
                c.setCustomerId(rs.getInt("customer_id"));
                c.setFullName(rs.getString("full_name"));
                c.setEmail(rs.getString("email"));
                list.add(c);
            }
        } catch (Exception e) { 
            e.printStackTrace(); 
        }
        return list;
    }

  
    public static boolean updateStatus(int customerId, String status) {
        String sql = "UPDATE customers SET status = ?, approved_at = NOW() WHERE customer_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, customerId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false; 
        }
    }
    
    public static List<Account> getAllAccounts() {
        List<Account> list = new ArrayList<>();
        String sql = "SELECT * FROM accounts"; 
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
        	while (rs.next()) {
        	    Account acc = new Account();
        	    acc.setAccountNo(rs.getString("account_no"));
        	    acc.setCustomerId(rs.getInt("customer_id"));
        	    acc.setAccountType(rs.getString("account_type"));
        	    acc.setBalance(rs.getDouble("balance"));
        	    acc.setAccountStatus(rs.getString("status")); 
        	    acc.setOpenedAt(rs.getTimestamp("created_at")); 
        	    list.add(acc);
        	}
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public static boolean resetCustomerPassword(int customerId, String defaultPassword) {
      
        String sql = "UPDATE customers SET password = ? WHERE customer_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, defaultPassword);
            ps.setInt(2, customerId);
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}