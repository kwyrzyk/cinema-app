package com.example;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class AccountsPage {

    public VBox getPage() {
        Label label = new Label("Welcome to the Accounts Page");
        // Add functionality related to accounts here, e.g., showing balances

        VBox layout = new VBox(10, label);
        return layout;
    }
}
