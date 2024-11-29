package com.example.database;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseManagerTest {

    @Test
    public void testGetConnection() {
        try (Connection connection = DatabaseManager.getConnection()) {
            assertNotNull(connection, "Connection should not be null");
            assertFalse(connection.isClosed(), "Connection should be open");
        } catch (SQLException e) {
            fail("Database connection failed: " + e.getMessage());
        }
    }


    
}
