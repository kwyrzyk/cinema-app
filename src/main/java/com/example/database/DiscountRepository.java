package com.example.database;

import com.example.database.db_classes.Discount;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DiscountRepository {

    // Constructor
    public DiscountRepository(DatabaseManager dbManager) {
        // Initialize with the database manager, if needed.
    }

    // Method to get a discount by its ID
    static public Discount getDiscountById(int discountId) throws SQLException {
        // Query to get the discount's basic info
        String discountQuery = "SELECT id_discount, price FROM discounts WHERE id_discount = " + discountId;

        // Execute discount query
        ResultSet discountResult = DatabaseManager.runSelectQuery(discountQuery);
        if (!discountResult.next()) {
            return null; // No discount found with the given ID
        }

        // Extract discount details
        int idDiscount = discountResult.getInt("id_discount");
        double price = discountResult.getDouble("price");

        // Create a Discount object
        Discount discount = new Discount(idDiscount, price);

        // Query to get the associated food and drink items for this discount
        String foodQuery = "SELECT id_food_price, food_count FROM discounts_positions WHERE id_discount = " + discountId;
        ResultSet foodResult = DatabaseManager.runSelectQuery(foodQuery);

        while (foodResult.next()) {
            int foodPriceId = foodResult.getInt("id_food_price");
            int foodCount = foodResult.getInt("food_count");
            // You could use the foodPriceId to retrieve more detailed food data (e.g., price, portion size)
            // For simplicity, we are just associating food count here
            discount.addFoodItem(foodPriceId, foodCount);
        }

        String drinkQuery = "SELECT id_drink_price, drinks_count FROM discounts_positions WHERE id_discount = " + discountId;
        ResultSet drinkResult = DatabaseManager.runSelectQuery(drinkQuery);

        while (drinkResult.next()) {
            int drinkPriceId = drinkResult.getInt("id_drink_price");
            int drinkCount = drinkResult.getInt("drinks_count");
            // Similarly, use the drinkPriceId to retrieve more details about the drink
            discount.addDrinkItem(drinkPriceId, drinkCount);
        }

        return discount;
    }

    // Method to get a list of all discounts
    static public List<Discount> getAllDiscounts() {
        List<Discount> discounts = new ArrayList<>();
        String discountQuery = "SELECT id_discount, price FROM discounts";

        try {
            ResultSet discountResult = DatabaseManager.runSelectQuery(discountQuery);
            if (discountResult == null) {
                System.err.println("Error: discountResult is null.");
                return discounts;
            }

            while (discountResult.next()) {
                int discountId = discountResult.getInt("id_discount");
                double price = discountResult.getDouble("price");

                // Create the Discount object
                Discount discount = new Discount(discountId, price);

                // Query to get the associated food items for this discount
                String foodQuery = "SELECT id_food_price, food_count FROM discounts_positions WHERE id_discount = " + discountId;
                ResultSet foodResult = DatabaseManager.runSelectQuery(foodQuery);
                while (foodResult.next()) {
                    int foodPriceId = foodResult.getInt("id_food_price");
                    int foodCount = foodResult.getInt("food_count");
                    discount.addFoodItem(foodPriceId, foodCount);
                }

                // Query to get the associated drink items for this discount
                String drinkQuery = "SELECT id_drink_price, drinks_count FROM discounts_positions WHERE id_discount = " + discountId;
                ResultSet drinkResult = DatabaseManager.runSelectQuery(drinkQuery);
                while (drinkResult.next()) {
                    int drinkPriceId = drinkResult.getInt("id_drink_price");
                    int drinkCount = drinkResult.getInt("drinks_count");
                    discount.addDrinkItem(drinkPriceId, drinkCount);
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
