package com.example.database;

import com.example.database.db_classes.Account;
import com.example.database.db_classes.Basket;
import com.example.database.db_classes.PricedItem;
import com.example.database.db_classes.OrderHistoryRecord;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AccountRepositoryTest {

    private static DatabaseManager databaseManager = new DatabaseManager();

    @Test
    public void testGetAccountById() throws SQLException {
        // Test retrieving an account by ID
        int accountId = 1; // Example account ID

        Account account = AccountRepository.getAccountById(accountId, databaseManager.getConnection());

        // Assert that the account is not null
        assertNotNull(account, "Account should not be null for ID " + accountId);

        // Assert that account details are populated
        assertNotNull(account.getLogin(), "Account login should not be null");
        assertNotNull(account.getPassword(), "Account password should not be null");
        assertNotNull(account.getEmail(), "Account email should not be null");
        assertNotNull(account.getPhoneNumber(), "Account phone number should not be null");
        assertNotNull(account.getBalance(), "Account balance should not be null");
    }

    @Test
    public void testGetAllAccounts() throws SQLException {
        // Test retrieving all accounts
        List<Account> accounts = AccountRepository.getAllAccounts(databaseManager.getConnection());

        // Assert the accounts list is not null and has at least one account
        assertNotNull(accounts, "Accounts list should not be null");
        assertTrue(accounts.size() > 0, "There should be at least one account in the list");

        // Assert each account in the list has valid details
        for (Account account : accounts) {
            assertNotNull(account, "Account should not be null");
            assertNotNull(account.getLogin(), "Account login should not be null");
            assertNotNull(account.getPassword(), "Account password should not be null");
            assertNotNull(account.getEmail(), "Account email should not be null");
            assertNotNull(account.getPhoneNumber(), "Account phone number should not be null");
            assertNotNull(account.getBalance(), "Account balance should not be null");
        }
    }

    @Test
    public void testAddAccount() throws SQLException {
        // Test adding a new account
        String login = "testUser";
        String password = "testPass";
        String email = "test@example.com";
        String phoneNumber = "123456789";

        boolean isAdded = AccountRepository.addAccount(login, password, email, phoneNumber, databaseManager.getConnection());

        // Assert that the account was successfully added
        assertTrue(isAdded, "Account should be successfully added");
    }

    @Test
    public void testChangeAccountDetails() throws SQLException {
        // Test changing account details
        int accountId = 1; // Example account ID
        String newLogin = "updatedUser";
        String newEmail = "updated@example.com";
        String newPhone = "987654321";

        Connection connection = databaseManager.getConnection();

        // Change login
        boolean isLoginChanged = AccountRepository.changeLogin(accountId, newLogin, connection);
        assertTrue(isLoginChanged, "Login should be successfully updated");

        // Change email
        boolean isEmailChanged = AccountRepository.changeEmail(accountId, newEmail, connection);
        assertTrue(isEmailChanged, "Email should be successfully updated");

        // Change phone number
        boolean isPhoneChanged = AccountRepository.changePhone(accountId, newPhone, connection);
        assertTrue(isPhoneChanged, "Phone number should be successfully updated");
    }

    @Test
    public void testLoyaltyPointsManagement() throws SQLException {
        // Test adding and taking loyalty points
        int accountId = 1; // Example account ID
        int pointsToAdd = 10;
        int pointsToTake = 5;

        Connection connection = databaseManager.getConnection();

        // Add loyalty points
        boolean isPointsAdded = AccountRepository.addLoyaltyPoints(accountId, pointsToAdd, connection);
        assertTrue(isPointsAdded, "Loyalty points should be successfully added");

        // Take loyalty points
        boolean isPointsTaken = AccountRepository.takeLoyaltyPoints(accountId, pointsToTake, connection);
        assertTrue(isPointsTaken, "Loyalty points should be successfully deducted");
    }

    @Test
    public void testBalanceManagement() throws SQLException {
        // Test adding and taking balance
        int accountId = 1; // Example account ID
        double balanceToAdd = 100.0;
        double balanceToTake = 50.0;

        Connection connection = databaseManager.getConnection();

        // Add balance
        boolean isBalanceAdded = AccountRepository.addBalance(accountId, balanceToAdd, connection);
        assertTrue(isBalanceAdded, "Balance should be successfully added");

        // Deduct balance
        boolean isBalanceDeducted = AccountRepository.takeBalance(accountId, -balanceToTake, connection);
        assertTrue(isBalanceDeducted, "Balance should be successfully deducted");
    }

    @Test
    public void testOrderManagement() throws SQLException {
        // Test adding an order
        int accountId = 1; // Example account ID
        Basket basket = new Basket();
        basket.addItem(new PricedItem("Name", "food", 10.0, 1));

        boolean isOrderAdded = AccountRepository.addOrder(accountId, basket, databaseManager.getConnection());
        assertTrue(isOrderAdded, "Order should be successfully added");

        // Test retrieving order history
        List<OrderHistoryRecord> orders = AccountRepository.getAllOrdersHistory(accountId, databaseManager.getConnection());
        assertNotNull(orders, "Order history should not be null");
        assertTrue(orders.size() > 0, "There should be at least one order in the history");
    }
}
