package com.example;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.*;

import com.example.database.db_classes.Account;
import com.example.listing.AccountListing;

public class AccountOptionsPage {
    private final VBox optionsBox = new VBox();
    private final AccountListing accountListing;
    private final Controller controller;
    private Account userAccount;

    public AccountOptionsPage(Controller controller, AccountListing accountListing){
        this.controller = controller;
        this.accountListing = accountListing;
        this.userAccount = accountListing.getAccountById(this.controller.getAccountId());
        createContent();
    }

    public VBox getPage(){
        return optionsBox;
    }
    private void createContent() {
        Label pageTitle = new Label("Account options");
        pageTitle.getStyleClass().add("page-title");

        Label loginLabel = new Label("Change login:");
        loginLabel.getStyleClass().add("item");
        TextField loginField = new TextField();
        loginField.getStyleClass().add("input-field");
        loginField.setPromptText("Login");
        Button changeLoginButton = new Button("Change");
        changeLoginButton.getStyleClass().add("btn");
        changeLoginButton.setOnAction(e -> changeLogin(loginField.getText()));

        Label passwordLabel = new Label("Change password:");
        passwordLabel.getStyleClass().add("item");
        PasswordField passwordField = new PasswordField();
        passwordField.getStyleClass().add("input-field");
        passwordField.setPromptText("Password");
        Button changePasswordButton = new Button("Change");
        changePasswordButton.getStyleClass().add("btn");
        changePasswordButton.setOnAction(e -> changePassword(passwordField.getText()));

        Label emailLabel = new Label("Change e-mail:");
        emailLabel.getStyleClass().add("item");
        TextField emailField = new TextField();
        emailField.getStyleClass().add("input-field");
        emailField.setPromptText("Email");
        Button changeEmailButton = new Button("Change");
        changeEmailButton.getStyleClass().add("btn");
        changeEmailButton.setOnAction(e -> changeEmail(emailField.getText()));

        Label phoneLabel = new Label("Change phone number:");
        phoneLabel.getStyleClass().add("item");
        TextField phoneField = new TextField();
        phoneField.getStyleClass().add("input-field");
        phoneField.setPromptText("Phone number");
        Button changePhoneButton = new Button("Change");
        changePhoneButton.getStyleClass().add("btn");
        changePhoneButton.setOnAction(e -> changePhone(phoneField.getText()));

        optionsBox.getChildren().addAll(
            pageTitle, loginLabel, loginField, changeLoginButton,
            passwordLabel, passwordField, changePasswordButton,
            emailLabel, emailField, changeEmailButton,
            phoneLabel, phoneField, changePhoneButton
        );

        optionsBox.getStyleClass().add("page");
    }

    private void changeLogin(String newLogin) {
        if (newLogin.isEmpty()) {
            Controller.showAlert(Alert.AlertType.ERROR, "Validation Error", "Username cannot be empty.");
        } else {
            userAccount.setLogin(newLogin);
        }
    }

    private void changePassword(String newPassword) {
        if (newPassword.isEmpty()) {
            Controller.showAlert(Alert.AlertType.ERROR, "Validation Error", "Username cannot be empty.");
        } else {
            userAccount.setPassword(newPassword);
        }
    }

    private void changeEmail(String newEmail) {
        if (!RegisterPage.isValidEmail(newEmail)) {
            Controller.showAlert(Alert.AlertType.ERROR, "Validation Error", "Please enter a valid email address.");
        } else {
            userAccount.setEmail(newEmail);
        }
    }

    private void changePhone(String newPhoneNumber) {
        if(!RegisterPage.isValidPhoneNumber(newPhoneNumber)){
            Controller.showAlert(Alert.AlertType.ERROR, "Validation Error", "Please enter a valid phone number.");
        } else {
            userAccount.setPhoneNumber(newPhoneNumber);
        }
    }
}
