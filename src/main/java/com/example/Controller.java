package com.example;

import java.util.List;

import com.example.database.AccountRepository;
import com.example.database.DatabaseManager;
import com.example.database.DrinkRepository;
import com.example.database.FoodRepository;
import com.example.database.ShowingRepository;
import com.example.database.TagsRepository;
import com.example.database.db_classes.Basket;
import com.example.database.db_classes.Discount;
import com.example.database.db_classes.Drink;
import com.example.database.db_classes.Food;
import com.example.database.db_classes.Tag;
import com.example.database.db_classes.PricedItem;
import com.example.listing.DrinksListing;
import com.example.listing.FoodListing;
import com.example.listing.OrderHistoryListing;
import com.example.listing.DiscountListing;
import com.example.listing.AccountListing;
import com.example.listing.FilmListing;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Controller {
    private Stage stage;
    private Scene scene;
    private FilmListing filmListing = new FilmListing();
    private int accountId = 0;

    private RepertoirePage repertoirePage = new RepertoirePage(this, filmListing);

    public OrderHistoryListing orderHistoryListing = new OrderHistoryListing();
    private AccountListing accountsListing = new AccountListing();
    
    private LoginPage loginPage = new LoginPage( this, accountsListing);
    private RegisterPage registerPage = new RegisterPage(accountsListing);
    private DrinksListing drinksListing = new DrinksListing();
    private DiscountListing discountListing = new DiscountListing();
    private AccoutOptionsPage accountOptionsPage = new AccoutOptionsPage(this, accountsListing);
    private OrderHistoryPage orderHistoryPage;
    public SeatsPage seatsPage;

    public Basket basket = new Basket();
    private FoodListing foodListing = new FoodListing();
    private final List<Food> listOfFoods = foodListing.getFoods();
    private final List<Drink> listOfDrinks = drinksListing.getDrinks();
    private final List<Discount> listOfDiscounts = discountListing.getDiscounts();
    


    private final List<Tag> listOTags = TagsRepository.getAllTags();

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

    public Stage getStage(){
        return this.stage;
    }

    public Scene getScene(){
        return this.scene;
    }

    public void login(int Id){
        this.accountId = Id;
    }

    public int getAccountId(){
        return this.accountId;
    }

    private void addOption(String optionText, String btnId, javafx.event.EventHandler<ActionEvent> action) {
        Button optionButton = new Button(optionText);
        optionButton.setId(btnId);
        optionButton.setOnAction(action);
        optionsBar.getChildren().add(optionButton);
    }

    @FXML
    public void handleOptionClick(ActionEvent event) {
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
            case "discountsBtn" ->{
                DiscountsMenu discountsMenu = new DiscountsMenu(basket, listOfDiscounts);
                container.getChildren().clear();
                container.getChildren().add(discountsMenu.getDiscountListVBox());
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
            case "optionsBtn" -> {
                if (accountId == 0){
                    container.getChildren().clear();
                    container.getChildren().add(loginPage.getLoginContainer());
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("You are not loged in");
                    alert.setHeaderText(null);
                    alert.setContentText("You need to log in first.");
                    alert.showAndWait();
                } else {
                    container.getChildren().clear();
                    container.getChildren().add(accountOptionsPage.getOptionContainer());
                }
            }
            case "orderHistoryBtn" -> {
                if (accountId == 0){
                    container.getChildren().clear();
                    container.getChildren().add(loginPage.getLoginContainer());
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("You are not loged in");
                    alert.setHeaderText(null);
                    alert.setContentText("You need to log in first.");
                    alert.showAndWait();
                    System.out.println("Order history");
                } else {
                    this.orderHistoryPage = new OrderHistoryPage(this, orderHistoryListing.getOrders());
                    container.getChildren().clear();
                    container.getChildren().add(orderHistoryPage.getPage());
                }
            }
            case "payBtn" -> {
                int totalQuantity = basket.getTotalQuantity();
                boolean[] goToPayment = { true };

                switch (totalQuantity) {
                    case 0:
                        Alert empty_alert = new Alert(Alert.AlertType.WARNING);
                        empty_alert.setTitle("Payment Failed");
                        empty_alert.setHeaderText(null);
                        empty_alert.setContentText("Your basket is empty. Please add items before paying.");
                        empty_alert.showAndWait();
                        break;  

                    case 1:
                        PricedItem firstItem = basket.getItems().get(0);
            
                        // Sprawdzenie, czy produkt znajduje się w zestawie
                        Discount matchingDiscount = listOfDiscounts.stream()
                                .filter(discount -> (firstItem.isFood() && discount.containsFoodItemById(firstItem.getFoodId()))
                                                 || (firstItem.isDrink() && discount.containsDrinkItemById(firstItem.getDrinkId())))
                                .findFirst()
                                .orElse(null);
            
                        if (matchingDiscount != null) {
                            // Wyświetlenie alertu z pytaniem
                            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                            confirmationAlert.setTitle("Suggested discount");
                            confirmationAlert.setHeaderText(null);
                            confirmationAlert.setContentText("Do you want to change the product to a set:\n" + matchingDiscount.toString() + "?");
                            
                            ButtonType buttonYes = new ButtonType("Yes");
                            ButtonType buttonNo = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                            confirmationAlert.getButtonTypes().setAll(buttonYes, buttonNo);
            
                            // Oczekiwanie na wybór użytkownika
                            confirmationAlert.showAndWait().ifPresent(response -> {
                                if (response == buttonYes) {
                                    goToPayment[0] = false; // Modyfikacja wartości w tablicy
            
                                    // Zamiana produktu na zestaw w koszyku
                                    basket.clear(); // Usunięcie obecnych produktów z koszyka
                                    basket.addItem(new PricedItem(matchingDiscount)); // Dodanie zestawu do koszyka
                                    
                                    // Wyświetlenie informacji o sukcesie
                                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                                    successAlert.setTitle("Zamiana na zestaw");
                                    successAlert.setHeaderText(null);
                                    successAlert.setContentText("Produkt został zamieniony na zestaw!");
                                    successAlert.showAndWait();
            
                                    BasketPage backetPage = new BasketPage(basket);
                                    container.getChildren().clear();
                                    container.getChildren().add(backetPage.getPage());
                                }
                            });
            
                            if (!goToPayment[0]) { // Sprawdzamy zmodyfikowaną wartość
                                break;
                            }
                        }
                        
                    default:
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Payment Successful");
                        alert.setHeaderText(null);
                        alert.setContentText("Your payment was processed successfully!");
                        alert.showAndWait();
                        if(accountId != 0){AccountRepository.addOrder(accountId, basket);}
                        for ( PricedItem item : basket.getItems()) {    
                            if (item.isTicket()) {
                                ShowingRepository.reserveSeat(item.getTicketId());
                            }
                        }
                        filmListing.update();
                        basket.clear(); // Opróżnij koszyk po udanej płatności
                        BasketPage backetPage = new BasketPage(basket);
                        container.getChildren().clear();
                        container.getChildren().add(backetPage.getPage());
                        this.orderHistoryListing.loadOrderHistory(accountId);
                        break;  
                }
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
                    BasketPage basketPage = new BasketPage(basket);
                    container.getChildren().clear();
                    container.getChildren().add(basketPage.getPage());
                    filmListing.update();
                    
                }
            }
        }
    }
    @FXML
    public void handleSidebarClick(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String buttonId = clickedButton.getId(); // Pobierz fx:id przycisku

        optionsBar.getChildren().clear();
        container.getChildren().clear();

        if(buttonId.equals("repertoireBackBtn")){
            addOption("Category", "categoryBtn", this::handleOptionClick);
            ListView<String> categoryList = repertoirePage.getCategoryList();
            categoryList.setId("categoryList");
            optionsBar.getChildren().add(categoryList);
            addOption("Type", "typeBtn", this::handleOptionClick);
            addOption("Other", "otherBtn", this::handleOptionClick);
            this.repertoirePage = new RepertoirePage(this, this.filmListing);
            container.getChildren().add(repertoirePage.getPage());
        } else if (buttonId.equals("repertoireBtn")) {
            addOption("Category", "categoryBtn", this::handleOptionClick);
            ListView<String> categoryList = repertoirePage.getCategoryList();
            categoryList.setId("categoryList");
            optionsBar.getChildren().add(categoryList);
            addOption("Type", "typeBtn", this::handleOptionClick);
            addOption("Other", "otherBtn", this::handleOptionClick);
            this.repertoirePage = new RepertoirePage(this, this.filmListing);
            container.getChildren().add(repertoirePage.getPage());
        } else if (buttonId.equals("ticketsBtn")) {
            addOption("Buy", "buyBtn", this::handleOptionClick);
            addOption("Change", "changeBtn", this::handleOptionClick);
        } else if (buttonId.equals("foodBtn")) {
            addOption("Snacks", "snacksBtn", this::handleOptionClick);
            addOption("Drinks", "drinksBtn", this::handleOptionClick);
            addOption("Discounts", "discountsBtn", this::handleOptionClick);
        } else if (buttonId.equals("accountsBtn")) {
            addOption("Sign", "signBtn", this::handleOptionClick);
            addOption("Register", "registerBtn", this::handleOptionClick);
            addOption("Options", "optionsBtn", this::handleOptionClick);
            addOption("Order history", "orderHistoryBtn", this::handleOptionClick);
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
