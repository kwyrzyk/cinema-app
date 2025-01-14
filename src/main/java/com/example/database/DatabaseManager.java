package com.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseManager {

    private Connection connection;


    public Connection getConnection(){
        return connection;
    }

    public DatabaseManager(){
        Properties properties = new Properties();
        
         try (InputStream input = DatabaseManager.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new IOException("No data in the database");
            }

            properties.load(input);

            String url = properties.getProperty("db.url");
            String username = properties.getProperty("db.username");
            String password = properties.getProperty("db.password");
            
            this.connection =  DriverManager.getConnection(url, username, password);
            
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println("Database error: " + ex.getMessage());
            this.connection = null;
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Database error: " + e.getMessage());
            this.connection = null;
        }
        
    }

    public static ResultSet runSelectQuery(String sql, Connection conn) {
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            
            return rs;
        } catch (SQLException e) {
            System.err.println("Error executing SELECT query: " + e.getMessage());
            return null;
        }
    }
}
