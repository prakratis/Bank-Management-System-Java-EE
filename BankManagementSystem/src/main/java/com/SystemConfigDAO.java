package com;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class SystemConfigDAO {
    
    public static Map<String, String> getAllConfigs() {
        Map<String, String> configs = new HashMap<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM system_settings");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                configs.put(rs.getString("setting_key"), rs.getString("setting_value"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return configs;
    }

    public static boolean updateSetting(String key, String value) {
        String sql = "UPDATE system_settings SET setting_value = ? WHERE setting_key = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, value);
            ps.setString(2, key);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static String getSetting(String key) {
        String value = "false"; 
        String sql = "SELECT setting_value FROM system_settings WHERE setting_key = ?";
        
        try (Connection con = DBConnection.getConnection()) {
            if (con != null) {
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, key);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    value = rs.getString("setting_value");
                }
            }
        } catch (Exception e) {
            
            System.err.println("Maintenance Check Error: " + e.getMessage());
        }
        return value;
    }
}