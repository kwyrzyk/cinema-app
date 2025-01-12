package com.example;

import com.example.database.AccountRepository;
import com.example.database.db_classes.OrderHistoryRecord;
import com.example.database.db_classes.Price;
import com.example.database.db_classes.PricedItem;
import com.example.listing.AccountListing;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderHistoryPage implements Page {
    private final VBox historyBox;         // Główne "pudełko" na widok
    private final ScrollPane scrollPane;   
    private final Controller controller;

    // Pola wyszukiwania
    private final TextField searchField;
    private final Button searchButton;

    // Przechowujemy wszystkie zamówienia (oryginalna lista)
    private final List<OrderHistoryRecord> allOrders;

    // Aktualnie wyświetlane zamówienia (po wyszukiwaniu/filtracji)
    private List<OrderHistoryRecord> displayedOrders;

    public OrderHistoryPage(Controller controller, List<OrderHistoryRecord> orders) {
        this.controller = controller;

        this.allOrders = (orders != null) ? orders : new ArrayList<>();
        this.displayedOrders = new ArrayList<>(this.allOrders);

        historyBox = new VBox(10);
        historyBox.getStyleClass().add("order-history-box");

        HBox searchBar = new HBox(10);
        searchBar.setAlignment(Pos.CENTER);

        // Pole tekstowe
        searchField = new TextField();
        searchField.setPromptText("Find in history...");

        // Przycisk "Szukaj"
        searchButton = new Button("Search");
        searchButton.setOnAction(event -> {
            String query = searchField.getText().trim().toLowerCase();
            filterOrders(query);
        });

        searchBar.getChildren().addAll(searchField, searchButton);

        // Dodajemy searchBar na górze strony
        historyBox.getChildren().add(searchBar);

        // --- Tytuł strony ---
        Label title = new Label("Order History");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        title.getStyleClass().add("order-history-title");
        historyBox.getChildren().add(title);

        // --- Wyświetlenie (pierwsze) listy zamówień ---
        updateOrdersView(displayedOrders);

        // Opakowujemy całość w ScrollPane
        scrollPane = new ScrollPane(historyBox);
        scrollPane.setFitToWidth(true);
        scrollPane.getStyleClass().add("order-history-scrollpane");
    }

    private void filterOrders(String query) {
        if (query.isEmpty()) {
            // Jeśli nie wpisano nic, pokaż wszystkie zamówienia
            displayedOrders = new ArrayList<>(allOrders);
        } else {
            // Filtrowanie w pamięci
            displayedOrders = allOrders.stream()
                .filter(order -> {
                    // Możesz tu określić, które pola zamówienia mają być przeszukiwane.
                    // Przykład: data, cena, zawartość koszyka
                    String dateStr = (order.getDate() != null) ? order.getDate().toString().toLowerCase() : "";
                    String priceStr = String.valueOf(order.getPrice()).toLowerCase();
                    String basketStr = (order.getBasket() != null) ? order.getBasket().toString().toLowerCase() : "";

                    // Czy któryś z tych stringów zawiera query?
                    return dateStr.contains(query) 
                           || priceStr.contains(query)
                           || basketStr.contains(query);
                })
                .collect(Collectors.toList());
        }

        // Aktualizujemy widok
        updateOrdersView(displayedOrders);
    }

    /**
     * Metoda do "przeładowania" widoku zamówień (czyli kasujemy stare etykiety i dodajemy nowe).
     */
    private void updateOrdersView(List<OrderHistoryRecord> ordersToDisplay) {
        // Najpierw usuwamy stare elementy zamówień z historyBox,
        // ale zostawiamy 2 elementy: searchBar i tytuł (indeksy 0 i 1).
        // Jeżeli wstawiłeś coś jeszcze, dostosuj w zależności od układu.
        while (historyBox.getChildren().size() > 2) {
            historyBox.getChildren().remove(2);
        }

        // Sprawdzamy, czy są jakiekolwiek zamówienia
        if (ordersToDisplay == null || ordersToDisplay.isEmpty()) {
            Label noOrdersLabel = new Label("No orders available.");
            noOrdersLabel.getStyleClass().add("no-orders-label");
            historyBox.getChildren().add(noOrdersLabel);
        } else {
            // Dodajemy etykiety dla każdego zamówienia
            for (OrderHistoryRecord order : ordersToDisplay.reversed()) {
                Label orderLabel = new Label(
                        "Date: " + order.getDate() + "\n" +
                        order.getBasket().toString()
                );
                orderLabel.getStyleClass().add("order-label");
                orderLabel.setWrapText(true);
                orderLabel.setMaxWidth(600);
                
                // Kliknięcie w etykietę -> alert z pytaniem o refund
                orderLabel.setOnMouseClicked(event -> {
                    int userLoyaltyPoints = controller.getAccountListing().getAccountById(controller.getAccountId()).getLoyaltyPoints();
                    Price moneyToRefund = order.getPrice();
                    int loyaltyPointsToRemove = moneyToRefund.getDollars();
                    String message = "Do you want to refund this order?";
                    if (loyaltyPointsToRemove > userLoyaltyPoints) {
                        moneyToRefund.subtractEquals(new Price(loyaltyPointsToRemove - userLoyaltyPoints, 0));
                        loyaltyPointsToRemove = userLoyaltyPoints;
                    }
                    final int pointsToRemove = loyaltyPointsToRemove;
                    String pointsLoseInfo = "You will lose " + loyaltyPointsToRemove + " loyalty points.";
                    String moneyRefundInfo = "You will get " + moneyToRefund + " refunded.";
                    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmationAlert.setTitle("Order refund");
                    confirmationAlert.setHeaderText(null);
                    confirmationAlert.setContentText(message + "\n" + moneyRefundInfo + "\n"+ pointsLoseInfo  );

                    ButtonType buttonYes = new ButtonType("Yes");
                    ButtonType buttonNo = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                    confirmationAlert.getButtonTypes().setAll(buttonYes, buttonNo);
                    
                    confirmationAlert.showAndWait().ifPresent(response -> {
                        if (response == buttonYes) {
                            System.out.println("Refunding order: " + order.getOrder_id());
                            // Usuwamy z widoku
                            historyBox.getChildren().remove(orderLabel);

                            // Aktualizacja w bazie: usunięcie zamówienia
                            AccountRepository.removeOrderById(order.getOrder_id());
                            // Zwracamy środki na konto
                            AccountRepository.addBalance(controller.getAccountId(), moneyToRefund.toDouble());
                            AccountRepository.takeLoyaltyPoints(controller.getAccountId(), pointsToRemove);
                            // Odświeżamy widok konta
                            controller.getAccountListing().updateAccount(controller.getAccountId());
                        }
                    });
                });

                historyBox.getChildren().add(orderLabel);
            }
        }
    }

    public VBox getPage() {
        return new VBox(scrollPane);
    }
}
