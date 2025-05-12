package com.example.pages;

import java.util.List;

import com.example.Controller;
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

/**
 * Class representing the Seats Page.
 */
public class SeatsPage implements Page {
    private final VBox pageContent = new VBox();
    private final ScrollPane scrollPane = new ScrollPane(pageContent);
    private final VBox historyBox = new VBox(scrollPane);
    private final Controller controller;

    private GridPane seatsGrid;
    private final List<Seat> seats;
    private final Showing showing;
    private final Film film;

    /**
     * Constructor for SeatsPage.
     * 
     * @param controller the controller instance
     * @param showing the showing instance
     * @param filmPage the film page instance
     * @param film the film instance
     */
    public SeatsPage(Controller controller, Showing showing, Page filmPage, Film film) {
        this.controller = controller;
        this.seats = showing.getSeats();
        this.showing = showing;
        this.film = film;

        createContent();
    }

    /**
     * Creates the content for the Seats Page.
     */
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
                seatButton.setText(String.valueOf(seatNumber));

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
                            updateAllSeats();
                        }
                    });
                } else {
                    seatButton.setDisable(true);
                }

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

    /**
     * Returns the page content.
     * 
     * @return the VBox containing the page content
     */
    public VBox getPage() {
        return pageContent;
    }

    /**
     * Returns the showing instance.
     * 
     * @return the showing instance
     */
    public Showing getShowing() {
        return showing;
    }

    /**
     * Converts a number to Roman numeral.
     * 
     * @param number the number to convert
     * @return the Roman numeral representation of the number
     */
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

    /**
     * Updates the style of a seat button based on the seat status.
     * 
     * @param seatButton the seat button
     * @param seat the seat instance
     */
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

    /**
     * Updates the style of all seat buttons.
     */
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

