package com.example;

import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Controller {
    private Stage stage;

    @FXML
    private Label label;

    @FXML
    private VBox sideBar;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void handleRefresh() {
        try {
            // Załadowanie nowego pliku FXML
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("/../../layout.fxml"));
            VBox root = loader.load();
            
            // Przekazanie Stage do nowego kontrolera
            Controller newController = loader.getController();
            newController.setStage(stage);

            // Zmiana zawartości sceny
            stage.getScene().setRoot(root);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
