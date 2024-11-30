package com.example;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class LoginPage {
    private VBox loginContainer;

    public LoginPage() {
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

        System.out.println("Registering user: " + username + " with password: " + password);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
