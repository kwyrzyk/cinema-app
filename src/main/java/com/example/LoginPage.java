package com.example;

import com.example.database.db_classes.Account;
import com.example.listing.AccountListing;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class LoginPage implements Page {
    private final Controller controller;
    private AccountListing accountListing;
    private final VBox loginBox = new VBox();

    private TextField usernameField;
    private PasswordField passwordField;

    public LoginPage(Controller controller, AccountListing accountListing) {
        this.controller = controller;
        this.accountListing = accountListing;
        this.accountListing.loadAllAccounts();
        System.out.println("Loaded accounts: " + this.accountListing.getAllAccounts().size());

        createContent();
    }

    public VBox getPage() {
        return loginBox;
    }

    private void createContent() {
        Label pageTitle = new Label("Sign in");
        pageTitle.getStyleClass().add("page-title");

        usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.getStyleClass().add("input-field");

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.getStyleClass().add("input-field");

        Button submitButton = new Button("Login");
        submitButton.getStyleClass().add("btn");
        submitButton.setOnAction(e -> handleRegister(
            usernameField.getText(),
            passwordField.getText()
        ));

        loginBox.getChildren().addAll(pageTitle, usernameField, passwordField, submitButton);
        loginBox.getStyleClass().add("page");
    }
    private void handleRegister(String username, String password) {
         if (username.isEmpty()) {
            Controller.showAlert(Alert.AlertType.ERROR, "Validation Error", "Username cannot be empty.");
            return;
        }

        if (password.isEmpty()) {
            Controller.showAlert(Alert.AlertType.ERROR, "Validation Error", "Password cannot be empty.");
            return;
        }

        Account account = this.accountListing.getAccountByLogin(username);
        if(account == null) {
            Controller.showAlert(AlertType.ERROR, "Unsuccessful login","There is no account with this login");
            return;
        }
        if(account.getPassword().equals(password)){
            Controller.showAlert(
                AlertType.INFORMATION,
                "Login Successful", 
                "Your registration for account - " + username + "was succesful.\nYour email: " + 
                account.getEmail() + " your phone number is " + account.getPhoneNumber() + " your balance is " + account.getBalance() + 
                "your loyalty points are " + account.getLoyaltyPoints()
            );
            usernameField.clear();
            passwordField.clear();

            controller.login(account.getIdAccount());
            controller.orderHistoryListing.loadOrderHistory(account.getIdAccount());
        } else {
            Controller.showAlert(AlertType.ERROR, "Unsuccessful login","There is no account with matching login and password");
        }
    }
}
