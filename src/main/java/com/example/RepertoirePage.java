package com.example;

import java.util.List;

import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RepertoirePage implements Page {
    private final ListView<String> categoryList = new ListView<>(); // Lista kategorii jako String
    private boolean isCategoryListVisible = false;

    @Override
    public VBox getPage() {
        // Przyciski w Sidebar
        javafx.scene.control.Button category = new javafx.scene.control.Button("Category");
        javafx.scene.control.Button type = new javafx.scene.control.Button("Type");
        javafx.scene.control.Button other = new javafx.scene.control.Button("Other");

        // Inicjalnie ukrywamy listę kategorii
        categoryList.setVisible(false);
        categoryList.setManaged(false);
        categoryList.getStyleClass().add("lists");

        category.setOnAction(e -> toggleCategoryList());

        categoryList.setOnMouseClicked(this::handleCategoryClick);

        VBox sideBar = new VBox(category, categoryList, type, other);
        sideBar.getStyleClass().add("sidebar");

        Movie sessionListGenerator = new Movie();
        VBox container = sessionListGenerator.getSessionListVBox().getContainer();
        container.getStyleClass().add("content");

        HBox main = new HBox(0, sideBar, container);
        main.getStyleClass().add("content");

        VBox layout = new VBox(0, main);
        layout.getStyleClass().add("newpage");
        return layout;
    }

    private void toggleCategoryList() {
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

            double rowHeight = 24;
            int itemCount = categories.size();
            categoryList.setPrefHeight(itemCount * rowHeight);

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
}
