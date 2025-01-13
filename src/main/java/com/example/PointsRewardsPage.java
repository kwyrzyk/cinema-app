package com.example;

import com.example.database.AccountRepository;
import com.example.database.db_classes.PointsReward;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PointsRewardsPage implements Page {

    private final VBox pageContent = new VBox();
    private final ScrollPane scrollPane = new ScrollPane(pageContent);
    private final VBox rewardsBox = new VBox(scrollPane);
    private final VBox rewardsItemsBox = new VBox();
    private final List<PointsReward> allRewards;
    private List<PointsReward> displayedRewards;
    private final Controller controller;

    public PointsRewardsPage(Controller controller) {
        this.controller = controller;
        this.allRewards = controller.getListOfRewards();
        this.displayedRewards = new ArrayList<>(this.allRewards);

        createContent();
    }

    private void createContent() {
        rewardsBox.getStyleClass().add("page");
        scrollPane.getStyleClass().add("scroll-pane");
        scrollPane.setFitToWidth(true);
        pageContent.getStyleClass().add("wide-box");
        rewardsItemsBox.getStyleClass().add("products-items-box");

        Label title = new Label("Rewards Menu");
        title.getStyleClass().add("page-title");

        HBox searchBox = new HBox();
        searchBox.getStyleClass().add("search-box");

        TextField searchField = new TextField();
        searchField.setPromptText("Search for rewards...");
        searchField.getStyleClass().add("input-field");

        Button searchButton = new Button("Search");
        searchButton.getStyleClass().add("btn");

        searchButton.setOnAction(event -> {
            String query = searchField.getText().trim().toLowerCase();
            filterRewards(query);
        });

        searchBox.getChildren().addAll(searchField, searchButton);

        updateRewardsView(displayedRewards);

        pageContent.getChildren().addAll(title, searchBox, rewardsItemsBox);
    }

    private void updateRewardsView(List<PointsReward> rewardsToDisplay) {
        rewardsItemsBox.getChildren().clear();

        if (rewardsToDisplay.isEmpty()) {
            Label noRewardsLabel = new Label("No rewards available.");
            noRewardsLabel.getStyleClass().add("no-items-label");
            rewardsItemsBox.getChildren().add(noRewardsLabel);
        } else {
            for (PointsReward reward : rewardsToDisplay) {
                VBox rewardBox = new VBox();
                rewardBox.getStyleClass().add("product-box");

                Label rewardLabel = new Label(reward.toString());
                rewardLabel.getStyleClass().add("product-price");

                rewardLabel.setOnMouseClicked(event -> handleRewardSelection(reward));

                rewardBox.getChildren().add(rewardLabel);
                rewardsItemsBox.getChildren().add(rewardBox);
            }
        }
    }

    private void filterRewards(String query) {
        if (query.isEmpty()) {
            displayedRewards = new ArrayList<>(allRewards);
        } else {
            displayedRewards = allRewards.stream()
                .filter(reward -> reward.toString().toLowerCase().contains(query))
                .collect(Collectors.toList());
        }
        updateRewardsView(displayedRewards);
    }

    private void handleRewardSelection(PointsReward reward) {
        int userLoyaltyPoints = controller.getAccountListing().getAccountById(controller.getAccountId()).getLoyaltyPoints();

        if (userLoyaltyPoints >= reward.getPointsPrice()) {
            Controller.showAlert(
                Alert.AlertType.INFORMATION,
                "Reward Claimed",
                "You claimed the reward for " + reward.getPointsPrice() + " points!"
                );
            
            int userId = controller.getAccountId();    
            AccountRepository.takeLoyaltyPoints(userId, reward.getPointsPrice());
            controller.getAccountListing().updateAccount(userId);
        } else {
            Controller.showAlert(
                Alert.AlertType.WARNING,
                "Insufficient Points",
                "You only have " + userLoyaltyPoints + " points."
                );
        }
    }

    public VBox getPage() {
        return rewardsBox;
    }
}
