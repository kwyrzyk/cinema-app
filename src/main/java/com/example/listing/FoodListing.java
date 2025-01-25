package com.example.listing;

import com.example.database.DatabaseManager;
import com.example.database.FoodRepository;
import com.example.database.db_classes.Food;
import com.example.exceptions.ErrorHandler;

import java.util.List;

public class FoodListing {

    private List<Food> foods;
    private DatabaseManager databaseManager;

    // Constructor
    public FoodListing(DatabaseManager databaseManager) {
        // Initialize foods list by fetching data from the database
        this.databaseManager = databaseManager;
        try{
            this.foods = FoodRepository.getAllFoods(this.databaseManager.getConnection());
        }catch (Exception e){
            ErrorHandler.handle(e);
        }
    }

    // Method to get the list of foods
    public List<Food> getFoods() {
        return foods;
    }

    // Method to display all the foods
    public void displayFoods() {
        for (Food food : foods) {
            System.out.println(food);
        }
    }

    // You can add more methods to filter, sort, or display the list of foods as needed
}
