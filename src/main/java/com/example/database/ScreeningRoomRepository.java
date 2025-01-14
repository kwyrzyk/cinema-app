package com.example.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.example.database.db_classes.ScreeningRoom;

public class ScreeningRoomRepository {
    
    public static List<ScreeningRoom> getAllScreeningRooms(Connection connection){

        String query = "SELECT id_room, name, num_rows, seats_per_row from screening_room";

        List<ScreeningRoom> ScreeningRooms = new ArrayList<>();

        try{
            ResultSet screeningRoomsResault = DatabaseManager.runSelectQuery(query, connection);
            
            if(screeningRoomsResault == null){
                System.err.println("Error: screeningRoomsResult is null.");
                return ScreeningRooms;
            }
            
            while (screeningRoomsResault.next()) {
                int id = screeningRoomsResault.getInt("id_room");
                String name = screeningRoomsResault.getString("name");
                int numRows = screeningRoomsResault.getInt("num_rows");
                int seatsPerRow = screeningRoomsResault.getInt("seats_per_row");

                ScreeningRooms.add(new ScreeningRoom(id, name, numRows, seatsPerRow));
            }
        } catch (SQLException e){
            e.printStackTrace();
            System.err.println("Database error while getting the screeening rooms: " + e.getMessage());
        }
        return ScreeningRooms;
    }

    

}
