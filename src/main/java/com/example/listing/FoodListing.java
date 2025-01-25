package com.example.listing;

import com.example.database.DatabaseManager;
import com.example.database.FoodRepository;
import com.example.database.db_classes.Food;
import com.example.exceptions.ErrorHandler;

import java.util.List;

public class FoodListing {
    private List<Food> foods;
    private DatabaseManager databaseManager;

    /**
     * Constructor initializes the foods list and database manager.
     * @param databaseManager the database manager
     */
    public FoodListing(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        try {
            this.foods = FoodRepository.getAllFoods(this.databaseManager.getConnection());
        } catch (Exception e) {
            ErrorHandler.handle(e);
        }
    }

    /**
     * Get the list of foods.
     * @return a list of foods
     */
    public List<Food> getFoods() {
        return foods;
    }

    /**
     * Display all the foods.
     */
    public void displayFoods() {
        for (Food food : foods) {
            System.out.println(food);
        }
    }
}
