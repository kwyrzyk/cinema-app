package com.example.database.db_classes; 

import java.util.ArrayList;
import java.util.List;

public class Basket {

    private List<PricedItem> items;
    private List<Integer> quantities;

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


    public void addNewItemInQuantity(PricedItem item, int quantity){
        items.add(item);
        quantities.add(quantity);
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
            if (item.getId() == foodId && item.getName().equals(name)) {
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
            if (item.getId() == drinkId && item.getName().equals(name)) {
                return i; 
            }
        }
        return -1; // Return -1 if no match is found
    }

    public void addDrink(Drink drinkItem, String size) {
        PricedItem pricedItem = new PricedItem(drinkItem, size);
        int index = findIndexByDrinkId(drinkItem.getIdDrink(), pricedItem.getName());
        if(index == -1){
            addItem(pricedItem);
        }else{
            quantities.set(index, quantities.get(index) + 1);
        }
    }

    
    public int findIndexByTicketId(int ticketId, String name) {
        for (int i = 0; i < items.size(); i++) {
            PricedItem item = items.get(i);
            if (item.getId() == ticketId && item.getName().equals(name)) {
                return i; 
            }
        }
        return -1; // Return -1 if no match is found
    }
    
    public void addTicket(Ticket ticketItem) {
        PricedItem pricedItem = new PricedItem(ticketItem);
        int index = findIndexByTicketId(ticketItem.getId(), pricedItem.getName());
        if(index == -1){
            addItem(pricedItem);
        }else{
            quantities.set(index, quantities.get(index) + 1);
        }
    }

    public boolean removeTicket(Ticket ticket) {

    int index = findIndexByTicketId(ticket.getId(), ticket.getName());
    if (index == -1) {
        return false;
    }

    int currentQuantity = quantities.get(index);
    if (currentQuantity > 1) {
        quantities.set(index, currentQuantity - 1);
    } else {
        items.remove(index);
        quantities.remove(index);
    }

    return true; 
}


    public int findIndexByDiscountId(int discountId, String name) {
        for (int i = 0; i < items.size(); i++) {
            PricedItem item = items.get(i);
            if (item.getId() == discountId && item.getName().equals(name)) {
                return i; 
            }
        }
        return -1; // Return -1 if no match is found
    }


    public void addDiscount(Discount discountItem) {
        PricedItem pricedItem = new PricedItem(discountItem);
        int index = findIndexByDiscountId(discountItem.getIdDiscount(), pricedItem.getName());
        if(index == -1){
            addItem(pricedItem);
        }else{
            quantities.set(index, quantities.get(index) + 1);
        }
    }


    public List<PricedItem> getItems() {
        return items;
    }

    public List<Integer> getQuantities() {
        return quantities;
    }

    public double getTotalPrice() {
        double total = 0;
        for (int i = 0; i < items.size(); i++) {
            double price = items.get(i).getPrice().getDollars() + items.get(i).getPrice().getCents() / 100.0;
            price *= quantities.get(i); 
            total += price;
        }
        return total;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public boolean containsTickets() {
        for (PricedItem item : items) {
            if (item.getId() != -1) {
                return true; 
            }
        }
        return false; 
    }


    public int getTotalQuantity() {
        int totalQuantity = 0;
        for (int quantity : quantities) {
            totalQuantity += quantity;
        }
        return totalQuantity;
    }

    

    public void clear() {
        items.clear();
        quantities.clear();
    }

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

        sb.append("Total Price: $")
        .append(String.format("%.2f", getTotalPrice()))
        .append("\n");

        return sb.toString();
    }
}
