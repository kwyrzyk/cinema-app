package com.example.database;

import com.example.database.db_classes.Food;
import com.example.database.db_classes.Price;

import javafx.util.Pair;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class FoodRepository {

    // Constructor
    public FoodRepository(DatabaseManager dbManager) {
        // Initialize with the database manager, if needed.
    }

    // Method to get a food item by its ID
    static public Food getFoodById(int foodId) throws SQLException {
        // Query to get the food's basic info
        String foodQuery = "SELECT id_food, name FROM foods WHERE id_food = " + foodId;
    
        // Execute food query
        ResultSet foodResult = DatabaseManager.runSelectQuery(foodQuery);
        
        // Check if the ResultSet is null or empty
        if (foodResult == null) {
            System.err.println("Error: The query did not return a result set.");
            return null; // Return null if the result set is null
        }
    
        if (!foodResult.next()) {
            System.err.println("No food found with id: " + foodId);
            return null; // Return null if no rows are returned
        }
    
        // Extract food details
        int idFood = foodResult.getInt("id_food");
        String name = foodResult.getString("name");
    
        // Query to get the prices for the food item
        String pricesQuery = "SELECT id_food_price, portion_size, price FROM food_prices WHERE id_food = " + foodId;
        ResultSet pricesResult = DatabaseManager.runSelectQuery(pricesQuery);
    
        // Map to hold portion size and price
        HashMap<String, Pair<Integer, Price>> prices = new HashMap<>();
        while (pricesResult.next()) {
            int id = pricesResult.getInt("id_food_price");
            String portionSize = pricesResult.getString("portion_size");
            Price price = new Price(pricesResult.getDouble("price"));
            prices.put(portionSize, new Pair<Integer, Price>(id, price));
        }
    
        // Return a Food object with all details
        Food food = new Food(idFood, name);
    
        food.setPrices(prices);

        return food;
    }
    
    // Method to get a list of all foods
    static public List<Food> getAllFoods() {
        List<Food> foods = new ArrayList<>();
        String foodQuery = "SELECT id_food, name FROM foods";
        String pricesQuery = "SELECT id_food_price, portion_size, price FROM food_prices WHERE id_food =";

        try {
            ResultSet foodResult = DatabaseManager.runSelectQuery(foodQuery);
            if (foodResult == null) {
                System.err.println("Error: foodResult is null.");
                return foods;
            }

            while (foodResult.next()) {
                int foodId = foodResult.getInt("id_food");
                String name = foodResult.getString("name");

                // Query to get the prices for this food item
                String fullPricesQuery = pricesQuery + foodId;
                ResultSet pricesResult = DatabaseManager.runSelectQuery(fullPricesQuery);

                if (pricesResult == null) {
                    System.err.println("Error: pricesResult is null for foodId " + foodId);
                    continue;
                }

                // Map to hold portion size and price
                HashMap<String, Pair<Integer, Price>> prices = new HashMap<>();
                while (pricesResult.next()) {
                    int id = pricesResult.getInt("id_food_price");
                    String portionSize = pricesResult.getString("portion_size");
                    Price price = new Price(pricesResult.getDouble("price"));
                    prices.put(portionSize, new Pair<Integer, Price>(id, price));
                }
            
                // Return a Food object with all details
                Food food = new Food(foodId, name);
            
                food.setPrices(prices);


                foods.add(food);  // Add the food object to the list
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Database error: " + e.getMessage());
        }

        return foods;
    }
}
