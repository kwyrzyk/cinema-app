package com.example.database.db_classes;

import java.util.Date;

public class OrderHistoryRecord {
    private Basket basket;
    private int order_id;
    private Price price;
    private Date date;

    public OrderHistoryRecord(int order_id, double price, Date date, Basket basket){
        this.order_id = order_id;
        this.price = new Price(price);
        this.basket = basket;
        this.date = date;
    }

    public Price getPrice() {
        return price;
    }

    public int getOrder_id() {
        return order_id;
    }

    public Basket getBasket() {
        return basket;
    }

    public Date getDate() {
        return date;
    }
}
