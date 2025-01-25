package com.example.database;

import com.example.database.db_classes.Account;
import com.example.database.db_classes.Basket;
import com.example.database.db_classes.OrderHistoryRecord;
import com.example.database.db_classes.PricedItem;
import com.example.exceptions.NonRecoverableDatabaseException;
import com.example.exceptions.RecoverableDatabaseException;
import com.example.database.db_classes.Price;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.List;

import java.util.Date;

public class AccountRepository {


    static public Account getAccountById(int accountId, Connection connection){
        String accountQuery = "SELECT id_account, login, password, email, phone_number, loyalty_points, balance  FROM accounts WHERE id_account = " + accountId;

        try(ResultSet accountResult = DatabaseManager.runSelectQuery(accountQuery, connection)){

        if (!accountResult.next()) {
            return null;
        }

            int idAccount = accountResult.getInt("id_account");
            String login = accountResult.getString("login");
            String password = accountResult.getString("password");
            String email = accountResult.getString("email");
            String phoneNumber = accountResult.getString("phone_number");
            int loyalty_points = accountResult.getInt("loyalty_points");
            Price balance = new Price(accountResult.getDouble("balance"));
            return new Account(idAccount, login, password, email, phoneNumber, loyalty_points, balance);
        } catch (SQLSyntaxErrorException e) {
            throw new NonRecoverableDatabaseException("Syntax error in SQL query: " + e.getMessage(), e);
        } catch (SQLException e) {
            throw new RecoverableDatabaseException("Database query getting the account: " + e.getMessage(), e);
        } 

    }

    static public List<Account> getAllAccounts(Connection connection) {
        List<Account> accounts = new ArrayList<>();
        String query = "SELECT id_account, login, password, email, phone_number, loyalty_points, balance FROM accounts";

        try {
            ResultSet result = DatabaseManager.runSelectQuery(query, connection);

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

                accounts.add(new Account(idAccount, login, password, email, phoneNumber, loyalty_points, balance));
            }
        } catch (SQLSyntaxErrorException e) {
            throw new NonRecoverableDatabaseException("Syntax error in SQL query: " + e.getMessage(), e);
        } catch (SQLException e) {
            throw new RecoverableDatabaseException("Database query getting the account: " + e.getMessage(), e);
        } 

