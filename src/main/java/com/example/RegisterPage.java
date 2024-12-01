package com.example;

import com.example.database.AccountRepository;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class RegisterPage {
    private VBox registerContainer;

    private TextField usernameField;
    private TextField emailField;
    private TextField phoneField;
    private PasswordField passwordField;
    private PasswordField repeatPasswordField; 

    public RegisterPage() {
        usernameField = new TextField();
        usernameField.setPromptText("Username");

        emailField = new TextField();
        emailField.setPromptText("Email");

        phoneField = new TextField();
        phoneField.setPromptText("Phone Number");

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        repeatPasswordField = new PasswordField();
        repeatPasswordField.setPromptText("Repeat Password");

        Button submitButton = new Button("Register");
        submitButton.setOnAction(e -> handleRegister(
            usernameField.getText(),
            emailField.getText(),
            phoneField.getText(),
            passwordField.getText(),
            repeatPasswordField.getText()
        ));

        registerContainer = new VBox(10);
        registerContainer.getChildren().addAll(usernameField, emailField, phoneField, passwordField, repeatPasswordField, submitButton);
        registerContainer.getStyleClass().add("content");
    }

    public VBox getRegisterContainer() {
        return registerContainer;
    }

    private void handleRegister(String username, String email, String phoneNumber, String password, String repeatPassword) {
         if (username.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Username cannot be empty.");
            return;
        }

        if (email.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Email cannot be empty.");
            return;
        }

        if (!isValidEmail(email)) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Please enter a valid email address.");
            return;
        }

        if(!isValidPhoneNumber(phoneNumber)){
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Please enter a valid phone number.");
            return;
        }

        if (password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Password cannot be empty.");
            return;
        }

        if (!password.equals(repeatPassword)) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Passwords do not match.");
            return;
        }
        System.out.println("Registering user: " + username + " with password: " + password);
        usernameField.clear();
        emailField.clear();
        phoneField.clear();
        passwordField.clear();
        repeatPasswordField.clear();

        AccountRepository.addAccount(username, password, email, phoneNumber);

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Registration Successful");
        alert.setHeaderText(null); // No header text
        alert.setContentText("Your registration was completed successfully!");

        // Show the alert and wait for the user to close it
        alert.showAndWait();
    }

    private boolean isValidEmail(String email) {
        // Prosta walidacja e-maila. Możesz użyć bardziej zaawansowanej walidacji regex.
        return email.contains("@") && email.contains(".");
    }
    

    private boolean isValidPhoneNumber(String phoneNumber) {
        // Check if the phone number is null or empty
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return false;
        }
        // Regular expression for exactly 9 digits
        String regex = "\\d{9}";
        // Check if the phone number matches the pattern
        return phoneNumber.matches(regex);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
