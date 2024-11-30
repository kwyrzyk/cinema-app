package com.example;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SellFoodPage implements Page {

    private final VBox content = new VBox(); // Kontener na dynamiczną zawartość

    @Override
    public VBox getPage() {
        VBox sidebar = createSidebar(); // Sidebar z przyciskami
        VBox contentContainer = createContent(); // Główna zawartość strony

        HBox mainLayout = new HBox(0, sidebar, contentContainer);
        mainLayout.getStyleClass().add("content");

        VBox finalLayout = new VBox(0, mainLayout);
        finalLayout.getStyleClass().add("newpage");

        return finalLayout;
    }

    private VBox createSidebar() {

        Button snacksButton = new Button("Snacks");
        Button drinksButton = new Button("Drinks");

        snacksButton.setOnAction(e -> displaySnacks());

        drinksButton.setOnAction(e -> displayDrinks());

        VBox sidebar = new VBox(10, snacksButton, drinksButton);
        sidebar.getStyleClass().add("sidebar");

        return sidebar;
    }

    private VBox createContent() {
        Label label = new Label("What you want");
        content.getChildren().add(label); // Domyślna zawartość
        content.setSpacing(10);
        content.getStyleClass().add("content");

        VBox contentContainer = new VBox(10, content);
        contentContainer.getStyleClass().add("content");

        return contentContainer;
    }

    private void displaySnacks() {
        // Czyszczenie dynamicznej zawartości
        content.getChildren().clear();

        // Dodanie zawartości z klasy Snacks
        Snacks snacksPage = new Snacks();
        content.getChildren().add(snacksPage.getSnacksContainer());
    }

    private void displayDrinks() {
        // Czyszczenie dynamicznej zawartości
        content.getChildren().clear();

        Drinks drinksPage = new Drinks();
        content.getChildren().add(drinksPage.getDrinksContainer());
    }
}
