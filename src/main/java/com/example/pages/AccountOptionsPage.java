package com.example.pages;

import com.example.Controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * Represents the account options page of the application.
 */
public class AccountOptionsPage implements Page{
    private final VBox optionsBox = new VBox();
    private final Controller controller;

    /**
     * Constructs an AccountOptionsPage with the specified controller.
     * @param controller the controller of the application
     */
    public AccountOptionsPage(Controller controller){
        this.controller = controller;
        createContent();
    }

    /**
     * Returns the VBox containing the account options page content.
     * @return the VBox containing the account options page content
     */
    public VBox getPage(){
        return optionsBox;
    }

    /**
     * Creates the content of the account options page.
     */
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

    /**
     * Changes the login of the account.
     * @param newLogin the new login to be set
     */
    private void changeLogin(String newLogin) {
        if (newLogin.isEmpty()) {
            Controller.showAlert(Alert.AlertType.ERROR, "Validation Error", "Username cannot be empty.");
        } else {
            controller.getAccountListing().changeLogin(controller.getAccountId(), newLogin);
        }
    }

    /**
     * Changes the password of the account.
     * @param newPassword the new password to be set
     */
    private void changePassword(String newPassword) {
        if (newPassword.isEmpty()) {
            Controller.showAlert(Alert.AlertType.ERROR, "Validation Error", "Username cannot be empty.");
        } else {
            controller.getAccountListing().changePassword(controller.getAccountId(), newPassword);
        }
    }

    /**
     * Changes the email of the account.
     * @param newEmail the new email to be set
     */
    private void changeEmail(String newEmail) {
        if (!RegisterPage.isValidEmail(newEmail)) {
            Controller.showAlert(Alert.AlertType.ERROR, "Validation Error", "Please enter a valid email address.");
        } else {
            controller.getAccountListing().changeEmail(controller.getAccountId(), newEmail);
        }
    }

    /**
     * Changes the phone number of the account.
     * @param newPhoneNumber the new phone number to be set
     */
    private void changePhone(String newPhoneNumber) {
        if(!RegisterPage.isValidPhoneNumber(newPhoneNumber)){
            Controller.showAlert(Alert.AlertType.ERROR, "Validation Error", "Please enter a valid phone number.");
        } else {
            controller.getAccountListing().changePhoneNumber(controller.getAccountId(), newPhoneNumber);
        }
    }
}
