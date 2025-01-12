package com.example;

import com.example.database.db_classes.Price;
import com.example.database.db_classes.Account;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;

import java.util.List;

public class BalancePage implements Page {
    private final VBox balanceBox;
    private final Controller controller;

    public BalancePage(Controller controller) {
        this.controller = controller;

        // Główna kontenerka dla elementów na stronie
        balanceBox = new VBox(10); // 10px odstępu między elementami
        // balanceBox.setAlignment(Pos.TOP_CENTER); // Wyrównanie do góry, na środku
        balanceBox.getStyleClass().add("balance-box");

        // Tytuł strony
        Label title = new Label("Balance");
        title.getStyleClass().add("balance-title");
        Account userAccount = controller.getAccountListing().getAccountById(controller.getAccountId());
        Label currentBalanceLabel = new Label("Current balance: " + userAccount.getBalance());
        currentBalanceLabel.getStyleClass().add("current-balance-label");
        Button topUpButton = new Button("Top up");
        topUpButton.getStyleClass().add("button");
        VBox topUpBtnBox = new VBox(topUpButton);
        topUpBtnBox.getStyleClass().add("top-up-btn-box");

        topUpButton.setOnAction(event -> {
            // Tworzenie dialogu do wprowadzenia kwoty
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Top Up Balance");
            dialog.setHeaderText("How many dolars do you want to top up?");
            dialog.setContentText("Dolars:");

            // Wyświetlanie dialogu i pobieranie wpisanej wartości
            dialog.showAndWait().ifPresent(input -> {
                try {
                    // Parsowanie wpisanej wartości jako liczba całkowita
                    int dolars = Integer.parseInt(input);
                    if (dolars > 0) {
                        // Aktualizacja salda w bazie danych
                        boolean success = controller.getAccountListing().addBalance(controller.getAccountId(), dolars);
                        if (success) {
                            // Aktualizacja salda na stronie
                            userAccount.setBalance(Price.operatorPlus(userAccount.getBalance(), new Price(dolars, 0)));
                            currentBalanceLabel.setText("Current balance: " + userAccount.getBalance());
                            Alert infoAlert = new Alert(Alert.AlertType.INFORMATION, "Balance topped up successfully!");
                            infoAlert.showAndWait();
                        } else {
                            Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Failed to top up balance.");
                            errorAlert.showAndWait();
                        }
                    } else {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Please enter a positive amount.");
                        errorAlert.showAndWait();
                    }
                } catch (NumberFormatException e) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Invalid number format. Please enter an integer.");
                    errorAlert.showAndWait();
                }
            });
        });

        balanceBox.getChildren().addAll(title, currentBalanceLabel, topUpBtnBox);
    }

    public VBox getPage() {
        return balanceBox;
    }
}
