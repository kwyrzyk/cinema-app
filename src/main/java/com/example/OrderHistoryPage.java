package com.example;

import com.example.database.AccountRepository;
import com.example.database.db_classes.Account;
import com.example.database.db_classes.OrderHistoryRecord;
import com.example.database.db_classes.Price;
import com.example.database.db_classes.PricedItem;
import com.example.listing.AccountListing;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.w3c.dom.Node;

public class OrderHistoryPage implements Page {
    private final VBox pageContent = new VBox();
    private final ScrollPane scrollPane = new ScrollPane(pageContent);
    private final VBox historyBox = new VBox(scrollPane);
    private final VBox orderItemsBox = new VBox();
    private final Controller controller;

    private final List<OrderHistoryRecord> allOrders;
    private List<OrderHistoryRecord> displayedOrders;

    public OrderHistoryPage(Controller controller, List<OrderHistoryRecord> orders) {
        this.controller = controller;

        this.allOrders = orders;
        this.displayedOrders = new ArrayList<>(this.allOrders);

        createContent();
    }
    
    private void createContent() {
        historyBox.getStyleClass().add("page");
        scrollPane.getStyleClass().add("scroll-pane");
        scrollPane.setFitToWidth(true);
        pageContent.getStyleClass().add("wide-box");
        
        Label title = new Label("Order History");
        title.getStyleClass().add("page-title");

        HBox searchBox = new HBox();
        searchBox.getStyleClass().add("search-box");

        TextField searchField = new TextField();
        searchField.setPromptText("Find in history...");
        searchField.getStyleClass().add("input-field");

        Button searchButton = new Button("Search");
        searchButton.getStyleClass().add("btn");
        searchButton.setOnAction(event -> {
            String query = searchField.getText().trim().toLowerCase();
            filterOrders(query);
        });

        searchBox.getChildren().addAll(searchField, searchButton);

        updateOrdersView(displayedOrders);

        pageContent.getChildren().addAll(title, searchBox, orderItemsBox);
    }

    private void updateOrdersView(List<OrderHistoryRecord> ordersToDisplay) {
        orderItemsBox.getChildren().clear();

        if (ordersToDisplay.isEmpty()) {
            Label noOrdersLabel = new Label("No orders available.");
            noOrdersLabel.getStyleClass().add("no-orders-label");
            orderItemsBox.getChildren().add(noOrdersLabel);
        } else {
            for (OrderHistoryRecord order : ordersToDisplay.reversed()) {
                Label orderLabel = new Label(
                        "Date: " + order.getDate() + "\n" +
                        order.getBasket().toString()
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

                    ButtonType buttonYes = new ButtonType("Yes");
                    ButtonType buttonNo = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                    confirmationAlert.getButtonTypes().setAll(buttonYes, buttonNo);
                    
                    confirmationAlert.showAndWait().ifPresent(response -> {
                        if (response == buttonYes) {
                            orderItemsBox.getChildren().remove(orderLabel);

                            AccountRepository.removeOrderById(order.getOrder_id());
                            AccountRepository.addBalance(controller.getAccountId(), moneyToRefund.toDouble());
                            AccountRepository.takeLoyaltyPoints(controller.getAccountId(), pointsToRemove);
                            
                            controller.getAccountListing().updateAccount(controller.getAccountId());
                        }
                    });
                });

                orderItemsBox.getChildren().add(orderLabel);
            }
        }
    }

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

    public VBox getPage() {
        return historyBox;
    }
}
