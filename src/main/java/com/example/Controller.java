package com.example;

import java.util.List;

import com.example.database.AccountRepository;
import com.example.database.DatabaseManager;
import com.example.database.DrinkRepository;
import com.example.database.FoodRepository;
import com.example.database.RewardsRepository;
import com.example.database.ShowingRepository;
import com.example.database.TagsRepository;
import com.example.database.db_classes.Basket;
import com.example.database.db_classes.Discount;
import com.example.database.db_classes.Drink;
import com.example.database.db_classes.Food;
import com.example.database.db_classes.PointsReward;
import com.example.database.db_classes.PricedItem;
import com.example.database.db_classes.Tag;
import com.example.listing.AccountListing;
import com.example.listing.DiscountListing;
import com.example.listing.DrinksListing;
import com.example.listing.FilmListing;
import com.example.listing.FoodListing;
import com.example.listing.OrderHistoryListing;

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
    public Boolean modifyTicketMode = false;
    public PricedItem modifyingTicket;

    private final List<Tag> listOfTags = TagsRepository.getAllTags();
    private final List<Integer> listOfPegi = List.of(3, 7, 12, 16, 18);
    public RepertoirePage repertoirePage = new RepertoirePage(this, filmListing);

    public OrderHistoryListing orderHistoryListing = new OrderHistoryListing();
    private AccountListing accountsListing = new AccountListing();
    
    private LoginPage loginPage = new LoginPage( this, accountsListing);
    private RegisterPage registerPage = new RegisterPage(accountsListing);
    private DrinksListing drinksListing = new DrinksListing();
    private DiscountListing discountListing = new DiscountListing();
    private AccoutOptionsPage accountOptionsPage = new AccoutOptionsPage(this, accountsListing);
    private OrderHistoryPage orderHistoryPage;
    private BalancePage balancePage;
    public SeatsPage seatsPage;
    
    public Basket basket = new Basket();
    private FoodListing foodListing = new FoodListing();
    private final List<Food> listOfFoods = foodListing.getFoods();
    private final List<Drink> listOfDrinks = drinksListing.getDrinks();
    private final List<PointsReward> listOfRewards = RewardsRepository.getAllPointsRewards();
   
    @FXML
    private Label label;
    @FXML
    private VBox sideBar;
    @FXML
    public VBox optionsBar;
    @FXML
    private VBox newSidebar;
    @FXML
    public VBox container;

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

    public VBox getOptionsBar(){
        return this.optionsBar;
    }

    public void login(int Id){
        this.accountId = Id;
    }

    public int getAccountId(){
        return this.accountId;
    }

    public List<Tag> getListOfTags(){
        return this.listOfTags;
    }

    public List<Integer> getListOfPegi(){
        return this.listOfPegi;
    }

    public AccountListing getAccountListing(){
        return this.accountsListing;
    }

    public void addOption(String optionText, String btnId, javafx.event.EventHandler<ActionEvent> action) {
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
            case "repertoireBackBtn" ->{    
                this.container.getChildren().clear();
                container.getChildren().add(this.repertoirePage.getBackPage());
            }
            case "pegiBtn" -> repertoirePage.togglePegiList();
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
                DiscountsMenu discountsMenu = new DiscountsMenu(basket, discountListing.getActiveDiscounts());
                container.getChildren().clear();
                container.getChildren().add(discountsMenu.getDiscountListVBox());
            }
            case "pointsRewardsBtn" ->{
                if (accountId == 0){
                    container.getChildren().clear();
                    container.getChildren().add(loginPage.getLoginContainer());
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("You are not loged in");
                    alert.setHeaderText(null);
                    alert.setContentText("You need to log in first.");
                    alert.showAndWait();
                } else {
                    PointsRewardsMenu pointsRewardsMenu = new PointsRewardsMenu(this, listOfRewards);
                    container.getChildren().clear();
                    container.getChildren().add(pointsRewardsMenu.getRewardListVBox());
                }
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
            case "balanceBtn" -> {
                if (accountId == 0){
                    container.getChildren().clear();
                    container.getChildren().add(loginPage.getLoginContainer());
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("You are not loged in");
                    alert.setHeaderText(null);
                    alert.setContentText("You need to log in first.");
                    alert.showAndWait();
                    System.out.println("Balance");
                } else {
                    this.balancePage = new BalancePage(this);
                    container.getChildren().clear();
                    container.getChildren().add(balancePage.getPage());
                }
            }
            case "reserveRoomBtn" -> {
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
                    RoomReservationPage reservationPage = new RoomReservationPage(this);
                    container.getChildren().clear();
                    container.getChildren().add(reservationPage.getPage());
                }
            }
            case "reservationsBtn" -> {
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
                    ReservationsPage reservationsPage = new ReservationsPage(this);
                    container.getChildren().clear();
                    container.getChildren().add(reservationsPage.getPage());
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
                        Discount matchingDiscount = discountListing.getActiveDiscounts().stream()
                                .filter(discount -> (firstItem.isFood() && discount.containsFoodItemById(firstItem.getId()))
                                                 || (firstItem.isDrink() && discount.containsDrinkItemById(firstItem.getId())))
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
                        int newLoyaltyPoints = (int) (basket.getTotalPrice());
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Payment Successful");
                        alert.setHeaderText(null);
                        if (accountId != 0) {
                            alert.setContentText("Your payment was processed successfully!\nAdded " + newLoyaltyPoints + " loyalty points.");
                        } else {
                            alert.setContentText("Your payment was processed successfully!");
                        }
                        alert.showAndWait();
                        if(accountId != 0){
                            AccountRepository.addOrder(accountId, basket);
                            AccountRepository.addLoyaltyPoints(accountId, newLoyaltyPoints);
                            accountsListing.updateAccount(accountId);
                            orderHistoryListing.loadOrderHistory(accountId);
                        }
                        for ( PricedItem item : basket.getItems()) {    
                            if (item.isTicket()) {
                                ShowingRepository.reserveSeat(item.getId());
                            }
                        }
                        filmListing.update();
                        basket.clear(); // Opróżnij koszyk po udanej płatności
                        BasketPage backetPage = new BasketPage(basket);
                        container.getChildren().clear();
                        container.getChildren().add(backetPage.getPage());
                        
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
                    alert.setContentText("Your basket is now empty!");
                    alert.showAndWait();
                    basket.clear();
                    BasketPage basketPage = new BasketPage(basket);
                    container.getChildren().clear();
                    container.getChildren().add(basketPage.getPage());
                    filmListing.update();    
                }
            }
            case "modifyTicketBtn" ->{
                if (basket.containsTickets()) {
                    ModifyBasketPage modifyBasketPage = new ModifyBasketPage(this, basket);
                    container.getChildren().clear();
                    container.getChildren().add(modifyBasketPage.getPage());
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("There is no ticket in the basket");
                    alert.setHeaderText(null);
                    alert.setContentText("You do not need to modify the ticket.");
                    alert.showAndWait();
                }
            }
            case "cancelBtn" ->{
                modifyTicketMode = false;
                modifyingTicket = null;
                container.getChildren().clear();
                container.getChildren().add(new BasketPage(basket).getPage());
                optionsBar.getChildren().clear();
                addOption("Pay", "payBtn", this::handleOptionClick);
                addOption("Remove All", "removeAllBtn", this::handleOptionClick);
                addOption("Modify ticket", "modifyTicketBtn", this::handleOptionClick);
            }
        }
    }
    @FXML
    public void handleSidebarClick(ActionEvent event) {
        if (modifyTicketMode) { return;}

        Button clickedButton = (Button) event.getSource();
        String buttonId = clickedButton.getId();

        optionsBar.getChildren().clear();
        container.getChildren().clear();

        if(buttonId.equals("repertoireBtn")) {
            addOption("Category", "categoryBtn", this::handleOptionClick);
            ListView<Tag> categoryListView = repertoirePage.getCategoryList();
            categoryListView.setId("categoryList");
            optionsBar.getChildren().add(categoryListView);
            addOption("Pegi", "pegiBtn", this::handleOptionClick);
            ListView<Integer> listPegi = repertoirePage.getPegiList();
            listPegi.setId("pegiList");
            optionsBar.getChildren().add(listPegi);
            addOption("Type", "typeBtn", this::handleOptionClick);
            addOption("Other", "otherBtn", this::handleOptionClick);
            container.getChildren().add(repertoirePage.getBackPage());
        } else if (buttonId.equals("roomsBtn")) {
            RoomReservationPage reservationPage = new RoomReservationPage(this);
            container.getChildren().add(reservationPage.getPage());
        } else if (buttonId.equals("foodBtn")) {
            addOption("Snacks", "snacksBtn", this::handleOptionClick);
            addOption("Drinks", "drinksBtn", this::handleOptionClick);
            addOption("Discounts", "discountsBtn", this::handleOptionClick);
            addOption("Points rewards", "pointsRewardsBtn", this::handleOptionClick);
        } else if (buttonId.equals("accountsBtn")) {
            addOption("Sign", "signBtn", this::handleOptionClick);
            addOption("Register", "registerBtn", this::handleOptionClick);
            addOption("Options", "optionsBtn", this::handleOptionClick);
            addOption("Order history", "orderHistoryBtn", this::handleOptionClick);
            addOption("Balance", "balanceBtn", this::handleOptionClick);
            addOption("Reserve room", "reserveRoomBtn", this::handleOptionClick);
            addOption("Reservations", "reservationsBtn", this::handleOptionClick);
        } else if (buttonId.equals("basketBtn")) {
            addOption("Pay", "payBtn", this::handleOptionClick);
            addOption("Remove All", "removeAllBtn", this::handleOptionClick);
            addOption("Modify ticket", "modifyTicketBtn", this::handleOptionClick);
            BasketPage basketPage = new BasketPage(basket);
            container.getChildren().add(basketPage.getPage());
        } else {
            System.err.println("Unknown button clicked: " + buttonId);
        }
    }

}
