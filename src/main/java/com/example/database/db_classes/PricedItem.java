package com.example.database.db_classes;


public class PricedItem {

    private String name;
    private Price price;

    private  ItemType type;

    private int id = -1;

    public static final Price ticketPrice = new Price(20.0);

    // Constructor to initialize name and price
    public PricedItem(String name, Price price) {
        this.name = name;
        this.price = price;
    }

    // Constructor for food item
    public PricedItem(Food foodItem, String size) {
        
        this.name = foodItem.getName() + ": " + size;
        this.price = foodItem.getPriceForSize(size);

        this.type = ItemType.FOOD;
        this.id = foodItem.getIdForSize(size);
    }

    // Constructor for drink item
    public PricedItem(Drink drinkItem, String size) {
        this.name = drinkItem.getName() + ": " + size;
        this.price = drinkItem.getPriceForSize(size);

        this.type = ItemType.DRINK;
        this.id = drinkItem.getIdForSize(size);
    }



    public PricedItem(Discount discountItem){
        this.name = discountItem.toString();
        this.price = discountItem.getPrice();


        this.type = ItemType.DISCOUNT;
        this.id = discountItem.getIdDiscount();
    }


    public PricedItem(Ticket ticketItem){
        this.name = ticketItem.getName();
        this.price = ticketPrice;


        this.type = ItemType.TICKET;
        this.id = ticketItem.getId();
    }

    // Constructor to initialize name and price using a double value
    public PricedItem(String name, String type, double price, int id) {
        switch (type) {
            case "food":
                this.type = ItemType.FOOD;
                break;
            case "drink":
                this.type = ItemType.DRINK;
                break;
            case "discount":
                this.type = ItemType.DISCOUNT;
                break;
            case "ticket":
                this.type = ItemType.TICKET;
                break;
            default:
                break;
        }
        this.id = id;
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

    public int getId(){
        return id;
    }

    public boolean isFood() {
        return type == ItemType.FOOD; 
    }

    public boolean isDrink() {
        return type == ItemType.DRINK; 
    }

    public boolean isTicket() {
        return type == ItemType.TICKET;
    }

    public boolean isDiscount() {
        return type == ItemType.DISCOUNT;
    }

    // ToString method for better debugging
    @Override
    public String toString() {
        return "PricedItem{name='" + name + "', price=" + price + ", Id=" + id + '}';
    }

}
