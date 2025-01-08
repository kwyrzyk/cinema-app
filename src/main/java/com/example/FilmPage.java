package com.example;

import java.util.List;

import com.example.database.db_classes.Actor;
import com.example.database.db_classes.Film;
import com.example.database.db_classes.Showing;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class FilmPage implements Page {
    private final VBox filmPage;
    private final Controller controller;

    public FilmPage(Controller controller, Film filmInfo) {
        this.controller = controller;
        List<Actor> actorsList = filmInfo.getActors();
        String actors = "";
        for (Actor actor : actorsList) {
            actors = actors.concat(actor.toString() + " ");
        }
        System.out.println(actors);


        ListView<Showing> showingsListView = new ListView<>();
        showingsListView.getStyleClass().add("lists");
        showingsListView.getItems().addAll(filmInfo.getShowings());
        showingsListView.setCellFactory(listView -> new ShowingListCell(controller, this, filmInfo));

        double rowHeight = 24;
        showingsListView.setPrefHeight(filmInfo.getShowings().size() * rowHeight);

        VBox showingsBox = new VBox(showingsListView);


        HBox titleRow = this.getRow("Title", filmInfo.getTitle());
        HBox ratingRow = this.getRow("Rating", String.valueOf(filmInfo.getRating()));
        HBox shortDescRow = this.getRow("Short Description", filmInfo.getShortDescription());
        HBox longDescRow = this.getRow("Description", filmInfo.getLongDescription());
        HBox showingsRow = this.getRow("Showings:", "");
        HBox actorsRow = this.getRow("Actors", actors);
        Button backButton = new Button("Back");
        backButton.getStyleClass().add("back-btn");
        backButton.setId("repertoireBackBtn");
        backButton.setOnAction(this.controller::handleSidebarClick);

        filmPage = new VBox();
        filmPage.getStyleClass().add("film-page");
        filmPage.getChildren().addAll(titleRow, ratingRow, shortDescRow, longDescRow, actorsRow, showingsRow, showingsBox, backButton);
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

    private static class ShowingListCell extends ListCell<Showing> {
        private final Controller controller;
        private final FilmPage filmPage; // Dodanie referencji do FilmPage
        private final Film filmInfo; // Dodanie referencji do FilmPage
        VBox container;
    
        public ShowingListCell(Controller controller, FilmPage filmPage, Film filmInfo) {
            this.controller = controller;
            this.filmPage = filmPage;
            this.filmInfo = filmInfo;
        }
    
        @Override
        protected void updateItem(Showing showing, boolean empty) {
            super.updateItem(showing, empty);
    
            if (empty || showing == null) {
                setText(null);
                setGraphic(null);
            } else {
                VBox content = new VBox();
    
                Label showTimeLabel = new Label("Showtime: " + showing.getShowTime());
    
                // Akcja kliknięcia na showing
                this.setOnMouseClicked(e -> {
                    System.out.println("Clicked showing: " + showing.getShowTime());
    
                    // Tworzenie i wyświetlanie strony z mapą miejsc (SeatsPage)
                    SeatsPage seatsPage = new SeatsPage(controller, showing, filmPage, filmInfo);
                    Parent parent = (this.getParent()).getParent().getParent().getParent().getParent().getParent();
                    container = (VBox) parent;
                    container.getChildren().clear();
                    container.getChildren().add(seatsPage.getPage()); // Przekazanie nowej strony do kontrolera
                    this.controller.seatsPage = seatsPage;
                });

    
                content.getChildren().addAll(showTimeLabel);
                setGraphic(content);
            }
        }
    }
    

    public VBox getPage() {
        return filmPage;
    }
}
