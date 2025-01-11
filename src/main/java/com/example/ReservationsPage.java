package com.example;

import com.example.database.db_classes.OrderHistoryRecord;
import com.example.database.db_classes.Price;
import com.example.database.db_classes.Reservation;
import com.example.database.ReservationRepository;
import com.example.database.db_classes.Account;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;

import java.beans.VetoableChangeListener;
import java.util.List;

public class ReservationsPage implements Page {
    private final VBox reservationsBox;
    private final Controller controller;

    public ReservationsPage(Controller controller) {
        this.controller = controller;

        // Główna kontenerka dla elementów na stronie
        reservationsBox = new VBox(10); // 10px odstępu między elementami
        // reservationsBox.setAlignment(Pos.TOP_CENTER); // Wyrównanie do góry, na środku
        reservationsBox.getStyleClass().add("balance-box");

        // Tytuł strony
        Label title = new Label("Your reservations");
        title.getStyleClass().add("reservations-title");

        List<Reservation> reservations = ReservationRepository.getAllReservationsByAccountId(controller.getAccountId());
        for (Reservation reservation : reservations) {
            Label reservationLabel = new Label(reservation.toString()); // Wyświetl tekstową reprezentację rezerwacji
            reservationLabel.getStyleClass().add("reservation-item");
            reservationsBox.getChildren().add(reservationLabel);
        }
        
        reservationsBox.getChildren().addAll(title);
    }

    public VBox getPage() {
        return reservationsBox;
    }
}
