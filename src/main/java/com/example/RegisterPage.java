package com.example;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class RegisterPage {
    private VBox registerContainer;

    public RegisterPage() {
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        PasswordField repeatPasswordField = new PasswordField();
        repeatPasswordField.setPromptText("Repeat Password");

        Button submitButton = new Button("Register");
        submitButton.setOnAction(e -> handleRegister(
            usernameField.getText(),
            emailField.getText(),
            passwordField.getText(),
            repeatPasswordField.getText()
        ));

        registerContainer = new VBox(10);
        registerContainer.getChildren().addAll(usernameField, emailField, passwordField, repeatPasswordField, submitButton);
        registerContainer.getStyleClass().add("content");
    }

    public VBox getRegisterContainer() {
        return registerContainer;
    }

    private void handleRegister(String username, String email, String password, String repeatPassword) {
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

        if (password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Password cannot be empty.");
            return;
        }

        if (!password.equals(repeatPassword)) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Passwords do not match.");
            return;
        }
        System.out.println("Registering user: " + username + " with password: " + password);
    }

    private boolean isValidEmail(String email) {
        // Prosta walidacja e-maila. Możesz użyć bardziej zaawansowanej walidacji regex.
        return email.contains("@") && email.contains(".");
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
