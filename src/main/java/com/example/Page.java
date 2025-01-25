package com.example;

import javafx.scene.layout.VBox;

/**
 * Interface representing a page in the application.
 */
public interface Page {
    /**
     * Returns the page content.
     * 
     * @return the VBox containing the page content
     */
    VBox getPage();
}

