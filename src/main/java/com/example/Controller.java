package com.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Controller {
    private Stage stage;
    private Scene scene;
    private RepertoirePage repertoirePage = new RepertoirePage();

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
        String buttonId = clickedOption.getId();
        switch (buttonId){
            case "categoryBtn" -> repertoirePage.toggleCategoryList();
            case "snacksBtn" -> {
                Snacks snacksPage = new Snacks();
                container.getChildren().clear();
                container.getChildren().add(snacksPage.getSnacksContainer());
            }
            case "drinksBtn" -> {
                Drinks drinksPage = new Drinks();
                container.getChildren().clear();
                container.getChildren().add(drinksPage.getDrinksContainer());
            }
            case "registerBtn" -> {
                RegisterPage registerPage = new RegisterPage();
                container.getChildren().clear(); // Czyścimy kontener, jeśli to konieczne
                container.getChildren().add(registerPage.getRegisterContainer());
                break;
            }
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
            ListView<String> categoryList = repertoirePage.getCategoryList();
            categoryList.setId("categoryList");
            optionsBar.getChildren().add(categoryList);
            addOption("Type", "typeBtn", this::handleOptionClick);
            addOption("Other", "otherBtn", this::handleOptionClick);
            container.getChildren().add(repertoirePage.getPage());
        } else if (buttonId.equals("ticketsBtn")) {
            addOption("Buy", "buyBtn", this::handleOptionClick);
            addOption("Change", "changeBtn", this::handleOptionClick);
        } else if (buttonId.equals("foodBtn")) {
            addOption("Snacks", "snacksBtn", this::handleOptionClick);
            addOption("Drinks", "drinksBtn", this::handleOptionClick);
        } else if (buttonId.equals("accountsBtn")) {
            addOption("Sign", "signBtn", this::handleOptionClick);
            addOption("Register", "registerBtn", this::handleOptionClick);
        } else {
            System.err.println("Unknown button clicked: " + buttonId);
        }
    }

}
