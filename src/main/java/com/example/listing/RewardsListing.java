package com.example.listing;

import java.sql.Connection;
import java.util.List;

import com.example.database.RewardsRepository;
import com.example.database.db_classes.PointsReward;

public class RewardsListing {
    List<PointsReward> rewards;

    /**
     * Constructor initializes the rewards list.
     * @param connection the database connection
     */
    public RewardsListing(Connection connection) {
        this.rewards = RewardsRepository.getAllPointsRewards(connection);
    }

    /**
     * Get the list of rewards.
     * @return a list of rewards
     */
    public List<PointsReward> getRewards() {
        return rewards;
    }
}