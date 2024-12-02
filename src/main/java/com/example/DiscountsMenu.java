package com.example;

import java.util.List;

import com.example.database.db_classes.Basket;
import com.example.database.db_classes.Discount;
import com.example.database.db_classes.PricedItem;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class DiscountsMenu {

    private final List<Discount> listOfDiscounts;
    private final Basket basket;

    public DiscountsMenu(Basket basket, List<Discount> listOfDiscounts) {
        this.listOfDiscounts = listOfDiscounts;
        this.basket = basket;
    }

    public VBox getDiscountListVBox() {
        // Create a ListView for discounts
        ListView<Discount> discountListView = new ListView<>();
        discountListView.getStyleClass().add("lists");

        // Add discounts to the ListView
        discountListView.getItems().addAll(listOfDiscounts);

        // Set custom cells in the ListView
        discountListView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Discount> call(ListView<Discount> listView) {
                return new DiscountListCell();
            }
        });

        VBox mainContainer = new VBox();
        HBox searchPanel = createSearchPanel(discountListView);

        mainContainer.getChildren().addAll(searchPanel, discountListView);

        return mainContainer;
    }

    private HBox createSearchPanel(ListView<Discount> discountListView) {
        // Text field for search
        javafx.scene.control.TextField searchField = new javafx.scene.control.TextField();
        searchField.setPromptText("Enter discount name...");
        searchField.getStyleClass().add("searchfield");

        // Search button
        javafx.scene.control.Button searchButton = new javafx.scene.control.Button("Search");
        searchButton.getStyleClass().add("searchbutton");

        searchButton.setOnAction(e -> {
            String query = searchField.getText().toLowerCase();
            List<Discount> filteredDiscounts = listOfDiscounts.stream()
                    .filter(discount -> discount.toString().toLowerCase().contains(query))
                    .toList();

            discountListView.getItems().setAll(filteredDiscounts);
        });

        HBox searchPanel = new HBox(10, searchField, searchButton);
        searchPanel.getStyleClass().add("searchpanel");

        return searchPanel;
    }

    private class DiscountListCell extends ListCell<Discount> {
        @Override
        protected void updateItem(Discount discount, boolean empty) {
            super.updateItem(discount, empty);

            if (empty || discount == null) {
                setText(null);
                setGraphic(null);
            } else {
                VBox content = new VBox();
                Label discountNameLabel = new Label(discount.toString());
                // Adding discount to the basket on click
                discountNameLabel.setOnMouseClicked(event -> {
                    basket.addItem(new PricedItem(discount.toString(), discount.getPrice()));
                    System.out.println("Discount " + discount.toString());
                });

                content.getChildren().addAll(discountNameLabel);
                content.getStyleClass().add("bartek");
                setGraphic(content);
            }
        }
    }
}