        return accounts;
    }

    static public boolean addAccount(String login, String password, String email, String phoneNumber, Connection connection) {
        String insertQuery = "INSERT INTO accounts (login, password, email, phone_number) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, phoneNumber);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLSyntaxErrorException e) {
            throw new NonRecoverableDatabaseException("Syntax error in SQL query: " + e.getMessage(), e);
        } catch (SQLException e) {
            throw new RecoverableDatabaseException("Database query adding the account: " + e.getMessage(), e);
        } 
    }

    static private boolean changeColumn(String columnName, int accountId, String newData, Connection connection){
        String updateQuery = "UPDATE accounts SET " + columnName + " = ? WHERE id_account = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setString(1, newData);
            preparedStatement.setInt(2, accountId);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }catch (SQLSyntaxErrorException e) {
            throw new NonRecoverableDatabaseException("Syntax error in SQL query: " + e.getMessage(), e);
        } catch (SQLException e) {
            throw new RecoverableDatabaseException("Database query changing the account: " + e.getMessage(), e);
        } 
    }

    static public boolean changeLogin(int accountId, String newLogin, Connection connection) {
    return changeColumn(new String("login"), accountId, newLogin, connection);
    }

    static public boolean changePassword(int accountId, String newLogin, Connection connection) {
        return changeColumn(new String("password"), accountId, newLogin, connection);
    }

    static public boolean changeEmail(int accountId, String newLogin, Connection connection) {
        return changeColumn(new String("email"), accountId, newLogin, connection);
    }

    static public boolean changePhone(int accountId, String newLogin, Connection connection) {
        return changeColumn(new String("phone_number"), accountId, newLogin, connection);
    }

    static public boolean addLoyaltyPoints(int accountId, int pointsToAdd, Connection connection) {
        String updateQuery = "UPDATE accounts SET loyalty_points = loyalty_points + ? WHERE id_account = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setInt(1, pointsToAdd);
            preparedStatement.setInt(2, accountId);

            
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLSyntaxErrorException e) {
            throw new NonRecoverableDatabaseException("Syntax error in SQL query: " + e.getMessage(), e);
        } catch (SQLException e) {
            throw new RecoverableDatabaseException("Database query adding loyalty points the account: " + e.getMessage(), e);
        } 
    }

    static public boolean takeLoyaltyPoints(int accountId, int pointsToTake, Connection connection) {
        String updateQuery = "UPDATE accounts SET loyalty_points = loyalty_points - ? WHERE id_account = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setInt(1, pointsToTake);
            preparedStatement.setInt(2, accountId);


            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLSyntaxErrorException e) {
            throw new NonRecoverableDatabaseException("Syntax error in SQL query: " + e.getMessage(), e);
        } catch (SQLException e) {
            throw new RecoverableDatabaseException("Database query taking the account: " + e.getMessage(), e);
        } 
    }

    static public boolean addBalance(int accountId, double balanceToAdd, Connection connection) {
        String updateQuery = "UPDATE accounts SET balance = balance + ? WHERE id_account = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setDouble(1, balanceToAdd);
            preparedStatement.setInt(2, accountId);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }catch (SQLSyntaxErrorException e) {
            throw new NonRecoverableDatabaseException("Syntax error in SQL query: " + e.getMessage(), e);
        } catch (SQLException e) {
            throw new RecoverableDatabaseException("Database query adding the balnasce to the account: " + e.getMessage(), e);
        } 
    }


    static public boolean takeBalance(int accountId, double balanceToAdd, Connection connection) {
        String updateQuery = "UPDATE accounts SET balance = balance + ? WHERE id_account = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setDouble(1, balanceToAdd); 
            preparedStatement.setInt(2, accountId);      

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; 
        } catch (SQLSyntaxErrorException e) {
            throw new NonRecoverableDatabaseException("Syntax error in SQL query: " + e.getMessage(), e);
        } catch (SQLException e) {
            throw new RecoverableDatabaseException("Database query taking balance from the account: " + e.getMessage(), e);
        } 
    }

    
    static public boolean addOrder(int accountId, Basket basket, Connection connection) {
        List<PricedItem> items = basket.getItems();
        List<Integer> quantities = basket.getQuantities();

        String insertOrderQuery = "INSERT INTO orders (id_account, price, order_date) VALUES (?, ?, SYSDATE)";
        String insertOrderItemQuery = "INSERT INTO order_item (item_type, item_reference_id, id_order, quantity, item_name, price) VALUES (?, ?, ?, ?, ?, ?)";
        

        double price = basket.getTotalPrice();
        
        int orderId = -1;
        try{
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
                            orderId = resultSet.getInt(1);                        }
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
                    }else if(item.isDrink()){
                        type = "drink";
                    }else if(item.isTicket()){
                        type = "ticket";
                    }else if(item.isDiscount()){
                        type = "discount";
                    }
                    id = item.getId();

                    itemStatement.setString(1, type);
                    itemStatement.setInt(2, id);
                    itemStatement.setInt(3, orderId);
                    itemStatement.setInt(4, quantity);
                    itemStatement.setString(5, name);
                    itemStatement.setDouble(6, item_price.toDouble());
                    itemStatement.addBatch();
                }
                int[] itemRowsAffected = itemStatement.executeBatch();
                if (itemRowsAffected.length != items.size()) {
                    connection.rollback();
                    throw new SQLException("Failed to insert all order items");
                }
            }
        
    
           return true;
        
        }catch (SQLSyntaxErrorException e) {
            throw new NonRecoverableDatabaseException("Syntax error in SQL query: " + e.getMessage(), e);
        } catch (SQLException e) {
            throw new RecoverableDatabaseException("Database query adding the order: " + e.getMessage(), e);
        } 
    }    


    public static List<OrderHistoryRecord> getAllOrdersHistory(int accountId, Connection connection){

        List<OrderHistoryRecord> orders = new ArrayList<>();
        String query = "SELECT id_order, price, order_date FROM orders WHERE id_account = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, accountId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int idOrder = resultSet.getInt("id_order");
                    double price = resultSet.getDouble("price");
                    Date orderDate = resultSet.getDate("order_date");

                    Basket basket = getOrderItemsByOrderId(idOrder, connection);

                    orders.add(new OrderHistoryRecord(idOrder, price, orderDate, basket));
                }
            }
        } catch (SQLSyntaxErrorException e) {
            throw new NonRecoverableDatabaseException("Syntax error in SQL query: " + e.getMessage(), e);
        } catch (SQLException e) {
            throw new RecoverableDatabaseException("Database query getting the orders history: " + e.getMessage(), e);
        } 

        return orders;
    }
    

    public static Basket getOrderItemsByOrderId(int orderId, Connection connection) {
        String query = """
            SELECT item_name, item_type, item_reference_id, price, quantity
            FROM order_item
            WHERE id_order = ?
            """;

        Basket basket = new Basket();

        try (PreparedStatement statement = connection.prepareStatement(query)) {

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
        } catch (SQLSyntaxErrorException e) {
            throw new NonRecoverableDatabaseException("Syntax error in SQL query: " + e.getMessage(), e);
        } catch (SQLException e) {
            throw new RecoverableDatabaseException("Database query getting the order: " + e.getMessage(), e);
        } 
        return basket;
    }


    public static boolean removeOrderById(int orderId, Connection connection){
        String deleteQuery = """
            DELETE FROM orders
            WHERE id_order = ?
            """;

        try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
                    
            connection.setAutoCommit(false);
            statement.setInt(1, orderId);
            int deletedOrders = statement.executeUpdate();

            if(deletedOrders == 1){
                connection.commit();
                return true;
            }else{
                connection.rollback();
                return false;
            }
        }catch (SQLSyntaxErrorException e) {
            throw new NonRecoverableDatabaseException("Syntax error in SQL query: " + e.getMessage(), e);
        } catch (SQLException e) {
            throw new RecoverableDatabaseException("Database query removing the order: " + e.getMessage(), e);
        } 

    }


    
}
