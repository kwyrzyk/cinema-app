package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create buttons
        Button repertoireButton = new Button("Repertoire");
        Button ticketButton = new Button("Tickets");
        Button snacksButton = new Button("Snacks");
        Button accountsButton = new Button("Accounts");

        // Set actions for buttons
        repertoireButton.setOnAction(e -> openRepertoirePage(primaryStage));
        ticketsButton.setOnAction(e -> openTicketsPage(primaryStage));
        snacksButton.setOnAction(e -> openSnacksPage(primaryStage));
        accountsButton.setOnAction(e -> openAccountsPage(primaryStage));

        // Layout for main scene
        VBox sideBar = new VBox(10, repertoireButton, ticketButton, snacksButton, accountsButton);
        VBox container = new VBox(10, new Label("Welcome in our cinema!"));
        HBox layout = new HBox(10, sideBar, container);
        Scene mainScene = new Scene(layout, 300, 200);
        
        // Set up primary stage
        primaryStage.setTitle("Main Application");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    private void openRepertoriePage(Stage primaryStage) {
        VBox repertoirePage = new RepertoirePage().getPage();
        container = repertoirePage;
    }

    private void openTicketsPage(Stage primaryStage) {
        VBox ticketsPage = new TicketsPage().getPage();
        container = ticketsPage;
    }

    private void openSnacksPage(Stage primaryStage) {
        VBox ticketsPage = new TicketsPage().getPage();
        container = ticketsPage;
    }

    private void openAccountsPage(Stage primaryStage) {
        VBox ticketsPage = new TicketsPage().getPage();
        container = ticketsPage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}