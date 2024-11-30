package com.example.database;

import com.example.database.db_classes.Actor;
import com.example.database.db_classes.Film;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FilmRepositoryTest {

    private FilmRepository filmRepository;

    @BeforeEach
    public void setup() throws Exception {
        // Initialize the repository
        filmRepository = new FilmRepository(new DatabaseManager());
    
        // Set up the database environment
        try (Connection conn = DatabaseManager.getConnection(); Statement stmt = conn.createStatement()) {
            // Create tables
            stmt.execute("CREATE TABLE films (" +
                "id_film INT PRIMARY KEY, " +
                "title VARCHAR(255) NOT NULL, " +
                "short_description VARCHAR2(1000), " + 
                "long_description VARCHAR2(4000), " +  
                "rating NUMBER(3, 1)" +
                ")");
            
            stmt.execute("CREATE TABLE actors (" +
                    "id_actor INT PRIMARY KEY, " +
                    "name VARCHAR(100) NOT NULL, " +
                    "surname VARCHAR(100) NOT NULL)");
    
            stmt.execute("CREATE TABLE emp_film_actors (" +
                    "id_film INT, " +
                    "id_actor INT, " +
                    "role VARCHAR(255), " +
                    "PRIMARY KEY (id_film, id_actor), " +
                    "FOREIGN KEY (id_film) REFERENCES films(id_film) ON DELETE CASCADE, " +
                    "FOREIGN KEY (id_actor) REFERENCES actors(id_actor) ON DELETE CASCADE)");
    
            // Insert mock data into films
            stmt.execute("INSERT INTO films (id_film, title, short_description, long_description, rating) " +
                    "VALUES (1, 'Inception', 'A dream within a dream.', 'A long description about Inception.', 8.8)");
    
            // Insert mock data into actors (separate INSERT for each row)
            stmt.execute("INSERT INTO actors (id_actor, name, surname) " +
                    "VALUES (1, 'Leonardo', 'DiCaprio')");
            stmt.execute("INSERT INTO actors (id_actor, name, surname) " +
                    "VALUES (2, 'Joseph', 'Gordon-Levitt')");
    
            // Insert mock data into emp_film_actors
            stmt.execute("INSERT INTO emp_film_actors (id_film, id_actor, role) " +
                    "VALUES (1, 1, 'Dom Cobb')");
            stmt.execute("INSERT INTO emp_film_actors (id_film, id_actor, role) " +
                    "VALUES (1, 2, 'Arthur')");
        }
    }
    
    @AfterEach
    public void teardown() throws Exception {
        // Clean up the database
        try (Connection conn = DatabaseManager.getConnection(); Statement stmt = conn.createStatement()) {
            // Drop tables in reverse order to satisfy foreign key constraints
            try { stmt.execute("DROP TABLE emp_film_actors CASCADE CONSTRAINTS"); } catch (SQLException e) { /* Ignore if table does not exist */ }
            try { stmt.execute("DROP TABLE actors CASCADE CONSTRAINTS"); } catch (SQLException e) { /* Ignore if table does not exist */ }
            try { stmt.execute("DROP TABLE films CASCADE CONSTRAINTS"); } catch (SQLException e) { /* Ignore if table does not exist */ }
        }
    }





    @Test
    public void testGetFilmById() throws Exception {
        // Test retrieving film by ID
        Film film = filmRepository.getFilmById(1);

        // Verify the film's basic details
        assertNotNull(film);
        assertEquals(1, film.getId());
        assertEquals("Inception", film.getTitle());
        assertEquals("A dream within a dream.", film.getShortDescription());
        assertEquals("A long description about Inception.", film.getLongDescription());
        assertEquals(8.8, film.getRating(), 0.1);

        // Verify the actors
        List<Actor> actors = film.getActors();
        assertEquals(2, actors.size());

        // Check actor details
        Actor actor1 = actors.get(0);
        assertEquals(1, actor1.getId());
        assertEquals("Leonardo", actor1.getName());
        assertEquals("DiCaprio", actor1.getSurname());
        assertEquals("Dom Cobb", actor1.getRole());

        Actor actor2 = actors.get(1);
        assertEquals(2, actor2.getId());
        assertEquals("Joseph", actor2.getName());
        assertEquals("Gordon-Levitt", actor2.getSurname());
        assertEquals("Arthur", actor2.getRole());
    }
}

