package com.example;

import java.util.List;
import java.util.List;

import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RepertoirePage implements Page {
    private final ListView<String> categoryList = new ListView<>();
    private boolean isCategoryListVisible = false;

    public RepertoirePage() {
        // Inicjalizacja ListView kategorii
        categoryList.setVisible(false);
        categoryList.setManaged(false);
        categoryList.getStyleClass().add("lists");

        categoryList.setOnMouseClicked(this::handleCategoryClick);
    }

    @Override
    public VBox getPage() {
        Movie sessionListGenerator = new Movie();
        VBox sessionListVBox = sessionListGenerator.getSessionListVBox();
        sessionListVBox.getStyleClass().add("content");

        HBox main = new HBox(sessionListVBox);
        main.getStyleClass().add("content");

        VBox layout = new VBox(main);
        layout.getStyleClass().add("newpage");
        return layout;
    }

    public void toggleCategoryList() {
        List<String> categories = List.of(
            "Action",
            "Drama",
            "Comedy",
            "Horror",
            "Sci-Fi"
        );

        if (isCategoryListVisible) {
            categoryList.setVisible(false);
            categoryList.setManaged(false);
            categoryList.getItems().clear();
        } else {
            categoryList.getItems().addAll(categories);
            categoryList.setPrefHeight(categories.size() * 24);
            categoryList.setVisible(true);
            categoryList.setManaged(true);
        }

        isCategoryListVisible = !isCategoryListVisible;
    }

    private void handleCategoryClick(MouseEvent event) {
        String selectedCategory = categoryList.getSelectionModel().getSelectedItem();
        if (selectedCategory != null) {
            System.out.println("Selected category: " + selectedCategory);
        }
    }

    public ListView<String> getCategoryList() {
        return categoryList;
    }
}
