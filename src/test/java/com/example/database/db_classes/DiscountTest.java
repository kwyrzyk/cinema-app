package com.example.database.db_classes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;


import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DiscountTest {

    private Discount discount;
    private Price price;
    private LocalTime startTime;
    private LocalTime endTime;

    @BeforeEach
    public void setup() {
        price = new Price(5.0); // $5.00 discount
        startTime = LocalTime.of(9, 0); // Start at 9:00 AM
        endTime = LocalTime.of(17, 0); // End at 5:00 PM

        discount = new Discount(1, price, startTime, endTime); // Initialize discount object
        discount.addFoodItem(101, "Pizza", 1);
        discount.addDrinkItem(201, "Coke", 1);

    }

    @Test
    public void testSetupInitialization() {
        assertNotNull(discount, "Discount object should be initialized in setup.");
    }

    @Test
    public void testAddFoodItem() {
        assertTrue(discount.containsFoodItemById(101), "Discount should contain food item Pizza.");
    }

    @Test
    public void testAddDrinkItem() {
        assertTrue(discount.containsDrinkItemById(201), "Discount should contain drink item Coke.");
    }

    @Test
    public void testContainsFoodItemById() {
        assertTrue(discount.containsFoodItemById(101), "Discount should contain food item with ID 101.");
        assertFalse(discount.containsFoodItemById(102), "Discount should not contain food item with ID 102.");
    }

    @Test
    public void testContainsDrinkItemById() {
        assertTrue(discount.containsDrinkItemById(201), "Discount should contain drink item with ID 201.");
        assertFalse(discount.containsDrinkItemById(202), "Discount should not contain drink item with ID 202.");
    }

    @Test
    public void testIsDiscountActiveDuringActiveTime() {
        Discount timeLimitedDiscount = new Discount(1, price, startTime, endTime);
        LocalTime currentTime = LocalTime.of(12, 0); 
        assertTrue(timeLimitedDiscount.isDiscountActive(currentTime), "Discount should be active during the time window.");
    }


    @Test
    public void testIsDiscountNotActiveDuringNotActiveTime() {
        Discount timeLimitedDiscount = new Discount(1, price, startTime, endTime);
        LocalTime currentTime = LocalTime.of(20, 0); 
        assertFalse(timeLimitedDiscount.isDiscountActive(currentTime), "Discount should be active during the time window.");
    }

    @Test
    public void testDiscountToString() {
        String discountString = discount.toString();
        assertTrue(discountString.contains("Pizza"), "ToString should contain 'Pizza'.");
        assertTrue(discountString.contains("Coke"), "ToString should contain 'Coke'.");
        assertTrue(discountString.contains("$5.0"), "ToString should contain the discount price of $5.00.");
    }

    @Test
    public void testSetPrice() {
        discount.setPrice(6.00);
        assertEquals(6.00, discount.getPrice().getDollars(), "The discount price should be updated to $6.00.");
    }
}
