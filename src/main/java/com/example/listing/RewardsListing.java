package com.example.listing;

import java.sql.Connection;
import java.util.List;

import com.example.database.RewardsRepository;
import com.example.database.db_classes.PointsReward;

public class RewardsListing {
    List<PointsReward> rewards;

    public RewardsListing(Connection connection){
        this.rewards = RewardsRepository.getAllPointsRewards(connection);
    }


    public List<PointsReward> getRewards() {
        return rewards;
    }
}