package com.example.database.db_classes;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalTime;

public class Discount {
    private int idDiscount;
    private Price price;
    private List<DiscountItem> foodItems;
    private List<DiscountItem> drinkItems;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean is_time_limited = false;


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


    public Discount(int idDiscount, double price, LocalTime startTime, LocalTime endTime) {
        this.idDiscount = idDiscount;
        this.price = new Price(price);
        this.is_time_limited = true;
        this.startTime = startTime;
        this.endTime = endTime;
        this.foodItems = new ArrayList<>();
        this.drinkItems = new ArrayList<>();
    }
    
    
    public Discount(int idDiscount, Price price, LocalTime startTime, LocalTime endTime) {
        this.idDiscount = idDiscount;
        this.price = price;
        this.is_time_limited = true;
        this.startTime = startTime;
        this.endTime = endTime;
        this.foodItems = new ArrayList<>();
        this.drinkItems = new ArrayList<>();
    }


    // time setters and getters
    public boolean is_time_limited(){
        return is_time_limited;
    }
    
    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setStartTime(LocalTime startTime) {
        if(this.is_time_limited == true){
            this.startTime = startTime;
        }
    }

    public void setEndTime(LocalTime endTime) {
        if(this.is_time_limited == true){
            this.endTime = endTime;
        }
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

