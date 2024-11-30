package com.example.listing;

import com.example.database.DiscountRepository;
import com.example.database.db_classes.Discount;

import java.util.List;

public class DiscountListing {

    // Static method to get the list of discounts
    public static List<Discount> getDiscounts() {
        // Fetch the list of discounts using the static method from DiscountRepository
        return DiscountRepository.getAllDiscounts();
    }

    // Static method to display all the discounts
    public static void displayDiscounts() {
        List<Discount> discounts = getDiscounts();  // Get the list of discounts
        
        for (Discount discount : discounts) {
            System.out.println(discount);
        }
    }

    // You can add more methods to filter, sort, or display the list of discounts as needed
}
