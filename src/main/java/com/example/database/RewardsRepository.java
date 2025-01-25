package com.example.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;

import com.example.database.db_classes.PointsReward;
import com.example.exceptions.NonRecoverableDatabaseException;
import com.example.exceptions.RecoverableDatabaseException;

import java.util.List;

public class RewardsRepository {
    
    public static List<PointsReward> getAllPointsRewards(Connection connection){
        List<PointsReward> rewards = new ArrayList<>();
        String query = "SELECT id_reward, name, points_price FROM point_rewards";

        try{
            ResultSet rewardsResault = DatabaseManager.runSelectQuery(query, connection);
            
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
        } catch (SQLSyntaxErrorException e) {
            throw new NonRecoverableDatabaseException("Syntax error in SQL query: " + e.getMessage(), e);
        } catch (SQLException e) {
            throw new RecoverableDatabaseException("Database query getting the rewards: " + e.getMessage(), e);
        }

        return rewards;
    }

}
