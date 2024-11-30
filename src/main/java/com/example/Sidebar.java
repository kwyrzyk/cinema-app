package com.example;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Sidebar {
    private final VBox sidebar;

    public Sidebar(Stage stage, MainApp mainApp) {
        Button repertoireButton = new Button("Repertoire");
        Button ticketsButton = new Button("Tickets");
        Button snacksButton = new Button("Food");
        Button accountsButton = new Button("Accounts");

        repertoireButton.setOnAction(e -> mainApp.loadPage(stage, getSidebar(), new RepertoirePage()));
        ticketsButton.setOnAction(e -> mainApp.loadPage(stage, getSidebar(), new SellTicketsPage()));
        snacksButton.setOnAction(e -> mainApp.loadPage(stage, getSidebar(), new SellFoodPage()));
        accountsButton.setOnAction(e -> mainApp.loadPage(stage, getSidebar(), new AccountsPage()));

        sidebar = new VBox(10, repertoireButton, ticketsButton, snacksButton, accountsButton);
        sidebar.getStyleClass().add("sidebar");
    }

    public VBox getSidebar() {
        return sidebar;
    }
}
