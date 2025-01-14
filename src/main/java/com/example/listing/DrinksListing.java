package com.example.listing;

import java.util.List;

import com.example.database.DatabaseManager;
import com.example.database.DrinkRepository;
import com.example.database.db_classes.Drink;

public class DrinksListing {
    private DatabaseManager databaseManager;
    private List<Drink> drinks;

    // Static method to get the list of drinks
    public DrinksListing(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        // Fetch the list of drinks using the static method from DrinkRepository
        drinks = DrinkRepository.getAllDrinks(this.databaseManager.getConnection());
    }

    public List<Drink> getDrinks() {
        return drinks;
    }

    // Static method to display all the drinks
    public void displayDrinks() {
        List<Drink> drinks = getDrinks();  // Get the list of drinks
        
        for (Drink drink : drinks) {
            System.out.println(drink);
        }
    }

    // You can add more methods to filter, sort, or display the list of drinks as needed
}
