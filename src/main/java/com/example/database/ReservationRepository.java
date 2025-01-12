package com.example.database;

import com.example.database.db_classes.Reservation;
import com.example.database.db_classes.ScreeningRoom;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.time.LocalDateTime;

public class ReservationRepository {
 

    public static List<Reservation> getAllReservationsByAccountId(int accountId){
        List<Reservation> reservations = new ArrayList<>();

        String query = """
                        SELECT r.reservation_id, r.start_time, r.end_time, sr.id_room, sr.name, sr.num_rows, sr.seats_per_row 
                        from reservations r JOIN screening_room sr on r.id_room = sr.id_room 
                        and r.id_account = """ + accountId;

        
        try{
            ResultSet reservationResult = DatabaseManager.runSelectQuery(query);
            
            while(reservationResult.next()){
                LocalDateTime startTime = reservationResult.getObject("start_time", LocalDateTime.class);
                LocalDateTime endTime = reservationResult.getObject("end_time", LocalDateTime.class);
                
                int id = reservationResult.getInt("id_room");
                String name = reservationResult.getString("name");
                int numRows = reservationResult.getInt("num_rows");
                int seatsPerRow = reservationResult.getInt("seats_per_row");

                ScreeningRoom room = new ScreeningRoom(id, name, numRows, seatsPerRow);
                reservations.add(new Reservation(startTime, endTime, room));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Database error: " + e.getMessage());
        }
        return reservations; 
    }

    public static boolean reserve_if_possible(int screeningRoomId, int accountId, String startTime, String endTime) {
        String conflictCheckQuery = """
                SELECT COUNT(*)
                FROM (
                    -- Check for conflicts in the reservation table
                    SELECT id_room
                    FROM reservations
                    WHERE id_room = ?
                    AND (start_time < TO_TIMESTAMP(?, 'YYYY-MM-DD HH24:MI:SS')
                    AND end_time > TO_TIMESTAMP(?, 'YYYY-MM-DD HH24:MI:SS'))

                    UNION ALL

                    SELECT id_room
                    FROM showing
                    WHERE id_room = ?
                    AND (show_time < TO_TIMESTAMP(?, 'YYYY-MM-DD HH24:MI:SS')
                    AND end_time > TO_TIMESTAMP(?, 'YYYY-MM-DD HH24:MI:SS'))
                ) conflicts
            """;


        try(Connection connection = DatabaseManager.getConnection();
        PreparedStatement checkStmt = connection.prepareStatement(conflictCheckQuery))  {
            
            checkStmt.setInt(1, screeningRoomId);
            checkStmt.setString(2, endTime);  
            checkStmt.setString(3, startTime); 

            checkStmt.setInt(4, screeningRoomId);
            checkStmt.setString(5, endTime);  
            checkStmt.setString(6, startTime);
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            int conflictCount = rs.getInt(1);
            rs.close();
            checkStmt.close();

            if (conflictCount == 0) {
                String insertQuery = """
                    INSERT INTO reservations (id_room, id_account, start_time, end_time)
                    VALUES (?, ?, TO_TIMESTAMP(?, 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP(?, 'YYYY-MM-DD HH24:MI:SS'))
                """;
                PreparedStatement insertStmt = connection.prepareStatement(insertQuery);
                insertStmt.setInt(1, screeningRoomId);
                insertStmt.setInt(2, accountId);
                insertStmt.setString(3, startTime);
                insertStmt.setString(4, endTime);
                insertStmt.executeUpdate();
                insertStmt.close();

                return true;  // Successfully added
            } else {
                return false;  // Conflict exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}
