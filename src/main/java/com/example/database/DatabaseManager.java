package com.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseManager {
    
    private static final String DB_URL = "jdbc:oracle:thin:@hostname:port:sid";  // @TODO replace this with the actual info
    private static final String DB_USERNAME = "yourUsername";
    private static final String DB_PASSWORD = "yourPassword";

    // Get connection to Oracle Database
    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (SQLException e) {
            throw new SQLException("Unable to connect to the database", e);
        }
    }

    // Run a SELECT query and return a result set
    public static ResultSet runSelectQuery(String sql) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            // Get database connection
            conn = getConnection();
            
            // Create statement and execute query
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            
            // Return result set
            return rs;
        } catch (SQLException e) {
            System.err.println("Error executing SELECT query: " + e.getMessage());
            return null;
        }
    }
}
