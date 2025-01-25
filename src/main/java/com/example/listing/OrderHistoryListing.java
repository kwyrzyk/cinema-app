package com.example.listing;

import com.example.database.AccountRepository;
import com.example.database.DatabaseManager;
import com.example.database.db_classes.OrderHistoryRecord;
import com.example.exceptions.ErrorHandler;

import java.util.List;

public class OrderHistoryListing {
    private List<OrderHistoryRecord> orders;
    private DatabaseManager databaseManager;

    /**
     * Constructor initializes the database manager.
     * @param databaseManager the database manager
     */
    public OrderHistoryListing(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    /**
     * Load the order history for a specific user.
     * @param user_id the user ID
     */
    public void loadOrderHistory(int user_id) {
        try {
            this.orders = AccountRepository.getAllOrdersHistory(user_id, databaseManager.getConnection());
        } catch (Exception e) {
            ErrorHandler.handle(e);
        }
    }

    /**
     * Get the list of order history records.
     * @return a list of order history records
     */
    public List<OrderHistoryRecord> getOrders() {
        return orders;
    }
}
