package com.example;

import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Controller {
    private Stage stage;
    private Scene scene;

    @FXML
    private Label label;


    @FXML
    private VBox sideBar;
    @FXML
    private VBox optionsBar;
    @FXML
    private VBox newSidebar;
    @FXML
    private VBox container;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    // @FXML
    // public void handleSideBar() {
    //     try {
    //         label.setText("Kliknięto");
    //         // Załadowanie nowego pliku FXML
    //         FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("/../../layout.fxml"));
    //         VBox root = loader.load();

            
    //         // Przekazanie Stage do nowego kontrolera
    //         Controller newController = loader.getController();
    //         newController.setStage(stage);

    //         // Zmiana zawartości sceny
    //         stage.getScene().setRoot(root);

    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }

    private void addOption(String optionText, javafx.event.EventHandler<ActionEvent> action) {
        Button optionButton = new Button(optionText);
        optionButton.setOnAction(action);
        optionsBar.getChildren().add(optionButton);
    }

    @FXML
    private void handleOptionClick(ActionEvent event) {
        Button clickedOption = (Button) event.getSource();
        String optionText = clickedOption.getText();

        // Zmień zawartość container w zależności od opcji
        container.getChildren().clear();
        container.getChildren().add(new Label("Selected: " + optionText));
    }

    @FXML
    private void handleSidebarClick(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String buttonId = clickedButton.getId(); // Pobierz fx:id przycisku

        optionsBar.getChildren().clear();

        if (buttonId.equals("repertoireBtn")) {
            addOption("Add Movie", this::handleOptionClick);
            addOption("Remove Movie", this::handleOptionClick);
        } else if (buttonId.equals("ticketsBtn")) {
            addOption("Sell", this::handleOptionClick);
            addOption("Return", this::handleOptionClick);
        } else if (buttonId.equals("ticketsBtn")) {
            addOption("Order Snacks", this::handleOptionClick);
            addOption("Restock", this::handleOptionClick);
        } else if (buttonId.equals("ticketsBtn")) {
            addOption("Create Account", this::handleOptionClick);
            addOption("Remove Account", this::handleOptionClick);
        } else {
            System.err.println("Unknown button clicked: " + buttonId);
        }
    }

}
