package com.example.listing;

import com.example.database.AccountRepository;
import com.example.database.DatabaseManager;
import com.example.database.db_classes.OrderHistoryRecord;
import com.example.exceptions.ErrorHandler;

import java.util.List;


public class OrderHistoryListing {
    private List<OrderHistoryRecord> orders;
    private DatabaseManager databaseManager;


    public OrderHistoryListing(DatabaseManager databaseManager){
        this.databaseManager = databaseManager;
    }

    public void loadOrderHistory(int user_id){
        try{
            this.orders = AccountRepository.getAllOrdersHistory(user_id, databaseManager.getConnection());
        }catch (Exception e){
            ErrorHandler.handle(e);
        }
    }

    public List<OrderHistoryRecord> getOrders() {
        return orders;
    }
}
