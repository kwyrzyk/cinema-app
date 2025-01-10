package com.example.database;

import com.example.database.db_classes.Account;
import com.example.database.db_classes.Basket;
import com.example.database.db_classes.OrderHistoryRecord;
import com.example.database.db_classes.PricedItem;
import com.example.database.db_classes.Price;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.util.Date;

public class AccountRepository {


    // Constructor
    public AccountRepository(DatabaseManager dbManager) {
    }

    // Method to get an account by its ID
    static public Account getAccountById(int accountId) throws SQLException {
        String accountQuery = "SELECT id_account, login, password, email, phone_number, loyalty_points, balance  FROM accounts WHERE id_account = " + accountId;

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
        int loyalty_points = accountResult.getInt("loyalty_points");
        Price balance = new Price(accountResult.getDouble("balance"));

        return new Account(idAccount, login, password, email, phoneNumber, loyalty_points, balance);
    }

    // Method to get all accounts
    static public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        String query = "SELECT id_account, login, password, email, phone_number, loyalty_points, balance FROM accounts";

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
                int loyalty_points = result.getInt("loyalty_points");
                Price balance = new Price(result.getDouble("balance"));

                // Add account to the list
                accounts.add(new Account(idAccount, login, password, email, phoneNumber, loyalty_points, balance));
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

    
    static public boolean addOrder(int accountId, Basket basket) {
        List<PricedItem> items = basket.getItems();
        List<Integer> quantities = basket.getQuantities();

        String insertOrderQuery = "INSERT INTO orders (id_account, price, order_date) VALUES (?, ?, SYSDATE)";
        String insertOrderItemQuery = "INSERT INTO order_item (item_type, item_reference_id, id_order, quantity, item_name, price) VALUES (?, ?, ?, ?, ?, ?)";
        

        double price = basket.getTotalPrice();
        
        int orderId = -1;
        try (Connection connection = DatabaseManager.getConnection()) {
            // Disable auto-commit for transaction
            connection.setAutoCommit(false);

        
            try (PreparedStatement orderStatement = connection.prepareStatement(insertOrderQuery)) {
                orderStatement.setInt(1, accountId);
                orderStatement.setDouble(2, price);
                
                int rowsAffected = orderStatement.executeUpdate();
                if (rowsAffected == 0) {
                    connection.rollback();
                    throw new SQLException("Failed to insert order into the database");
                }else{
                    try (Statement statement = connection.createStatement()) {
                        ResultSet resultSet = statement.executeQuery("SELECT orders_seq.CURRVAL FROM dual");
                        if (resultSet.next()) {
                            orderId = resultSet.getInt(1); // Get the last generated sequence value
                        }
                    }
                }

            } 
        
            try (PreparedStatement itemStatement = connection.prepareStatement(insertOrderItemQuery)) {
                for(int i = 0; i < items.size(); i++){
                    PricedItem item = items.get(i);
                    String name = item.getName();
                    Price item_price = item.getPrice();

                    int quantity = quantities.get(i);
                    int id = 0;
                    String type = "";            
                    if(item.isFood()){
                        type = "food";
                        id = item.getFoodId();
                    }else if(item.isDrink()){
                        type = "drink";
                        id = item.getDrinkId();
                    }else if(item.isTicket()){
                        type = "ticket";
                        id = item.getTicketId();
                    }else if(item.isDiscount()){
                        type = "discount";
                        id = item.getDiscountId();
                    }

                    itemStatement.setString(1, type);
                    itemStatement.setInt(2, id);
                    itemStatement.setInt(3, orderId);
                    itemStatement.setInt(4, quantity);
                    itemStatement.setString(5, name);
                    itemStatement.setDouble(6, item_price.toDouble());
                    itemStatement.addBatch(); // Add to batch for efficient execution
                }
                int[] itemRowsAffected = itemStatement.executeBatch();
                if (itemRowsAffected.length != items.size()) {
                    connection.rollback();
                    throw new SQLException("Failed to insert all order items");
                }
            }
        
    
           return true;
        
        }catch (SQLException e){
            System.err.println("Somehing went wrong inserting the order" + e.getMessage());
            return false;
        }
    }    


    public static List<OrderHistoryRecord> getAllOrdersHistory(int accountId){

        List<OrderHistoryRecord> orders = new ArrayList<>();
        String query = "SELECT id_order, price, order_date FROM orders WHERE id_account = ?";

        try (Connection connection = DatabaseManager.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, accountId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int idOrder = resultSet.getInt("id_order");
                    double price = resultSet.getDouble("price");
                    Date orderDate = resultSet.getDate("order_date");

                    Basket basket = getOrderItemsByOrderId(idOrder);

                    orders.add(new OrderHistoryRecord(idOrder, price, orderDate, basket));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }
    
        // Query to get all items for a given order ID
    public static Basket getOrderItemsByOrderId(int orderId) {
        String query = """
            SELECT item_name, item_type, item_reference_id, price, quantity
            FROM order_item
            WHERE id_order = ?
            """;

        Basket basket = new Basket();

        try (Connection connection = DatabaseManager.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, orderId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String itemName = resultSet.getString("item_name");
                    String itemType = resultSet.getString("item_type");
                    int itemReferenceId = resultSet.getInt("item_reference_id");
                    double price = resultSet.getDouble("price");
                    int quantity = resultSet.getInt("quantity");

                    basket.addNewItemInQuantity(new PricedItem(itemName, itemType, price, itemReferenceId), quantity);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return basket;
    }
    
}
