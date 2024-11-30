package com.example;

import java.util.List;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Drinks {
    private final VBox drinksContainer; // Główny kontener dla przekąsek
    private final ListView<String> drinksList; // Lista przekąsek jako ListView

    public Drinks() {
        drinksContainer = new VBox(10); // Tworzenie głównego VBox
        drinksContainer.getStyleClass().add("newpage");

        drinksList = new ListView<>();
        drinksList.getStyleClass().add("lists");

        HBox searchPanel = createSearchPanel();

        populateSnacksList();

        drinksList.setOnMouseClicked(this::handleSnackClick);

        updateListViewHeight();

        drinksContainer.getChildren().addAll(searchPanel, drinksList);
    }

    public VBox getDrinksContainer() {
        return drinksContainer;
    }

    private HBox createSearchPanel() {
        // Pole tekstowe do wyszukiwania
        TextField searchField = new TextField();
        searchField.setPromptText("Enter snack name...");
        searchField.getStyleClass().add("searchfield");

        // Przycisk wyszukiwania
        Button searchButton = new Button("Search");
        searchButton.getStyleClass().add("searchbutton");

        searchButton.setOnAction(e -> {
            String query = searchField.getText().toLowerCase();
            System.out.println("Searching for: " + query);
            // Możesz tutaj dodać logikę filtrowania wyników
        });

        // Panel wyszukiwania
        HBox searchPanel = new HBox(10, searchField, searchButton);
        searchPanel.getStyleClass().add("searchpanel");

        return searchPanel;
    }

    private void populateSnacksList() {
        // Lista przekąsek
        List<String> drinks = List.of(
            "Pepsi",
            "Cola",
            "Lipton",
            "Orange Juice",
            "Apple Juice",
            "Water"
        );

        // Dodaj przekąski do ListView
        drinksList.getItems().addAll(drinks);
    }

    private void updateListViewHeight() {
        double rowHeight = 24.0;
    

        int itemCount = drinksList.getItems().size();
    
        // Ustaw preferowaną wysokość
        drinksList.setPrefHeight(itemCount * rowHeight);
    }
    

    private void handleSnackClick(MouseEvent event) {
        String selectedDrink = drinksList.getSelectionModel().getSelectedItem();
        if (selectedDrink != null) {
            System.out.println("You selected: " + selectedDrink);
            // Możesz tutaj dodać logikę, np. pokazanie szczegółów przekąski
        }
    }
}
