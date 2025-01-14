package com.example;

import java.util.List;

import com.example.database.db_classes.Film;
import com.example.database.db_classes.Seat;
import com.example.database.db_classes.Showing;
import com.example.database.db_classes.Ticket;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class SeatsPage implements Page {
    private final VBox pageContent = new VBox();
    private final ScrollPane scrollPane = new ScrollPane(pageContent);
    private final VBox historyBox = new VBox(scrollPane);
    private final Controller controller;

    private GridPane seatsGrid; // Siatka miejsc
    private final List<Seat> seats; // Lista miejsc (aktualizowana przy każdej zmianie)
    private final Showing showing; // Seans, dla którego wyświetlamy miejsca
    private final Film film;

    public SeatsPage(Controller controller, Showing showing, Page filmPage, Film film) {
        this.controller = controller;
        this.seats = showing.getSeats();
        this.showing = showing;
        this.film = film;

        createContent();
    }

    private void createContent() {
        historyBox.getStyleClass().add("page");
        scrollPane.getStyleClass().add("scroll-pane");
        scrollPane.setFitToWidth(true);
        pageContent.getStyleClass().add("wide-box"); 

        Label title = new Label("Choose seats");
        title.getStyleClass().add("page-title");

        seatsGrid = new GridPane();
        seatsGrid.getStyleClass().add("seats-grid");

        int maxRowNumber = seats.stream().mapToInt(Seat::getRowNumber).max().orElse(0);
        int maxSeatNumber = seats.stream().mapToInt(Seat::getSeatNumber).max().orElse(0);

        for (int i = 1; i <= maxRowNumber; i++) {
            Label rowLabel = new Label(toRoman(i));
            rowLabel.getStyleClass().add("seats-row-label");
            seatsGrid.add(rowLabel, 0, i - 1);

            for (int j = 1; j <= maxSeatNumber; j++) {
                final int rowNumber = i;
                final int seatNumber = j;

                Seat seat = seats.stream()
                        .filter(s -> s.getRowNumber() == rowNumber && s.getSeatNumber() == seatNumber)
                        .findFirst()
                        .orElse(null);

                if (controller.modifyTicketMode && seat.getId() == controller.modifyingTicket.getId()){
                    seat.setStatus("modifying");
                }

                Button seatButton = new Button();
                seatButton.setText(String.valueOf(seatNumber)); // Wyświetla numer miejsca

                if (seat != null) {
                    updateSeatStyle(seatButton, seat);

                    seatButton.setOnAction(e -> {
                        if (controller.modifyTicketMode) {
                            if (seat.getStatus().equals("available")) {
                                seat.setStatus("inBasket");
                                Ticket ticket = new Ticket(film, showing, seat);
                                controller.basket.addTicket(ticket);
                                Seat modifyingSeat = seats.stream()
                                    .filter(s -> s.getId() == controller.modifyingTicket.getId())
                                    .findFirst()
                                    .orElse(null);
                                if (modifyingSeat != null) {
                                    modifyingSeat.setStatus("available");
                                }
                                updateAllSeats();
                                controller.modifyTicketMode = false;
                                controller.basket.removeItem(controller.modifyingTicket);
                                controller.modifyContainer(new BasketPage(controller.basket));
                                controller.optionsBar.getChildren().clear();
                                controller.addOption("Pay", "payBtn", controller::handleOptionClick);
                                controller.addOption("Remove All", "removeAllBtn", controller::handleOptionClick);
                                controller.addOption("Modify ticket", "modifyTicketBtn", controller::handleOptionClick);
                                return;
                            }
                        } else {
                            if (seat.getStatus().equals("available")) {
                                seat.setStatus("inBasket");
                                Ticket ticket = new Ticket(film, showing, seat);
                                controller.basket.addTicket(ticket);
                                controller.getFilmListing().addModified(showing);
                            } else if (seat.getStatus().equals("inBasket")) {
                                seat.setStatus("available");
                                controller.basket.removeTicket(new Ticket(film, showing, seat));
                            }
                            updateAllSeats(); // Uaktualniamy wszystkie miejsca
                        }
                    });
                } else {
                    seatButton.setDisable(true);
                }

                // Dodanie przycisku do siatki
                seatButton.getStyleClass().add("seat-btn");
                seatsGrid.add(seatButton, j, i - 1);
            }
        }
        
        Button backButton = new Button("Back");
        backButton.getStyleClass().add("btn");
        backButton.setId("repertoireBackBtn");
        backButton.setOnAction(e -> {
            controller.modifyContainer(new FilmPage(controller, film));
        });
        VBox backBtnBox = new VBox(backButton);
        backBtnBox.getStyleClass().add("wide-box");

        pageContent.getChildren().addAll(title, seatsGrid, backBtnBox);
    }

    public VBox getPage() {
        return pageContent;
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

