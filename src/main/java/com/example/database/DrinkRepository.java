package com.example.database;

import com.example.database.db_classes.Drink;
import com.example.database.db_classes.Price;
import com.example.exceptions.NonRecoverableDatabaseException;
import com.example.exceptions.RecoverableDatabaseException;

import javafx.util.Pair;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DrinkRepository {


    static public Drink getDrinkById(int drinkId, Connection connection) throws SQLException {
        String drinkQuery = "SELECT id_drink, name FROM drinks WHERE id_drink = " + drinkId;

        ResultSet drinkResult = DatabaseManager.runSelectQuery(drinkQuery, connection);
        
        if (!drinkResult.next()) {
            return null; 
        }

        int idDrink = drinkResult.getInt("id_drink");
        String name = drinkResult.getString("name");

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
        
                drinks.add(drink);
            }
        } catch (SQLSyntaxErrorException e) {
            throw new NonRecoverableDatabaseException("Syntax error in SQL query: " + e.getMessage(), e);
        } catch (SQLException e) {
            throw new RecoverableDatabaseException("Database query getting the orders: " + e.getMessage(), e);
        } 

        return drinks;
    }
}
