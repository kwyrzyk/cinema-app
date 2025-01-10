package com.example;

import java.util.List;

import com.example.database.db_classes.Film;
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
    FilmListing filmListing;
    List<Film> allFilms;

    private Controller controller;
    
    public RepertoirePage(Controller controller, FilmListing filmListing) {
        this.controller = controller;
        this.listOfTags = controller.getListOfTags();
        this.filmListing = filmListing;
        this.allFilms = filmListing.getFilms();
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

        makeRepertoireContent(this.allFilms);
    }

    public void makeRepertoireContent(List<Film> Films){
        this.sessionListGenerator = new Movie(this.controller, Films);
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
        makeRepertoireContent(this.filmListing.getFilms());
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
            List<Film> filmsWithTags = this.filmListing.getFilmsByTag(selectedTag);
            makeRepertoireContent(filmsWithTags);
            this.controller.container.getChildren().clear();
            this.controller.container.getChildren().add(this.getPage());
        }
    }

    public ListView<Tag> getCategoryList() {
        return categoryList;
    }
}
