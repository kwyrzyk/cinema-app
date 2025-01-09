package com.example;

import java.util.List;

import com.example.database.db_classes.Tag;
import com.example.listing.FilmListing;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RepertoirePage implements Page {
    private final ListView<Tag> categoryList = new ListView<>();
    private boolean isCategoryListVisible = false;
    private Movie sessionListGenerator;
    private VBox sessionListVbox;
    private List<Tag> listOfTags;

    private Controller controller;
    
    public RepertoirePage(Controller controller, FilmListing filmListing) {
        this.controller = controller;
        this.listOfTags = controller.getListOfTags();
        categoryList.setVisible(false);
        categoryList.setManaged(false);
        categoryList.getStyleClass().add("lists");
        categoryList.setOnMouseClicked(this::handleCategoryClick);
        categoryList.setCellFactory(param -> new ListCell<Tag>() {
            @Override
            protected void updateItem(Tag item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });

        this.sessionListGenerator = new Movie(this.controller, filmListing);
        this.sessionListVbox = sessionListGenerator.getSessionListVBox();
        this.sessionListVbox.getStyleClass().add("content");
    }

    @Override
    public VBox getPage() {
        HBox main = new HBox(this.sessionListVbox);

        VBox layout = new VBox(main);
        layout.getStyleClass().add("newpage");
        return layout;
    }

    public VBox getBackPage() {
        this.sessionListVbox = this.sessionListGenerator.getSessionListVBox();
        this.sessionListVbox.getStyleClass().add("content");
        HBox main = new HBox(this.sessionListVbox);

        VBox layout = new VBox(main);
        layout.getStyleClass().add("newpage");
        return layout;
    }



    public void toggleCategoryList() {
        if (isCategoryListVisible) {
            categoryList.setVisible(false);
            categoryList.setManaged(false);
            categoryList.getItems().clear();
        } else {
            categoryList.getItems().clear();
            categoryList.getItems().addAll(listOfTags);
            categoryList.setPrefHeight((listOfTags.size() + 1) * 24); // Dostosuj wysokość
            categoryList.setVisible(true);
            categoryList.setManaged(true);
        }

        isCategoryListVisible = !isCategoryListVisible;
    }

    private void handleCategoryClick(MouseEvent event) {
        Tag selectedTag = categoryList.getSelectionModel().getSelectedItem();
        if (selectedTag != null) {
            System.out.println("Selected tag: " + selectedTag.getName());
        }
    }

    public ListView<Tag> getCategoryList() {
        return categoryList;
    }
}
