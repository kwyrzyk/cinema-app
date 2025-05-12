package com.example;

import java.util.List;

import com.example.database.AccountRepository;
import com.example.database.DatabaseManager;
import com.example.database.ShowingRepository;
import com.example.database.db_classes.Basket;
import com.example.database.db_classes.Discount;
import com.example.database.db_classes.Drink;
import com.example.database.db_classes.Film;
import com.example.database.db_classes.Food;
import com.example.database.db_classes.PointsReward;
import com.example.database.db_classes.PricedItem;
import com.example.database.db_classes.Tag;
import com.example.exceptions.ErrorHandler;
import com.example.listing.AccountListing;
import com.example.listing.DiscountListing;
import com.example.listing.DrinksListing;
import com.example.listing.FilmListing;
import com.example.listing.FoodListing;
import com.example.listing.OrderHistoryListing;
import com.example.listing.RewardsListing;
import com.example.listing.TagListing;
import com.example.pages.AccountOptionsPage;
import com.example.pages.BalancePage;
import com.example.pages.BasketPage;
import com.example.pages.DiscountsPage;
import com.example.pages.DrinksPage;
import com.example.pages.FoodsPage;
import com.example.pages.LoginPage;
import com.example.pages.ModifyBasketPage;
import com.example.pages.OrderHistoryPage;
import com.example.pages.Page;
import com.example.pages.PointsRewardsPage;
import com.example.pages.RegisterPage;
import com.example.pages.RepertoirePage;
import com.example.pages.ReservationsPage;
import com.example.pages.RoomReservationPage;
import com.example.pages.SeatsPage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Controller class for managing the application.
 */
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
    private AccountOptionsPage accountOptionsPage = new AccountOptionsPage(this);
    private RewardsListing rewardsListing = new RewardsListing(databaseManager.getConnection());
    private TagListing tagListing = new TagListing(databaseManager.getConnection());
    private OrderHistoryPage orderHistoryPage;
    private BalancePage balancePage;
    public SeatsPage seatsPage;
    
    public Basket basket = new Basket();
    private final List<Film> listOfFilms = filmListing.getFilms();
    private final List<Food> listOfFoods = foodListing.getFoods();
    private final List<Drink> listOfDrinks = drinksListing.getDrinks();
    private final List<Discount> listOfDiscounts = discountListing.getDiscounts();
    private final List<PointsReward> listOfRewards = rewardsListing.getRewards();
    private final List<Tag> listOfTags = tagListing.getTags();
    
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

    /**
     * Sets the stage for the application.
     * @param stage the stage to be set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Sets the scene for the application.
     * @param scene the scene to be set
     */
    public void setScene(Scene scene) {
        this.scene = scene;
    }

    /**
     * Returns the stage of the application.
     * @return the stage of the application
     */
    public Stage getStage(){
        return this.stage;
    }

    /**
     * Returns the scene of the application.
     * @return the scene of the application
     */
    public Scene getScene(){
        return this.scene;
    }

    /**
     * Returns the options bar of the application.
     * @return the options bar of the application
     */
    public VBox getOptionsBar(){
        return this.optionsBar;
    }

    /**
     * Logs in the user with the specified ID.
     * @param Id the ID of the user to log in
     */
    public void login(int Id){
        this.accountId = Id;
    }

    /**
     * Returns the ID of the logged-in account.
     * @return the ID of the logged-in account
     */
    public int getAccountId(){
        return this.accountId;
    }

    /**
     * Returns the list of films.
     * @return the list of films
     */
    public List<Film> getListOfFilms(){
        return this.listOfFilms;
    }

    /**
     * Returns the list of foods.
     * @return the list of foods
     */
    public List<Food> getListOfFoods(){
        return this.listOfFoods;
    }

    /**
     * Returns the list of drinks.
     * @return the list of drinks
     */
    public List<Drink> getListOfDrinks(){
        return this.listOfDrinks;
    }

    /**
     * Returns the list of discounts.
     * @return the list of discounts
     */
    public List<Discount> getListOfDiscounts(){
        return this.listOfDiscounts;
    }

    /**
     * Returns the list of rewards.
     * @return the list of rewards
     */
    public List<PointsReward> getListOfRewards(){
        return this.listOfRewards;
    }

    /**
     * Returns the list of tags.
     * @return the list of tags
     */
    public List<Tag> getListOfTags(){
        return this.listOfTags;
    }

    /**
     * Returns the list of PEGI values.
     * @return the list of PEGI values
     */
    public List<Integer> getListOfPegiValues(){
        return this.listOfPegiValues;
    }

    /**
     * Returns the account listing.
     * @return the account listing
     */
    public AccountListing getAccountListing(){
        return this.accountsListing;
    }

    /**
     * Returns the film listing.
     * @return the film listing
     */
    public FilmListing getFilmListing(){
        return this.filmListing;
    }

    /**
     * Adds an option to the options bar.
     * @param optionText the text of the option
     * @param btnId the ID of the button
     * @param action the action to be performed when the button is clicked
     */
    public void addOption(String optionText, String btnId, javafx.event.EventHandler<ActionEvent> action) {
        Button optionButton = new Button(optionText);
        optionButton.setId(btnId);
        optionButton.setOnAction(action);
        optionButton.getStyleClass().add("options-bar-btn");
        optionsBar.getChildren().add(optionButton);
    }

    /**
     * Modifies the container with the specified page.
     * @param newPage the new page to be set in the container
     */
    public void modifyContainer(Page newPage){
        container.getChildren().clear();
        container.getChildren().add(newPage.getPage());
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
                modifyContainer(foodPage);
            }
            case "drinksBtn" -> {
                DrinksPage drinksPage = new DrinksPage(this);
                modifyContainer(drinksPage);
            }
            case "discountsBtn" ->{
                DiscountsPage discountsPage = new DiscountsPage(this);
                modifyContainer(discountsPage);
            }
            case "pointsRewardsBtn" ->{
                if (accountId == 0){
                    modifyContainer(loginPage);
                    showAlert(AlertType.WARNING, "You are not loged in","You need to log in first.");
                } else {
                    PointsRewardsPage pointsRewardsPage = new PointsRewardsPage(this);
                    modifyContainer(pointsRewardsPage);
                }
            }
            case "signBtn"-> {
                modifyContainer(loginPage);
            }
            case "registerBtn" -> {
                modifyContainer(registerPage);
                break;
            }
            case "optionsBtn" -> {
                if (accountId == 0){
                    modifyContainer(loginPage);
                    showAlert(Alert.AlertType.WARNING, "You are not loged in", "You need to log in first.");
                } else {
                    modifyContainer(accountOptionsPage);
                }
            }
            case "orderHistoryBtn" -> {
                if (accountId == 0){
                    modifyContainer(loginPage);
                    showAlert(AlertType.WARNING, "You are not loged in","You need to log in first.");
                } else {
                    this.orderHistoryPage = new OrderHistoryPage(this, orderHistoryListing.getOrders());
                    modifyContainer(orderHistoryPage);
                }
            }
            case "balanceBtn" -> {
                if (accountId == 0){
                    modifyContainer(loginPage);
                    showAlert(AlertType.WARNING, "You are not loged in","You need to log in first.");
                } else {
                    this.balancePage = new BalancePage(this);
                    modifyContainer(balancePage);
                }
            }
            case "reserveRoomBtn" -> {
                if (accountId == 0){
                    modifyContainer(loginPage);
                    showAlert(AlertType.WARNING, "You are not loged in","You need to log in first.");
                } else {
                    RoomReservationPage reservationPage = new RoomReservationPage(this);
                    modifyContainer(reservationPage);
                }
            }
            case "reservationsBtn" -> {
                if (accountId == 0){
                    modifyContainer(loginPage);
                    showAlert(AlertType.WARNING, "You are not loged in","You need to log in first.");
                } else {
                    ReservationsPage reservationsPage = new ReservationsPage(this);
                    modifyContainer(reservationsPage);
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
                                    modifyContainer(backetPage);
                                }
                            });
            
                            if (!goToPayment[0]) { // Sprawdzamy zmodyfikowaną wartość
                                break;
                            }
                        }
                        
                    default:
                        int newLoyaltyPoints = (int) (basket.getTotalPrice());
                        if (accountId != 0) {
                            try{
                            AccountRepository.addOrder(accountId, basket, databaseManager.getConnection());
                            AccountRepository.addLoyaltyPoints(accountId, newLoyaltyPoints, databaseManager.getConnection());
                            accountsListing.updateAccount(accountId);
                            orderHistoryListing.loadOrderHistory(accountId);
                            showAlert(AlertType.WARNING, "Payment Successful","Your payment was processed successfully!\nAdded " + newLoyaltyPoints + " loyalty points.");
                            } catch(Exception e){
                                ErrorHandler.handle(e);
                            }

                        } else {
                            showAlert(AlertType.WARNING, "Payment Successful","Your payment was processed successfully!");
                        }
                        for ( PricedItem item : basket.getItems()) {    
                            if (item.isTicket()) {
                                try{
                                ShowingRepository.reserveSeat(item.getId(), databaseManager.getConnection());
                                } catch (Exception e){
                                    ErrorHandler.handle(e);
                                }
                            }
                        }
                        filmListing.updateModified();
                        basket.clear(); // Opróżnij koszyk po udanej płatności
                        BasketPage backetPage = new BasketPage(basket);
                        modifyContainer(backetPage);
                        
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
                    modifyContainer(basketPage);
                    filmListing.updateModified();    
                }
            }
            case "modifyTicketBtn" ->{
                if (basket.containsTickets()) {
                    ModifyBasketPage modifyBasketPage = new ModifyBasketPage(this);
                    modifyContainer(modifyBasketPage);
                } else {
                    showAlert(AlertType.WARNING, "There is no ticket in the basket","You do not need to modify the ticket.");
                }
            }
            case "cancelBtn" ->{
                modifyTicketMode = false;
                modifyingTicket = null;
                modifyContainer(new BasketPage(basket));
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

        switch (buttonId) {
            case "repertoireBtn" -> {
                addOption("Category", "categoryBtn", this::handleOptionClick);
                categoryList.setManaged(false);
                categoryList.setVisible(false);
                optionsBar.getChildren().add(categoryList);
                addOption("Pegi", "pegiBtn", this::handleOptionClick);
                pegisList.setManaged(false);
                pegisList.setVisible(false);
                optionsBar.getChildren().add(pegisList);
                container.getChildren().add(repertoirePage.getPage());
            }
            case "roomsBtn" -> {
                RoomReservationPage reservationPage = new RoomReservationPage(this);
                container.getChildren().add(reservationPage.getPage());
            }
            case "foodBtn" -> {
                addOption("Snacks", "snacksBtn", this::handleOptionClick);
                addOption("Drinks", "drinksBtn", this::handleOptionClick);
                addOption("Discounts", "discountsBtn", this::handleOptionClick);
                addOption("Points rewards", "pointsRewardsBtn", this::handleOptionClick);
            }
            case "accountsBtn" -> {
                addOption("Sign", "signBtn", this::handleOptionClick);
                addOption("Register", "registerBtn", this::handleOptionClick);
                addOption("Options", "optionsBtn", this::handleOptionClick);
                addOption("Order history", "orderHistoryBtn", this::handleOptionClick);
                addOption("Balance", "balanceBtn", this::handleOptionClick);
                addOption("Reserve room", "reserveRoomBtn", this::handleOptionClick);
                addOption("Reservations", "reservationsBtn", this::handleOptionClick);
            }
            case "basketBtn" -> {
                addOption("Pay", "payBtn", this::handleOptionClick);
                addOption("Remove All", "removeAllBtn", this::handleOptionClick);
                addOption("Modify ticket", "modifyTicketBtn", this::handleOptionClick);
                BasketPage basketPage = new BasketPage(basket);
                container.getChildren().add(basketPage.getPage());
            }
            default -> System.err.println("Unknown button clicked: " + buttonId);
        }
    }

    /**
     * Shows an alert with the specified type, title, and message.
     * @param alertType the type of the alert
     * @param title the title of the alert
     * @param message the message of the alert
     */
    public static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        String cssfile = Controller.class.getResource("/css/styles.css").toExternalForm();
        alert.getDialogPane().getStylesheets().add(cssfile);
        alert.showAndWait();
    }

    /**
     * Toggles the visibility of the category list.
     */
    private void toggleCategoryList() {
        if (categoryList.isVisible()) {
            categoryList.setVisible(false);
            categoryList.setManaged(false);
        } else {
            categoryList.setVisible(true);
            categoryList.setManaged(true);
        }
    }

    /**
     * Toggles the visibility of the PEGI list.
     */
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
