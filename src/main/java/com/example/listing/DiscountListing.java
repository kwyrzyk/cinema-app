package com.example.listing;

import com.example.database.DiscountRepository;
import com.example.database.db_classes.Discount;

import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalTime;

public class DiscountListing {
    private List<Discount> discounts;
    


    public DiscountListing(){
        this.discounts = DiscountRepository.getAllDiscounts();
    }

    public List<Discount> getDiscounts() {
        return discounts;
    }

    // Static method to display all the discounts
    public void displayDiscounts() {
        List<Discount> discounts = getDiscounts();  // Get the list of discounts
        
        for (Discount discount : discounts) {
            System.out.println(discount);
        }
    }

    public List<Discount> getActiveDiscounts() {
        return discounts.stream()
                .filter(discount -> discount.isDiscountActive(LocalTime.now()))
                .collect(Collectors.toList());
    }

}
