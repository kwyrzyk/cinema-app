package com.example.database.db_classes;

import java.util.ArrayList;
import java.util.List;

public class Discount {
    private int idDiscount;
    private double discountPrice;
    private List<Food> foods;
    private List<Drink> drinks;

    // Constructor
    public Discount(int idDiscount, double discountPrice) {
        this.idDiscount = idDiscount;
        this.discountPrice = discountPrice;
        this.foods = new ArrayList<>();
        this.drinks = new ArrayList<>();
    }

    // Getters and Setters
    public int getIdDiscount() {
        return idDiscount;
    }

    public void setIdDiscount(int idDiscount) {
        this.idDiscount = idDiscount;
    }

    public double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
    }

    // Method to add food item to the discount
    public void addFood(Food food) {
        this.foods.add(food);
    }

    // Method to add drink item to the discount
    public void addDrink(Drink drink) {
        this.drinks.add(drink);
    }

    // Method to calculate the final price for the discount
    public double calculateFinalPrice() {
        double totalFoodPrice = 0.0;
        double totalDrinkPrice = 0.0;

        // Calculate the total price for all food items
        for (Food food : foods) {
            // For simplicity, let's assume you always get the price for the "Medium" portion size.
            // You can modify this to dynamically select a size based on a given input.
            totalFoodPrice += food.getPriceForSize("Medium") != null ? food.getPriceForSize("Medium") : 0;
        }

        // Calculate the total price for all drink items
        for (Drink drink : drinks) {
            // Again, we assume the "Medium" size for drinks
            totalDrinkPrice += drink.getPriceForSize("Medium") != null ? drink.getPriceForSize("Medium") : 0;
        }

        // Calculate the total price for food and drink, minus the discount price
        return (totalFoodPrice + totalDrinkPrice) - this.discountPrice;
    }

    // To String method (for printing object details)
    @Override
    public String toString() {
        return "Discount{idDiscount=" + idDiscount + ", discountPrice=" + discountPrice + ", foods=" + foods + ", drinks=" + drinks + "}";
    }
}
