package com.example;

import java.util.List;

import com.example.database.db_classes.Film;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class FilmPage implements Page {
    private VBox filmPage;
    private Film filmInfo;

    public FilmPage() {
        HBox titleRow = this.getRow("Title", filmInfo.getTitle());
        HBox ratingRow = this.getRow("Rating", String.valueOf(filmInfo.getRating()));
        HBox shortDescRow = this.getRow("Rating", filmInfo.getShortDescription());
        HBox longDescRow = this.getRow("Rating", filmInfo.getLongDescription());
        filmPage.getChildren().addAll(titleRow, ratingRow, shortDescRow, longDescRow);
    }
    public void setFilmInfo(Film newFilmInfo){
        this.filmInfo = newFilmInfo;
    }
    private HBox getRow(String attribute, String value) {
        Label attrLabel = new Label(attribute);
        Label valueLabel = new Label(attribute);
        HBox row = new HBox();
        row.getChildren().addAll(attrLabel, valueLabel);
        return row;
    }

    public VBox getPage() {
        return filmPage;
    }
}
