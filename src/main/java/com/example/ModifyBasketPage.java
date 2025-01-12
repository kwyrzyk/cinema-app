package com.example;

import com.example.database.db_classes.Basket;
import com.example.database.db_classes.PricedItem;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.List;

public class ModifyBasketPage implements Page {
    private final VBox basketBox;
    private final Basket basket;
    private final Controller controller;

    public ModifyBasketPage(Controller controller, Basket basket) {
        this.controller = controller;
        this.basket = basket;

        // Główna kontenerka dla elementów na stronie
        basketBox = new VBox(10); // 10px odstępu między elementami
        basketBox.setAlignment(Pos.TOP_CENTER); // Wyrównanie do góry, na środku
        basketBox.getStyleClass().add("modify-basket-box");

        // Tytuł strony
        Label title = new Label("Choose tickets to modify:");
        title.getStyleClass().add("modify-basket-title");

        // Dodanie biletów do widoku
        List<PricedItem> tickets = basket.getItems().stream()
                .filter(item -> item.getId() != -1)
                .map(item -> (PricedItem) item)
                .toList();

        if (tickets.isEmpty()) {
            // Jeśli brak biletów
            Label noTicketsLabel = new Label("No tickets in your basket.");
            noTicketsLabel.getStyleClass().add("basket-empty-message");
            basketBox.getChildren().addAll(title, noTicketsLabel);
        } else {
            // Dodanie biletów do strony
            basketBox.getChildren().add(title);
            for (PricedItem ticket : tickets) {
                Label ticketLabel = new Label("Ticket: " + ticket.getName());
                ticketLabel.getStyleClass().add("modify-ticket-label");
                ticketLabel.setWrapText(true); // Zawijanie tekstu w przypadku długiego opisu
                ticketLabel.setMaxWidth(600); // Maksymalna szerokość etykiety

                // Placeholder action for button click
                ticketLabel.setOnMouseClicked(e -> {
                    controller.modifyTicketMode = true;
                    controller.modifyingTicket = ticket;
                    // addOption("Category", "categoryBtn", this::handleOptionClick);
                    // ListView<Tag> categoryListView = repertoirePage.getCategoryList();
                    // categoryListView.setId("categoryList");
                    // optionsBar.getChildren().add(categoryListView);
                    // addOption("Type", "typeBtn", this::handleOptionClick);
                    // addOption("Other", "otherBtn", this::handleOptionClick);
                    controller.container.getChildren().clear();
                    controller.container.getChildren().add(controller.repertoirePage.getBackPage());
                    controller.optionsBar.getChildren().clear();
                    controller.addOption("Cancel", "cancelBtn", controller::handleOptionClick);
                });

                basketBox.getChildren().add(ticketLabel);
            }
        }
    }

    @Override
    public VBox getPage() {
        VBox finalLayout = new VBox(basketBox);
        finalLayout.getStyleClass().add("basket-page");
        finalLayout.setAlignment(Pos.CENTER); // Wyrównanie elementów na stronie
        return finalLayout;
    }
}
