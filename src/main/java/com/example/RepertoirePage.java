package com.example;


import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class RepertoirePage implements Page {
    @Override
    public VBox getPage() {
        Button category = new Button("Category");
        Button type = new Button("Type");
        Button other = new Button("Other");

        VBox sideBar = new VBox(10, category, type, other);
        sideBar.getStyleClass().add("sidebar");

        MovieCategory sessionListGenerator = new MovieCategory();
        VBox container = sessionListGenerator.getSessionListVBox().getContainer();
        container.getStyleClass().add("content");
        
        Region separator = new Region();
        separator.getStyleClass().add("separator");
        
        HBox main = new HBox(0, sideBar, separator, container);
        main.getStyleClass().add("content");
        
        VBox layout = new VBox(0, main);
        layout.getStyleClass().add("newpage");
        return layout;
    }
}
