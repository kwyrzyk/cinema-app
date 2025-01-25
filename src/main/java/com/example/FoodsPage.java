package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.database.db_classes.Basket;
import com.example.database.db_classes.Food;
import com.example.database.db_classes.Price;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

/**
 * Represents the foods page of the application.
 */
public class FoodsPage implements Page {
    private final VBox pageContent = new VBox();
    private final ScrollPane scrollPane = new ScrollPane(pageContent);
    private final VBox foodBox = new VBox(scrollPane);
    private final VBox foodItemsBox = new VBox();
    private final List<Food> allFoods;
    private List<Food> displayedFoods;
    private final Basket basket;

    /**
     * Constructs a FoodsPage with the specified controller.
     * @param controller the controller of the application
     */
    public FoodsPage(Controller controller) {
        this.basket = controller.basket;
        this.allFoods = controller.getListOfFoods();
        this.displayedFoods = new ArrayList<>(this.allFoods);

        createContent();
    }

    /**
     * Creates the content of the foods page.
     */
    private void createContent() {
        foodBox.getStyleClass().add("page");
        scrollPane.getStyleClass().add("scroll-pane");
        scrollPane.setFitToWidth(true);
        pageContent.getStyleClass().add("wide-box");
        foodItemsBox.getStyleClass().add("products-items-box");

        VBox mainContainer = new VBox();
        mainContainer.getStyleClass().add("page");

        Label title = new Label("Food Menu");
        title.getStyleClass().add("page-title");

        SearchPanel searchPanel = new SearchPanel("Search for food...", this::filterFoods);

        updateFoodsView(displayedFoods);    
        
        pageContent.getChildren().addAll(title, searchPanel, foodItemsBox);
    }

    /**
     * Updates the view of the foods to display.
     * @param foodsToDisplay the list of foods to display
     */
    private void updateFoodsView(List<Food> foodsToDisplay) {
        foodItemsBox.getChildren().clear();

        if (foodsToDisplay.isEmpty()) {
            Label noFoodsLabel = new Label("No foods available.");
            noFoodsLabel.getStyleClass().add("no-items-label");
            foodItemsBox.getChildren().add(noFoodsLabel);
        } else {
            for (Food food : foodsToDisplay) {
                VBox foodBox = new VBox();
                foodBox.getStyleClass().add("product-box");

                Label nameLabel = new Label(food.getName());
                nameLabel.getStyleClass().add("product-name");

                VBox priceBox = new VBox();
                for (Map.Entry<String, Pair<Integer, Price>> entry : food.getPrices().entrySet()) {
                    String size = entry.getKey();
                    Price price = entry.getValue().getValue();
                    Label sizePriceLabel = new Label("Size: " + size + " - Price: " + price);
                    sizePriceLabel.getStyleClass().add("product-price");

                    sizePriceLabel.setOnMouseClicked(event -> {
                        basket.addFood(food, size);
                    });

                    priceBox.getChildren().add(sizePriceLabel);
                }

                foodBox.getChildren().addAll(nameLabel, priceBox);
                foodItemsBox.getChildren().add(foodBox);
            }
        }
    }

    /**
     * Filters the foods based on the query.
     * @param query the query to filter the foods
     */
    private void filterFoods(String query) {
        if (query.isEmpty()) {
            displayedFoods = new ArrayList<>(allFoods);
        } else {
            displayedFoods = allFoods.stream()
                .filter(food -> food.getName().toLowerCase().contains(query))
                .collect(Collectors.toList());
        }
        updateFoodsView(displayedFoods);
    }

    /**
     * Returns the VBox containing the foods page content.
     * @return the VBox containing the foods page content
     */
    public VBox getPage() {
        return foodBox;
    }
}
