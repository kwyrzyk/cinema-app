package com.example;

import java.util.List;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Snacks {
    private final VBox snacksContainer; // Główny kontener dla przekąsek
    private final ListView<String> snacksList; // Lista przekąsek jako ListView

    public Snacks() {
        snacksContainer = new VBox(10); // Tworzenie głównego VBox
        snacksContainer.getStyleClass().add("newpage");

        snacksList = new ListView<>();
        snacksList.getStyleClass().add("lists");

        HBox searchPanel = createSearchPanel();

        populateSnacksList();

        snacksList.setOnMouseClicked(this::handleSnackClick);

        updateListViewHeight();

        snacksContainer.getChildren().addAll(searchPanel, snacksList);
    }

    public VBox getSnacksContainer() {
        return snacksContainer;
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
        List<String> snacks = List.of(
            "Chips",
            "Chocolate",
            "Popcorn",
            "Nachos",
            "Apple",
            "Fjutek Bartka"
        );

        // Dodaj przekąski do ListView
        snacksList.getItems().addAll(snacks);
    }

    private void updateListViewHeight() {
        double rowHeight = 24.0;
    

        int itemCount = snacksList.getItems().size();
    
        // Ustaw preferowaną wysokość
        snacksList.setPrefHeight(itemCount * rowHeight);
    }
    

    private void handleSnackClick(MouseEvent event) {
        String selectedSnack = snacksList.getSelectionModel().getSelectedItem();
        if (selectedSnack != null) {
            System.out.println("You selected: " + selectedSnack);
            // Możesz tutaj dodać logikę, np. pokazanie szczegółów przekąski
        }
    }
}
