package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {
    int height = 900;
    int width = 800;


    @Override
    public void start(Stage primaryStage) {
        // Create buttons
        Button ticketsButton = new Button("Sell Tickets");
        Button snacksButton = new Button("Sell Food");
        Button accountsButton = new Button("Accounts");
        Button repertoireButton = new Button("Repertoire");

        // Set actions for buttons
        ticketsButton.setOnAction(e -> openTicketsPage(primaryStage));
        snacksButton.setOnAction(e -> openSnacksPage(primaryStage));
        accountsButton.setOnAction(e -> openAccountsPage(primaryStage));
        repertoireButton.setOnAction(e -> openRepeitoirePage(primaryStage));

        // Layout for main scene
        VBox layout = new VBox(10, ticketsButton, snacksButton, accountsButton, repertoireButton);
        layout.getStyleClass().add("vbox");
        Scene mainScene = new Scene(layout, width, height);
        mainScene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());

        
        // Set up primary stage
        primaryStage.setTitle("Main Application");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    private Scene createScene(VBox layout) {
        Scene scene = new Scene(layout, width, height);
        scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
        return scene;
    }

    private void openPage(Stage primaryStage, Page page) {
        VBox layout = page.getPage();
        layout.getStyleClass().add("vbox");
        Scene scene = createScene(layout);
        primaryStage.setScene(scene);
    }
    
    private void openTicketsPage(Stage primaryStage) {
        openPage(primaryStage, new SellTicketsPage());
    }

    private void openSnacksPage(Stage primaryStage) {
        openPage(primaryStage, new SellFoodPage());
    }

    private void openAccountsPage(Stage primaryStage) {
        openPage(primaryStage, new AccountsPage());
    }

    private void openRepeitoirePage(Stage primaryStage) {
        openPage(primaryStage, new RepeitoirePage());
    }

    public static void main(String[] args) {
        launch(args);
    }
}