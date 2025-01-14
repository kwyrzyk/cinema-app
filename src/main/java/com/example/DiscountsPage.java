package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.database.db_classes.Basket;
import com.example.database.db_classes.Discount;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class DiscountsPage implements Page {
    private final VBox pageContent = new VBox();
    private final ScrollPane scrollPane = new ScrollPane(pageContent);
    private final VBox discountBox = new VBox(scrollPane);
    private final VBox discountItemsBox = new VBox();
    private final List<Discount> allDiscounts;
    private List<Discount> displayedDiscounts;
    private final Basket basket;

    public DiscountsPage(Controller controller) {
        this.basket = controller.basket;
        this.allDiscounts = controller.getListOfDiscounts();
        this.displayedDiscounts = new ArrayList<>(this.allDiscounts);

        createContent();
    }

    private void createContent() {
        discountBox.getStyleClass().add("page");
        scrollPane.getStyleClass().add("scroll-pane");
        scrollPane.setFitToWidth(true);
        pageContent.getStyleClass().add("wide-box");
        discountItemsBox.getStyleClass().add("products-items-box");

        Label title = new Label("Discounts Menu");
        title.getStyleClass().add("page-title");

        SearchPanel searchPanel = new SearchPanel("Search for discount...", this::filterDiscounts);

        updateDiscountsView(displayedDiscounts);

        pageContent.getChildren().addAll(title, searchPanel, discountItemsBox);
    }

    private void updateDiscountsView(List<Discount> discountsToDisplay) {
        discountItemsBox.getChildren().clear();

        if (discountsToDisplay.isEmpty()) {
            Label noDiscountsLabel = new Label("No discounts available.");
            noDiscountsLabel.getStyleClass().add("no-items-label");
            discountItemsBox.getChildren().add(noDiscountsLabel);
        } else {
            for (Discount discount : discountsToDisplay) {
                VBox discountBox = new VBox();
                discountBox.getStyleClass().add("product-box");

                Label discountLabel = new Label(discount.toString());
                discountLabel.getStyleClass().add("product-price");

                // Dodawanie zniżki do koszyka po kliknięciu
                discountLabel.setOnMouseClicked(event -> {
                    basket.addDiscount(discount);
                });

                discountBox.getChildren().add(discountLabel);
                discountItemsBox.getChildren().add(discountBox);
            }
        }
    }

    private void filterDiscounts(String query) {
        if (query.isEmpty()) {
            displayedDiscounts = new ArrayList<>(allDiscounts);
        } else {
            displayedDiscounts = allDiscounts.stream()
                .filter(discount -> discount.toString().toLowerCase().contains(query))
                .collect(Collectors.toList());
        }
        updateDiscountsView(displayedDiscounts);
    }

    public VBox getPage() {
        return discountBox;
    }
}
