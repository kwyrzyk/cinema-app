package com.example;

import com.example.database.AccountRepository;
import com.example.database.db_classes.Film;

import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RepertoirePage implements Page {

    private final VBox pageContent = new VBox();
    private final ScrollPane scrollPane = new ScrollPane(pageContent);
    private final VBox repertoireBox = new VBox(scrollPane);
    private final VBox filmItemsBox = new VBox();
    private final List<Film> allFilms;
    private List<Film> displayedFilms;
    private final Controller controller;

    public RepertoirePage(Controller controller) {
        this.controller = controller;
        this.allFilms = controller.getListOfFilms();
        this.displayedFilms = new ArrayList<>(this.allFilms);

        createContent();
    }

    private void createContent() {
        repertoireBox.getStyleClass().add("page");
        scrollPane.getStyleClass().add("scroll-pane");
        scrollPane.setFitToWidth(true);
        pageContent.getStyleClass().add("wide-box");
        filmItemsBox.getStyleClass().add("products-items-box");

        Label title = new Label("Repertorie");  
        title.getStyleClass().add("page-title");

        HBox searchBox = new HBox();
        searchBox.getStyleClass().add("search-box");

        TextField searchField = new TextField();
        searchField.setPromptText("Search for films...");
        searchField.getStyleClass().add("input-field");

        Button searchButton = new Button("Search");
        searchButton.getStyleClass().add("btn");

        searchButton.setOnAction(event -> {
            String query = searchField.getText().trim().toLowerCase();
            filterFilms(query);
        });

        searchBox.getChildren().addAll(searchField, searchButton);

        updateFilmsView(displayedFilms);

        pageContent.getChildren().addAll(title, searchBox, filmItemsBox);
    }

    private void updateFilmsView(List<Film> filmsToDisplay) {
        filmItemsBox.getChildren().clear();

        if (filmsToDisplay.isEmpty()) {
            Label noFilmsLabel = new Label("No rewards available.");
            noFilmsLabel.getStyleClass().add("no-items-label");
            filmItemsBox.getChildren().add(noFilmsLabel);
        } else {
            for (Film film : filmsToDisplay) {
                VBox filmBox = new VBox();
                filmBox.getStyleClass().add("product-box");

                Label filmLabel = new Label(film.getTitle());
                filmLabel.getStyleClass().add("product-price");

                filmLabel.setOnMouseClicked(event -> {
                    FilmPage filmPage = new FilmPage(this, film);
                    controller.container.getChildren().clear();
                    controller.container.getChildren().add(filmPage.getPage());
                });

                filmBox.getChildren().add(filmLabel);
                filmItemsBox.getChildren().add(filmBox);
            }
        }
    }

    private void filterFilms(String query) {
        if (query.isEmpty()) {
            displayedFilms = new ArrayList<>(allFilms);
        } else {
            displayedFilms = allFilms.stream()
                .filter(film -> film.toString().toLowerCase().contains(query))
                .collect(Collectors.toList());
        }
        updateFilmsView(displayedFilms);
    }

    public VBox getPage() {
        return repertoireBox;
    }
}
