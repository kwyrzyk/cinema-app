package com.example.database.db_classes;

import java.util.HashMap;
import java.util.Map;

public class Food {
    private int idFood;
    private String name;
    private Map<String, Price> prices;  // A map to hold size and price

    // Constructor
    public Food(int idFood, String name) {
        this.idFood = idFood;
        this.name = name;
        this.prices = new HashMap<>();
    }

    // Getters and Setters
    public int getIdFood() {
        return idFood;
    }

    public void setIdFood(int idFood) {
        this.idFood = idFood;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Method to add price for a given portion size
    public void addPrice(String portionSize, Price price) {
        this.prices.put(portionSize, price);
    }

    public Map<String, Price> getPrices(){
        return this.prices;
    }

    // Method to get the price for a specific portion size
    public Price getPriceForSize(String portionSize) {
        return this.prices.get(portionSize);
    }

    // To String method (for printing object details)
    @Override
    public String toString() {
        return "Food{idFood=" + idFood + ", name='" + name + "', prices=" + prices + "}";
    }
}
