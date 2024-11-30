package com.example;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class MainScene {
    private final int height = 900;
    private final int width = 800;
    private Layout layout;

    public MainScene() {
        // this.layout = layout;
        Button repertoireButton = new Button("Repertoire");
        Button ticketsButton = new Button("Tickets");
        Button snacksButton = new Button("Food");
        Button accountsButton = new Button("Accounts");
        
        repertoireButton.setOnAction(e -> loadPage(new RepertoirePage()));
        // ticketsButton.setOnAction(e -> loadPage(primaryStage, sideBar, new SellTicketsPage()));
        // snacksButton.setOnAction(e -> loadPage(primaryStage, sideBar, new SellFoodPage()));
        // accountsButton.setOnAction(e -> loadPage(primaryStage, sideBar, new AccountsPage()));

        VBox sideBar = new VBox(10, repertoireButton, ticketsButton, snacksButton, accountsButton);
        sideBar.getStyleClass().add("sidebar");
        
        VBox optionsBar = new VBox();
        
        VBox container = new VBox(10, new Label("Welcome in our cinema!"));
        container.getStyleClass().add("content");
        
        this.layout = new Layout(sideBar, optionsBar, container);

    }

    public HBox getLayout() {
        return this.layout.getLayout();
    }

    public Scene getScene() {
        return new Scene(this.layout.getLayout(), width, height);
    }

    public void loadPage(Page newPage) {
        this.layout.changeContainer(newPage.getPage());
    }
}
