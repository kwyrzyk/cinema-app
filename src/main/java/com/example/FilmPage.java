package com.example;

import java.time.LocalDateTime;
import java.util.List;

import com.example.database.db_classes.Actor;
import com.example.database.db_classes.Film;
import com.example.database.db_classes.Showing;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class FilmPage implements Page {
    private final VBox filmPage;

    public FilmPage(Film filmInfo) {
        List<Actor> actorsList = filmInfo.getActors();
        String actors = "";
        for (Actor actor : actorsList) {
            actors.concat(actor + " ");
        }
        List<Showing> showings = filmInfo.getShowings();
        String showTimes = "";
        for (Showing showing : showings) {
            showTimes.concat(showing.getShowTime() + "\n");
        }
        HBox titleRow = this.getRow("Title", filmInfo.getTitle());
        HBox ratingRow = this.getRow("Rating", String.valueOf(filmInfo.getRating()));
        HBox shortDescRow = this.getRow("Short Description", filmInfo.getShortDescription());
        HBox longDescRow = this.getRow("Description", filmInfo.getLongDescription());
        // HBox actorsRow = this.getRow("Actors", actors);

        filmPage = new VBox();
        filmPage.getStyleClass().add("film-page");
        filmPage.getChildren().addAll(titleRow, ratingRow, shortDescRow, longDescRow);
    }

    private HBox getRow(String attribute, String value) {
        Label attrLabel = new Label(attribute);
        attrLabel.getStyleClass().add("film-attr");
        Label valueLabel = new Label(value);
        valueLabel.getStyleClass().add("film-attr-value");
        HBox row = new HBox();
        row.getChildren().addAll(attrLabel, valueLabel);
        return row;
    }

    public VBox getPage() {
        return filmPage;
    }
}
