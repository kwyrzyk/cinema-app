package com.example.listing;

import java.util.List;

import com.example.database.DrinkRepository;
import com.example.database.db_classes.Drink;

public class DrinksListing {

    // Static method to get the list of drinks
    public List<Drink> getDrinks() {
        // Fetch the list of drinks using the static method from DrinkRepository
        return DrinkRepository.getAllDrinks();
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
