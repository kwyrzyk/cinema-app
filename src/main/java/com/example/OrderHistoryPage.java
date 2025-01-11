package com.example;

import com.example.database.AccountRepository;
import com.example.database.db_classes.OrderHistoryRecord;
import com.example.database.db_classes.PricedItem;
import com.example.listing.AccountListing;


import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.util.List;

public class OrderHistoryPage implements Page {
    private final VBox historyBox;
    private final ScrollPane scrollPane;
    private final Controller controller;

    public OrderHistoryPage(Controller controller, List<OrderHistoryRecord> orders) {
        this.controller = controller;

        // Główna kontenerka dla elementów na stronie
        historyBox = new VBox(10); // 10px odstępu między elementami
        historyBox.setAlignment(Pos.TOP_CENTER); // Wyrównanie do góry, na środku
        historyBox.getStyleClass().add("order-history-box");

        // Tytuł strony
        Label title = new Label("Order History");
        title.getStyleClass().add("order-history-title");

        // Dodanie zamówień do widoku
        if (orders == null || orders.isEmpty()) {
            // Jeśli brak zamówień
            Label noOrdersLabel = new Label("No orders available.");
            noOrdersLabel.getStyleClass().add("no-orders-label");
            historyBox.getChildren().addAll(title, noOrdersLabel);
        } else {
            // Dodanie zamówień do strony
            historyBox.getChildren().add(title);
            for (OrderHistoryRecord order : orders) {
                Label orderLabel = new Label(
                    "Date: " + order.getDate() + "\n" + order.getBasket().toString()
                );
                
                orderLabel.setOnMouseClicked(event -> {
                    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmationAlert.setTitle("Order refund");
                    confirmationAlert.setHeaderText(null);
                    confirmationAlert.setContentText("Do you want to refund this order?");
                    
                    ButtonType buttonYes = new ButtonType("Yes");
                    ButtonType buttonNo = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                    confirmationAlert.getButtonTypes().setAll(buttonYes, buttonNo);
    
                    // Oczekiwanie na wybór użytkownika
                    confirmationAlert.showAndWait().ifPresent(response -> {
                        if (response == buttonYes) {
                            System.out.println("Refunding order: " + order.getOrder_id());
                            historyBox.getChildren().remove(orderLabel); 
                            AccountRepository.removeOrderById(order.getOrder_id());
                            AccountRepository.addBalance(controller.getAccountId(), order.getPrice().toDouble());
                            controller.getAccountListing().updateAccount(controller.getAccountId());
                        }
                    });
                });
                orderLabel.getStyleClass().add("order-label");
                orderLabel.setWrapText(true); // Zawijanie tekstu w przypadku długiego opisu
                orderLabel.setMaxWidth(600); // Maksymalna szerokość etykiety
                historyBox.getChildren().add(orderLabel); 
            }
        }

        // Dodanie kontenera do ScrollPane
        scrollPane = new ScrollPane(historyBox);
        scrollPane.setFitToWidth(true); // Dopasowanie szerokości do dostępnego miejsca
        scrollPane.getStyleClass().add("order-history-scrollpane");
    }

    public VBox getPage() {
        return new VBox(scrollPane);
    }
}
