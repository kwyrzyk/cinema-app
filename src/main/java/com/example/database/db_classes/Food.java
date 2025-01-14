package com.example.database.db_classes;

import java.util.HashMap;
import java.util.Map;
import javafx.util.Pair;

public class Food {
    private int idFood;
    private String name;
    private Map<String, Pair<Integer, Price>> prices;


    public Food(int idFood, String name) {
        this.idFood = idFood;
        this.name = name;
        this.prices = new HashMap<>();
    }

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


    @Override
    public String toString() {
        return "Food{idFood=" + idFood + ", name='" + name + "', prices=" + prices + "}";
    }
}
