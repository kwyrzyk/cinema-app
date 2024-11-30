package com.example.database;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.time.LocalDateTime;

public class ShowingRepository {

    public ShowingRepository(DatabaseManager dbManager) {
    }

    public int createShowing(int filmId, int roomId, LocalDateTime showTime) {
        String query = "INSERT INTO showing (id_film, id_room, show_time) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, filmId);
            stmt.setInt(2, roomId);
            stmt.setTimestamp(3, Timestamp.valueOf(showTime));
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // Return the ID of the created showing
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
