package com.example;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class SellFoodPage implements Page {

    @Override
    public VBox getPage() {
        VBox sidebar = createSidebar();

        VBox content = createContent();

        HBox mainLayout = new HBox(0, sidebar, content);
        mainLayout.getStyleClass().add("content");

        VBox finalLayout = new VBox(0, mainLayout);
        finalLayout.getStyleClass().add("newpage");

        return finalLayout;
    }

    private VBox createSidebar() {
        Button snacks = new Button("Snacks");
        Button drinks = new Button("Drinks");

        VBox sidebar = new VBox(10, snacks, drinks);
        sidebar.getStyleClass().add("sidebar");

        return sidebar;
    }

    private VBox createContent() {
        Label label = new Label("What you want");
        VBox content = new VBox(10, label);
        content.getStyleClass().add("content");

        return content;
    }

    private Region createSeparator() {
        Region separator = new Region();
        separator.getStyleClass().add("separator");

        return separator;
    }
}
