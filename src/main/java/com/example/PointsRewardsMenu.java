package com.example;

import java.util.List;
import java.util.stream.Collectors;

import com.example.database.AccountRepository;
import com.example.database.db_classes.Basket;
import com.example.database.db_classes.PointsReward;
import com.example.database.db_classes.PointsReward;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class PointsRewardsMenu {

    private final List<PointsReward> listOfRewards;
    private final Controller controller;

    public PointsRewardsMenu(Controller controller, List<PointsReward> listOfRewards) {
        this.listOfRewards = listOfRewards;
        this.controller = controller;
    }

    public VBox getRewardListVBox() {
        // Create a ListView for discounts
        ListView<PointsReward> rewardListView = new ListView<>();
        rewardListView.getStyleClass().add("lists");
        // Add active rewards to the ListView
        rewardListView.getItems().addAll(listOfRewards);

        // Set custom cells in the ListView
        rewardListView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<PointsReward> call(ListView<PointsReward> listView) {
                return new RewardListCell();
            }
        });

        VBox mainContainer = new VBox();
        HBox searchPanel = createSearchPanel(rewardListView);

        mainContainer.getChildren().addAll(searchPanel, rewardListView);

        return mainContainer;
    }

    private HBox createSearchPanel(ListView<PointsReward> rewardListView) {
        // Text field for search
        javafx.scene.control.TextField searchField = new javafx.scene.control.TextField();
        searchField.setPromptText("Enter reward name...");
        searchField.getStyleClass().add("searchfield");

        // Search button
        javafx.scene.control.Button searchButton = new javafx.scene.control.Button("Search");
        searchButton.getStyleClass().add("searchbutton");

        searchButton.setOnAction(e -> {
            String query = searchField.getText().toLowerCase();
            List<PointsReward> filteredRewarlistOfRewards = rewardListView.getItems().stream()
                    .filter(reward -> reward.toString().toLowerCase().contains(query))
                    .collect(Collectors.toList());

            rewardListView.getItems().setAll(filteredRewarlistOfRewards);
        });

        HBox searchPanel = new HBox(10, searchField, searchButton);
        searchPanel.getStyleClass().add("searchpanel");

        return searchPanel;
    }

    private class RewardListCell extends ListCell<PointsReward> {
        @Override
        protected void updateItem(PointsReward reward, boolean empty) {
            super.updateItem(reward, empty);

            if (empty || reward == null) {
                setText(null);
                setGraphic(null);
            } else {
                VBox content = new VBox();
                Label rewardNameLabel = new Label(reward.toString());
                // Adding reward to the basket on click
                int userLoyaltyPoints = controller.getAccountListing().getAccountById(controller.getAccountId()).getLoyaltyPoints();
                rewardNameLabel.setOnMouseClicked(event -> {
                    if (userLoyaltyPoints >= reward.getPointsPrice()) {
                        Alert empty_alert = new Alert(Alert.AlertType.WARNING);
                        empty_alert.setTitle("Reward get");
                        empty_alert.setHeaderText(null);
                        empty_alert.setContentText("You got the reward for " + reward.getPointsPrice() + " points");
                        empty_alert.showAndWait();
                        AccountRepository.takeLoyaltyPoints(controller.getAccountId(), reward.getPointsPrice(), controller.databaseManager.getConnection());
                        controller.getAccountListing().updateAccount(controller.getAccountId());
                    } else {
                        Alert empty_alert = new Alert(Alert.AlertType.WARNING);
                        empty_alert.setTitle("You don't have enough points");
                        empty_alert.setHeaderText(null);
                        empty_alert.setContentText("You have only " + userLoyaltyPoints + " points");
                        empty_alert.showAndWait();
                    }
                });

                content.getChildren().addAll(rewardNameLabel);
                content.getStyleClass().add("bartek");
                setGraphic(content);
            }
        }
    }
}
