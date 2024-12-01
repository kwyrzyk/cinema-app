package com.example;

import com.example.database.db_classes.Film;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class FilmPage implements Page {
    private final VBox filmPage;

    public FilmPage(Film filmInfo) {
        System.out.println(filmInfo.getRating());
        HBox titleRow = this.getRow("Title", filmInfo.getTitle());
        HBox ratingRow = this.getRow("Rating", String.valueOf(filmInfo.getRating()));
        HBox shortDescRow = this.getRow("Rating", filmInfo.getShortDescription());
        HBox longDescRow = this.getRow("Rating", filmInfo.getLongDescription());
        filmPage = new VBox();
        filmPage.getChildren().addAll(titleRow, ratingRow, shortDescRow, longDescRow);
    }

    private HBox getRow(String attribute, String value) {
        Label attrLabel = new Label(attribute);
        Label valueLabel = new Label(value);
        HBox row = new HBox();
        row.getChildren().addAll(attrLabel, valueLabel);
        return row;
    }

    public VBox getPage() {
        return filmPage;
    }
}
