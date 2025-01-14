package com.example.database;

import com.example.database.db_classes.Discount;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DiscountRepository {

    // Method to get a discount by its ID
    static public Discount getDiscountById(int discountId, Connection connection) throws SQLException {
        // Query to get the discount's basic info
        String discountQuery = "SELECT id_discount, price, start_time, end_time FROM discounts WHERE id_discount = " + discountId;
    
        // Execute discount query
        ResultSet discountResult = DatabaseManager.runSelectQuery(discountQuery, connection);
        if (!discountResult.next()) {
            return null; // No discount found with the given ID
        }
    
        // Extract discount details
        int idDiscount = discountResult.getInt("id_discount");
        double price = discountResult.getDouble("price");
        LocalTime startTime = discountResult.getObject("start_time", LocalTime.class);
        LocalTime endTime = discountResult.getObject("end_time", LocalTime.class);
        
        Discount discount;
        if(startTime == null || endTime == null){
        // Create a Discount object
            discount = new Discount(idDiscount, price);
        }else{
            discount = new Discount(idDiscount, price, startTime, endTime);
        }

        // Query to get the associated food items for this discount
        String foodQuery = """
            SELECT dp.id_food_price, dp.food_count, fp.size, f.name
            FROM discounts_positions dp
            JOIN food_prices fp ON dp.id_food_price = fp.id_food_price
            JOIN foods f ON fp.id_food = f.id_food
            WHERE dp.id_discount = """ + discountId;
    
        ResultSet foodResult = DatabaseManager.runSelectQuery(foodQuery, connection);
        if(foodResult != null){
            while (foodResult.next()) {
                int foodPriceId = foodResult.getInt("id_food_price");
                int foodCount = foodResult.getInt("food_count");
                String foodName = foodResult.getString("name");
                String foodSize = foodResult.getString("size");
                discount.addFoodItem(foodPriceId, (foodName + foodSize), foodCount);
            }
        }
        // Query to get the associated drink items for this discount
        String drinkQuery = """
            SELECT dp.id_drink_price, dp.drinks_count, dp.size, d.name
            FROM discounts_positions dp
            JOIN drinks_prices dp ON dp.id_drink_price = dp.id_drink_price
            JOIN drinks d ON dp.id_drink = d.id_drink
            WHERE dp.id_discount = """ + discountId;
    
        ResultSet drinkResult = DatabaseManager.runSelectQuery(drinkQuery, connection);
        if(drinkResult != null){
            while (drinkResult.next()) {
                int drinkPriceId = drinkResult.getInt("id_drink_price");
                int drinkCount = drinkResult.getInt("drinks_count");
                String drinkName = drinkResult.getString("name");
                String drinkSize = drinkResult.getString("size");
                discount.addDrinkItem(drinkPriceId, (drinkName + drinkSize), drinkCount);
            }
        }
        return discount;
    }
    


    // Method to get a list of all discounts
    static public List<Discount> getAllDiscounts(Connection connection) {
        List<Discount> discounts = new ArrayList<>();
        String discountQuery = "SELECT id_discount, price, start_time, end_time FROM discounts";

        try {
            ResultSet discountResult = DatabaseManager.runSelectQuery(discountQuery, connection);
            if (discountResult == null) {
                System.err.println("Error: discountResult is null.");
                return discounts;
            }

            while (discountResult.next()) {
                int discountId = discountResult.getInt("id_discount");
                double price = discountResult.getDouble("price");
                LocalTime startTime = discountResult.getObject("start_time", LocalTime.class);
                LocalTime endTime = discountResult.getObject("end_time", LocalTime.class);
                
                Discount discount;
                if(startTime == null || endTime == null){
                // Create a Discount object
                    discount = new Discount(discountId, price);
                }else{
                    discount = new Discount(discountId, price, startTime, endTime);
                }
                // Query to get the associated food items for this discount
                String foodQuery = """
                    SELECT dp.id_food_price, dp.food_count, fp.portion_size, f.name
                    FROM discounts_positions dp
                    JOIN food_prices fp ON dp.id_food_price = fp.id_food_price
                    JOIN foods f ON fp.id_food = f.id_food
                    WHERE dp.id_discount = """ + discountId;

                ResultSet foodResult = DatabaseManager.runSelectQuery(foodQuery, connection);
                if(foodResult != null){
                    while (foodResult.next()) {
                        int foodPriceId = foodResult.getInt("id_food_price");
                        int foodCount = foodResult.getInt("food_count");
                        String foodSize = foodResult.getString("portion_size");
                        String foodName = foodResult.getString("name");

                        discount.addFoodItem(foodPriceId, (foodName + " " + foodSize),  foodCount);
                    }
                }


                // Query to get the associated drink items for this discount
                String drinkQuery = """
                    SELECT dp.id_drink_price, dp.drinks_count, drp.portion_size, d.name
                    FROM discounts_positions dp
                    JOIN drinks_prices drp ON dp.id_drink_price = drp.id_drink_price
                    JOIN drinks d ON drp.id_drink = d.id_drink
                    WHERE dp.id_discount = """ + discountId;

                ResultSet drinkResult = DatabaseManager.runSelectQuery(drinkQuery, connection);
                if(drinkResult != null){
                    while (drinkResult.next()) {
                        int drinkPriceId = drinkResult.getInt("id_drink_price");
                        int drinkCount = drinkResult.getInt("drinks_count");
                        String drinkSize = drinkResult.getString("portion_size");
                        String drinkName = drinkResult.getString("name");
                        
                        discount.addDrinkItem(drinkPriceId, (drinkName+drinkSize), drinkCount);
                    }
                }

                discounts.add(discount);  // Add the discount object to the list
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Database error: " + e.getMessage());
        }

        return discounts;
    }
}
