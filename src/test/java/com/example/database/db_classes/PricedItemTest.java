package com.example.database.db_classes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PricedItemTest {

    private PricedItem foodItem;
    private PricedItem drinkItem;
    private PricedItem discountItem;
    private Price foodPrice;
    private Price drinkPrice;
    private Price discountPrice;

    private Food food;
    private Drink drink;
    private Discount discount;
    @BeforeEach
    public void setup() {
        // Prepare some mock data
        foodPrice = new Price(10.0); // Price for food
        drinkPrice = new Price(5.0); // Price for drink
        discountPrice = new Price(2.0); // Discount price
    
        // Create food, drink, discount, and objects
        food = new Food(1, "Burger");
        drink = new Drink(2, "Soda");
        discount = new Discount(1, discountPrice); // Assuming Discount constructor takes Price
    
        // Adding prices for sizes to food and drink (using only two arguments)
        food.addPrice("Large", foodPrice, 1);  // Only two arguments: size and price
        drink.addPrice("Medium", drinkPrice, 1);  // Only two arguments: size and price
    
        // Create PricedItem instances using the different constructors
        foodItem = new PricedItem(food, "Large");
        drinkItem = new PricedItem(drink, "Medium");
        discountItem = new PricedItem(discount);
    }
    

    @Test
    public void testPricedItemFoodConstructor() {
        // Verify that a food PricedItem has the correct name and price
        assertEquals("Burger: Large", foodItem.getName(), "The food item name should be 'Burger: Large'.");
        assertEquals(10, foodItem.getPrice().getDollars(), "The price of the food item should be $10.00.");
        assertTrue(foodItem.isFood(), "The item should be recognized as a food item.");
        assertFalse(foodItem.isDrink(), "The item should not be recognized as a drink.");
    }

    @Test
    public void testPricedItemDrinkConstructor() {
        // Verify that a drink PricedItem has the correct name and price
        assertEquals("Soda: Medium", drinkItem.getName(), "The drink item name should be 'Soda: Medium'.");
        assertEquals(5, drinkItem.getPrice().getDollars(), "The price of the drink item should be $5.00.");
        assertTrue(drinkItem.isDrink(), "The item should be recognized as a drink item.");
        assertFalse(drinkItem.isFood(), "The item should not be recognized as a food.");
    }

    @Test
    public void testPricedItemDiscountConstructor() {
        // Verify that a discount PricedItem has the correct name and price
        assertTrue(discountItem.isDiscount(), "The item should be recognized as a discount.");
        assertEquals(discountPrice, discountItem.getPrice(), "The discount item price should match.");
    }


    @Test
    public void testGetId() {
        // Verify that the ID of the PricedItem is correctly set
        assertEquals(1, foodItem.getId(), "The ID for the food item should be 1.");
        assertEquals(1, drinkItem.getId(), "The ID for the drink item should be 2.");
        assertEquals(1, discountItem.getId(), "The ID for the discount item should be 1.");
    }

    @Test
    public void testPricedItemConstructorWithDoublePrice() {
        // Test the constructor where price is set using a double
        PricedItem pricedItem = new PricedItem("Item", "food", 15.0, 100);
        assertEquals(15, pricedItem.getPrice().getDollars(), "The price should be set to 15.0.");
        assertTrue(pricedItem.isFood(), "The item should be recognized as a food item.");
    }

    

    @Test
    public void testIsFood() {
        // Check that the `isFood` method works correctly
        assertTrue(foodItem.isFood(), "The food item should return true for isFood.");
        assertFalse(drinkItem.isFood(), "The drink item should return false for isFood.");
    }

    @Test
    public void testIsDrink() {
        // Check that the `isDrink` method works correctly
        assertTrue(drinkItem.isDrink(), "The drink item should return true for isDrink.");
        assertFalse(foodItem.isDrink(), "The food item should return false for isDrink.");
    }


    @Test
    public void testIsDiscount() {
        // Check that the `isDiscount` method works correctly
        assertTrue(discountItem.isDiscount(), "The discount item should return true for isDiscount.");
        assertFalse(foodItem.isDiscount(), "The food item should return false for isDiscount.");
    }
}
