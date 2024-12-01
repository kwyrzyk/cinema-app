package com.example;

import java.util.List;

import com.example.database.DatabaseManager;
import com.example.database.DrinkRepository;
import com.example.database.FoodRepository;
import com.example.database.db_classes.Basket;
import com.example.database.db_classes.Drink;
import com.example.database.db_classes.Food;
import com.example.listing.DrinksListing;
import com.example.listing.FoodListing;
import com.example.listing.AccountListing;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Controller {
    private Stage stage;
    private Scene scene;
    private RepertoirePage repertoirePage = new RepertoirePage();

    private AccountListing accountsListing = new AccountListing();
    private LoginPage loginPage = new LoginPage(accountsListing);
    private RegisterPage registerPage = new RegisterPage(accountsListing);
    private DrinksListing drinksListing = new DrinksListing();
    public Basket basket = new Basket();
    private FoodListing foodListing = new FoodListing();
    private final List<Food> listOfFoods = foodListing.getFoods();
    private final List<Drink> listOfDrinks = drinksListing.getDrinks();

    @FXML
    private Label label;
    @FXML
    private VBox sideBar;
    @FXML
    private VBox optionsBar;
    @FXML
    private VBox newSidebar;
    @FXML
    private VBox container;
    @FXML
    private ListView<String> categoryList;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    // @FXML
    // public void handleSideBar() {
    //     try {
    //         label.setText("Kliknięto");
    //         // Załadowanie nowego pliku FXML
    //         FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("/../../layout.fxml"));
    //         VBox root = loader.load();

            
    //         // Przekazanie Stage do nowego kontrolera
    //         Controller newController = loader.getController();
    //         newController.setStage(stage);

    //         // Zmiana zawartości sceny
    //         stage.getScene().setRoot(root);

    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }

    private void addOption(String optionText, String btnId, javafx.event.EventHandler<ActionEvent> action) {
        Button optionButton = new Button(optionText);
        optionButton.setId(btnId);
        optionButton.setOnAction(action);
        optionsBar.getChildren().add(optionButton);
    }

    @FXML
    private void handleOptionClick(ActionEvent event) {
        Button clickedOption = (Button) event.getSource();
        String buttonId = clickedOption.getId();
        switch (buttonId){
            case "categoryBtn" -> repertoirePage.toggleCategoryList();
            case "snacksBtn" -> {
                FoodMenu foodMenu = new FoodMenu(new FoodRepository(new DatabaseManager()), basket, listOfFoods);
                container.getChildren().clear();
                container.getChildren().add(foodMenu.getFoodListVBox());
            }
            case "drinksBtn" -> {
                DrinksMenu drinkMenu = new DrinksMenu(new DrinkRepository(new DatabaseManager()), basket, listOfDrinks);
                container.getChildren().clear();
                container.getChildren().add(drinkMenu.getDrinkListVBox());
            }
            case "signBtn"-> {
                container.getChildren().clear();
                container.getChildren().add(loginPage.getLoginContainer());
            }
            case "registerBtn" -> {
                container.getChildren().clear();
                container.getChildren().add(registerPage.getRegisterContainer());
                break;
            }
            case "payBtn" -> {
                if (basket.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Payment Failed");
                    alert.setHeaderText(null);
                    alert.setContentText("Your basket is empty. Please add items before paying.");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Payment Successful");
                    alert.setHeaderText(null);
                    alert.setContentText("Your payment was processed successfully!");
                    alert.showAndWait();
                    basket.clear(); // Opróżnij koszyk po udanej płatności
                    BasketPage backetPage = new BasketPage(basket);
                    container.getChildren().clear();
                    container.getChildren().add(backetPage.getPage());
                }
                break;
            }
            case "removeAllBtn" ->{
                if (basket.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Remove Failed");
                    alert.setHeaderText(null);
                    alert.setContentText("Your basket is empty. Please add items before removing.");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Remove Successful");
                    alert.setHeaderText(null);
                    alert.setContentText("Your busket is now empty!");
                    alert.showAndWait();
                    basket.clear();
                    BasketPage backetPage = new BasketPage(basket);
                    container.getChildren().clear();
                    container.getChildren().add(backetPage.getPage());
                }
                break;
            }
        }
    }
    @FXML
    private void handleSidebarClick(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String buttonId = clickedButton.getId(); // Pobierz fx:id przycisku

        optionsBar.getChildren().clear();
        container.getChildren().clear();

        if (buttonId.equals("repertoireBtn")) {
            addOption("Category", "categoryBtn", this::handleOptionClick);
            ListView<String> categoryList = repertoirePage.getCategoryList();
            categoryList.setId("categoryList");
            optionsBar.getChildren().add(categoryList);
            addOption("Type", "typeBtn", this::handleOptionClick);
            addOption("Other", "otherBtn", this::handleOptionClick);
            container.getChildren().add(repertoirePage.getPage());
        } else if (buttonId.equals("ticketsBtn")) {
            addOption("Buy", "buyBtn", this::handleOptionClick);
            addOption("Change", "changeBtn", this::handleOptionClick);
        } else if (buttonId.equals("foodBtn")) {
            addOption("Snacks", "snacksBtn", this::handleOptionClick);
            addOption("Drinks", "drinksBtn", this::handleOptionClick);
        } else if (buttonId.equals("accountsBtn")) {
            addOption("Sign", "signBtn", this::handleOptionClick);
            addOption("Register", "registerBtn", this::handleOptionClick);
        } else if (buttonId.equals("basketBtn")) {
            addOption("Pay", "payBtn", this::handleOptionClick);
            addOption("Remove All", "removeAllBtn", this::handleOptionClick);
            BasketPage backetPage = new BasketPage(basket);
            container.getChildren().add(backetPage.getPage());
        } else {
            System.err.println("Unknown button clicked: " + buttonId);
        }
    }

}
