package com.example.database.db_classes;

import java.util.ArrayList;
import java.util.List;

public class Basket {

    private List<PricedItem> items;
    private List<Integer> quantities;

    // Constructor to initialize the basket
    public Basket() {
        this.items = new ArrayList<>();
        this.quantities = new ArrayList<>();
    }

    // Method to add an item to the basket
    public void addItem(PricedItem item) {
        int index = items.indexOf(item);
        if (index == -1) {
            // Item is not in the basket, so add it
            items.add(item);
            quantities.add(1); // Start with quantity 1
        } else {
            // Item already exists, increase its quantity
            quantities.set(index, quantities.get(index) + 1);
        }
    }
    // Method to remove an item from the basket
    public boolean removeItem(PricedItem item) {
        int index = items.indexOf(item);
        if (index == -1) {
            return false; // Item not found
        }
        int currentQuantity = quantities.get(index);
        if (currentQuantity > 1) {
            // Decrease quantity
            quantities.set(index, currentQuantity - 1);
        } else {
            // Remove item completely if quantity is 1
            items.remove(index);
            quantities.remove(index);
        }
        return true; // Item successfully removed
    }


    public int findIndexByFoodId(int foodId, String name) {
        for (int i = 0; i < items.size(); i++) {
            PricedItem item = items.get(i);
            if (item.getFoodId() == foodId && item.getName().equals(name)) {
                return i; // Return the index if a match is found
            }
        }
        return -1; // Return -1 if no match is found
    }

    // Method to add a Food item to the basket
    public void addFood(Food foodItem, String size) {
        PricedItem pricedItem = new PricedItem(foodItem, size);
        int index = findIndexByFoodId(foodItem.getIdFood(), pricedItem.getName());
        if(index == -1){
            addItem(pricedItem);   
        }else{
            quantities.set(index, quantities.get(index) + 1);
        }
    }


    public int findIndexByDrinkId(int drinkId, String name) {
        for (int i = 0; i < items.size(); i++) {
            PricedItem item = items.get(i);
            if (item.getDrinkId() == drinkId && item.getName().equals(name)) {
                return i; // Return the index if a match is found
            }
        }
        return -1; // Return -1 if no match is found
    }

    // Method to add a Drink item to the basket
    public void addDrink(Drink drinkItem, String size) {
        PricedItem pricedItem = new PricedItem(drinkItem, size);
        int index = findIndexByDrinkId(drinkItem.getIdDrink(), pricedItem.getName());
        if(index == -1){
            addItem(pricedItem);
        }else{
            quantities.set(index, quantities.get(index) + 1);
        }
    }

    
    public int findIndexByShowingId(int showingId, String name) {
        for (int i = 0; i < items.size(); i++) {
            PricedItem item = items.get(i);
            if (item.getDrinkId() == showingId && item.getName().equals(name)) {
                return i; // Return the index if a match is found
            }
        }
        return -1; // Return -1 if no match is found
    }
    
    // Method to add a Ticket item to the basket
    public void addTicket(Showing showingItem) {
        PricedItem pricedItem = new PricedItem(showingItem);
        int index = findIndexByShowingId(showingItem.getId(), pricedItem.getName());
        if(index == -1){
            addItem(pricedItem);
        }else{
            quantities.set(index, quantities.get(index) + 1);
        }
    }

    public int findIndexByDiscountId(int discountId, String name) {
        for (int i = 0; i < items.size(); i++) {
            PricedItem item = items.get(i);
            if (item.getDrinkId() == discountId && item.getName().equals(name)) {
                return i; // Return the index if a match is found
            }
        }
        return -1; // Return -1 if no match is found
    }


    public void addDiscount(Discount discountItem) {
        PricedItem pricedItem = new PricedItem(discountItem);
        int index = findIndexByShowingId(discountItem.getIdDiscount(), pricedItem.getName());
        if(index == -1){
            addItem(pricedItem);
        }else{
            quantities.set(index, quantities.get(index) + 1);
        }
    }


    // Method to get the list of items
    public List<PricedItem> getItems() {
        return items;
    }

    // Method to get the list of quantities
    public List<Integer> getQuantities() {
        return quantities;
    }

    // Method to get the total price of all items in the basket
    public double getTotalPrice() {
        double total = 0;
        for (int i = 0; i < items.size(); i++) {
            double price = items.get(i).getPrice().getDollars() + items.get(i).getPrice().getCents() / 100.0;
            price *= quantities.get(i); // Multiply by quantity of this item
            total += price;
        }
        return total;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void clear() {
        items.clear();
        quantities.clear();
    }

    // Method to display the basket content
    public void displayBasket() {
        for (int i = 0; i < items.size(); i++) {
            System.out.println("Item: " + items.get(i).getName() + ", Quantity: " + quantities.get(i) + ", Price: " + items.get(i).getPrice() + ", Total: " + (items.get(i).getPrice().getDollars() + items.get(i).getPrice().getCents() / 100.0) * quantities.get(i));
        }
        System.out.println("Total Price: " + getTotalPrice());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Basket Contents:\n");

        // Loop through all items in the basket and add their details
        for (int i = 0; i < items.size(); i++) {
            PricedItem item = items.get(i);
            int quantity = quantities.get(i);
            double totalItemPrice = (item.getPrice().getDollars() + item.getPrice().getCents() / 100.0) * quantity;

            sb.append(item.getName())
            .append(" - Quantity: ")
            .append(quantity)
            .append(", Price: $")
            .append(item.getPrice().getDollars())
            .append(".")
            .append(String.format("%02d", item.getPrice().getCents()))
            .append(", Total: $")
            .append(String.format("%.2f", totalItemPrice))
            .append("\n");
        }

        // Display the total price of all items in the basket
        sb.append("Total Price: $")
        .append(String.format("%.2f", getTotalPrice()))
        .append("\n");

        return sb.toString();
    }
}
