package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MainApp extends Application {
    int height = 900;
    int width = 800;


    @Override
    public void start(Stage primaryStage) {
        // Create buttons
        Button repertoireButton = new Button("Repertoire");
        Button ticketsButton = new Button("Tickets");
        Button snacksButton = new Button("Snacks");
        Button accountsButton = new Button("Accounts");

        
        // Layout for main scene
        VBox sideBar = new VBox(10, repertoireButton, ticketsButton, snacksButton, accountsButton);
        VBox container = new VBox(10, new Label("Welcome in our cinema!"));
        HBox layout = new HBox(10, sideBar, container);
        layout.getStyleClass().add("vbox");
        Scene mainScene = new Scene(layout, width, height);
        mainScene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
        
        // Set actions for buttons
        repertoireButton.setOnAction(e -> openRepertoirePage(container));
        ticketsButton.setOnAction(e -> openTicketsPage(primaryStage, sideBar));
        snacksButton.setOnAction(e -> openSnacksPage(container));
        accountsButton.setOnAction(e -> openAccountsPage(container));
        
        // Set up primary stage
        primaryStage.setTitle("Main Application");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    private Scene createScene(HBox layout) {
        Scene scene = new Scene(layout, width, height);
        scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
        return scene;
    }

    // private void openPage(Stage primaryStage, Page page) {
    //     VBox layout = page.getPage();
    //     layout.getStyleClass().add("vbox");
    //     Scene scene = createScene(layout);
    //     primaryStage.setScene(scene);
    // }

    // private VBox getPage(Page page) {
    //     VBox layout = page.getPage();
    //     layout.getStyleClass().add("vbox");
    //     return layout;
    // }

    private void openTicketsPage(Stage stage, VBox sideBar) {
        // Scene new_scene = 
        VBox container = new SellTicketsPage().getPage();
        HBox layout = new HBox(10, sideBar, container);
        Scene new_scene = createScene(layout);
        stage.setScene(new_scene);
    }

    private void openSnacksPage(VBox container) {
        container = new SellFoodPage().getPage();
    }

    private void openAccountsPage(VBox container) {
        container = new AccountsPage().getPage();
    }

    private void openRepertoirePage(VBox container) {
        container = new RepertoirePage().getPage();
        
    }
    // private void openTicketsPage(Stage primaryStage) {
    //     openPage(primaryStage, new SellTicketsPage());
    // }

    // private void openSnacksPage(Stage primaryStage) {
    //     openPage(primaryStage, new SellFoodPage());
    // }

    // private void openAccountsPage(Stage primaryStage) {
    //     openPage(primaryStage, new AccountsPage());
    // }

    // private void openRepetoirePage(Stage primaryStage) {
    //     openPage(primaryStage, new RepeitoirePage());
    // }

    public static void main(String[] args) {
        launch(args);
    }
}