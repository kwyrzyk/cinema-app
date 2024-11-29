package com.example.database;

import com.example.database.db_classes.Film;
import com.example.database.db_classes.Actor;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FilmRepository {
    // Constructor
    public FilmRepository(DatabaseManager dbManager) {
    }

    public Film getFilmById(int filmId) throws SQLException {
        // Query to get the film's basic info
        String filmQuery = "SELECT id_film, title, short_description, long_description, rating FROM films WHERE id_film = " + filmId;

        // Query to get the film's actors
        String actorsQuery = "SELECT a.id_actor, a.name, a.surname, fa.role " +
                             "FROM actors a " +
                             "JOIN emp_film_actors fa ON a.id_actor = fa.id_actor " +
                             "WHERE fa.id_film = " + filmId;

        // Execute film query
        ResultSet filmResult = DatabaseManager.runSelectQuery(filmQuery);
        if (!filmResult.next()) {
            return null; // No film found with the given ID
        }

        // Extract film details
        int id = filmResult.getInt("id_film");
        String title = filmResult.getString("title");
        String shortDescription = filmResult.getString("short_description");
        String longDescription = filmResult.getString("long_description");
        double rating = filmResult.getDouble("rating");

        // Execute actors query
        ResultSet actorsResult = DatabaseManager.runSelectQuery(actorsQuery);
        List<Actor> actors = new ArrayList<>();
        while (actorsResult.next()) {
            int actorId = actorsResult.getInt("id_actor");
            String name = actorsResult.getString("name");
            String surname = actorsResult.getString("surname");
            String role = actorsResult.getString("role");
            actors.add(new Actor(actorId, name, surname, role));
        }

        // Return a Film object with all details
        return new Film(id, title, shortDescription, longDescription, rating, actors);
    }
}
