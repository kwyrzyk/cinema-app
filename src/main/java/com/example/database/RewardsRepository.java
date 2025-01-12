package com.example.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.example.database.db_classes.PointsReward;

import java.util.List;

public class RewardsRepository {
    
    public static List<PointsReward> getAllPointsRewards(){
        List<PointsReward> rewards = new ArrayList<>();
        String query = "SELECT id_reward, name, points_price FROM point_rewards";

        try{
            ResultSet rewardsResault = DatabaseManager.runSelectQuery(query);
            
            if(rewardsResault == null){
                System.err.println("Error: rewardsResult is null.");
                return rewards;
            }
            
            while (rewardsResault.next()) {
                int id = rewardsResault.getInt("id_reward");
                String name = rewardsResault.getString("name");
                int price = rewardsResault.getInt("points_price");


                rewards.add(new PointsReward(id, name, price));
            }
        } catch (SQLException e){
            e.printStackTrace();
            System.err.println("Database error while getting the points rewards: " + e.getMessage());
        }

        return rewards;
    }

}
