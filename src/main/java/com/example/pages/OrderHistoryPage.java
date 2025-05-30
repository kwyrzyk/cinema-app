package com.example.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.Controller;
import com.example.database.AccountRepository;
import com.example.database.db_classes.Account;
import com.example.database.db_classes.OrderHistoryRecord;
import com.example.database.db_classes.Price;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

/**
 * Class representing the Order History Page.
 */
public class OrderHistoryPage implements Page {
    private final VBox pageContent = new VBox();
    private final ScrollPane scrollPane = new ScrollPane(pageContent);
    private final VBox historyBox = new VBox(scrollPane);
    private final VBox orderItemsBox = new VBox();
    private final Controller controller;

    private final List<OrderHistoryRecord> allOrders;
    private List<OrderHistoryRecord> displayedOrders;

    /**
     * Constructor for OrderHistoryPage.
     * 
     * @param controller the controller instance
     * @param orders the list of order history records
     */
    public OrderHistoryPage(Controller controller, List<OrderHistoryRecord> orders) {
        this.controller = controller;
        this.allOrders = orders;
        this.displayedOrders = new ArrayList<>(this.allOrders);
        createContent();
    }
    
    /**
     * Creates the content for the Order History Page.
     */
    private void createContent() {
        historyBox.getStyleClass().add("page");
        scrollPane.getStyleClass().add("scroll-pane");
        scrollPane.setFitToWidth(true);
        pageContent.getStyleClass().add("wide-box");
        
        Label title = new Label("Order History");
        title.getStyleClass().add("page-title");

        SearchPanel searchPanel = new SearchPanel("Search for films...", this::filterOrders);

        updateOrdersView(displayedOrders);

        pageContent.getChildren().addAll(title, searchPanel, orderItemsBox);
    }

    /**
     * Updates the view with the given list of orders.
     * 
     * @param ordersToDisplay the list of orders to display
     */
    private void updateOrdersView(List<OrderHistoryRecord> ordersToDisplay) {
        orderItemsBox.getChildren().clear();

        if (ordersToDisplay.isEmpty()) {
            Label noOrdersLabel = new Label("No orders available.");
            noOrdersLabel.getStyleClass().add("no-orders-label");
            orderItemsBox.getChildren().add(noOrdersLabel);
        } else {
            for (OrderHistoryRecord order : ordersToDisplay.reversed()) {
                Label orderLabel = new Label(
                        "Date: " + order.getDate() + "\n" + order.getBasket().toString()
                );
                orderLabel.getStyleClass().add("item");
                
                orderLabel.setOnMouseClicked(event -> {
                    int userId = controller.getAccountId();
                    Account userAccount = controller.getAccountListing().getAccountById(userId);
                    int userLoyaltyPoints = userAccount.getLoyaltyPoints();

                    Price moneyToRefund = order.getPrice();
                    int loyaltyPointsToRemove = moneyToRefund.getDollars();

                    String message = "Do you want to refund this order?";
                    if (loyaltyPointsToRemove > userLoyaltyPoints) {
                        moneyToRefund.subtractEquals(new Price(loyaltyPointsToRemove - userLoyaltyPoints, 0));
                        loyaltyPointsToRemove = userLoyaltyPoints;
                    }
                    final int pointsToRemove = loyaltyPointsToRemove;

                    String pointsLoseInfo = "You will lose " + loyaltyPointsToRemove + " loyalty points.";
                    String moneyRefundInfo = "You will get " + moneyToRefund + " refunded.";
                    
                    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmationAlert.setTitle("Order refund");
                    confirmationAlert.setHeaderText(null);
                    confirmationAlert.setContentText(message + "\n" + moneyRefundInfo + "\n"+ pointsLoseInfo  );
                    String cssfile = Controller.class.getResource("/css/styles.css").toExternalForm();
                    confirmationAlert.getDialogPane().getStylesheets().add(cssfile);

                    ButtonType buttonYes = new ButtonType("Yes");
                    ButtonType buttonNo = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                    confirmationAlert.getButtonTypes().setAll(buttonYes, buttonNo);
                    
                    confirmationAlert.showAndWait().ifPresent(response -> {
                        if (response == buttonYes) {
                            orderItemsBox.getChildren().remove(orderLabel);

                            AccountRepository.removeOrderById(order.getOrder_id(), controller.databaseManager.getConnection());
                            AccountRepository.addBalance(controller.getAccountId(), moneyToRefund.toDouble(), controller.databaseManager.getConnection());
                            AccountRepository.takeLoyaltyPoints(controller.getAccountId(), pointsToRemove, controller.databaseManager.getConnection());
                            
                            controller.getAccountListing().updateAccount(controller.getAccountId());
                        }
                    });
                });
                orderItemsBox.getChildren().add(orderLabel);
            }
        }
    }

    /**
     * Filters the orders based on the given query.
     * 
     * @param query the search query
     */
    private void filterOrders(String query) {
        if (query.isEmpty()) {
            displayedOrders = new ArrayList<>(allOrders);
        } else {
            displayedOrders = allOrders.stream()
                .filter(order -> {
                    String dateStr = (order.getDate() != null) ? order.getDate().toString().toLowerCase() : "";
                    String priceStr = String.valueOf(order.getPrice()).toLowerCase();
                    String basketStr = (order.getBasket() != null) ? order.getBasket().toString().toLowerCase() : "";

                    return dateStr.contains(query) 
                           || priceStr.contains(query)
                           || basketStr.contains(query);
                })
                .collect(Collectors.toList());
        }
        updateOrdersView(displayedOrders);
    }

    /**
     * Returns the page content.
     * 
     * @return the VBox containing the page content
     */
    public VBox getPage() {
        return historyBox;
    }
}
