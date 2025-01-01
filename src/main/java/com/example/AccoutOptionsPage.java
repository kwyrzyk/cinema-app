package com.example;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

import com.example.database.db_classes.Account;
import com.example.listing.AccountListing;

public class AccoutOptionsPage {
    private VBox optionPage;
    private final AccountListing accountListing;
    private final Controller controller;
    private Account acc;

    public AccoutOptionsPage(Controller controller, AccountListing accountListing){
        this.controller = controller;
        this.accountListing = accountListing;
        this.acc = this.accountListing.getAccountById(this.controller.getAccountId());
        optionPage = new VBox(10);
        optionPage.getStyleClass().add("option_page");
        initializeOptions();
    }

    private void initializeOptions() {
        Label loginLabel = new Label("Zmień login:");
        TextField loginField = new TextField();
        Button changeLoginButton = new Button("Zmień");
        changeLoginButton.setOnAction(e -> changeLogin(loginField.getText()));

        Label passwordLabel = new Label("Zmień hasło:");
        TextField passwordField = new TextField();
        Button changePasswordButton = new Button("Zmień");
        changePasswordButton.setOnAction(e -> changePassword(passwordField.getText()));

        Label emailLabel = new Label("Zmień e-mail:");
        TextField emailField = new TextField();
        Button changeEmailButton = new Button("Zmień");
        changeEmailButton.setOnAction(e -> changeEmail(emailField.getText()));

        Label phoneLabel = new Label("Zmień numer telefonu:");
        TextField phoneField = new TextField();
        Button changePhoneButton = new Button("Zmień");
        changePhoneButton.setOnAction(e -> changePhone(phoneField.getText()));

        int ballance = 100;
        Label points = new Label("Points: " + ballance);
        points.getStyleClass().add("ballance");

        optionPage.getChildren().addAll(
            loginLabel, loginField, changeLoginButton,
            passwordLabel, passwordField, changePasswordButton,
            emailLabel, emailField, changeEmailButton,
            phoneLabel, phoneField, changePhoneButton, points
        );
    }

    private void changeLogin(String newLogin) {
        acc.setLogin(newLogin);
    }

    private void changePassword(String newPassword) {
        acc.setPassword(newPassword);
    }

    private void changeEmail(String newEmail) {
        acc.setEmail(newEmail);
    }

    private void changePhone(String newPhone) {
        acc.setPhoneNumber(newPhone);
    }

    public VBox getOptionContainer(){
        return optionPage;
    }
}
