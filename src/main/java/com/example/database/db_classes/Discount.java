package com.example.database.db_classes;

import java.util.ArrayList;
import java.util.List;

public class Discount {
    private int idDiscount;
    private double price;
    private List<DiscountItem> foodItems;
    private List<DiscountItem> drinkItems;

    // Constructor
    public Discount(int idDiscount, double price) {
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // Methods to add food and drink items
    public void addFoodItem(int foodPriceId, int foodCount) {
        this.foodItems.add(new DiscountItem(foodPriceId, foodCount));
    }

    public void addDrinkItem(int drinkPriceId, int drinkCount) {
        this.drinkItems.add(new DiscountItem(drinkPriceId, drinkCount));
    }

    // To String method (for printing object details)
    @Override
    public String toString() {
        return foodItems +" "+drinkItems+ "price = " + price;
    }
}

