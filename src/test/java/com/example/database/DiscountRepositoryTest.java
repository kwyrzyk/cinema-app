package com.example.database;

import com.example.database.db_classes.Discount;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class DiscountRepositoryTest {

    @Test
    public void testGetDiscountById() throws Exception {
        // Test retrieving discount by ID
        Discount discount = DiscountRepository.getDiscountById(1);

        // Assert the discount is not null
        assertNotNull(discount, "Discount should not be null for ID 1");

        // You can add more assertions if necessary to check specific values
        assertNotNull(discount.getPrice(), "Discount price should not be null");
    }

    @Test
    public void testGetAllDiscounts() throws Exception {
        // Test retrieving all discounts
        List<Discount> discounts = DiscountRepository.getAllDiscounts();

        // Assert the list is not null and has at least one item
        assertNotNull(discounts, "Discounts list should not be null");
        assertTrue(discounts.size() > 0, "There should be at least one discount item");
    }
}
