package com.example.database.db_classes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;


import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BasketTest {

    private Basket basket;
    private Food foodItem;
    private Drink drinkItem;
    private Discount discountItem;
    private Price foodPriceSmall, foodPriceLarge, drinkPriceSmall, drinkPriceLarge;

    @BeforeEach
    public void setup() {
        basket = new Basket();

        // Create example food and drink objects
        foodItem = new Food(1, "Pizza");
        drinkItem = new Drink(1, "Coke"); // Example drink item
        
        // Create prices for different portion sizes
        foodPriceSmall = new Price(10, 99); // $10.99
        foodPriceLarge = new Price(12, 99); // $12.99
        drinkPriceSmall = new Price(2, 50); // $2.50
        drinkPriceLarge = new Price(3, 50); // $3.50

        // Add prices for different portion sizes to food and drink items
        foodItem.addPrice("Small", foodPriceSmall, 101);
        foodItem.addPrice("Large", foodPriceLarge, 102);
        drinkItem.addPrice("Small", drinkPriceSmall, 201);
        drinkItem.addPrice("Large", drinkPriceLarge, 202);

        discountItem = new Discount(1, new Price(5.0)); // Discount of $5.00
        discountItem.addFoodItem(101, "Pizza", 1);  // Discount on pizza
    }

    @Test
    public void testAddFoodItem() {
        basket.addFood(foodItem, "Small");

        // Check if the basket contains the added food item
        List<PricedItem> items = basket.getItems();
        assertEquals(1, items.size(), "Basket should contain one item.");
        assertEquals(foodItem.getName() + ": Small" , items.get(0).getName(), "The added food item's name should match.");
        assertEquals(foodPriceSmall, items.get(0).getPrice(), "The price of the added food item should match the price for the small portion.");
    }

    @Test
    public void testAddDrinkItem() {
        // Add a small drink
        basket.addDrink(drinkItem, "Small");

        // Check if the basket contains the added drink item
        List<PricedItem> items = basket.getItems();
        assertEquals(1, items.size(), "Basket should contain one item.");
        assertEquals(drinkItem.getName() + ": Small", items.get(0).getName(), "The added drink item's name should match.");
        assertEquals(drinkPriceSmall, items.get(0).getPrice(), "The price of the added drink item should match the price for the small portion.");
    }

    
    @Test
    public void testGetTotalQuantity() {
        // Add food, drink, and items to the basket
        basket.addFood(foodItem, "Small");
        basket.addDrink(drinkItem, "Large");

        // Total quantity should be 3 (1 food item, 1 drink item, 1 ticket item)
        int totalQuantity = basket.getTotalQuantity();
        assertEquals(2, totalQuantity, "Total quantity of items should be 3.");
    }

    @Test
    public void testIsDiscountActive() {
        // Set a time-limited discount
        LocalTime currentTime = LocalTime.now();
        LocalTime startTime = currentTime.minusMinutes(1);
        LocalTime endTime = currentTime.plusMinutes(1);
        Discount timeLimitedDiscount = new Discount(2, new Price(2.0), startTime, endTime);
        
        // Check if the discount is active during the time window
        assertTrue(timeLimitedDiscount.isDiscountActive(currentTime), "Discount should be active during the specified time window.");
    }


    @Test
    public void testClearBasket() {
        // Add a small pizza and a drink
        basket.addFood(foodItem, "Small");
        basket.addDrink(drinkItem, "Small");
        basket.clear();

        // Basket should be empty after clearing
        assertTrue(basket.isEmpty(), "Basket should be empty after clearing.");
    }

    

    @Test
    public void testToString() {
        // Add a small pizza
        basket.addFood(foodItem, "Small");

        // Check if the toString method includes food item details
        String basketString = basket.toString();
        assertTrue(basketString.contains("Pizza"), "Basket string should contain 'Pizza'.");
        assertTrue(basketString.contains("Small"), "Basket string should contain 'Small' portion.");
    }
}
