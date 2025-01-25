package com.example.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.example.database.db_classes.ScreeningRoom;
import com.example.exceptions.NonRecoverableDatabaseException;
import com.example.exceptions.RecoverableDatabaseException;

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
        }  catch (SQLSyntaxErrorException e) {
            throw new NonRecoverableDatabaseException("Syntax error in SQL query: " + e.getMessage(), e);
        } catch (SQLException e) {
            throw new RecoverableDatabaseException("Database query getting the rooms: " + e.getMessage(), e);
        }
        return ScreeningRooms;
    }

    

}
