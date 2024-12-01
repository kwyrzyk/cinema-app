package com.example.database.db_classes;

public class Price {

    private int dollars;
    private int cents;

    // Constructor to initialize with dollars and cents
    public Price(int dollars, int cents) {
        this.dollars = dollars;
        this.cents = cents;
        normalize();  // Ensure cents are normalized (i.e., less than 100)
    }

    // Constructor to initialize with a double value
    public Price(double amount) {
        this.dollars = (int) amount;               // Extract the whole dollars
        this.cents = (int) Math.round((amount - this.dollars) * 100);  // Extract the cents
        normalize();  // Ensure cents don't exceed 100
    }

    // Normalize cents to ensure it doesn't exceed 100
    private void normalize() {
        if (cents >= 100) {
            dollars += cents / 100;   // Convert excess cents to dollars
            cents = cents % 100;      // Keep only the remainder of cents
        } else if (cents < 0) {
            dollars -= (Math.abs(cents) / 100) + 1;
            cents = 100 - (Math.abs(cents) % 100);
        }
    }

    // Getter for dollars
    public int getDollars() {
        return dollars;
    }

    // Getter for cents
    public int getCents() {
        return cents;
    }

    // Setter for dollars
    public void setDollars(int dollars) {
        this.dollars = dollars;
    }

    // Setter for cents
    public void setCents(int cents) {
        this.cents = cents;
        normalize();  // Ensure cents don't exceed 100
    }

    // Add another Price to this Price
    public Price add(Price other) {
        int newDollars = this.dollars + other.dollars;
        int newCents = this.cents + other.cents;
        return new Price(newDollars, newCents);
    }

    // Subtract another Price from this Price
    public Price subtract(Price other) {
        int newDollars = this.dollars - other.dollars;
        int newCents = this.cents - other.cents;
        return new Price(newDollars, newCents);
    }

    // Operator overloading for "+"
    public static Price operatorPlus(Price p1, Price p2) {
        return p1.add(p2);
    }

    // Operator overloading for "-"
    public static Price operatorMinus(Price p1, Price p2) {
        return p1.subtract(p2);
    }

    // "+=" operator for Price
    public void addEquals(Price other) {
        this.dollars += other.dollars;
        this.cents += other.cents;
        normalize();  // Ensure cents don't exceed 100
    }

    // "-=" operator for Price
    public void subtractEquals(Price other) {
        this.dollars -= other.dollars;
        this.cents -= other.cents;
        normalize();  // Ensure cents don't exceed 100
    }

    // Override toString() for display
    @Override
    public String toString() {
        return "$" + dollars + "." + (cents < 10 ? "0" + cents : cents);
    }

}
