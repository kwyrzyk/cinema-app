package com.example;
import java.util.List;
import java.util.Map;
import com.example.database.DrinkRepository;
import com.example.database.db_classes.Basket;
import com.example.database.db_classes.Drink;
import com.example.database.db_classes.Price;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.scene.control.Label;

public class DrinksMenu {

    private final List<Drink> listOfDrinks;
    public Basket basket;

    public DrinksMenu(DrinkRepository drinkRepository, Basket basket, List<Drink> listOfDrinks) {
        this.basket = basket;
        this.listOfDrinks = listOfDrinks;
    }

    public VBox getDrinkListVBox() {
        ListView<Drink> drinkListView = new ListView<>();
        drinkListView.getStyleClass().add("lists");

        drinkListView.getItems().addAll(listOfDrinks);

        drinkListView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Drink> call(ListView<Drink> listView) {
                return new DrinkListCell();
            }
        });

        VBox mainContainer = new VBox();
        HBox searchPanel = createSearchPanel(drinkListView);

        mainContainer.getChildren().addAll(searchPanel, drinkListView);

        return mainContainer;
    }

    private HBox createSearchPanel(ListView<Drink> drinkListView) {
        javafx.scene.control.TextField searchField = new javafx.scene.control.TextField();
        searchField.setPromptText("Enter drink name...");
        searchField.getStyleClass().add("searchfield");

        javafx.scene.control.Button searchButton = new javafx.scene.control.Button("Search");
        searchButton.getStyleClass().add("searchbutton");

        searchButton.setOnAction(e -> {
            String query = searchField.getText().toLowerCase();
            List<Drink> filteredDrinks = DrinkRepository.getAllDrinks().stream()
                    .filter(drink -> drink.getName().toLowerCase().contains(query))
                    .toList();

            drinkListView.getItems().setAll(filteredDrinks);
        });

        HBox searchPanel = new HBox(10, searchField, searchButton);
        searchPanel.getStyleClass().add("searchpanel");

        return searchPanel;
    }

    private class DrinkListCell extends ListCell<Drink> {
        @Override
        protected void updateItem(Drink drink, boolean empty) {
            super.updateItem(drink, empty);
            if (empty || drink == null) {
                setText(null);
                setGraphic(null);
            } else {
                VBox content = new VBox();
                content.setId("drinkItem");
                content.getChildren().add(new Label("Name:" + drink.getName()));
                for(Map.Entry<String, Price> entry :drink.getPrices().entrySet()){
                    String size = entry.getKey();
                    Price price = entry.getValue();
                    Label sizePriceLabel = new Label("Size: " + size + " Price: " + price);
                    sizePriceLabel.setOnMousePressed(event -> {
                        basket.addDrink(drink, size);
                        System.out.println("Name: " + drink.getName() + " Size: " + size + ", Price: " + price);
                    });
                    content.getChildren().add(sizePriceLabel);
                }
                content.getStyleClass().add("bartek");
                setGraphic(content);
            }
        }
    }
}
