package com.example.listing;

import com.example.database.DatabaseManager;
import com.example.database.DiscountRepository;
import com.example.database.db_classes.Discount;
import com.example.exceptions.ErrorHandler;

import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalTime;

public class DiscountListing {
    private List<Discount> discounts;
    private DatabaseManager databaseManager;



    public DiscountListing(DatabaseManager databaseManager){
        this.databaseManager = databaseManager;
        try{
            this.discounts = DiscountRepository.getAllDiscounts(this.databaseManager.getConnection());
        } catch( Exception e){
            ErrorHandler.handle(e);
        }
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
