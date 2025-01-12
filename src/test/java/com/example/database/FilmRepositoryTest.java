package com.example.database;

import com.example.database.db_classes.Film;
import com.example.database.db_classes.Actor;
import com.example.database.db_classes.Showing;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FilmRepositoryTest {

    private static DatabaseManager databaseManager = new DatabaseManager();


    @Test
    public void testGetFilmById() throws SQLException {
        // Test retrieving a film by ID
        int filmId = 1; // Example film ID

        Film film = FilmRepository.getFilmById(filmId, databaseManager.getConnection());

        // Assert the film is not null
        assertNotNull(film, "Film should not be null for ID " + filmId);

        // Assert the film's basic details are not null
        assertNotNull(film.getTitle(), "Film title should not be null");
        assertNotNull(film.getShortDescription(), "Film short description should not be null");
        assertNotNull(film.getLongDescription(), "Film long description should not be null");
        assertNotNull(film.getRating(), "Film rating should not be null");

        // Assert the film's actors are not null
        assertNotNull(film.getActors(), "Film actors list should not be null");
        assertTrue(film.getActors().size() > 0, "Film should have at least one actor");

        // Assert each actor's details are not null
        for (Actor actor : film.getActors()) {
            assertNotNull(actor, "Actor should not be null");
            assertNotNull(actor.getName(), "Actor name should not be null");
            assertNotNull(actor.getSurname(), "Actor surname should not be null");
            assertNotNull(actor.getRole(), "Actor role should not be null");
        }

        // Assert the film's showings are not null
        assertNotNull(film.getShowings(), "Film showings list should not be null");
        assertTrue(film.getShowings().size() > 0, "Film should have at least one showing");

        // Assert each showing's details are not null
        for (Showing showing : film.getShowings()) {
            assertNotNull(showing, "Showing should not be null");
            assertNotNull(showing.getShowTime(), "Showing time should not be null");
            assertNotNull(showing.getSeats(), "Seats list should not be null");
        }
    }

    @Test
    public void testGetAllFilms() throws SQLException {
        // Test retrieving all films
        List<Film> films = FilmRepository.getAllFilms(databaseManager.getConnection());

        // Assert the films list is not null and has at least one film
        assertNotNull(films, "Films list should not be null");
        assertTrue(films.size() > 0, "There should be at least one film in the list");

        // Assert each film in the list is not null
        for (Film film : films) {
            assertNotNull(film, "Film should not be null");
            assertNotNull(film.getTitle(), "Film title should not be null");
            assertNotNull(film.getShortDescription(), "Film short description should not be null");
            assertNotNull(film.getLongDescription(), "Film long description should not be null");
            assertNotNull(film.getRating(), "Film rating should not be null");

            // Assert each film's actors and showings
            assertNotNull(film.getActors(), "Film actors list should not be null");
            assertNotNull(film.getShowings(), "Film showings list should not be null");

            // Check that the actors and showings are not empty
            assertTrue(film.getActors().size() > 0, "Film should have at least one actor");
            assertTrue(film.getShowings().size() > 0, "Film should have at least one showing");

            // Check that each actor and showing are not null
            for (Actor actor : film.getActors()) {
                assertNotNull(actor, "Actor should not be null");
            }
            for (Showing showing : film.getShowings()) {
                assertNotNull(showing, "Showing should not be null");
                assertNotNull(showing.getShowTime(), "Showing time should not be null");
                assertNotNull(showing.getSeats(), "Seats list should not be null");
            }
        }
    }
}
