package com.example.database;

import com.example.database.db_classes.Food;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class FoodRepositoryTest {

    @Test
    public void testGetFoodById() throws Exception {
        // Test retrieving food by ID
        Food food = FoodRepository.getFoodById(1);

        // Assert the food is not null
        assertNotNull(food, "Food should not be null for ID 1");

        // You can add more assertions if necessary to check specific values
        assertNotNull(food.getName(), "Food name should not be null");
    }

    @Test
    public void testGetAllFoods() throws Exception {
        // Test retrieving all foods
        List<Food> foods = FoodRepository.getAllFoods();

        // Assert the list is not null and has at least one item
        assertNotNull(foods, "Foods list should not be null");
        assertTrue(foods.size() > 0, "There should be at least one food item");
    }
}
