package com.example.database;

import com.example.database.db_classes.Showing;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ShowingRepositoryTest {

    private static DatabaseManager databaseManager = new DatabaseManager();

    @Test
    public void testGetShowingsByFilmIdWithSeats() throws SQLException {
        // Test retrieving showings for a given film ID
        int filmId = 1; // Use an example film ID
        List<Showing> showings = ShowingRepository.getShowingsByFilmIdWithSeats(filmId, databaseManager.getConnection());

        // Assert the showings list is not null
        assertNotNull(showings, "Showings list should not be null");

        // Assert that the list has at least one showing
        assertTrue(showings.size() > 0, "There should be at least one showing for the given film ID");

        // Assert each showing in the list is not null
        for (Showing showing : showings) {
            assertNotNull(showing, "Each showing should not be null");
            assertNotNull(showing.getShowTime(), "Show time should not be null for showing ID: " + showing.getId());
            assertNotNull(showing.getSeats(), "Seats list should not be null for showing ID: " + showing.getId());
        }
    }
}
