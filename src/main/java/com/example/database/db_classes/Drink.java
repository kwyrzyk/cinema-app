package com.example.database.db_classes;

import java.util.HashMap;
import java.util.Map;

import javafx.util.Pair;

public class Drink {
    private int idDrink;
    private String name;
    private Map<String, Pair<Integer, Price>> prices;
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

    public void addPrice(String portionSize, Price price, int id) {
        this.prices.put(portionSize, new Pair< Integer, Price>(id, price));
    }

    public void setPrices(HashMap<String, Pair<Integer, Price>> prices){
        this.prices = prices;
    }

    public Map<String, Pair<Integer, Price>> getPrices(){
        return this.prices;
    }

    // Method to get the price for a specific portion size
    public Price getPriceForSize(String portionSize) {
        return this.prices.get(portionSize).getValue();
    }


    public Integer getIdForSize(String portionSize) {
        return this.prices.get(portionSize).getKey();
    }


    // To String method (for printing object details)
    @Override
    public String toString() {
        return "Drink{idDrink=" + idDrink + ", name='" + name + "', prices=" + prices + "}";
    }
}
