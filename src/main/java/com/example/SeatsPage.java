package com.example;

import java.util.List;

import com.example.database.db_classes.Film;
import com.example.database.db_classes.Seat;
import com.example.database.db_classes.Showing;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Node;

public class SeatsPage implements Page {
    private final VBox seatsPage;
    private final Controller controller;

    public SeatsPage(Controller controller, Showing showing, Page filmPage, Film filmInfo) {
        this.controller = controller;
        List<Seat> seats = showing.getSeats();

        // Tworzenie siatki miejsc
        GridPane seatsGrid = new GridPane();
        seatsGrid.setHgap(10); // Odstęp poziomy między siedzeniami
        seatsGrid.setVgap(10); // Odstęp pionowy między rzędami
        seatsGrid.setAlignment(Pos.CENTER); // Wyrównanie siatki na środku

        // Grupa miejsc po rzędach
        int maxRowNumber = seats.stream().mapToInt(Seat::getRowNumber).max().orElse(0);
        int maxSeatNumber = seats.stream().mapToInt(Seat::getSeatNumber).max().orElse(0);

        // Wypełnianie siatki siedzeniami
        for (int i = 1; i <= maxRowNumber; i++) {
            for (int j = 1; j <= maxSeatNumber; j++) {
                final int rowNumber = i;
                final int seatNumber = j;
                // Szukamy miejsca o danym numerze rzędu i miejsca w rzędzie
                Seat seat = seats.stream()
                        .filter(s -> s.getRowNumber() == rowNumber && s.getSeatNumber() == seatNumber)
                        .findFirst()
                        .orElse(null);

                Button seatButton = new Button();
                seatButton.setPrefWidth(50); // Szerokość przycisku
                seatButton.setPrefHeight(50); // Wysokość przycisku
                seatButton.setText(rowNumber + "-" + seatNumber); // Wyświetla numer miejsca

                if (seat != null) {
                    // Ustawiamy kolor w zależności od statusu miejsca
                    switch (seat.getStatus()) {
                        case "available":
                            seatButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");
                            break;
                        case "reserved":
                            seatButton.setStyle("-fx-background-color: yellow; -fx-text-fill: black;");
                            break;
                        case "booked":
                            seatButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
                            break;
                        default:
                            seatButton.setStyle("-fx-background-color: gray; -fx-text-fill: white;");
                    }

                    // Dodanie akcji kliknięcia
                    seatButton.setOnAction(e -> {
                        System.out.println("Selected seat: Row " + rowNumber + ", Seat " + seatNumber);
                    });
                } else {
                    // Miejsce nie istnieje (puste pole w siatce)
                    seatButton.setDisable(true);
                }

                // Dodanie przycisku do siatki
                seatsGrid.add(seatButton, j - 1, i - 1); // Indeksy w GridPane zaczynają się od 0
            }
        }

        // Przycisk "Powrót"
        Button backButton = new Button("Back");
        backButton.getStyleClass().add("back-btn");
        backButton.setId("repertoireBackBtn");
        backButton.setOnAction( e -> {
            Parent parent = (backButton).getParent().getParent().getParent();
            VBox container = (VBox) parent;
            container.getChildren().clear();
            container.getChildren().add(new FilmPage(controller, filmInfo).getPage());
        });

        // Złożenie całej strony
        HBox titleRow = new HBox();
        titleRow.getChildren().add(backButton);
        titleRow.setAlignment(Pos.CENTER);

        seatsPage = new VBox(20); // Odstęp między elementami
        seatsPage.getStyleClass().add("seats-page");
        seatsPage.getChildren().addAll(seatsGrid, titleRow);
        seatsPage.setAlignment(Pos.CENTER);
    }

    public VBox getPage() {
        return seatsPage;
    }
}
