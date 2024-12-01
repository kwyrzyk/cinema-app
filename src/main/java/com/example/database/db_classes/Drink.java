package com.example.database.db_classes;

import java.util.HashMap;
import java.util.Map;

public class Drink {
    private int idDrink;
    private String name;
    private Map<String, Double> prices;  // A map to hold size and price for drinks

    // Constructor
    public Drink(int idDrink, String name) {
        this.idDrink = idDrink;
        this.name = name;
        this.prices = new HashMap<>();
    }

    // Getters and Setters
    public int getIdDrink() {
        return idDrink;
    }

    public void setIdDrink(int idDrink) {
        this.idDrink = idDrink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Double> getPrices(){
        return this.prices;
    }

    // Method to add price for a given portion size
    public void addPrice(String portionSize, double price) {
        this.prices.put(portionSize, price);
    }

    // Method to get the price for a specific portion size
    public Double getPriceForSize(String portionSize) {
        return this.prices.get(portionSize);
    }

    // To String method (for printing object details)
    @Override
    public String toString() {
        return "Drink{idDrink=" + idDrink + ", name='" + name + "', prices=" + prices + "}";
    }
}
