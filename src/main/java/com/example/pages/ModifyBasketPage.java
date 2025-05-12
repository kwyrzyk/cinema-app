package com.example.pages;

import com.example.Controller;
import com.example.database.db_classes.Basket;
import com.example.database.db_classes.PricedItem;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.util.List;

/**
 * Class representing the Modify Basket Page.
 */
public class ModifyBasketPage implements Page {
    private final VBox pageContent = new VBox();
    private final ScrollPane scrollPane = new ScrollPane(pageContent);
    private final VBox modifyBox = new VBox(scrollPane);
    private final VBox ticketItemsBox = new VBox();
    private final Controller controller;
    private final Basket basket;

    /**
     * Constructor for ModifyBasketPage.
     * 
     * @param controller the controller instance
     */
    public ModifyBasketPage(Controller controller) {
        this.controller = controller;
        this.basket = controller.basket;
        createContent();
    }

    /**
     * Creates the content for the Modify Basket Page.
     */
    private void createContent() {
        modifyBox.getStyleClass().add("page");
        scrollPane.getStyleClass().add("scroll-pane");
        scrollPane.setFitToWidth(true);
        pageContent.getStyleClass().add("wide-box");

        Label title = new Label("Modify ticket");
        title.getStyleClass().add("page-title");

        Label infoLabel = new Label("Click on a ticket to modify it");
        infoLabel.getStyleClass().add("info-label");

        List<PricedItem> tickets = basket.getItems().stream()
                .filter(item -> item.isTicket())
                .map(item -> (PricedItem) item)
                .toList();

        for (PricedItem ticket : tickets) {
            Label ticketLabel = new Label("Ticket: " + ticket.getName());
            ticketLabel.getStyleClass().add("product-price");
            ticketItemsBox.getChildren().add(ticketLabel);

            ticketLabel.setOnMouseClicked(e -> {
                controller.modifyTicketMode = true;
                controller.modifyingTicket = ticket;
                controller.container.getChildren().clear();
                controller.container.getChildren().add(controller.repertoirePage.getPage());
                controller.optionsBar.getChildren().clear();
                controller.addOption("Cancel", "cancelBtn", controller::handleOptionClick);
            });
        }
        pageContent.getChildren().addAll(title, infoLabel, ticketItemsBox);
    }

    /**
     * Returns the page content.
     * 
     * @return the VBox containing the page content
     */
    @Override
    public VBox getPage() {
        return modifyBox;
    }
}
