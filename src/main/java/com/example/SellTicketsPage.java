package com.example;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class SellTicketsPage {

    public VBox getPage() {
        Label label = new Label("Welcome to the Sell Tickets Page");
        // Add other functionality for selling tickets here, e.g., form inputs

        VBox layout = new VBox(10, label);
        return layout;
    }
}

