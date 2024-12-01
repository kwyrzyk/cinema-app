package com.example.database.db_classes;

public class PricedItem {

    private String name;
    private Price price;

    // Constructor to initialize name and price
    public PricedItem(String name, Price price) {
        this.name = name;
        this.price = price;
    }

    // Constructor to initialize name and price using a double value
    public PricedItem(String name, double price) {
        this.name = name;
        this.price = new Price(price); // Create a Price object from the double value
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Getter for price
    public Price getPrice() {
        return price;
    }
}
