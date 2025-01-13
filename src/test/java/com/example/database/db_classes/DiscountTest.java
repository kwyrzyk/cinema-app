package com.example.database.db_classes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class DiscountTest {

    private Discount discount;
    private Price price;
    private LocalTime startTime;
    private LocalTime endTime;

    @BeforeEach
    public void setup() {
        // Create a basic price for the discount
        price = new Price(5.0); // $5.00 discount

        // Create a time-limited discount (if necessary)
        startTime = LocalTime.of(9, 0); // Start at 9:00 AM
        endTime = LocalTime.of(17, 0); // End at 5:00 PM
        
        // Create a discount that applies to food and drink items
        discount = new Discount(1, price);
        
        // Add food and drink items to the discount
        discount.addFoodItem(101, "Pizza", 1);
        discount.addDrinkItem(201, "Coke", 1);
    }


    @Test
    public void testDiscountConstructorWithTimeLimit() {
        // Create a time-limited discount
        Discount timeLimitedDiscount = new Discount(2, price, startTime, endTime);

        // Verify that the time-limited discount is correctly set
        assertTrue(timeLimitedDiscount.is_time_limited(), "Discount should be time-limited.");
        assertEquals(startTime, timeLimitedDiscount.getStartTime(), "Start time should be 9:00 AM.");
        assertEquals(endTime, timeLimitedDiscount.getEndTime(), "End time should be 5:00 PM.");
    }

    @Test
    public void testAddFoodItem() {
        // Verify that food items can be added to the discount
        assertTrue(discount.containsFoodItemById(101), "Discount should contain food item Pizza.");
    }

    @Test
    public void testAddDrinkItem() {
        // Verify that drink items can be added to the discount
        assertTrue(discount.containsDrinkItemById(201), "Discount should contain drink item Coke.");
    }

    @Test
    public void testContainsFoodItemById() {
        // Test if a specific food item ID is part of the discount
        assertTrue(discount.containsFoodItemById(101), "Discount should contain food item with ID 101.");
        assertFalse(discount.containsFoodItemById(102), "Discount should not contain food item with ID 102.");
    }

    @Test
    public void testContainsDrinkItemById() {
        // Test if a specific drink item ID is part of the discount
        assertTrue(discount.containsDrinkItemById(201), "Discount should contain drink item with ID 201.");
        assertFalse(discount.containsDrinkItemById(202), "Discount should not contain drink item with ID 202.");
    }

    @Test
    public void testIsDiscountActiveDuringActiveTime() {
        // Set the current time to a valid time within the discount period
        LocalTime currentTime = LocalTime.of(12, 0); // 12:00 PM (within the time window)
        
        // Test if the discount is active
        assertTrue(discount.isDiscountActive(currentTime), "Discount should be active during the time window.");
    }


    @Test
    public void testIsDiscountActiveWhenNoTimeLimit() {
        // Create a discount without a time limit
        Discount noTimeLimitDiscount = new Discount(3, price);
        
        // Discount should always be active as there is no time limit
        LocalTime currentTime = LocalTime.of(18, 0); // 6:00 PM
        assertTrue(noTimeLimitDiscount.isDiscountActive(currentTime), "Discount without time limit should always be active.");
    }

    @Test
    public void testDiscountToString() {
        // Verify the toString method for the discount
        String discountString = discount.toString();
        assertTrue(discountString.contains("Pizza"), "ToString should contain 'Pizza'.");
        assertTrue(discountString.contains("Coke"), "ToString should contain 'Coke'.");
        assertTrue(discountString.contains("$5.0"), "ToString should contain the discount price of $5.00.");
    }

    @Test
    public void testSetPrice() {
        // Change the price of the discount
        discount.setPrice(6.00); // Set to $6.00

        // Verify that the price was updated
        assertEquals(6, discount.getPrice().getDollars(), "The discount price should be updated to $6.00.");
    }
}
