package com.example.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class ErrorHandler {
    private static final Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

    public static void handle(Exception exception) {
        if (exception instanceof RecoverableDatabaseException) {
            // Log the recoverable error
            logger.warn("Recoverable error: {}", exception.getMessage());
            
            // Notify the user
            showAlertToUser("Something went wrong while processing your request. Please try again.");
        } else if (exception instanceof NonRecoverableDatabaseException) {
            // Log the critical error
            logger.error("Non-recoverable error: {}", exception.getMessage(), exception);
            
            // Show an error dialog or terminate gracefully
            showCriticalErrorToUser("A serious error occurred. Please contact support.");
            System.exit(1); // Optional: Stop the application
        } else {
            // Handle unexpected exceptions (fallback)
            logger.error("Unexpected error: {}", exception.getMessage(), exception);
            showAlertToUser("An unexpected error occurred. Please try again.");
        }
    }

    public static void showAlertToUser(String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



    public static void showCriticalErrorToUser(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}