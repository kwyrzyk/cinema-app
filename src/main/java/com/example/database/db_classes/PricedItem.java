package com.example.database.db_classes;

public class PricedItem {

    private String name;
    private Price price;

    private int foodId = -1;
    private int drinkId = -1;
    private int ticketId = -1;
    private int  discountId= -1;
    public static final Price ticketPrice = new Price(20.0);

    // Constructor to initialize name and price
    public PricedItem(String name, Price price) {
        this.name = name;
        this.price = price;
    }

    // Constructor for food item
    public PricedItem(Food foodItem, String size) {
        this.name = foodItem.getName() + ": " + size;
        this.price = foodItem.getPrices().get(size);
        this.foodId = foodItem.getIdFood();
    }

    // Constructor for drink item
    public PricedItem(Drink drinkItem, String size) {
        this.name = drinkItem.getName() + ": " + size;
        this.price = drinkItem.getPrices().get(size);
        this.drinkId = drinkItem.getIdDrink();
    }



    public PricedItem(Discount discountItem){
        this.name = discountItem.toString();
        this.price = discountItem.getPrice();
        this.discountId = discountItem.getIdDiscount();
    }


    public PricedItem(Ticket ticketItem){
        this.name = ticketItem.getName();
        this.price = ticketPrice;
        this.ticketId = ticketItem.getId();
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

    // Getter for foodId
    public int getFoodId() {
        return foodId;
    }

    // Getter for drinkId
    public int getDrinkId() {
        return drinkId;
    }

    // Getter for ticketId
    public int getTicketId() {
        return ticketId;
    }

    // Check if the item is a food item
    public boolean isFood() {
        return foodId != -1; // Food items have a valid foodId
    }

    // Check if the item is a drink item
    public boolean isDrink() {
        return drinkId != -1; // Drink items have a valid drinkId
    }

    // Check if the item is a showing (e.g., movie ticket)
    public boolean isTicket() {
        return ticketId != -1; // Showings have a valid showingId
    }

    public boolean isDiscount() {
        return discountId != -1; // Showings have a valid showingId
    }

    // ToString method for better debugging
    @Override
    public String toString() {
        return "PricedItem{name='" + name + "', price=" + price + ", foodId=" + foodId + ", drinkId=" + drinkId + ", ticketId=" + ticketId + '}';
    }
    public int getDiscountId(){
        return discountId;
    }

}
