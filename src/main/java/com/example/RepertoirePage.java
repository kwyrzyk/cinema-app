package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.database.db_classes.Film;
import com.example.database.db_classes.Tag;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

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

        SearchPanel searchPanel = new SearchPanel("Search for film...", this::filterFilms);

        updateFilmsView(displayedFilms);

        pageContent.getChildren().addAll(title, searchPanel, filmItemsBox);
    }

    private void updateFilmsView(List<Film> filmsToDisplay) {
        filmItemsBox.getChildren().clear();

        if (filmsToDisplay.isEmpty()) {
            Label noFilmsLabel = new Label("No films available.");
            noFilmsLabel.getStyleClass().add("no-items-label");
            filmItemsBox.getChildren().add(noFilmsLabel);
        } else {
            for (Film film : filmsToDisplay) {
                VBox filmBox = new VBox();
                filmBox.getStyleClass().add("product-box");

                Label filmLabel = new Label(film.getTitle());
                filmLabel.getStyleClass().add("product-price");

                filmLabel.setOnMouseClicked(event -> {
                    FilmPage filmPage = new FilmPage(controller, film);
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

    private void filterFilmsByCategory(Tag category) {
        displayedFilms = controller.getFilmListing().getFilmsByTag(category);
        updateFilmsView(displayedFilms);
    }

    private void filterFilmsByPegi(int pegi) {
        displayedFilms = controller.getFilmListing().getFilmsByPegi(pegi);  
        updateFilmsView(displayedFilms);
    }

    public VBox getCategories() {
        VBox categoriesBox = new VBox();
        categoriesBox.getStyleClass().add("films-filter-box");

        List<Tag> categories = controller.getListOfTags();
        for (Tag category : categories) {
            Label categoryLabel = new Label(category.getName());
            categoryLabel.getStyleClass().add("product-price");

            categoryLabel.setOnMouseClicked(event -> {
                filterFilmsByCategory(category);
            });

            categoriesBox.getChildren().add(categoryLabel);
        }

        return categoriesBox;
    }
    public VBox getPegis() {
        VBox categoriesBox = new VBox();
        categoriesBox.getStyleClass().add("films-filter-box");

        List<Integer> pegiValues = controller.getListOfPegiValues();
        for (int value : pegiValues) {
            Label categoryLabel = new Label(String.valueOf(value));
            categoryLabel.getStyleClass().add("product-price");

            categoryLabel.setOnMouseClicked(event -> {
                filterFilmsByPegi(value);
            });

            categoriesBox.getChildren().add(categoryLabel);
        }

        return categoriesBox;
    }

    public VBox getPage() {
        return repertoireBox;
    }
}
