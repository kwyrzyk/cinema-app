package com.example.database.db_classes;

public class PricedItem {

    private String name;
    private Price price;


    private int foodId;
    private int drinkId;
    private int showingId;
    public static final Price ticketPrice = new Price(20.0);

    // Constructor to initialize name and price
    public PricedItem(String name, Price price) {
        this.name = name;
        this.price = price;
    }


    public PricedItem(Food foodItem, String size){
        this.name = foodItem.getName();
        this.price = foodItem.getPrices().get(size);
        this.foodId = foodItem.getIdFood();
    }


    public PricedItem(Drink drinkItem, String size){
        this.name = drinkItem.getName();
        this.price = drinkItem.getPrices().get(size);
        this.drinkId = drinkItem.getIdDrink();
    }


    public PricedItem(Showing showingItem){
        this.name = String.valueOf(showingItem.getFilmId()) + " " +   showingItem.getShowTime();
        this.price = ticketPrice;
        this.showingId = showingItem.getId();
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

}
