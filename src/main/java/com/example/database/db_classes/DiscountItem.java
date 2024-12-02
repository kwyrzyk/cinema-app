package com.example.database.db_classes;

public class DiscountItem {
    private int idItem;
    private int count;
    private String name;

    // Constructor
    public DiscountItem(int idItem, String name, int count) {
        this.name = name;
        this.idItem = idItem;
        this.count = count;
    }

    // Getters and Setters
    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName(){
        return this.name;
    }

    // To String method (for printing object details)
    @Override
    public String toString() {
        return  name + " x" + count + " ";
    }
}
