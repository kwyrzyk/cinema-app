package com.example.listing;

import com.example.database.AccountRepository;
import com.example.database.db_classes.OrderHistoryRecord;

import java.util.List;


public class OrderHistoryListing {
    private List<OrderHistoryRecord> orders;

    public OrderHistoryListing(){}

    public void loadOrderHistory(int user_id){
        this.orders = AccountRepository.getAllOrdersHistory(user_id);
    }

    public List<OrderHistoryRecord> getOrders() {
        return orders;
    }
}
