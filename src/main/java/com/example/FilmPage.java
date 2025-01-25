package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.database.db_classes.Actor;
import com.example.database.db_classes.Film;
import com.example.database.db_classes.Showing;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Represents the film page of the application.
 */
public class FilmPage implements Page {

    private final VBox pageContent = new VBox();
    private final ScrollPane scrollPane = new ScrollPane(pageContent);
    private final VBox repertoireBox = new VBox(scrollPane);
    private final VBox filmItemsBox = new VBox();
    private final Controller controller;
    private Film film;

    /**
     * Constructs a FilmPage with the specified controller and film.
     * @param controller the controller of the application
     * @param film the film to be displayed
     */
    public FilmPage(Controller controller, Film film) {
        this.controller = controller;
        this.film = film;

        createContent();
    }

    /**
     * Creates the content of the film page.
     */
    private void createContent() {
        repertoireBox.getStyleClass().add("page");
        scrollPane.getStyleClass().add("scroll-pane");
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        pageContent.getStyleClass().add("wide-box");
        filmItemsBox.getStyleClass().add("products-items-box");

        Label title = new Label("Film info");  
        title.getStyleClass().add("page-title");

        VBox infoBox = new VBox();
        infoBox.getStyleClass().add("wide-box");

        HBox titleRow = getRow("Title", film.getTitle());
        HBox ratingRow = getRow("Rating", String.valueOf(film.getRating()));
        HBox shortDescRow = getRow("Short Description", film.getShortDescription());
        HBox longDescRow = getRow("Description", film.getLongDescription());
        HBox actorsRow = getRow("Actors", getActors());
        actorsRow.getStyleClass().add("last-info-row");
        infoBox.getChildren().addAll(titleRow, ratingRow, shortDescRow, longDescRow, actorsRow);
        
        Label showingsLabel = new Label("Showings");
        showingsLabel.getStyleClass().add("info-label");
        VBox showingsItemsBox = new VBox();
        showingsItemsBox.getStyleClass().add("products-items-box");
        for (Showing showing : film.getShowings()) {
            Label showingItemLabel = new Label(String.valueOf(showing.getShowTime()));
            showingItemLabel.getStyleClass().add("product-price");

            showingItemLabel.setOnMouseClicked(event -> {
                SeatsPage seatsPage = new SeatsPage(controller, showing, this, film);
                controller.modifyContainer(seatsPage);
            });

            showingsItemsBox.getChildren().add(showingItemLabel);
        }
        VBox showingsBox = new VBox();
        showingsBox.getStyleClass().add("wide-box");
        showingsBox.getChildren().addAll(showingsLabel, showingsItemsBox);

        Button backButton = new Button("Back");
        backButton.getStyleClass().add("btn");
        backButton.setOnAction(e -> {
            controller.modifyContainer(new RepertoirePage(controller));
        });
        VBox backBtnBox = new VBox(backButton);
        backBtnBox.getStyleClass().add("wide-box");

        pageContent.getChildren().addAll(title, infoBox, showingsBox, backBtnBox);
    }

    /**
     * Creates a row with the specified key and value.
     * @param key the key of the row
     * @param value the value of the row
     * @return the HBox containing the row
     */
    private HBox getRow(String key, String value) {
        Label filmKeyLabel = new Label(key);
        filmKeyLabel.getStyleClass().add("info-key");
        Label filmValueLabel = new Label(value);
        filmValueLabel.getStyleClass().add("info-value");
        HBox infoRow = new HBox();
        infoRow.getChildren().addAll(filmKeyLabel, filmValueLabel);
        infoRow.getStyleClass().add("info-row");
        return infoRow;
    }

    /**
     * Returns a string representation of the actors in the film.
     * @return a string representation of the actors in the film
     */
    private String getActors() {
        List<String> actors = new ArrayList<>();
        for (Actor actor : film.getActors()) {
            actors.add(actor.toString());
        }
        return actors.stream().collect(Collectors.joining(", "));
    }

    /**
     * Returns the VBox containing the film page content.
     * @return the VBox containing the film page content
     */
    public VBox getPage() {
        return repertoireBox;
    }
}
