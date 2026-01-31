package com;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CustomerDAO {
	
	public static boolean registerCustomer(Customer c) {
   
        String sql = "INSERT INTO customers (full_name, dob, gender, email, mobile, id_type, id_number, address_line, city, state, pincode, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection con = DBConnection.getConnection()) {
            if (con == null) return false;
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, c.getFullName());
            ps.setString(2, c.getDob());
            ps.setString(3, c.getGender().toLowerCase()); 
            ps.setString(4, c.getEmail());
            ps.setString(5, c.getMobile());
            ps.setString(6, c.getIdType().toLowerCase()); 
            ps.setString(7, c.getIdNumber());
            ps.setString(8, c.getAddressLine());
            ps.setString(9, c.getCity());
            ps.setString(10, c.getState());
            ps.setString(11, c.getPincode());
            ps.setString(12, c.getPassword());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ SQL Error during registration:");
            e.printStackTrace();
            return false;
        }
    }
   
   public static Customer login(String email, String password) {

	    String sql = "SELECT * FROM customers WHERE email = ? AND password = ? AND status = 'approved'";
	    
	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        
	        ps.setString(1, email);
	        ps.setString(2, password);
	        
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                Customer c = new Customer();
	                
	                
	                c.setCustomerId(rs.getInt("customer_id"));     
	                c.setFullName(rs.getString("full_name"));      
	                c.setEmail(rs.getString("email"));            
	                c.setMobile(rs.getString("mobile"));         
	                c.setStatus(rs.getString("status"));          
	                
	                c.setCreatedAt(rs.getTimestamp("created_at").toString()); 
	                
	                return c; 
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null; 
	}
   public static Customer getCustomerById(int customerId) {
	    Customer customer = null;
	    String sql = "SELECT * FROM customers WHERE customer_id = ?";
	
	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        
	        ps.setInt(1, customerId);
	        
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                customer = new Customer();
	                customer.setCustomerId(rs.getInt("customer_id"));
	                customer.setIdNumber(rs.getString("id_number"));
	                customer.setFullName(rs.getString("full_name"));
	                customer.setEmail(rs.getString("email"));
	                customer.setMobile(rs.getString("mobile"));
	                customer.setAddressLine(rs.getString("address_line"));
	                customer.setStatus(rs.getString("status"));
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return customer; 
	}
   public static List<Customer> getPendingCustomers() {
       List<Customer> list = new ArrayList<>();
       String sql = "SELECT * FROM customers WHERE status = 'pending'"; //
       
       try (Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
           
           while (rs.next()) {
               Customer c = new Customer();
               c.setCustomerId(rs.getInt("customer_id"));
               c.setFullName(rs.getString("full_name"));
               c.setEmail(rs.getString("email"));
               c.setMobile(rs.getString("mobile"));
               c.setIdNumber(rs.getString("id_number"));
               list.add(c);
           }
       } catch (SQLException e) {
           e.printStackTrace();
       }
       return list;
   }

   public static boolean updateStatus(int customerId, String newStatus) {
  
       String sql = "UPDATE customers SET status = ?, approved_at = NOW() WHERE customer_id = ?";
       
       try (Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
           
           ps.setString(1, newStatus);
           ps.setInt(2, customerId);
           
           return ps.executeUpdate() > 0;
       } catch (SQLException e) {
           e.printStackTrace();
           return false;
       }
   }


   public static int getPendingCount() {
       int count = 0;
       String sql = "SELECT COUNT(*) FROM customers WHERE status = 'pending'";
       try (Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
           if (rs.next()) count = rs.getInt(1);
       } catch (SQLException e) { e.printStackTrace(); }
       return count;
   }
}

  
