package com.example.database.db_classes;

import java.util.ArrayList;
import java.util.List;

public class Discount {
    private int idDiscount;
    private Price price;
    private List<DiscountItem> foodItems;
    private List<DiscountItem> drinkItems;

    // Constructor
    public Discount(int idDiscount, double price) {
        this.idDiscount = idDiscount;
        this.price = new Price(price);
        this.foodItems = new ArrayList<>();
        this.drinkItems = new ArrayList<>();
    }
    
    
    public Discount(int idDiscount, Price price) {
        this.idDiscount = idDiscount;
        this.price = price;
        this.foodItems = new ArrayList<>();
        this.drinkItems = new ArrayList<>();
    }

    // Getters and Setters
    public int getIdDiscount() {
        return idDiscount;
    }

    public void setIdDiscount(int idDiscount) {
        this.idDiscount = idDiscount;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = new Price(price);
    }


    public void setPrice(Price price) {
        this.price = price;
    }

    // Methods to add food and drink items
    public void addFoodItem(int foodPriceId, String name, int foodCount) {
        this.foodItems.add(new DiscountItem(foodPriceId, name, foodCount));
    }

    public void addDrinkItem(int drinkPriceId, String name, int drinkCount) {
        this.drinkItems.add(new DiscountItem(drinkPriceId, name, drinkCount));
    }

    // To String method (for printing object details)
    @Override
    public String toString() {
        return "Set of " + foodItems +" "+drinkItems+ " price: " + price;
    }
}

