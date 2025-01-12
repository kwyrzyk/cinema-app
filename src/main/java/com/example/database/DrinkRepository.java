package com.example.database;

import com.example.database.db_classes.Drink;
import com.example.database.db_classes.Price;

import javafx.util.Pair;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DrinkRepository {


    // Method to get a drink item by its ID
    static public Drink getDrinkById(int drinkId, Connection connection) throws SQLException {
        // Query to get the drink's basic info
        String drinkQuery = "SELECT id_drink, name FROM drinks WHERE id_drink = " + drinkId;

        // Execute drink query
        ResultSet drinkResult = DatabaseManager.runSelectQuery(drinkQuery, connection);
        
        if (!drinkResult.next()) {
            return null; // No drink found with the given ID
        }

        // Extract drink details
        int idDrink = drinkResult.getInt("id_drink");
        String name = drinkResult.getString("name");

        // Query to get the prices for the drink item
        String pricesQuery = "SELECT id_drink_price, portion_size, price FROM drinks_prices WHERE id_drink = " + drinkId;
        ResultSet pricesResult = DatabaseManager.runSelectQuery(pricesQuery, connection);

        HashMap<String, Pair<Integer, Price>> prices = new HashMap<>();
        while (pricesResult.next()) {
            int id = pricesResult.getInt("id_drink_price");
            String portionSize = pricesResult.getString("portion_size");
            Price price = new Price(pricesResult.getDouble("price"));
            prices.put(portionSize, new Pair<Integer, Price>(id, price));
        }
    
        Drink drink = new Drink(idDrink, name);
    
        drink.setPrices(prices);

        return drink;
    }

    // Method to get a list of all drinks
    static public List<Drink> getAllDrinks(Connection connection) {
        List<Drink> drinks = new ArrayList<>();
        String drinkQuery = "SELECT id_drink, name FROM drinks";
        String pricesQuery = "SELECT id_drink_price, portion_size, price FROM drinks_prices WHERE id_drink =";

        try {
            ResultSet drinkResult = DatabaseManager.runSelectQuery(drinkQuery, connection);
            if (drinkResult == null) {
                System.err.println("Error: drinkResult is null.");
                return drinks;
            }

            while (drinkResult.next()) {
                int drinkId = drinkResult.getInt("id_drink");
                String name = drinkResult.getString("name");

                // Query to get the prices for this drink item
                String fullPricesQuery = pricesQuery + drinkId;
                ResultSet pricesResult = DatabaseManager.runSelectQuery(fullPricesQuery, connection);

                if (pricesResult == null) {
                    System.err.println("Error: pricesResult is null for drinkId " + drinkId);
                    continue;
                }

                HashMap<String, Pair<Integer, Price>> prices = new HashMap<>();
                while (pricesResult.next()) {
                    int id = pricesResult.getInt("id_drink_price");
                    String portionSize = pricesResult.getString("portion_size");
                    Price price = new Price(pricesResult.getDouble("price"));
                    prices.put(portionSize, new Pair<Integer, Price>(id, price));
                }
            
                Drink drink = new Drink(drinkId, name);
            
                drink.setPrices(prices);
        
                drinks.add(drink);  // Add the drink object to the list
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Database error: " + e.getMessage());
        }

        return drinks;
    }
}
