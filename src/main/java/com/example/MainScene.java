package com.example;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Class representing the main scene of the application.
 */
public class MainScene {
    private final int height = 900;
    private final int width = 800;
    private Layout layout;

    /**
     * Constructor for MainScene.
     */
    public MainScene() {
        Button repertoireButton = new Button("Repertoire");
        Button ticketsButton = new Button("Tickets");
        Button snacksButton = new Button("Food");
        Button accountsButton = new Button("Accounts");
        
        VBox sideBar = new VBox(10, repertoireButton, ticketsButton, snacksButton, accountsButton);
        sideBar.getStyleClass().add("sidebar");
        
        VBox optionsBar = new VBox();
        
        VBox container = new VBox(10, new Label("Welcome in our cinema!"));
        container.getStyleClass().add("content");
        
        this.layout = new Layout(sideBar, optionsBar, container);
    }

    /**
     * Returns the layout of the main scene.
     * 
     * @return the HBox containing the layout
     */
    public HBox getLayout() {
        return this.layout.getLayout();
    }

    /**
     * Returns the scene of the main scene.
     * 
     * @return the Scene object
     */
    public Scene getScene() {
        return new Scene(this.layout.getLayout(), width, height);
    }

    /**
     * Loads a new page into the main scene.
     * 
     * @param newPage the new page to be loaded
     */
    public void loadPage(Page newPage) {
        this.layout.changeContainer(newPage.getPage());
    }
}
