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

    /**
     * Constructs a SearchPanel.
     *
     * @param promptText The placeholder text for the search field.
     * @param onSearch   The callback to execute when a search is performed.
     */
    public SearchPanel(String promptText, Consumer<String> onSearch) {
        this.getStyleClass().add("search-box");

        // Initialize search field
        searchField = new TextField();
        searchField.setPromptText(promptText);
        searchField.getStyleClass().add("input-field");

        // Allow pressing Enter to trigger search
        searchField.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                performSearch(onSearch);
            }
        });

        // Initialize search button
        searchButton = new Button("Search");
        searchButton.getStyleClass().add("btn");

        // Set button action
        searchButton.setOnAction(event -> performSearch(onSearch));

        // Add components to HBox
        this.getChildren().addAll(searchField, searchButton);
    }

    /**
     * Performs the search by invoking the callback with the current query.
     *
     * @param onSearch The callback to execute.
     */
    private void performSearch(Consumer<String> onSearch) {
        String query = searchField.getText().trim().toLowerCase();
        onSearch.accept(query);
    }

    /**
     * Clears the search field.
     */
    public void clear() {
        searchField.clear();
    }
}
