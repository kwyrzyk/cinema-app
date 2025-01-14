package com.example.database;

import com.example.database.db_classes.Food;
import com.example.database.db_classes.Price;

import javafx.util.Pair;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class FoodRepository {


    static public Food getFoodById(int foodId, Connection connection) throws SQLException {
        String foodQuery = "SELECT id_food, name FROM foods WHERE id_food = " + foodId;
    
        ResultSet foodResult = DatabaseManager.runSelectQuery(foodQuery, connection);
        
        if (foodResult == null) {
            System.err.println("Error: The query did not return a result set.");
            return null;
        }
    
        if (!foodResult.next()) {
            System.err.println("No food found with id: " + foodId);
            return null;
        }
    
        int idFood = foodResult.getInt("id_food");
        String name = foodResult.getString("name");
    
        String pricesQuery = "SELECT id_food_price, portion_size, price FROM food_prices WHERE id_food = " + foodId;
        ResultSet pricesResult = DatabaseManager.runSelectQuery(pricesQuery, connection);
    
        HashMap<String, Pair<Integer, Price>> prices = new HashMap<>();
        while (pricesResult.next()) {
            int id = pricesResult.getInt("id_food_price");
            String portionSize = pricesResult.getString("portion_size");
            Price price = new Price(pricesResult.getDouble("price"));
            prices.put(portionSize, new Pair<Integer, Price>(id, price));
        }
    
        Food food = new Food(idFood, name);
    
        food.setPrices(prices);

        return food;
    }
    
    static public List<Food> getAllFoods(Connection connection) {
        List<Food> foods = new ArrayList<>();
        String foodQuery = "SELECT id_food, name FROM foods";
        String pricesQuery = "SELECT id_food_price, portion_size, price FROM food_prices WHERE id_food =";

        try {
            ResultSet foodResult = DatabaseManager.runSelectQuery(foodQuery, connection);
            if (foodResult == null) {
                System.err.println("Error: foodResult is null.");
                return foods;
            }

            while (foodResult.next()) {
                int foodId = foodResult.getInt("id_food");
                String name = foodResult.getString("name");

                String fullPricesQuery = pricesQuery + foodId;
                ResultSet pricesResult = DatabaseManager.runSelectQuery(fullPricesQuery, connection);

                if (pricesResult == null) {
                    System.err.println("Error: pricesResult is null for foodId " + foodId);
                    continue;
                }

                HashMap<String, Pair<Integer, Price>> prices = new HashMap<>();
                while (pricesResult.next()) {
                    int id = pricesResult.getInt("id_food_price");
                    String portionSize = pricesResult.getString("portion_size");
                    Price price = new Price(pricesResult.getDouble("price"));
                    prices.put(portionSize, new Pair<Integer, Price>(id, price));
                }
            

                Food food = new Food(foodId, name);
            
                food.setPrices(prices);


                foods.add(food); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Database error: " + e.getMessage());
        }

        return foods;
    }
}
