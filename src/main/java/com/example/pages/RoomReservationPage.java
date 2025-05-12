package com.example.pages;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.example.Controller;
import com.example.database.ReservationRepository;
import com.example.database.ScreeningRoomRepository;
import com.example.database.db_classes.ScreeningRoom;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Class representing the Room Reservation Page.
 */
public class RoomReservationPage implements Page {
    private final VBox roomReservationBox = new VBox();
    private final Controller controller;

    /**
     * Constructor for RoomReservationPage.
     * 
     * @param controller the controller instance
     */
    public RoomReservationPage(Controller controller) {
        this.controller = controller;
        createContent();
    }

    /**
     * Creates the content for the Room Reservation Page.
     */
    private void createContent() {
        roomReservationBox.getStyleClass().add("page");

        Label title = new Label("Room Reservation");
        title.getStyleClass().add("page-title");

        Label infoLabel = new Label("Click button to add new reservation");
        infoLabel.getStyleClass().add("info-label");

        Button newReservationBtn = new Button("New reservation");
        newReservationBtn.getStyleClass().add("btn");

        VBox reservationBtnBox = new VBox(newReservationBtn);
        reservationBtnBox.getStyleClass().add("wide-box");

        newReservationBtn.setOnAction(event -> showReservationDialog());

        roomReservationBox.getChildren().addAll(title, infoLabel, reservationBtnBox);
    }

    /**
     * Shows the reservation dialog.
     */
    private void showReservationDialog() {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("New Room Reservation");
        String cssFile = getClass().getResource("/css/styles.css").toExternalForm();
        dialog.getDialogPane().getStylesheets().add(cssFile);
    
        ButtonType confirmButtonType = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, cancelButtonType);
    
        VBox dialogContent = new VBox(10);
        dialogContent.setStyle("-fx-padding: 10;");
    
        Label roomLabel = new Label("Select room:");
        ComboBox<ScreeningRoom> roomComboBox = new ComboBox<>();
        List<ScreeningRoom> rooms = ScreeningRoomRepository.getAllScreeningRooms(controller.databaseManager.getConnection());
        roomComboBox.getItems().addAll(rooms);
    
        roomComboBox.setCellFactory(param -> new ListCell<ScreeningRoom>() {
            @Override
            protected void updateItem(ScreeningRoom item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item.getName());
                } else {
                    setText(null);
                }
            }
        });
    
        roomComboBox.setPromptText("Choose a room");
    
        Label dateLabel = new Label("Select date:");
        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("Select reservation date");
    
        Label startTimeLabel = new Label("Start time:");
        ComboBox<Integer> startHourComboBox = createHourComboBox();
        ComboBox<Integer> startMinuteComboBox = createMinuteComboBox();
    
        Label endTimeLabel = new Label("End time:");
        ComboBox<Integer> endHourComboBox = createHourComboBox();
        ComboBox<Integer> endMinuteComboBox = createMinuteComboBox();
    
        HBox startTimeBox = new HBox(5, startHourComboBox, new Label(":"), startMinuteComboBox);
        HBox endTimeBox = new HBox(5, endHourComboBox, new Label(":"), endMinuteComboBox);
    
        dialogContent.getChildren().addAll(
            roomLabel, roomComboBox,
            dateLabel, datePicker,
            startTimeLabel, startTimeBox,
            endTimeLabel, endTimeBox
        );
        dialogContent.setStyle("-fx-text-fill: red");
        dialog.getDialogPane().setContent(dialogContent);
        
    
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
                    Controller.showAlert(Alert.AlertType.ERROR, "Reservation unsuccessful", "Please fill in all fields.");
                } else {
                    LocalDateTime startDateTime = LocalDateTime.of(selectedDate, LocalTime.of(startHour, startMinute));
                    LocalDateTime endDateTime = LocalDateTime.of(selectedDate, LocalTime.of(endHour, endMinute));
    
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    String startTime = startDateTime.format(formatter);
                    String endTime = endDateTime.format(formatter);

                    if (endDateTime.isBefore(startDateTime)) {
                        Controller.showAlert(Alert.AlertType.ERROR, "Reservation unsuccessful","End time cannot be before start time.");
                    } else if (startDateTime.isBefore(LocalDateTime.now())) {
                        Controller.showAlert(Alert.AlertType.ERROR, "Reservation unsuccessful", "Reservation cannot be in the past.");
                    } else {
                        boolean reservationSuccesful = ReservationRepository.reserve_if_possible(selectedRoom.getId(), controller.getAccountId(), startTime, endTime, controller.databaseManager.getConnection());
                        if (reservationSuccesful) {
                            Controller.showAlert(
                                Alert.AlertType.INFORMATION, 
                                "Reservation successful",
                                "Reservation confirmed:\nRoom: " + selectedRoom.getName() + 
                                "\nDate: " + selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + 
                                "\nFrom: " + startDateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")) + 
                                "\nTo: " + endDateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"))
                                );
                        } else {
                            Controller.showAlert(
                                Alert.AlertType.ERROR,
                                "Reservation unsuccessful", 
                                "Room is already reserved for this time."
                                );
                        }
                    }
                }
            }
            return null;
        });
    
        dialog.showAndWait();
    }

    /**
     * Creates a ComboBox for selecting hours.
     * 
     * @return the ComboBox for hours
     */
    private ComboBox<Integer> createHourComboBox() {
        ComboBox<Integer> comboBox = new ComboBox<>();
        for (int i = 0; i < 24; i++) {
            comboBox.getItems().add(i);
        }
        comboBox.setPromptText("HH");
        return comboBox;
    }

    /**
     * Creates a ComboBox for selecting minutes.
     * 
     * @return the ComboBox for minutes
     */
    private ComboBox<Integer> createMinuteComboBox() {
        ComboBox<Integer> comboBox = new ComboBox<>();
        for (int i = 0; i < 60; i += 5) {
            comboBox.getItems().add(i);
        }
        comboBox.setPromptText("MM");
        return comboBox;
    }

    /**
     * Returns the page content.
     * 
     * @return the VBox containing the page content
     */
    public VBox getPage() {
        return roomReservationBox;
    }
}