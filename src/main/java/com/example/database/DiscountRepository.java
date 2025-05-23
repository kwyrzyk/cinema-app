package com.example.database;

import com.example.database.db_classes.Discount;
import com.example.exceptions.NonRecoverableDatabaseException;
import com.example.exceptions.RecoverableDatabaseException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DiscountRepository {

    static public Discount getDiscountById(int discountId, Connection connection) throws SQLException {
        String discountQuery = "SELECT id_discount, price, start_time, end_time FROM discounts WHERE id_discount = " + discountId;
    
        ResultSet discountResult = DatabaseManager.runSelectQuery(discountQuery, connection);
        if (!discountResult.next()) {
            return null;
        }
    
        int idDiscount = discountResult.getInt("id_discount");
        double price = discountResult.getDouble("price");
        LocalTime startTime = discountResult.getObject("start_time", LocalTime.class);
        LocalTime endTime = discountResult.getObject("end_time", LocalTime.class);
        
        Discount discount;
        if(startTime == null || endTime == null){
            discount = new Discount(idDiscount, price);
        }else{
            discount = new Discount(idDiscount, price, startTime, endTime);
        }

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
                    discount = new Discount(discountId, price);
                }else{
                    discount = new Discount(discountId, price, startTime, endTime);
                }
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

                discounts.add(discount);
            }
        }catch (SQLSyntaxErrorException e) {
            throw new NonRecoverableDatabaseException("Syntax error in SQL query: " + e.getMessage(), e);
        } catch (SQLException e) {
            throw new RecoverableDatabaseException("Database query getting the orders: " + e.getMessage(), e);
        } 

        return discounts;
    }
}
