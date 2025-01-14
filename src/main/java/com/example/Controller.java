package com.example;

import java.sql.Connection;
import java.util.List;

import com.example.database.AccountRepository;
import com.example.database.DatabaseManager;
import com.example.database.RewardsRepository;
import com.example.database.ShowingRepository;
import com.example.database.TagsRepository;
import com.example.database.db_classes.Basket;
import com.example.database.db_classes.Discount;
import com.example.database.db_classes.Drink;
import com.example.database.db_classes.Film;
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
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Controller {
    public DatabaseManager databaseManager = new DatabaseManager();
    private Stage stage;
    private Scene scene;
    private FilmListing filmListing = new FilmListing(databaseManager);
    private int accountId = 0;
    public Boolean modifyTicketMode = false;
    public PricedItem modifyingTicket;


    private final List<Integer> listOfPegiValues = List.of(3, 7, 12, 16, 18);

    public OrderHistoryListing orderHistoryListing = new OrderHistoryListing(databaseManager);
    private AccountListing accountsListing = new AccountListing(databaseManager);
    
    private LoginPage loginPage = new LoginPage( this, accountsListing);
    private RegisterPage registerPage = new RegisterPage(this, accountsListing);
    private FoodListing foodListing = new FoodListing(databaseManager);
    private DrinksListing drinksListing = new DrinksListing(databaseManager);
    private DiscountListing discountListing = new DiscountListing(databaseManager);
    private AccountOptionsPage accountOptionsPage = new AccountOptionsPage(this, accountsListing);
    private OrderHistoryPage orderHistoryPage;
    private BalancePage balancePage;
    public SeatsPage seatsPage;
    
    public Basket basket = new Basket();
    private final List<Film> listOfFilms = filmListing.getFilms();
    private final List<Food> listOfFoods = foodListing.getFoods();
    private final List<Drink> listOfDrinks = drinksListing.getDrinks();
    private final List<Discount> listOfDiscounts = discountListing.getDiscounts();
    private final List<PointsReward> listOfRewards = RewardsRepository.getAllPointsRewards(databaseManager.getConnection());
    private final List<Tag> listOfTags = TagsRepository.getAllTags(databaseManager.getConnection());
    

    public RepertoirePage repertoirePage = new RepertoirePage(this);
    private VBox categoryList = repertoirePage.getCategories();
    private VBox pegisList = repertoirePage.getPegis();
   
    
    @FXML
    private Label label;
    @FXML
    private VBox sideBar;
    @FXML
    public VBox optionsBar;
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

    public List<Film> getListOfFilms(){
        return this.listOfFilms;
    }

    public List<Food> getListOfFoods(){
        return this.listOfFoods;
    }

    public List<Drink> getListOfDrinks(){
        return this.listOfDrinks;
    }

    public List<Discount> getListOfDiscounts(){
        return this.listOfDiscounts;
    }

    public List<PointsReward> getListOfRewards(){
        return this.listOfRewards;
    }

    public List<Tag> getListOfTags(){
        return this.listOfTags;
    }

    public List<Integer> getListOfPegiValues(){
        return this.listOfPegiValues;
    }

    public AccountListing getAccountListing(){
        return this.accountsListing;
    }

    public FilmListing getFilmListing(){
        return this.filmListing;
    }

    public void addOption(String optionText, String btnId, javafx.event.EventHandler<ActionEvent> action) {
        Button optionButton = new Button(optionText);
        optionButton.setId(btnId);
        optionButton.setOnAction(action);
        optionButton.getStyleClass().add("options-bar-btn");
        optionsBar.getChildren().add(optionButton);
    }

    @FXML
    public void handleOptionClick(ActionEvent event) {
        Button clickedOption = (Button) event.getSource();
        String buttonId = clickedOption.getId();
        switch (buttonId){
            case "categoryBtn" -> toggleCategoryList();
            case "pegiBtn" -> togglePegisList();
            case "snacksBtn" -> {
                FoodsPage foodPage = new FoodsPage(this);
                container.getChildren().clear();
                container.getChildren().add(foodPage.getPage());
            }
            case "drinksBtn" -> {
                DrinksPage drinksPage = new DrinksPage(this);
                container.getChildren().clear();
                container.getChildren().add(drinksPage.getPage());
            }
            case "discountsBtn" ->{
                DiscountsPage discountsPage = new DiscountsPage(this);
                container.getChildren().clear();
                container.getChildren().add(discountsPage.getPage());
            }
            case "pointsRewardsBtn" ->{
                if (accountId == 0){
                    container.getChildren().clear();
                    container.getChildren().add(loginPage.getPage());
                    showAlert(AlertType.WARNING, "You are not loged in","You need to log in first.");
                } else {
                    PointsRewardsPage pointsRewardsPage = new PointsRewardsPage(this);
                    container.getChildren().clear();
                    container.getChildren().add(pointsRewardsPage.getPage());
                }
            }
            case "signBtn"-> {
                container.getChildren().clear();
                container.getChildren().add(loginPage.getPage());
            }
            case "registerBtn" -> {
                container.getChildren().clear();
                container.getChildren().add(registerPage.getPage());
                break;
            }
            case "optionsBtn" -> {
                if (accountId == 0){
                    container.getChildren().clear();
                    container.getChildren().add(loginPage.getPage());
                    showAlert(Alert.AlertType.WARNING, "You are not loged in", "You need to log in first.");
                } else {
                    container.getChildren().clear();
                    container.getChildren().add(accountOptionsPage.getPage());
                }
            }
            case "orderHistoryBtn" -> {
                if (accountId == 0){
                    container.getChildren().clear();
                    container.getChildren().add(loginPage.getPage());
                    showAlert(AlertType.WARNING, "You are not loged in","You need to log in first.");
                } else {
                    this.orderHistoryPage = new OrderHistoryPage(this, orderHistoryListing.getOrders());
                    container.getChildren().clear();
                    container.getChildren().add(orderHistoryPage.getPage());
                }
            }
            case "balanceBtn" -> {
                if (accountId == 0){
                    container.getChildren().clear();
                    container.getChildren().add(loginPage.getPage());
                    showAlert(AlertType.WARNING, "You are not loged in","You need to log in first.");
                } else {
                    this.balancePage = new BalancePage(this);
                    container.getChildren().clear();
                    container.getChildren().add(balancePage.getPage());
                }
            }
            case "reserveRoomBtn" -> {
                if (accountId == 0){
                    container.getChildren().clear();
                    container.getChildren().add(loginPage.getPage());
                    showAlert(AlertType.WARNING, "You are not loged in","You need to log in first.");
                } else {
                    RoomReservationPage reservationPage = new RoomReservationPage(this);
                    container.getChildren().clear();
                    container.getChildren().add(reservationPage.getPage());
                }
            }
            case "reservationsBtn" -> {
                if (accountId == 0){
                    container.getChildren().clear();
                    container.getChildren().add(loginPage.getPage());
                    showAlert(AlertType.WARNING, "You are not loged in","You need to log in first.");
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
                    showAlert(AlertType.WARNING, "Payment Failed","Your basket is empty. Please add items before paying.");
                        break;  

                    case 1:
                        PricedItem firstItem = basket.getItems().get(0);
            
                        Discount matchingDiscount = discountListing.getActiveDiscounts().stream()
                                .filter(discount -> (firstItem.isFood() && discount.containsFoodItemById(firstItem.getId()))
                                                 || (firstItem.isDrink() && discount.containsDrinkItemById(firstItem.getId())))
                                .findFirst()
                                .orElse(null);
            
                        if (matchingDiscount != null) {
                            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                            confirmationAlert.setTitle("Suggested discount");
                            confirmationAlert.setHeaderText(null);
                            confirmationAlert.setContentText("Do you want to change the product to a set:\n" + matchingDiscount.toString() + "?");
                            String cssfile = Controller.class.getResource("/css/styles.css").toExternalForm();
                            confirmationAlert.getDialogPane().getStylesheets().add(cssfile);

                            
                            ButtonType buttonYes = new ButtonType("Yes");
                            ButtonType buttonNo = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                            confirmationAlert.getButtonTypes().setAll(buttonYes, buttonNo);
            
                            confirmationAlert.showAndWait().ifPresent(response -> {
                                if (response == buttonYes) {
                                    goToPayment[0] = false;
            
                                    basket.clear();
                                    basket.addItem(new PricedItem(matchingDiscount));
                                    
                                    showAlert(AlertType.WARNING, "Zamiana na zestaw","Produkt został zamieniony na zestaw!");
            
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
                        if (accountId != 0) {
                            showAlert(AlertType.WARNING, "Payment Successful","Your payment was processed successfully!\nAdded " + newLoyaltyPoints + " loyalty points.");
                        } else {
                            showAlert(AlertType.WARNING, "Payment Successful","Your payment was processed successfully!");
                        }
                        alert.showAndWait();
                        if(accountId != 0){
                            AccountRepository.addOrder(accountId, basket, databaseManager.getConnection());
                            AccountRepository.addLoyaltyPoints(accountId, newLoyaltyPoints, databaseManager.getConnection());
                            accountsListing.updateAccount(accountId);
                            orderHistoryListing.loadOrderHistory(accountId);
                        }
                        for ( PricedItem item : basket.getItems()) {    
                            if (item.isTicket()) {
                                ShowingRepository.reserveSeat(item.getId(), databaseManager.getConnection());
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
                    showAlert(AlertType.WARNING, "Remove Failed","Your basket is empty. Please add items before removing.");
                } else {
                    showAlert(AlertType.WARNING, "Remove Successful","Your basket is now empty!");
                    basket.clear();
                    BasketPage basketPage = new BasketPage(basket);
                    container.getChildren().clear();
                    container.getChildren().add(basketPage.getPage());
                    filmListing.update();    
                }
            }
            case "modifyTicketBtn" ->{
                if (basket.containsTickets()) {
                    ModifyBasketPage modifyBasketPage = new ModifyBasketPage(this);
                    container.getChildren().clear();
                    container.getChildren().add(modifyBasketPage.getPage());
                } else {
                    showAlert(AlertType.WARNING, "There is no ticket in the basket","You do not need to modify the ticket.");
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
            categoryList.setManaged(false);
            categoryList.setVisible(false);
            optionsBar.getChildren().add(categoryList);
            addOption("Pegi", "pegiBtn", this::handleOptionClick);
            pegisList.setManaged(false);
            pegisList.setVisible(false);
            optionsBar.getChildren().add(pegisList);
            container.getChildren().add(repertoirePage.getPage());
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

    public static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        String cssfile = Controller.class.getResource("/css/styles.css").toExternalForm();
        alert.getDialogPane().getStylesheets().add(cssfile);
        alert.showAndWait();
    }

    private void toggleCategoryList() {
        if (categoryList.isVisible()) {
            categoryList.setVisible(false);
            categoryList.setManaged(false);
        } else {
            categoryList.setVisible(true);
            categoryList.setManaged(true);
        }
    }

    private void togglePegisList() {
        if (pegisList.isVisible()) {
            pegisList.setVisible(false);
            pegisList.setManaged(false);
        } else {
            pegisList.setVisible(true);
            pegisList.setManaged(true);
        }
    }

}
