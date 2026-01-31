package com;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    public static boolean createAccount(Account acc) {
        
        String sql = "INSERT INTO accounts (account_no, customer_id, account_type, balance, account_status) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            
            ps.setLong(1, Long.parseLong(acc.getAccountNo().replace("BNK", ""))); 
            ps.setInt(2, acc.getCustomerId());
            ps.setString(3, acc.getAccountType().toLowerCase()); // matches enum
            ps.setDouble(4, acc.getBalance());
            ps.setString(5, acc.getAccountStatus().toLowerCase()); // matches enum 'active'
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public static List<Account> getAccountsByCustomerId(int customerId) {
       
        List<Account> accountList = new ArrayList<>();
        String sql = "SELECT * FROM accounts WHERE customer_id = ?";

        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            
            ps.setInt(1, customerId);
            
            
            try (ResultSet rs = ps.executeQuery()) {
                
               
            	while (rs.next()) {
            	    Account acc = new Account();
            	    acc.setAccountNo(String.valueOf(rs.getLong("account_no"))); 
            	    acc.setAccountType(rs.getString("account_type"));
            	    acc.setBalance(rs.getDouble("balance"));
            	    acc.setAccountStatus(rs.getString("account_status"));
            	    accountList.add(acc);
            	}
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return accountList;
    }
    
    public static Account getAccountByNo(long accountNo) {
     
        String sql = "SELECT * FROM accounts WHERE account_no = ?";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setLong(1, accountNo);
            try (ResultSet rs = ps.executeQuery()) {
         
                if (rs.next()) {
                    Account acc = new Account();
                    acc.setAccountNo(String.valueOf(rs.getLong("account_no")));
                    acc.setCustomerId(rs.getInt("customer_id"));
                    acc.setAccountType(rs.getString("account_type"));
                    acc.setBalance(rs.getDouble("balance"));
                    acc.setAccountNo(String.valueOf(rs.getLong("account_no")));
                    acc.setOpenedAt(rs.getTimestamp("opened_at"));
                    
                    return acc; 
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static boolean updateBalance(long accountNo , double newBalance) {
    	String sql = "UPDATE accounts SET balance = ? WHERE account_no = ? ";
    	try(Connection con = DBConnection.getConnection();
    		PreparedStatement ps = con.prepareStatement(sql)){
    		ps.setDouble(1, newBalance);
    		ps.setLong(2, accountNo);
    		
    		int rowsAffected = ps.executeUpdate();
    		return rowsAffected > 0;
    		
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    	return false;
    }
    
    public static boolean freezeOrCloseAccount(long accountNo , String newStatus ) {
    
    	String sql = "UPDATE accounts SET account_status = ? WHERE account_no = ? ";
    	
    	// Flowchart : get DB Connection
    	try (Connection con = DBConnection.getConnection();
    		PreparedStatement ps = con.prepareStatement(sql)){
    		
	    	ps.setString(1, newStatus);
	    	ps.setLong(2, accountNo);
	    	int rowsAffected = ps.executeUpdate();
	    	return rowsAffected > 0; 
    	
    	}catch (Exception e) {
    		e.printStackTrace();
    	}
    	

    	return false;
    	
    }
    public static boolean isAccountValid(long accountNo, int customerId) {

        String sql = "SELECT * FROM accounts WHERE account_no = ? AND customer_id = ? AND account_status = 'active'";
        

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
       
            ps.setLong(1, accountNo);
            ps.setInt(2, customerId);
            
            
            try (ResultSet rs = ps.executeQuery()) {
              
                if (rs.next()) {
                	return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        

        return false;
    }
    public static int getPendingCount() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM customers WHERE status = 'pending'"; //
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
    public static int getTotalAccountCount() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM accounts"; //
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
    public static boolean createDefaultAccount(int customerId) {
        long generatedNo = (long) (Math.random() * 9_000_000_000L) + 1_000_000_000L;
        String sql = "INSERT INTO accounts (account_no, customer_id, account_type, balance, account_status) VALUES (?, ?, 'savings', 0.00, 'active')";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setLong(1, generatedNo); 
            ps.setInt(2, customerId);
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static List<Account> getAllAccounts() {
        List<Account> accountList = new ArrayList<>();
        
        String sql = "SELECT * FROM accounts";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
        	while (rs.next()) {
        	    Account acc = new Account();
        	  
        	    acc.setAccountNo(String.valueOf(rs.getLong("account_no"))); 
        	    acc.setCustomerId(rs.getInt("customer_id"));
        	    acc.setAccountType(rs.getString("account_type"));
        	    acc.setBalance(rs.getDouble("balance"));
        	    acc.setAccountStatus(rs.getString("account_status"));
        	    acc.setOpenedAt(rs.getTimestamp("opened_at"));
        	    
        	    accountList.add(acc);
        	}
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accountList;
    }
    public static boolean toggleAccountStatus(int customerId) {
        String getCurrentStatus = "SELECT status FROM accounts WHERE customer_id = ?";
        String updateStatus = "UPDATE accounts SET status = ? WHERE customer_id = ?";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement psSelect = con.prepareStatement(getCurrentStatus)) {
            
            psSelect.setInt(1, customerId);
            ResultSet rs = psSelect.executeQuery();
            
            if (rs.next()) {
                String current = rs.getString("status");
                
                String newStatus = "active".equalsIgnoreCase(current) ? "frozen" : "active";
                
                try (PreparedStatement psUpdate = con.prepareStatement(updateStatus)) {
                    psUpdate.setString(1, newStatus);
                    psUpdate.setInt(2, customerId);
                    return psUpdate.executeUpdate() > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
	}
