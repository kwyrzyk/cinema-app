package com.example;

import com.example.database.db_classes.Reservation;
import com.example.database.ReservationRepository;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.List;

public class ReservationsPage implements Page {
    private final VBox reservationsBox = new VBox();
    private final Controller controller;

    public ReservationsPage(Controller controller) {
        this.controller = controller;
        
        createContent();
    }

    private void createContent() {
        Label title = new Label("Reservations");
        title.getStyleClass().add("page-title");
        reservationsBox.getChildren().addAll(title);
        
        int userId = controller.getAccountId();
        List<Reservation> reservations = ReservationRepository.getAllReservationsByAccountId(userId, controller.databaseManager.getConnection());
        for (Reservation reservation : reservations) {
            Label reservationLabel = new Label(reservation.toString()); // Wyświetl tekstową reprezentację rezerwacji
            reservationLabel.getStyleClass().add("item");
            reservationsBox.getChildren().add(reservationLabel);
        }
    }

    public VBox getPage() {
        return reservationsBox;
    }
}
