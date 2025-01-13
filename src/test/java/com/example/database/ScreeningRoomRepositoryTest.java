package com.example.database;

import com.example.database.db_classes.ScreeningRoom;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ScreeningRoomRepositoryTest {

    private static DatabaseManager databaseManager = new DatabaseManager();

    @Test
    public void testGetAllScreeningRooms() throws SQLException {
        // Test retrieving all screening rooms
        List<ScreeningRoom> screeningRooms = ScreeningRoomRepository.getAllScreeningRooms(databaseManager.getConnection());

        // Assert the screening rooms list is not null and has at least one room
        assertNotNull(screeningRooms, "Screening rooms list should not be null");
        assertTrue(screeningRooms.size() > 0, "There should be at least one screening room in the list");

        // Assert each screening room in the list is not null
        for (ScreeningRoom room : screeningRooms) {
            assertNotNull(room, "Screening room should not be null");
            assertNotNull(room.getName(), "Screening room name should not be null");
            assertTrue(room.getName().length() > 0, "Screening room name should not be empty");
            assertTrue(room.getId() > 0, "Screening room ID should be greater than 0");
            assertTrue(room.getNumRows() > 0, "Screening room number of rows should be greater than 0");
            assertTrue(room.getSeatsPerRow() > 0, "Screening room seats per row should be greater than 0");
        }
    }

}
