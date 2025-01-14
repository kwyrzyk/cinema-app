package com.example;

import com.example.listing.AccountListing;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class RegisterPage implements Page{
    private final Controller controller;
    private AccountListing accountListing;
    private final VBox registerBox = new VBox();

    private TextField usernameField;
    private TextField emailField;
    private TextField phoneField;
    private PasswordField passwordField;
    private PasswordField repeatPasswordField; 

    public RegisterPage(Controller controller, AccountListing accountListing) {
        this.accountListing = accountListing;
        this.controller = controller;
        createContent();
    }

    public VBox getPage() {
        return registerBox;
    }

    private void createContent() {
        Label pageTitle = new Label("Sign up");
        pageTitle.getStyleClass().add("page-title");

        usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.getStyleClass().add("input-field");

        emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.getStyleClass().add("input-field");

        phoneField = new TextField();
        phoneField.setPromptText("Phone number");
        phoneField.getStyleClass().add("input-field");

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.getStyleClass().add("input-field");

        repeatPasswordField = new PasswordField();
        repeatPasswordField.setPromptText("Repeat Password");
        repeatPasswordField.getStyleClass().add("input-field");

        Button submitButton = new Button("Register");
        submitButton.getStyleClass().add("btn");
        submitButton.setOnAction(e -> handleRegister(
            usernameField.getText(),
            emailField.getText(),
            phoneField.getText(),
            passwordField.getText(),
            repeatPasswordField.getText()
        ));

        registerBox.getChildren().addAll(pageTitle, usernameField, emailField, phoneField, passwordField, repeatPasswordField, submitButton);
        registerBox.getStyleClass().add("page");
    }

    private void handleRegister(String username, String email, String phoneNumber, String password, String repeatPassword) {
         if (username.isEmpty()) {
            Controller.showAlert(Alert.AlertType.ERROR, "Validation Error", "Username cannot be empty.");
            return;
        }

        if (email.isEmpty()) {
            Controller.showAlert(Alert.AlertType.ERROR, "Validation Error", "Email cannot be empty.");
            return;
        }

        if (!isValidEmail(email)) {
            Controller.showAlert(Alert.AlertType.ERROR, "Validation Error", "Please enter a valid email address.");
            return;
        }

        if(!isValidPhoneNumber(phoneNumber)){
            Controller.showAlert(Alert.AlertType.ERROR, "Validation Error", "Please enter a valid phone number.");
            return;
        }

        if (password.isEmpty()) {
            Controller.showAlert(Alert.AlertType.ERROR, "Validation Error", "Password cannot be empty.");
            return;
        }

        if (!password.equals(repeatPassword)) {
            Controller.showAlert(Alert.AlertType.ERROR, "Validation Error", "Passwords do not match.");
            return;
        }

        usernameField.clear();
        emailField.clear();
        phoneField.clear();
        passwordField.clear();
        repeatPasswordField.clear();

        accountListing.addAccount(username, password, email, phoneNumber);

        Controller.showAlert(AlertType.INFORMATION, "Registration Successful", "Your registration was completed successfully!");
    }

    public static boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return false;
        }
        String regex = "\\d{9}";

        return phoneNumber.matches(regex);
    }
}
