package com.example.database;

import com.example.database.db_classes.Drink;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class DrinkRepositoryTest {

    private static DatabaseManager databaseManager = new DatabaseManager();


    @Test
    public void testGetDrinkById() throws Exception {
        // Test retrieving drink by ID
        Drink drink = DrinkRepository.getDrinkById(1, databaseManager.getConnection());

        // Assert the drink is not null
        assertNotNull(drink, "Drink should not be null for ID 1");

        // You can add more assertions if necessary to check specific values
        assertNotNull(drink.getName(), "Drink name should not be null");
    }

    @Test
    public void testGetAllDrinks() throws Exception {
        // Test retrieving all drinks
        List<Drink> drinks = DrinkRepository.getAllDrinks(databaseManager.getConnection());

        // Assert the list is not null and has at least one item
        assertNotNull(drinks, "Drinks list should not be null");
        assertTrue(drinks.size() > 0, "There should be at least one drink item");
    }
}
