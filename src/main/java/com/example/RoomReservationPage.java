package com.example;

import com.example.database.db_classes.OrderHistoryRecord;
import com.example.database.db_classes.Price;
import com.example.database.db_classes.Account;
import com.example.database.db_classes.ScreeningRoom;
import com.example.database.ReservationRepository;
import com.example.database.ScreeningRoomRepository;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import java.time.LocalDate;

import java.beans.VetoableChangeListener;
import java.util.List;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class RoomReservationPage implements Page {
    private final VBox reservationBox;
    private final Controller controller;

    public RoomReservationPage(Controller controller) {
        this.controller = controller;

        // Główna kontenerka dla elementów na stronie
        reservationBox = new VBox(10); // 10px odstępu między elementami
        reservationBox.getStyleClass().add("reservation-box");

        // Tytuł strony
        Label title = new Label("Room Reservation");
        title.getStyleClass().add("page-title");

        Label infoLabel = new Label("Click button to add new reservation");
        infoLabel.getStyleClass().add("info-label");

        Button newReservationBtn = new Button("New reservation");
        newReservationBtn.getStyleClass().add("button");
        VBox reservationBtnBox = new VBox(newReservationBtn);
        reservationBtnBox.getStyleClass().add("reservation-btn-box");

        // Obsługa kliknięcia przycisku
        newReservationBtn.setOnAction(event -> showReservationDialog());

        reservationBox.getChildren().addAll(title, infoLabel, reservationBtnBox);
    }

    private void showReservationDialog() {
        // Tworzenie okna dialogowego
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("New Room Reservation");
    
        // Ustawienie przycisków dialogu
        ButtonType confirmButtonType = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, cancelButtonType);
    
        // Layout w oknie dialogowym
        VBox dialogContent = new VBox(10);
        dialogContent.setStyle("-fx-padding: 10;");
    
        // ComboBox do wyboru pokoju
        Label roomLabel = new Label("Select room:");
        ComboBox<ScreeningRoom> roomComboBox = new ComboBox<>();
        List<ScreeningRoom> rooms = ScreeningRoomRepository.getAllScreeningRooms();
        roomComboBox.getItems().addAll(rooms);
    
        // Ustawienie, jak będą wyświetlane elementy w ComboBox
        roomComboBox.setCellFactory(param -> new ListCell<ScreeningRoom>() {
            @Override
            protected void updateItem(ScreeningRoom item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item.getName()); // Wyświetl tylko nazwę pokoju
                } else {
                    setText(null);
                }
            }
        });
    
        // Zdarzenie po wybraniu pokoju (wybór zwróci ScreeningRoom, a nie tylko nazwę)
        roomComboBox.setOnAction(event -> {
            ScreeningRoom selectedRoom = roomComboBox.getValue();
            if (selectedRoom != null) {
                int roomId = selectedRoom.getId(); // Pobierz id wybranego pokoju
                System.out.println("Selected room ID: " + roomId); // Możesz użyć tego id później
            }
        });
    
        roomComboBox.setPromptText("Choose a room");
    
        // DatePicker do wyboru daty
        Label dateLabel = new Label("Select date:");
        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("Select reservation date");
    
        // ComboBox do wyboru godziny i minut dla rozpoczęcia i zakończenia
        Label startTimeLabel = new Label("Start time:");
        ComboBox<Integer> startHourComboBox = createHourComboBox();
        ComboBox<Integer> startMinuteComboBox = createMinuteComboBox();
    
        Label endTimeLabel = new Label("End time:");
        ComboBox<Integer> endHourComboBox = createHourComboBox();
        ComboBox<Integer> endMinuteComboBox = createMinuteComboBox();
    
        // Grupowanie pól godziny i minut
        HBox startTimeBox = new HBox(5, startHourComboBox, new Label(":"), startMinuteComboBox);
        HBox endTimeBox = new HBox(5, endHourComboBox, new Label(":"), endMinuteComboBox);
    
        // Dodanie elementów do layoutu
        dialogContent.getChildren().addAll(
            roomLabel, roomComboBox,
            dateLabel, datePicker,
            startTimeLabel, startTimeBox,
            endTimeLabel, endTimeBox
        );
        dialog.getDialogPane().setContent(dialogContent);
    
        // Obsługa wyniku dialogu
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == confirmButtonType) {
                ScreeningRoom selectedRoom = roomComboBox.getValue();
                LocalDate selectedDate = datePicker.getValue();
                Integer startHour = startHourComboBox.getValue();
                Integer startMinute = startMinuteComboBox.getValue();
                Integer endHour = endHourComboBox.getValue();
                Integer endMinute = endMinuteComboBox.getValue();
    
                if (selectedRoom == null || selectedDate == null ||
                    startHour == null || startMinute == null || endHour == null || endMinute == null) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Please fill in all fields.");
                    errorAlert.showAndWait();
                } else {
                    LocalDateTime startDateTime = LocalDateTime.of(selectedDate, LocalTime.of(startHour, startMinute));
                    LocalDateTime endDateTime = LocalDateTime.of(selectedDate, LocalTime.of(endHour, endMinute));
    
                    // Formatuj daty na "yyyy-MM-dd HH:mm:ss"
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    String startTime = startDateTime.format(formatter);
                    String endTime = endDateTime.format(formatter);

                    System.out.println("Start time: " + startTime);
                    System.out.println("End time: " + endTime);
    
                    if (endDateTime.isBefore(startDateTime)) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR, "End time cannot be before start time.");
                        errorAlert.showAndWait();
                    } else {
                        // Tutaj dodaj logikę zapisu rezerwacji do bazy danych
                        boolean reservationSuccesful = ReservationRepository.reserve_if_possible(selectedRoom.getId(), controller.getAccountId(), startTime, endTime);
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION, 
                            "Reservation confirmed:\nRoom: " + selectedRoom.getName() + 
                            "\nDate: " + selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + 
                            "\nFrom: " + startDateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")) + 
                            "\nTo: " + endDateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
                        successAlert.showAndWait();
                    }
                }
            }
            return null;
        });
    
        dialog.showAndWait();
    }
    

    private ComboBox<Integer> createHourComboBox() {
        ComboBox<Integer> comboBox = new ComboBox<>();
        for (int i = 0; i < 24; i++) {
            comboBox.getItems().add(i);
        }
        comboBox.setPromptText("HH");
        return comboBox;
    }

    private ComboBox<Integer> createMinuteComboBox() {
        ComboBox<Integer> comboBox = new ComboBox<>();
        for (int i = 0; i < 60; i += 5) { // Minuty co 5 minut
            comboBox.getItems().add(i);
        }
        comboBox.setPromptText("MM");
        return comboBox;
    }

    public VBox getPage() {
        return reservationBox;
    }
}