package com.example.listing;

import java.util.List;

import com.example.database.DatabaseManager;
import com.example.database.DrinkRepository;
import com.example.database.db_classes.Drink;
import com.example.exceptions.ErrorHandler;

public class DrinksListing {
    private DatabaseManager databaseManager;
    private List<Drink> drinks;

    /**
     * Constructor initializes the drinks list and database manager.
     * @param databaseManager the database manager
     */
    public DrinksListing(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        try {
            drinks = DrinkRepository.getAllDrinks(this.databaseManager.getConnection());
        } catch (Exception e) {
            ErrorHandler.handle(e);
        }
    }

    /**
     * Get the list of drinks.
     * @return a list of drinks
     */
    public List<Drink> getDrinks() {
        return drinks;
    }

    /**
     * Display all the drinks.
     */
    public void displayDrinks() {
        List<Drink> drinks = getDrinks();
        for (Drink drink : drinks) {
            System.out.println(drink);
        }
    }
}
