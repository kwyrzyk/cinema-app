// package com.example;

// import java.util.List;

// import com.example.database.db_classes.Film;

// import javafx.fxml.FXML;
// import javafx.scene.Parent;
// import javafx.scene.control.Label;
// import javafx.scene.control.ListCell;
// import javafx.scene.control.ListView;
// import javafx.scene.layout.HBox;
// import javafx.scene.layout.VBox;
// import javafx.util.Callback;

// public class Movie {

//     private final List<Film> listOfFilms;
//     private Controller controller;

//     public Movie(Controller controller, List<Film> listOfFilms) {
//         this.controller = controller;
//         this.listOfFilms = listOfFilms;
//     }

//     public List<Film> getlistOfFilms() {
//         return this.listOfFilms;
//     }

//     public VBox getSessionListVBox() {
//         System.out.println("Movie page");
//         return getSessionListVBox(this.listOfFilms);
//     }

//     public VBox getSessionListVBox(List<Film> films) {
//         System.out.println("Movie page");
//         // Pobranie listy filmów z FilmListing
//         Label pageTitle = new Label("Repertoire");
//         pageTitle.getStyleClass().add("page-title");

//         List<Film> filmsToUse = (films != null) ? films : this.listOfFilms;
//         ListView<Film> filmListView = new ListView<>();
//         filmListView.getStyleClass().add("lists");

//         // Ustawienie listy filmów w ListView
//         filmListView.getItems().addAll(filmsToUse);

//         // Dostosowanie komórek ListView
//         filmListView.setCellFactory(new Callback<ListView<Film>, ListCell<Film>>() {
//             @Override
//             public ListCell<Film> call(ListView<Film> listView) {
//                 return new FilmListCell(controller);
//             }
//         });

//         double rowHeight = 24;
//         int itemCount = listOfFilms.size();
//         filmListView.setPrefHeight(itemCount * rowHeight);
//         VBox mainContainer = new VBox();

//         HBox searchPanel = createSearchPanel(filmListView);

//         mainContainer.getChildren().addAll(pageTitle, searchPanel, filmListView);
//         mainContainer.getStyleClass().add("page");

//         return mainContainer;
//     }

//     private HBox createSearchPanel(ListView<Film> filmListView) {
//         // Pole tekstowe do wyszukiwania
//         javafx.scene.control.TextField searchField = new javafx.scene.control.TextField();
//         searchField.setPromptText("Enter movie name...");
//         searchField.getStyleClass().add("input-field");

//         // Przycisk wyszukiwania
//         javafx.scene.control.Button searchButton = new javafx.scene.control.Button("Search");
//         searchButton.getStyleClass().add("btn");

//         searchButton.setOnAction(e -> {
//             String query = searchField.getText().toLowerCase();
//             List<Film> filteredFilms = listOfFilms.stream()
//                     .filter(film -> film.getTitle().toLowerCase().contains(query))
//                     .toList();

//             filmListView.getItems().setAll(filteredFilms);
//         });

//         HBox searchPanel = new HBox(10, searchField, searchButton);
//         searchPanel.getStyleClass().add("search-panel");

//         return searchPanel;
//     }

//     private static class FilmListCell extends ListCell<Film> {
//         private final Controller controller; // Add a reference to the controller
//         @FXML
//         VBox container;
    
//         // Constructor to receive the controller
//         public FilmListCell(Controller controller) {
//             this.controller = controller;
//         }
    
//         @Override
//         protected void updateItem(Film film, boolean empty) {
//             super.updateItem(film, empty);
    
//             if (empty || film == null) {
//                 setText(null);
//                 setGraphic(null);
//             } else {
//                 VBox content = new VBox();
//                 Label filmLabel = new Label("Title: " + film.getTitle());
//                 this.setUserData(film);
    
//                 this.setId("filmLabel");
//                 this.setOnMouseClicked(e -> {
//                     Film filmInfo = (Film) this.getUserData();
//                     System.out.println(filmInfo.getTitle());
//                     // FilmPage filmPage = new FilmPage(this.controller, filmInfo); // Use the controller
//                     Parent parent = (this.getParent()).getParent().getParent().getParent().getParent();
//                     container = (VBox) parent;
//                     container.getChildren().clear();
//                     container.getChildren().add(filmPage.getPage());
//                     System.out.println(filmInfo.getTitle());
//                 });
    
//                 content.getChildren().add(filmLabel);
//                 setGraphic(content);
//             }
//         }
//     }
    
// }
