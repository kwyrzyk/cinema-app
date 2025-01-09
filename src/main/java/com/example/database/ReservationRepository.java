package com.example.database;

import java.sql.*;

public class ReservationRepository {
 
    public static boolean reserve_if_possible(int screeningRoomId, int accountId, String startTime, String endTime) {
        String conflictCheckQuery = """
                SELECT COUNT(*)
                FROM (
                    -- Check for conflicts in the reservation table
                    SELECT screening_room_id
                    FROM reservation
                    WHERE screening_room_id = ?
                    AND (start_time < TO_TIMESTAMP(?, 'YYYY-MM-DD HH24:MI:SS')
                    AND end_time > TO_TIMESTAMP(?, 'YYYY-MM-DD HH24:MI:SS'))

                    UNION ALL

                    -- Check for conflicts in the showing table
                    SELECT screening_room_id
                    FROM showing
                    WHERE screening_room_id = ?
                    AND (showtime < TO_TIMESTAMP(?, 'YYYY-MM-DD HH24:MI:SS')
                    AND end_time > TO_TIMESTAMP(?, 'YYYY-MM-DD HH24:MI:SS'))
                ) conflicts;
            """;


        try(Connection connection = DatabaseManager.getConnection();
        PreparedStatement checkStmt = connection.prepareStatement(conflictCheckQuery))  {
            
            checkStmt.setInt(1, screeningRoomId);
            checkStmt.setString(2, endTime);  
            checkStmt.setString(3, startTime); 
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            int conflictCount = rs.getInt(1);
            rs.close();
            checkStmt.close();

            if (conflictCount == 0) {
                String insertQuery = """
                    INSERT INTO reservation (screening_room_id, account_id, start_time, end_time)
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
