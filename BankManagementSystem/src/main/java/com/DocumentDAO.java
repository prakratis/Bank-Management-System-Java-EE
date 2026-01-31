package com;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DocumentDAO {
	    public static boolean uploadDocument(int customerId, String docType, String filePath) {
	        String sql = "INSERT INTO documents (customer_id, document_type, file_path) VALUES (?, ?, ?)";
	        
	
	        try (Connection con = DBConnection.getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {
	            
	       
	            ps.setInt(1, customerId);
	            ps.setString(2, docType); 
	            ps.setString(3, filePath);
	            
	          
	            int rows = ps.executeUpdate();
	            
	        
	            return rows > 0;
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	    public static List<Document> getDocumentsByCustomer(int customerId) {
	    
	        List<Document> docList = new ArrayList<>();
	        String sql = "SELECT * FROM documents WHERE customer_id = ?";

	        try (Connection con = DBConnection.getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {
	            
	            ps.setInt(1, customerId);
	            
	            try (ResultSet rs = ps.executeQuery()) {
	                while (rs.next()) {

	                    Document doc = new Document();
	                    
	                    
	                    doc.setDocumentId(rs.getInt("document_id"));
	                    doc.setCustomerId(rs.getInt("customer_id"));
	                    doc.setDocumentType(rs.getString("document_type"));
	                    doc.setFilePath(rs.getString("file_path"));
	                    doc.setUploadedAT(rs.getTimestamp("uploaded_at").toString());
	                    
	                    
	                    docList.add(doc);
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        
	     
	        return docList;
	    }
	    public static boolean isDocumentUploaded(int customerId, String docType) {
	 
	        String sql = "SELECT 1 FROM documents WHERE customer_id = ? AND document_type = ?";
	        

	        try (Connection con = DBConnection.getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {
	            
	            ps.setInt(1, customerId);
	            ps.setString(2, docType);
	            
	            
	            try (ResultSet rs = ps.executeQuery()) {
	                
	                return rs.next(); 
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        // Flowchart: No -> Return false
	        return false;
	    }
}
