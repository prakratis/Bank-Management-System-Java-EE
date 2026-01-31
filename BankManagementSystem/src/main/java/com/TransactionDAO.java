package com;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

 
    public static boolean isAccountValid(long accountNo, int customerId) {
        String sql = "SELECT 1 FROM accounts WHERE account_no = ? AND customer_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, accountNo);
            ps.setInt(2, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) { return false; }
    }

    public static double getBalance(long accountNo) {
        String sql = "SELECT balance FROM accounts WHERE account_no = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, accountNo);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getDouble("balance") : 0.0;
            }
        } catch (SQLException e) { return 0.0; }
    }



    public static boolean deposit(long accountNo, double amount) {
        
        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);
            double newBal = getBalance(accountNo) + amount;

            String updateSql = "UPDATE accounts SET balance = ? WHERE account_no = ?";
            try (PreparedStatement psUpdate = con.prepareStatement(updateSql)) {
                psUpdate.setDouble(1, newBal);
                psUpdate.setLong(2, accountNo);
                psUpdate.executeUpdate();
            }

            String logSql = "INSERT INTO transactions (account_no, transaction_type, amount, balance_after, transactions_status) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement psLog = con.prepareStatement(logSql)) {
                psLog.setLong(1, accountNo);
                psLog.setString(2, "deposit");
                psLog.setDouble(3, amount);
                psLog.setDouble(4, newBal);
                psLog.setString(5, "success");
                psLog.executeUpdate();
            }

            con.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addTransaction(Transaction t) {
      
        String sql = "INSERT INTO transactions (account_no, amount, transaction_type, description, created_at) VALUES (?, ?, ?, ?, NOW())";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setLong(1, t.getAccountNo());        
            ps.setString(3, t.getTransactionType().toLowerCase()); 
            ps.setString(4, t.getDescription());   
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Transaction> getRecentTransactions(long accountNo) {
        List<Transaction> list = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE account_no = ? ORDER BY created_at DESC LIMIT 10";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, accountNo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Transaction t = new Transaction();
                    t.setTransactionId(rs.getLong("id"));
                    t.setTransactionType(rs.getString("transaction_type"));
                    t.setAmount(rs.getDouble("amount"));
                    t.setBalanceAfter(rs.getDouble("balance_after"));
                    Timestamp ts = rs.getTimestamp("created_at");
                    if (ts != null) t.setCreatedAt(ts.toString());
                    
                    t.setReferenceAccount(rs.getLong("reference_account"));
                    t.setTransactionStatus(rs.getString("transactions_status"));
                    list.add(t);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
    
    public static boolean verifyPin(int userId, String pin) {
        String sql = "SELECT transaction_pin FROM customers WHERE customer_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String dbPin = rs.getString("transaction_pin");
                    return dbPin != null && dbPin.equals(pin);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static boolean withdraw(long accountNo, double amount) {
        String updateSql = "UPDATE accounts SET balance = balance - ? WHERE account_no = ? AND balance >= ?";
        String logSql = "INSERT INTO transactions (account_no, transaction_type, amount, balance_after, transactions_status) " +
                        "VALUES (?, 'withdraw', ?, (SELECT balance FROM accounts WHERE account_no = ?), 'success')";

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false); 

            try (PreparedStatement psUpdate = con.prepareStatement(updateSql);
                 PreparedStatement psLog = con.prepareStatement(logSql)) {
                psUpdate.setDouble(1, amount);
                psUpdate.setLong(2, accountNo);
                psUpdate.setDouble(3, amount); 
                int rows = psUpdate.executeUpdate();

                if (rows == 0) {
                    con.rollback();
                    return false;
                }

                psLog.setLong(1, accountNo);
                psLog.setDouble(2, amount);
                psLog.setLong(3, accountNo);
                psLog.executeUpdate();

                con.commit();
                return true;
            } catch (SQLException e) {
                con.rollback();
                e.printStackTrace();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static boolean transfer(long fromAccount, long toAccount, double amount) {
        String deductSql = "UPDATE accounts SET balance = balance - ? WHERE account_no = ? AND balance >= ?";
        String addSql = "UPDATE accounts SET balance = balance + ? WHERE account_no = ?";
        String logSql = "INSERT INTO transactions (account_no, transaction_type, amount, balance_after, reference_account, transactions_status) " + 
                        "VALUES (?, ?, ?, (SELECT balance FROM accounts WHERE account_no = ?), ?, 'success')";

        try (Connection con = DBConnection.getConnection()) {
            if (con == null) return false;
            con.setAutoCommit(false); 

            try (PreparedStatement psDeduct = con.prepareStatement(deductSql);
                 PreparedStatement psAdd = con.prepareStatement(addSql);
                 PreparedStatement psLog = con.prepareStatement(logSql)) {

                psDeduct.setDouble(1, amount);
                psDeduct.setLong(2, fromAccount);
                psDeduct.setDouble(3, amount);
                if (psDeduct.executeUpdate() == 0) {
                    con.rollback();
                    return false; 
                }

                psAdd.setDouble(1, amount);
                psAdd.setLong(2, toAccount);
                if (psAdd.executeUpdate() == 0) {
                    con.rollback();
                    return false; 
                }

                psLog.setLong(1, fromAccount);
                psLog.setString(2, "transfer_out");
                psLog.setDouble(3, amount);
                psLog.setLong(4, fromAccount);
                psLog.setLong(5, toAccount);
                psLog.executeUpdate();

                psLog.setLong(1, toAccount);
                psLog.setString(2, "transfer_in");
                psLog.setDouble(3, amount);
                psLog.setLong(4, toAccount);
                psLog.setLong(5, fromAccount);
                psLog.executeUpdate();

                con.commit(); 
                return true;

            } catch (SQLException e) {
                con.rollback();
                e.printStackTrace();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}