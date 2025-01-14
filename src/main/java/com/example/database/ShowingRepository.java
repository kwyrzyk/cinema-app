package com.example.database;

import com.example.database.db_classes.Seat;
import com.example.database.db_classes.Showing;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ShowingRepository {

    
    static public List<Showing> getShowingsByFilmIdWithSeats(int filmId, Connection connection) throws SQLException {
        List<Showing> showings = new ArrayList<>();
        String showingQuery = "SELECT id_showing, id_room, show_time, end_time FROM showing WHERE id_film = ?";
        String seatQuery = "SELECT id_seat, id_showing, row_number, seat_number, status " +
                           "FROM seats WHERE id_showing = ?";

        try (PreparedStatement showingStmt = connection.prepareStatement(showingQuery)) {

            showingStmt.setInt(1, filmId);

            try (ResultSet showingRs = showingStmt.executeQuery()) {
                while (showingRs.next()) {
                    int showingId = showingRs.getInt("id_showing");
                    int roomId = showingRs.getInt("id_room");
                    Timestamp showTimeStamp = showingRs.getTimestamp("show_time");
                    LocalDateTime showTime = showTimeStamp.toLocalDateTime();
                    Timestamp endTimeStamp = showingRs.getTimestamp("end_time");
                    LocalDateTime endTime = endTimeStamp.toLocalDateTime();


                    // Fetch seats for the showing
                    List<Seat> seats = new ArrayList<>();
                    try (PreparedStatement seatStmt = connection.prepareStatement(seatQuery)) {
                        seatStmt.setInt(1, showingId);
                        try (ResultSet seatRs = seatStmt.executeQuery()) {
                            while (seatRs.next()) {
                                int seatId = seatRs.getInt("id_seat");
                                int rowNumber = seatRs.getInt("row_number");
                                int seatNumber = seatRs.getInt("seat_number");
                                String status = seatRs.getString("status");

                                Seat seat = new Seat(seatId, showingId, rowNumber, seatNumber, status);
                                seats.add(seat);
                            }
                        }
                    }

                    // Create Showing object and add to the list
                    Showing showing = new Showing(showingId, filmId, roomId, showTime, endTime, seats);
                    showings.add(showing);
                }
            }
        }

        return showings;
    }

    public static boolean reserveSeat(int seatId, Connection connection) {
        String updateQuery = "UPDATE seats " +
                             "SET status = 'reserved' " +
                             "WHERE id_seat = ? AND status = 'available'";
    
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
    
            // Set the parameters for the query
            preparedStatement.setInt(1, seatId);
            
            // Execute the update
            int rowsAffected = preparedStatement.executeUpdate();
    
            // If a row was updated, the seat was successfully reserved
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to reserve seat: " + e.getMessage());
            return false;
        }
    }
    

    public static boolean freeSeat(int showingId, int rowNumber, int seatNumber, Connection connection) {
        String updateQuery = "UPDATE seats " +
                             "SET status = 'available' " +
                             "WHERE showing_id = ? AND row_number = ? AND seat_number = ? AND status = 'available'";
    
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
    
            // Set the parameters for the query
            preparedStatement.setInt(1, showingId);
            preparedStatement.setInt(2, rowNumber);
            preparedStatement.setInt(3, seatNumber);
    
            // Execute the update
            int rowsAffected = preparedStatement.executeUpdate();
    
            // If a row was updated, the seat was successfully reserved
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to reserve seat: " + e.getMessage());
            return false;
        }
    }

}
