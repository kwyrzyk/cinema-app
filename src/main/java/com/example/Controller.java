package com.example;

import javafx.scene.control.ListView;
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
    @FXML
    private ListView<String> categoryList;

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

    private void addOption(String optionText, String btnId, javafx.event.EventHandler<ActionEvent> action) {
        Button optionButton = new Button(optionText);
        optionButton.setId(btnId);
        optionButton.setOnAction(action);
        optionsBar.getChildren().add(optionButton);
    }

    @FXML
    private void handleOptionClick(ActionEvent event) {
        Button clickedOption = (Button) event.getSource();
        String optionText = clickedOption.getText();
        String buttonId = clickedOption.getId();
        System.out.println(buttonId);

        // Zmień zawartość container w zależności od opcji
        container.getChildren().clear();
        if (buttonId.equals("categoryBtn")) {
            categoryList.setVisible(false);
        }
        if (buttonId.equals("snacksBtn")) {
            Snacks snacksPage = new Snacks();
            container.getChildren().add(snacksPage.getSnacksContainer());
        } else if (buttonId.equals("drinksBtn")) {
            Drinks drinksPage = new Drinks();
            container.getChildren().add(drinksPage.getDrinksContainer());
        } 
    }

    @FXML
    private void handleSidebarClick(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String buttonId = clickedButton.getId(); // Pobierz fx:id przycisku

        optionsBar.getChildren().clear();
        container.getChildren().clear();

        if (buttonId.equals("repertoireBtn")) {
            addOption("Category", "categoryBtn", this::handleOptionClick);
            RepertoirePage repertoirePage = new RepertoirePage();
            ListView<String> list = repertoirePage.getCategoryList();
            list.setId("categoryList");
            optionsBar.getChildren().add(list);
            addOption("Type", "typeBtn", this::handleOptionClick);
            addOption("Other", "otherBtn", this::handleOptionClick);
            Movie sessionListGenerator = new Movie();
            container.getChildren().add(sessionListGenerator.getSessionListVBox());
        } else if (buttonId.equals("ticketsBtn")) {
            addOption("Buy", "buyBtn", this::handleOptionClick);
            addOption("Change", "changeBtn", this::handleOptionClick);
        } else if (buttonId.equals("foodBtn")) {
            addOption("Snacks", "snacksBtn", this::handleOptionClick);
            addOption("Drinks", "drinksBtn", this::handleOptionClick);
        } else if (buttonId.equals("accountsBtn")) {
            addOption("Sign", "signBtn", this::handleOptionClick);
        } else {
            System.err.println("Unknown button clicked: " + buttonId);
        }
    }

}
