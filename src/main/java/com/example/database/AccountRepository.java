package com.example.database;

import com.example.database.db_classes.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountRepository {


    // Constructor
    public AccountRepository(DatabaseManager dbManager) {
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
    static public boolean addAccount(String login, String password, String email, String phoneNumber) {
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

    static private boolean changeColumn(String columnName, int accountId, String newData){
        String updateQuery = "UPDATE accounts SET " + columnName + " = ? WHERE id_account = ?";

        // Use try-with-resources to manage resources
        try (Connection connection = DatabaseManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            // Set the parameters for the query
            preparedStatement.setString(1, newData); // Set the new login
            preparedStatement.setInt(2, accountId);  // Set the account ID

            // Execute the update and check rows affected
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Return true if the update was successful
        } catch (SQLException e) {
            // Log and handle SQL exceptions
            e.printStackTrace();
            System.err.println("Failed to update login for account ID " + accountId + ": " + e.getMessage());
            return false;
        }
    }

    // Method to change the login for a given account ID
    static public boolean changeLogin(int accountId, String newLogin) {
    return changeColumn(new String("login"), accountId, newLogin);
    }

    static public boolean changePassword(int accountId, String newLogin) {
        return changeColumn(new String("password"), accountId, newLogin);
    }

    static public boolean changeEmail(int accountId, String newLogin) {
        return changeColumn(new String("email"), accountId, newLogin);
    }

    static public boolean changePhone(int accountId, String newLogin) {
        return changeColumn(new String("phone_number"), accountId, newLogin);
    }

    // Method to add loyalty points to a given account ID
    static public boolean addLoyaltyPoints(int accountId, int pointsToAdd) {
        // SQL query to update the loyalty_points column in the accounts table
        String updateQuery = "UPDATE accounts SET loyalty_points = loyalty_points + ? WHERE id_account = ?";

        // Use try-with-resources to manage resources
        try (Connection connection = DatabaseManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            // Set the parameters for the query
            preparedStatement.setInt(1, pointsToAdd); // Set the amount of points to add
            preparedStatement.setInt(2, accountId);      // Set the account ID

            // Execute the update and check rows affected
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Return true if the update was successful
        } catch (SQLException e) {
            // Log and handle SQL exceptions
            e.printStackTrace();
            System.err.println("Failed to add loyalty points for account ID " + accountId + ": " + e.getMessage());
            return false;
        }
    }

    static public boolean takeLoyaltyPoints(int accountId, int pointsToTake) {
        // SQL query to update the loyalty_points column in the accounts table
        String updateQuery = "UPDATE accounts SET loyalty_points = loyalty_points - ? WHERE id_account = ?";

        // Use try-with-resources to manage resources
        try (Connection connection = DatabaseManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            // Set the parameters for the query
            preparedStatement.setInt(1, pointsToTake); // Set the amount of points to add
            preparedStatement.setInt(2, accountId);      // Set the account ID

            // Execute the update and check rows affected
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Return true if the update was successful
        } catch (SQLException e) {
            // Log and handle SQL exceptions
            e.printStackTrace();
            System.err.println("Failed to add loyalty points for account ID " + accountId + ": " + e.getMessage());
            return false;
        }
    }

    static public boolean addBalance(int accountId, double balanceToAdd) {
        // SQL query to update the loyalty_points column in the accounts table
        String updateQuery = "UPDATE accounts SET balance = balance + ? WHERE id_account = ?";

        // Use try-with-resources to manage resources
        try (Connection connection = DatabaseManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            // Set the parameters for the query
            preparedStatement.setDouble(1, balanceToAdd); // Set the amount of points to add
            preparedStatement.setInt(2, accountId);      // Set the account ID

            // Execute the update and check rows affected
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Return true if the update was successful
        } catch (SQLException e) {
            // Log and handle SQL exceptions
            e.printStackTrace();
            System.err.println("Failed to add loyalty points for account ID " + accountId + ": " + e.getMessage());
            return false;
        }
    }


    static public boolean takeBalance(int accountId, double balanceToAdd) {
        // SQL query to update the loyalty_points column in the accounts table
        String updateQuery = "UPDATE accounts SET balance = balance + ? WHERE id_account = ?";

        // Use try-with-resources to manage resources
        try (Connection connection = DatabaseManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            // Set the parameters for the query
            preparedStatement.setDouble(1, balanceToAdd); // Set the amount of points to add
            preparedStatement.setInt(2, accountId);      // Set the account ID

            // Execute the update and check rows affected
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Return true if the update was successful
        } catch (SQLException e) {
            // Log and handle SQL exceptions
            e.printStackTrace();
            System.err.println("Failed to add loyalty points for account ID " + accountId + ": " + e.getMessage());
            return false;
        }
    }

}
