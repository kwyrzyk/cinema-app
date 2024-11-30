package com.example;

import java.util.List;

import com.example.database.db_classes.Film;
import com.example.listing.FilmListing;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class Movie {

    private final FilmListing filmListing;

    public Movie() {
        // Inicjalizacja obiektu FilmListing, który pobiera filmy z bazy danych
        this.filmListing = new FilmListing();
    }

    public ContentContainer getSessionListVBox() {
        // Pobranie listy filmów z FilmListing
        ListView<Film> filmListView = new ListView<>();
        filmListView.getStyleClass().add("lists");

        // Ustawienie listy filmów w ListView
        filmListView.getItems().addAll(filmListing.getFilms());

        // Dostosowanie komórek ListView
        filmListView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Film> call(ListView<Film> listView) {
                return new FilmListCell();
            }
        });

        // Główny kontener
        ContentContainer mainContainer = new ContentContainer();

        // Panel wyszukiwania
        HBox searchPanel = createSearchPanel(filmListView);

        // Dodanie elementów do głównego kontenera
        mainContainer.getContainer().getChildren().addAll(searchPanel, filmListView);

        return mainContainer;
    }

    private HBox createSearchPanel(ListView<Film> filmListView) {
        // Pole tekstowe do wyszukiwania
        javafx.scene.control.TextField searchField = new javafx.scene.control.TextField();
        searchField.setPromptText("Enter movie name...");
        searchField.getStyleClass().add("searchfield");

        // Przycisk wyszukiwania
        javafx.scene.control.Button searchButton = new javafx.scene.control.Button("Search");
        searchButton.getStyleClass().add("searchbutton");

        searchButton.setOnAction(e -> {
            String query = searchField.getText().toLowerCase();
            List<Film> filteredFilms = filmListing.getFilms().stream()
                    .filter(film -> film.getTitle().toLowerCase().contains(query))
                    .toList();

            filmListView.getItems().setAll(filteredFilms);
        });

        HBox searchPanel = new HBox(10, searchField, searchButton);
        searchPanel.getStyleClass().add("searchpanel");

        return searchPanel;
    }

    private static class FilmListCell extends ListCell<Film> {
        @Override
        protected void updateItem(Film film, boolean empty) {
            super.updateItem(film, empty);
            if (empty || film == null) {
                setText(null);
                setGraphic(null);
            } else {
                VBox content = new VBox(
                        new javafx.scene.control.Label("Title: " + film.getTitle())
                );
                content.getStyleClass().add("bartek");
                content.getStyleClass().add("film-cell-content");
                setGraphic(content);
            }
        }
    }
}
