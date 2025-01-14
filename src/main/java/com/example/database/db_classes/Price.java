package com.example.database.db_classes;

public class Price {

    private int dollars;
    private int cents;

    public Price(int dollars, int cents) {
        this.dollars = dollars;
        this.cents = cents;
        normalize();  
    }

    public Price(double amount) {
        this.dollars = (int) amount;               
        this.cents = (int) Math.round((amount - this.dollars) * 100);  
        normalize();  
    }

    private void normalize() {
        if (cents >= 100) {
            dollars += cents / 100;   
            cents = cents % 100;      
        } else if (cents < 0) {
            dollars -= (Math.abs(cents) / 100) + 1;
            cents = 100 - (Math.abs(cents) % 100);
        }
    }

    public int getDollars() {
        return dollars;
    }

    public int getCents() {
        return cents;
    }

    public void setDollars(int dollars) {
        this.dollars = dollars;
    }

    public void setCents(int cents) {
        this.cents = cents;
        normalize(); 
    }

    public Price add(Price other) {
        int newDollars = this.dollars + other.dollars;
        int newCents = this.cents + other.cents;
        return new Price(newDollars, newCents);
    }

    public Price subtract(Price other) {
        int newDollars = this.dollars - other.dollars;
        int newCents = this.cents - other.cents;
        return new Price(newDollars, newCents);
    }

    public static Price operatorPlus(Price p1, Price p2) {
        return p1.add(p2);
    }

    public static Price operatorMinus(Price p1, Price p2) {
        return p1.subtract(p2);
    }

    public void addEquals(Price other) {
        this.dollars += other.dollars;
        this.cents += other.cents;
        normalize();  
    }

    public void subtractEquals(Price other) {
        this.dollars -= other.dollars;
        this.cents -= other.cents;
        normalize();  
    }

    public double toDouble(){
        return dollars + ((double)cents)/100;
    }


    @Override
    public String toString() {
        return "$" + dollars + "." + (cents < 10 ? "0" + cents : cents);
    }

}
