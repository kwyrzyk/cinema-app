package com.example;

import java.util.List;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Snacks {

    public ContentContainer getSessionListVBox() {
        // Lista filmów
        List<String> movieSessions = List.of(
            "Pepsi",
            "Cola",
            "Lipton",
            "Orange Juice",
            "Apple Juice"
        );

        ContentContainer mainContainer = new ContentContainer();
        
        HBox searchPanel = createSearchPanel();

        VBox sessionList = new VBox(10);
        sessionList.getStyleClass().add("moviecontent");

        for (String session : movieSessions) {
            Button sessionButton = new Button(session);
            sessionButton.getStyleClass().add("moviebutton");
            sessionButton.setOnAction(e -> handleSessionClick(session));
            sessionList.getChildren().add(sessionButton);
        }

        mainContainer.getContainer().getChildren().addAll(searchPanel, sessionList);

        return mainContainer;
    }

    private HBox createSearchPanel() {
        TextField searchField = new TextField();
        searchField.setPromptText("Enter snack name...");
        searchField.getStyleClass().add("searchfield");

        Button searchButton = new Button("Search");
        searchButton.getStyleClass().add("searchbutton");

        searchButton.setOnAction(e -> {
            String query = searchField.getText().toLowerCase();
            System.out.println("Searching for: " + query);
        });

        HBox searchPanel = new HBox(10, searchField, searchButton);
        searchPanel.getStyleClass().add("searchpanel");

        return searchPanel;
    }

    private void handleSessionClick(String session) {
        System.out.println("You selected: " + session);
    }
}
