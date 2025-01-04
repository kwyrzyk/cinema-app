package com.example.database.db_classes;

public class PricedItem {

    private String name;
    private Price price;

    private int foodId = -1;
    private int drinkId = -1;
    private int showingId = -1;
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


    public PricedItem(Showing showingItem){
        this.name = String.valueOf(showingItem.getFilmId()) + " " +   showingItem.getShowTime();
        this.price = ticketPrice;
        this.showingId = showingItem.getId();
    }

    // Constructor to initialize name and price using a double value
    public PricedItem(String name, String type, double price, int id) {
        switch (type) {
            case "food":
                this.foodId = id;
                break;
            case "drink":
                this.foodId = id;
                break;
            case "discount":
                this.discountId = id;
                break;
            case "ticket":
                this.showingId = id;
                break;
            default:
                break;
        }
        this.name = name;
        this.price = new Price(price); 
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

    // Getter for showingId
    public int getShowingId() {
        return showingId;
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
    public boolean isShowing() {
        return showingId != -1; // Showings have a valid showingId
    }

    // ToString method for better debugging
    @Override
    public String toString() {
        return "PricedItem{name='" + name + "', price=" + price + ", foodId=" + foodId + ", drinkId=" + drinkId + ", showingId=" + showingId + '}';
    }
    public int getDiscountId(){
        return discountId;
    }

}
