package com.example;

import com.example.database.db_classes.Price;
import com.example.database.db_classes.Account;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;

/**
 * Represents the balance page of the application.
 */
public class BalancePage implements Page {
    private final VBox balanceBox = new VBox();
    private final Controller controller;

    /**
     * Constructs a BalancePage with the specified controller.
     * @param controller the controller of the application
     */
    public BalancePage(Controller controller) {
        this.controller = controller;

        createContent();
    }

    /**
     * Creates the content of the balance page.
     */
    private void createContent() {
        balanceBox.getStyleClass().add("page");

        Label title = new Label("Balance");
        title.getStyleClass().add("page-title");

        int userId = controller.getAccountId();
        Account userAccount = controller.getAccountListing().getAccountById(userId);
        Price userBalance = userAccount.getBalance();

        Label currentBalanceLabel = new Label("Current balance: " + userBalance);
        currentBalanceLabel.getStyleClass().add("info-label");
        
        Button topUpButton = new Button("Top up");
        topUpButton.getStyleClass().add("btn");

        VBox topUpBtnBox = new VBox(topUpButton);
        topUpBtnBox.getStyleClass().add("wide-box");

        topUpButton.setOnAction(event -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Top Up Balance");
            dialog.setHeaderText("How many dolars do you want to top up?");
            dialog.setContentText("Dolars:");

            dialog.showAndWait().ifPresent(input -> {
                try {
                    int dolars = Integer.parseInt(input);
                    if (dolars > 0) {
                        boolean success = controller.getAccountListing().addBalance(controller.getAccountId(), dolars);
                        
                        if (success) {
                            userAccount.setBalance(Price.operatorPlus(userAccount.getBalance(), new Price(dolars, 0)));
                            currentBalanceLabel.setText("Current balance: " + userAccount.getBalance());
                            Controller.showAlert(Alert.AlertType.INFORMATION, "Balance topped up", "Balance topped up successfully!");
                        } else {
                            Controller.showAlert(Alert.AlertType.ERROR, "Top up unsuccessful", "Failed to top up balance.");
                        }
                    } else {
                        Controller.showAlert(Alert.AlertType.ERROR, "Top up unsuccessful", "Please enter a positive amount.");
                    }
                } catch (NumberFormatException e) {
                    Controller.showAlert(Alert.AlertType.ERROR, "Top up unsuccessful", "Invalid number format. Please enter an integer.");
                }
            });
        });
        balanceBox.getChildren().addAll(title, currentBalanceLabel, topUpBtnBox);
    }

    /**
     * Returns the VBox containing the balance page content.
     * @return the VBox containing the balance page content
     */
    public VBox getPage() {
        return balanceBox;
    }
}
