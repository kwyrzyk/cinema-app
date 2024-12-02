package com.example;
import java.util.List;
import java.util.Map;
import com.example.database.FoodRepository;
import com.example.database.db_classes.Basket;
import com.example.database.db_classes.Food;
import com.example.database.db_classes.Price;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.scene.control.Label;

public class FoodMenu {

    private final List<Food> listOfFoods;
    private Basket basket;

    public FoodMenu(FoodRepository foodRepository, Basket basket, List<Food> listOfFoods) {
        this.basket = basket;
        this.listOfFoods = listOfFoods;

        if (this.basket == null) {
            System.err.println("Basket is null in FoodMenu constructor!");
        }
    }

    public VBox getFoodListVBox() {
        ListView<Food> foodListView = new ListView<>();
        foodListView.getStyleClass().add("lists");

        foodListView.getItems().addAll(listOfFoods);

        foodListView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Food> call(ListView<Food> listView) {
                return new FoodListCell();
            }
        });


        VBox mainContainer = new VBox();

        HBox searchPanel = createSearchPanel(foodListView);

        mainContainer.getChildren().addAll(searchPanel, foodListView);

        return mainContainer;
    }

    private HBox createSearchPanel(ListView<Food> foodListView) {
        javafx.scene.control.TextField searchField = new javafx.scene.control.TextField();
        searchField.setPromptText("Enter food name...");
        searchField.getStyleClass().add("searchfield");

        javafx.scene.control.Button searchButton = new javafx.scene.control.Button("Search");
        searchButton.getStyleClass().add("searchbutton");

        searchButton.setOnAction(e -> {
            String query = searchField.getText().toLowerCase();
            List<Food> filteredFoods = FoodRepository.getAllFoods().stream()
                    .filter(food -> food.getName().toLowerCase().contains(query))
                    .toList();

            foodListView.getItems().setAll(filteredFoods);
        });

        HBox searchPanel = new HBox(10, searchField, searchButton);
        searchPanel.getStyleClass().add("searchpanel");

        return searchPanel;
    }

    private class FoodListCell extends ListCell<Food> {
    @Override
    protected void updateItem(Food food, boolean empty) {
        super.updateItem(food, empty);
        if (empty || food == null) {
            setText(null);
            setGraphic(null);
        } else {
            VBox content = new VBox();
            content.setId("foodItem");
            content.getChildren().add(new Label("Name: " + food.getName()));
            for (Map.Entry<String, Price> entry : food.getPrices().entrySet()) {
                String size = entry.getKey();
                Price price = entry.getValue();
                Label sizePriceLabel = new Label("Size: " + size + " Price: " + price);
                sizePriceLabel.setOnMousePressed(event -> {
                    basket.addFood(food, size);
                    System.out.println("Name: " + food.getName() + " Size: " + size + ", Price: " + price);
                });
                
                content.getChildren().add(sizePriceLabel);
            }
            content.getStyleClass().add("bartek");
            setGraphic(content);
        }
    }
}

}
