package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create buttons
        Button sellTicketsButton = new Button("Sell Tickets");
        Button sellFoodButton = new Button("Sell Food");
        Button accountsButton = new Button("Accounts");

        // Set actions for buttons
        sellTicketsButton.setOnAction(e -> openSellTicketsPage(primaryStage));
        sellFoodButton.setOnAction(e -> openSellFoodPage(primaryStage));
        accountsButton.setOnAction(e -> openAccountsPage(primaryStage));

        // Layout for main scene
        VBox layout = new VBox(10, sellTicketsButton, sellFoodButton, accountsButton);
        Scene mainScene = new Scene(layout, 300, 200);
        
        // Set up primary stage
        primaryStage.setTitle("Main Application");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    private void openSellTicketsPage(Stage primaryStage) {
        Scene sellTicketsScene = new Scene(new SellTicketsPage().getPage(), 300, 200);
        primaryStage.setScene(sellTicketsScene);
    }

    private void openSellFoodPage(Stage primaryStage) {
        Scene sellFoodScene = new Scene(new SellFoodPage().getPage(), 300, 200);
        primaryStage.setScene(sellFoodScene);
    }

    private void openAccountsPage(Stage primaryStage) {
        Scene accountsScene = new Scene(new AccountsPage().getPage(), 300, 200);
        primaryStage.setScene(accountsScene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}