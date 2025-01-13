package com.example;

import java.util.List;

import com.example.database.db_classes.Film;
import com.example.database.db_classes.Seat;
import com.example.database.db_classes.Showing;
import com.example.database.db_classes.Ticket;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Node;

public class SeatsPage implements Page {
    private final VBox seatsPage;
    private final Controller controller;
    private final GridPane seatsGrid; // Siatka miejsc
    private final List<Seat> seats; // Lista miejsc (aktualizowana przy każdej zmianie)
    private final Showing showing; // Seans, dla którego wyświetlamy miejsca

    public SeatsPage(Controller controller, Showing showing, Page filmPage, Film filmInfo) {
        this.controller = controller;
        this.seats = showing.getSeats();
        this.showing = showing;

        // Tworzenie siatki miejsc
        seatsGrid = new GridPane();
        seatsGrid.setHgap(10); // Odstęp poziomy między siedzeniami
        seatsGrid.setVgap(10); // Odstęp pionowy między rzędami
        seatsGrid.setAlignment(Pos.CENTER); // Wyrównanie siatki na środku

        // Grupa miejsc po rzędach
        int maxRowNumber = seats.stream().mapToInt(Seat::getRowNumber).max().orElse(0);
        int maxSeatNumber = seats.stream().mapToInt(Seat::getSeatNumber).max().orElse(0);

        // Wypełnianie siatki siedzeniami
        for (int i = 1; i <= maxRowNumber; i++) {
            // Dodanie numeru rzędu (liczba rzymska)
            Label rowLabel = new Label(toRoman(i));
            rowLabel.getStyleClass().add("row-label"); // Dodanie klasy CSS
            seatsGrid.add(rowLabel, 0, i - 1); // Umieszczenie w pierwszej kolumnie (0)

            for (int j = 1; j <= maxSeatNumber; j++) {
                final int rowNumber = i;
                final int seatNumber = j;

                // Szukamy miejsca o danym numerze rzędu i miejsca w rzędzie
                Seat seat = seats.stream()
                        .filter(s -> s.getRowNumber() == rowNumber && s.getSeatNumber() == seatNumber)
                        .findFirst()
                        .orElse(null);

                if (controller.modifyTicketMode && seat.getId() == controller.modifyingTicket.getTicketId()){
                    seat.setStatus("modifying");
                }

                Button seatButton = new Button();
                seatButton.setText(String.valueOf(seatNumber)); // Wyświetla numer miejsca

                if (seat != null) {
                    // Ustawiamy kolor w zależności od statusu miejsca
                    updateSeatStyle(seatButton, seat);

                    // Dodanie akcji kliknięcia
                    seatButton.setOnAction(e -> {
                        if (controller.modifyTicketMode) {
                            // Modyfikacja biletu
                            if (seat.getStatus().equals("available")) {
                                seat.setStatus("inBasket");
                                Ticket ticket = new Ticket(filmInfo, showing, seat);
                                controller.basket.addTicket(ticket);
                                Seat modifyingSeat = seats.stream()
                                    .filter(s -> s.getId() == controller.modifyingTicket.getTicketId())
                                    .findFirst()
                                    .orElse(null);
                                if (modifyingSeat != null) {
                                    modifyingSeat.setStatus("available");
                                }
                                updateAllSeats(); // Uaktualniamy wszystkie miejsca
                                controller.modifyTicketMode = false;
                                controller.basket.removeItem(controller.modifyingTicket);
                                controller.container.getChildren().clear();
                                controller.container.getChildren().add(new BasketPage(controller.basket).getPage());
                                controller.optionsBar.getChildren().clear();
                                controller.addOption("Pay", "payBtn", controller::handleOptionClick);
                                controller.addOption("Remove All", "removeAllBtn", controller::handleOptionClick);
                                controller.addOption("Modify ticket", "modifyTicketBtn", controller::handleOptionClick);
                                return;
                            }
                        } else {
                            // Dodanie/usunięcie biletu do/z koszyka
                            if (seat.getStatus().equals("available")) {
                                seat.setStatus("inBasket");
                                Ticket ticket = new Ticket(filmInfo, showing, seat);
                                controller.basket.addTicket(ticket);
                            } else if (seat.getStatus().equals("inBasket")) {
                                seat.setStatus("available");
                                controller.basket.removeTicket(new Ticket(filmInfo, showing, seat));
                            }
                            updateAllSeats(); // Uaktualniamy wszystkie miejsca
                            
                        }
                    });
                } else {
                    // Miejsce nie istnieje (puste pole w siatce)
                    seatButton.setDisable(true);
                }

                // Dodanie przycisku do siatki
                seatButton.getStyleClass().add("seat-btn");
                seatsGrid.add(seatButton, j, i - 1); // Indeksy w GridPane zaczynają się od 0
            }
        }

        // Przycisk "Powrót"
        Button backButton = new Button("Back");
        backButton.getStyleClass().add("back-btn");
        backButton.setId("repertoireBackBtn");
        backButton.setOnAction(e -> {
            Parent parent = backButton.getParent().getParent().getParent();
            VBox container = (VBox) parent;
            container.getChildren().clear();
            // container.getChildren().add(new FilmPage(controller, filmInfo).getPage());
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

    public Showing getShowing() {
        return showing;
    }

    // Metoda konwertująca liczbę na zapis rzymski
    private String toRoman(int number) {
        String[] romanNumerals = {
            "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"
        };
        int[] arabicValues = {
            1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1
        };

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < arabicValues.length; i++) {
            while (number >= arabicValues[i]) {
                result.append(romanNumerals[i]);
                number -= arabicValues[i];
            }
        }
        return result.toString();
    }

    // Metoda aktualizująca styl przycisku w zależności od statusu siedzenia
    public void updateSeatStyle(Button seatButton, Seat seat) {
        switch (seat.getStatus()) {
            case "available":
                seatButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");
                break;
            case "reserved":
                seatButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
                break;
            case "inBasket":
                seatButton.setStyle("-fx-background-color: yellow; -fx-text-fill: black;");
                break;
            case "modifying":
                seatButton.setStyle("-fx-background-color: orange; -fx-text-fill: black;");
                break;
            default:
                seatButton.setStyle("-fx-background-color: gray; -fx-text-fill: white;");
        }
    }

    // Metoda aktualizująca wszystkie miejsca w siatce
    public void updateAllSeats() {
        for (Node node : seatsGrid.getChildren()) {
            if (node instanceof Button) {
                Button seatButton = (Button) node;
                int row = GridPane.getRowIndex(seatButton) + 1;
                int col = GridPane.getColumnIndex(seatButton);

                Seat seat = seats.stream()
                        .filter(s -> s.getRowNumber() == row && s.getSeatNumber() == col)
                        .findFirst()
                        .orElse(null);

                if (seat != null) {
                    updateSeatStyle(seatButton, seat);
                }
            }
        }
    }
}

