package com.example.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.database.db_classes.Drink;

public class DrinkRepository {

    // Constructor
    public DrinkRepository(DatabaseManager dbManager) {
        // Initialize with the database manager, if needed.
    }

    // Method to get a drink item by its ID
    static public Drink getDrinkById(int drinkId) throws SQLException {
        // Query to get the drink's basic info
        String drinkQuery = "SELECT id_drink, name FROM drinks WHERE id_drink = " + drinkId;

        // Execute drink query
        ResultSet drinkResult = DatabaseManager.runSelectQuery(drinkQuery);
        
        if (!drinkResult.next()) {
            return null; // No drink found with the given ID
        }

        // Extract drink details
        int idDrink = drinkResult.getInt("id_drink");
        String name = drinkResult.getString("name");

        // Query to get the prices for the drink item
        String pricesQuery = "SELECT portion_size, price FROM drinks_prices WHERE id_drink = " + drinkId;
        ResultSet pricesResult = DatabaseManager.runSelectQuery(pricesQuery);

        // Map to hold portion size and price
        Map<String, Double> prices = new HashMap<>();
        while (pricesResult.next()) {
            String portionSize = pricesResult.getString("portion_size");
            double price = pricesResult.getDouble("price");
            prices.put(portionSize, price);
        }

        // Return a Drink object with all details
        Drink drink = new Drink(idDrink, name);
        for (Map.Entry<String, Double> entry : prices.entrySet()) {
            drink.addPrice(entry.getKey(), entry.getValue());  // Add prices to the drink object
        }

        return drink;
    }

    // Method to get a list of all drinks
    static public List<Drink> getAllDrinks() {
        List<Drink> drinks = new ArrayList<>();
        String drinkQuery = "SELECT id_drink, name FROM drinks";
        String pricesQuery = "SELECT portion_size, price FROM drinks_prices WHERE id_drink =";

        try {
            ResultSet drinkResult = DatabaseManager.runSelectQuery(drinkQuery);
            if (drinkResult == null) {
                System.err.println("Error: drinkResult is null.");
                return drinks;
            }

            while (drinkResult.next()) {
                int drinkId = drinkResult.getInt("id_drink");
                String name = drinkResult.getString("name");

                // Query to get the prices for this drink item
                String fullPricesQuery = pricesQuery + drinkId;
                ResultSet pricesResult = DatabaseManager.runSelectQuery(fullPricesQuery);

                if (pricesResult == null) {
                    System.err.println("Error: pricesResult is null for drinkId " + drinkId);
                    continue;
                }

                // Map to hold portion size and price
                Map<String, Double> prices = new HashMap<>();
                while (pricesResult.next()) {
                    String portionSize = pricesResult.getString("portion_size");
                    double price = pricesResult.getDouble("price");
                    prices.put(portionSize, price);
                }

                // Create a Drink object and add prices
                Drink drink = new Drink(drinkId, name);
                for (Map.Entry<String, Double> entry : prices.entrySet()) {
                    drink.addPrice(entry.getKey(), entry.getValue());  // Add prices to the drink object
                }

                drinks.add(drink);  // Add the drink object to the list
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Database error: " + e.getMessage());
        }

        return drinks;
    }
}
