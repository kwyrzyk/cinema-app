package com.example.database;

import com.example.database.db_classes.Account;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountRepository {

    private final DatabaseManager dbManager;

    // Constructor
    public AccountRepository(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    // Method to get an account by its ID
    static public Account getAccountById(int accountId) throws SQLException {
        String accountQuery = "SELECT id_account, login, password, email, phone_number FROM accounts WHERE id_account = " + accountId;

        ResultSet accountResult = DatabaseManager.runSelectQuery(accountQuery);

        if (!accountResult.next()) {
            return null; // No account found with the given ID
        }

        // Extract account details
        int idAccount = accountResult.getInt("id_account");
        String login = accountResult.getString("login");
        String password = accountResult.getString("password");
        String email = accountResult.getString("email");
        String phoneNumber = accountResult.getString("phone_number");

        return new Account(idAccount, login, password, email, phoneNumber);
    }

    // Method to get all accounts
    static public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        String query = "SELECT id_account, login, password, email, phone_number FROM accounts";

        try {
            ResultSet result = DatabaseManager.runSelectQuery(query);

            if (result == null) {
                System.err.println("Error: result is null.");
                return accounts;
            }

            while (result.next()) {
                int idAccount = result.getInt("id_account");
                String login = result.getString("login");
                String password = result.getString("password");
                String email = result.getString("email");
                String phoneNumber = result.getString("phone_number");

                // Add account to the list
                accounts.add(new Account(idAccount, login, password, email, phoneNumber));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Database error: " + e.getMessage());
        }

        return accounts;
    }

    // Method to add a new account
    public boolean addAccount(String login, String password, String email, String phoneNumber) {
        String insertQuery = "INSERT INTO accounts (login, password, email, phone_number) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = DatabaseManager.getConnection().prepareStatement(insertQuery)) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, phoneNumber);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Returns true if insertion was successful
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to insert account: " + e.getMessage());
            return false;
        }
    }
}
