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
    private boolean isPegiListVisible = false;
    private Movie sessionListGenerator;
    private VBox sessionListVbox;
    private List<Tag> listOfTags;
    private final List<Film> allFilms;
    private final ListView<Integer> listPegi = new ListView<>();
    private final List<Integer> listOfPegi;
    FilmListing filmListing;
    

    private Controller controller;
    
    public RepertoirePage(Controller controller, FilmListing filmListing) {
        this.controller = controller;
        this.listOfTags = controller.getListOfTags();
        this.filmListing = filmListing;
        this.allFilms = filmListing.getFilms();
        this.listOfPegi = this.controller.getListOfPegi();
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

        listPegi.setVisible(false);
        listPegi.setManaged(false);
        listPegi.getStyleClass().add("lists");
        listPegi.setOnMouseClicked(this::handlePegiClick);
        listPegi.setCellFactory(param -> new ListCell<Integer>() {
            @Override
            protected void updateItem(Integer pegi, boolean empty) {
                super.updateItem(pegi, empty);
                if (empty || pegi == null) {
                    setText(null);
                } else {
                    setText(pegi.toString());
                }
            }
        });

        makeRepertoireContent(this.allFilms);
    }

    public void makeRepertoireContent(List<Film> Films){
        this.sessionListGenerator = new Movie(this.controller, Films);
        this.sessionListVbox = sessionListGenerator.getSessionListVBox();
        this.sessionListVbox.getStyleClass().add("page");
    }
    @Override
    public VBox getPage() {
        System.out.println("Repertoire page");
        HBox main = new HBox(this.sessionListVbox);
        main.getStyleClass().add("page");
        VBox layout = new VBox(main);
        layout.getStyleClass().add("page");
        return layout;
    }

    public VBox getBackPage() {
        System.out.println("Repertoire page");
        makeRepertoireContent(this.filmListing.getFilms());
        HBox main = new HBox(this.sessionListVbox);

        VBox layout = new VBox(main);
        layout.getStyleClass().add("page");
        return layout;
    }

    public void togglePegiList() {
        if (isPegiListVisible) {
            listPegi.setVisible(false);
            listPegi.setManaged(false);
            listPegi.getItems().clear();
        } else {
            listPegi.getItems().clear();
            listPegi.getItems().addAll(listOfPegi);
            listPegi.setPrefHeight((listOfPegi.size()) * 24); // Dostosuj wysokość
            listPegi.setVisible(true);
            listPegi.setManaged(true);
        }

        isPegiListVisible = !isPegiListVisible;
    }

    public void toggleCategoryList() {
        if (isCategoryListVisible) {
            categoryList.setVisible(false);
            categoryList.setManaged(false);
            categoryList.getItems().clear();
        } else {
            categoryList.getItems().clear();
            categoryList.getItems().addAll(listOfTags);
            categoryList.setPrefHeight((listOfTags.size()) * 24); // Dostosuj wysokość
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

    private void handlePegiClick(MouseEvent event) {
        Integer selectedPegi = listPegi.getSelectionModel().getSelectedItem();
        if (selectedPegi != null) {
            System.out.println("Selected Pegi: " + selectedPegi);
            List<Film> filmsWithPegi = this.filmListing.getFilmsByPegi(selectedPegi);
            makeRepertoireContent(filmsWithPegi);
            this.controller.container.getChildren().clear();
            this.controller.container.getChildren().add(this.getPage());
        }
    }

    public ListView<Tag> getCategoryList() {
        return this.categoryList;
    }

    public ListView<Integer> getPegiList() {
        return this.listPegi;
    }
}
