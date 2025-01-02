package com.example;

import java.util.List;
import java.time.LocalTime;
import java.util.stream.Collectors;

import com.example.database.db_classes.Basket;
import com.example.database.db_classes.Discount;

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
        System.out.println("Number of discounts: " + listOfDiscounts.size());
        // Filter active discounts based on current time
        List<Discount> activeDiscounts = listOfDiscounts.stream()
                .filter(discount -> discount.isDiscountActive(LocalTime.now()))
                .collect(Collectors.toList());

        // Add active discounts to the ListView
        discountListView.getItems().addAll(activeDiscounts);

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
            List<Discount> filteredDiscounts = discountListView.getItems().stream()
                    .filter(discount -> discount.toString().toLowerCase().contains(query))
                    .collect(Collectors.toList());

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
                    basket.addDiscount(discount);
                    System.out.println("Discount " + discount.toString());
                });

                content.getChildren().addAll(discountNameLabel);
                content.getStyleClass().add("bartek");
                setGraphic(content);
            }
        }
    }
}
