package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.database.db_classes.Basket;
import com.example.database.db_classes.Drink;
import com.example.database.db_classes.Price;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

public class DrinksPage implements Page {
    private final VBox pageContent = new VBox();
    private final ScrollPane scrollPane = new ScrollPane(pageContent);
    private final VBox drinkBox = new VBox(scrollPane);
    private final VBox drinkItemsBox = new VBox();
    private final List<Drink> allDrinks;
    private List<Drink> displayedDrinks;
    private final Basket basket;

    public DrinksPage(Controller controller) {
        this.basket = controller.basket;
        this.allDrinks = controller.getListOfDrinks();
        this.displayedDrinks = new ArrayList<>(this.allDrinks);

        createContent();
    }

    private void createContent() {
        drinkBox.getStyleClass().add("page");
        scrollPane.getStyleClass().add("scroll-pane");
        scrollPane.setFitToWidth(true);
        pageContent.getStyleClass().add("wide-box");
        drinkItemsBox.getStyleClass().add("products-items-box");

        VBox mainContainer = new VBox();
        mainContainer.getStyleClass().add("page");

        Label title = new Label("Drinks Menu");
        title.getStyleClass().add("page-title");

        SearchPanel searchPanel = new SearchPanel("Search for drink...", this::filterDrinks);

        updateDrinksView(displayedDrinks);

        pageContent.getChildren().addAll(title, searchPanel, drinkItemsBox);
    }

    private void updateDrinksView(List<Drink> drinksToDisplay) {
        drinkItemsBox.getChildren().clear();

        if (drinksToDisplay.isEmpty()) {
            Label noDrinksLabel = new Label("No drinks available.");
            noDrinksLabel.getStyleClass().add("no-items-label");
            drinkItemsBox.getChildren().add(noDrinksLabel);
        } else {
            for (Drink drink : drinksToDisplay) {
                VBox drinkBox = new VBox();
                drinkBox.getStyleClass().add("product-box");

                Label nameLabel = new Label(drink.getName());
                nameLabel.getStyleClass().add("product-name");

                VBox priceBox = new VBox();
                for (Map.Entry<String, Pair<Integer, Price>> entry : drink.getPrices().entrySet()) {
                    String size = entry.getKey();
                    Price price = entry.getValue().getValue();
                    Label sizePriceLabel = new Label("Size: " + size + " - Price: " + price);
                    sizePriceLabel.getStyleClass().add("product-price");

                    sizePriceLabel.setOnMouseClicked(event -> {
                        basket.addDrink(drink, size);
                    });

                    priceBox.getChildren().add(sizePriceLabel);
                }

                drinkBox.getChildren().addAll(nameLabel, priceBox);
                drinkItemsBox.getChildren().add(drinkBox);
            }
        }
    }

    private void filterDrinks(String query) {
        if (query.isEmpty()) {
            displayedDrinks = new ArrayList<>(allDrinks);
        } else {
            displayedDrinks = allDrinks.stream()
                .filter(drink -> drink.getName().toLowerCase().contains(query))
                .collect(Collectors.toList());
        }
        updateDrinksView(displayedDrinks);
    }

    public VBox getPage() {
        return drinkBox;
    }
}
