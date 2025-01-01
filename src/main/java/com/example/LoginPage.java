package com.example;

import com.example.database.db_classes.Account;
import com.example.listing.AccountListing;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class LoginPage {
    private VBox loginContainer;
    private AccountListing accountListing;
    private final Controller controller;

    public LoginPage(Controller controller, AccountListing accountListing) {
        this.controller = controller;
        this.accountListing = accountListing;
        this.accountListing.loadAllAccounts();
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button submitButton = new Button("Login");
        submitButton.setOnAction(e -> handleRegister(
            usernameField.getText(),
            passwordField.getText()
        ));

        loginContainer = new VBox(10);
        loginContainer.getChildren().addAll(usernameField, passwordField, submitButton);
        loginContainer.getStyleClass().add("content");
    }

    public VBox getLoginContainer() {
        return loginContainer;
    }

    private void handleRegister(String username, String password) {
         if (username.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Username cannot be empty.");
            return;
        }

        if (password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Password cannot be empty.");
            return;
        }

        Account account = this.accountListing.getAccountByLogin(username);
        if(account == null) {showAlert(AlertType.ERROR, "Unsuccessful login","There is no account with this login");}
        if(account.getPassword().equals(password)){
            showAlert(AlertType.INFORMATION, "Login Successful", "Your registration for account - " + username + "was succesful.\nYour email: "
            + account.getEmail() + " your phone number is " + account.getPhoneNumber());
            controller.login(account.getIdAccount());
            controller.getContainer().getChildren().add(controller.accountPage.getOptionContainer());
        }else{
            showAlert(AlertType.ERROR, "Unsuccessful login","There is no account with matching login and password");
        }


    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
