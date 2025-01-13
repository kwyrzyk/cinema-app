package com.example.database;

import com.example.database.db_classes.PointsReward;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RewardsRepositoryTest {

    private static DatabaseManager databaseManager = new DatabaseManager();

    @Test
    public void testGetAllPointsRewards() throws SQLException {
        // Test retrieving all points rewards
        List<PointsReward> rewards = RewardsRepository.getAllPointsRewards(databaseManager.getConnection());

        // Assert the rewards list is not null and has at least one reward
        assertNotNull(rewards, "Rewards list should not be null");
        assertTrue(rewards.size() > 0, "There should be at least one reward in the list");

        // Assert each reward in the list is not null
        for (PointsReward reward : rewards) {
            assertNotNull(reward, "Reward should not be null");
            assertNotNull(reward.getName(), "Reward name should not be null");
            assertTrue(reward.getName().length() > 0, "Reward name should not be empty");
            assertTrue(reward.getId() > 0, "Reward ID should be greater than 0");
            assertTrue(reward.getPointsPrice() > 0, "Reward points price should be greater than 0");
        }
    }
}
