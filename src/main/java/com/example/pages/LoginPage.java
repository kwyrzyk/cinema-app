package com.example.pages;

import com.example.Controller;
import com.example.database.db_classes.Account;
import com.example.listing.AccountListing;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * Represents the login page of the application.
 */
public class LoginPage implements Page {
    private final Controller controller;
    private AccountListing accountListing;
    private final VBox loginBox = new VBox();

    private TextField usernameField;
    private PasswordField passwordField;

    /**
     * Constructs a LoginPage with the specified controller and account listing.
     * @param controller the controller of the application
     * @param accountListing the account listing to be used
     */
    public LoginPage(Controller controller, AccountListing accountListing) {
        this.controller = controller;
        this.accountListing = accountListing;
        this.accountListing.loadAllAccounts();

        createContent();
    }

    /**
     * Returns the VBox containing the login page content.
     * @return the VBox containing the login page content
     */
    public VBox getPage() {
        return loginBox;
    }

    /**
     * Creates the content of the login page.
     */
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

    /**
     * Handles the registration process.
     * @param username the username entered by the user
     * @param password the password entered by the user
     */
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
            controller.login(account.getIdAccount());
            controller.orderHistoryListing.loadOrderHistory(account.getIdAccount());
            Controller.showAlert(
                AlertType.INFORMATION,
                "Login Successful", 
                "Your registration for account - " + username + " was succesful.\n" +
                "your loyalty points are " + account.getLoyaltyPoints()
            );
            usernameField.clear();
            passwordField.clear();
        } else {
            Controller.showAlert(AlertType.ERROR, "Unsuccessful login","There is no account with matching login and password");
        }
    }
}
