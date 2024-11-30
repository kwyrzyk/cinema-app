package com.example.database.db_classes;

public class DiscountItem {
    private int idItem;
    private int count;

    // Constructor
    public DiscountItem(int idItem, int count) {
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

    // To String method (for printing object details)
    @Override
    public String toString() {
        return "DiscountItem{idItem=" + idItem + ", count=" + count + "}";
    }
}
