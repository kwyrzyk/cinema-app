package com.example.database;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.TestInstance;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DatabaseManagerTest {

    private static DatabaseManager databaseManager = new DatabaseManager();
    private static Connection connection = databaseManager.getConnection();


    @Test
    public void testGetConnection() {
            assertNotNull(connection, "Connection should not be null");
            //assertFalse(connection.isClosed(), "Connection should be open");
        
    }
    private static final String CREATE_TABLE_SQL = """
        CREATE TABLE test_table (
            id NUMBER PRIMARY KEY,
            name VARCHAR2(50)
        )
    """;

    private static final String INSERT_SQL = """
        INSERT INTO test_table (id, name) VALUES (1, 'Test Name')
    """;

    private static final String SELECT_SQL = "SELECT * FROM test_table";
    private static final String DROP_TABLE_SQL = "DROP TABLE test_table";

    @BeforeAll
    public static void setupDatabase() throws SQLException {
        // Create the test table
        try (Statement statement = connection.createStatement()) {
            statement.execute(CREATE_TABLE_SQL);
        }
    }

    @Test
    public void testDatabaseOperations() throws SQLException {
        // Insert a record into the table
        try (Statement statement = connection.createStatement()) {
            int rowsInserted = statement.executeUpdate(INSERT_SQL);
            assertEquals(1, rowsInserted, "One row should have been inserted.");
        }

        // Query the table and verify the data
        try (ResultSet rs = DatabaseManager.runSelectQuery(SELECT_SQL, databaseManager.getConnection())) {
            assertNotNull(rs, "ResultSet should not be null.");
            assertTrue(rs.next(), "ResultSet should have at least one row.");
            assertEquals(1, rs.getInt("id"), "ID should match the inserted value.");
            assertEquals("Test Name", rs.getString("name"), "Name should match the inserted value.");
        }
    }

    @AfterAll
    public static void cleanupDatabase() throws SQLException {
        try (Connection connection = databaseManager.getConnection();
             Statement statement = connection.createStatement()) {
            
            // Check if the table exists
            String checkTableExistsQuery = "SELECT COUNT(*) FROM all_tables WHERE table_name = 'TEST_TABLE'";
            ResultSet rs = statement.executeQuery(checkTableExistsQuery);
            
            if (rs.next() && rs.getInt(1) > 0) {
                // Table exists, so we can safely drop it
                statement.execute(DROP_TABLE_SQL);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error cleaning up the database: " + e.getMessage());
        }
    }
    
}
