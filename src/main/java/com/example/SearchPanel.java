package com.example;

import java.util.function.Consumer;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

public class SearchPanel extends HBox {
    private final TextField searchField;
    private final Button searchButton;

    public SearchPanel(String promptText, Consumer<String> onSearch) {
        this.getStyleClass().add("search-box");

        searchField = new TextField();
        searchField.setPromptText(promptText);
        searchField.getStyleClass().add("input-field");

        searchField.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                performSearch(onSearch);
            }
        });

        searchButton = new Button("Search");
        searchButton.getStyleClass().add("btn");

        searchButton.setOnAction(event -> performSearch(onSearch));

        this.getChildren().addAll(searchField, searchButton);
    }

    private void performSearch(Consumer<String> onSearch) {
        String query = searchField.getText().trim().toLowerCase();
        onSearch.accept(query);
    }

    public void clear() {
        searchField.clear();
    }
}
