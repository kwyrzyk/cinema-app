package com.example.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.database.db_classes.Actor;
import com.example.database.db_classes.Film;

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

      // Method to get a list of all films
    static public List<Film> getAllFilms() {
        List<Film> films = new ArrayList<>();
        String filmQuery = "SELECT id_film, title, short_description, long_description, rating FROM films";

        // Query to get the actors for a specific film
        String actorsQuery = "SELECT a.id_actor, a.name, a.surname, fa.role " +
                             "FROM actors a " +
                             "JOIN emp_film_actors fa ON a.id_actor = fa.id_actor " +
                             "WHERE fa.id_film = ?";

        // Execute the film query to get all films
        try{
        ResultSet filmResult = DatabaseManager.runSelectQuery(filmQuery);
        while (filmResult.next()) {
            // Extract basic film information
            int filmId = filmResult.getInt("id_film");
            String title = filmResult.getString("title");
            String shortDescription = filmResult.getString("short_description");
            String longDescription = filmResult.getString("long_description");
            double rating = filmResult.getDouble("rating");

            String fullActorsQuery = actorsQuery + filmId;

            // Now retrieve the associated actors for each film by calling runSelectQuery
            ResultSet actorsResult = DatabaseManager.runSelectQuery(fullActorsQuery);
            List<Actor> actors = new ArrayList<>();
            while (actorsResult.next()) {
                int actorId = actorsResult.getInt("id_actor");
                String name = actorsResult.getString("name");
                String surname = actorsResult.getString("surname");
                String role = actorsResult.getString("role");
                actors.add(new Actor(actorId, name, surname, role));
            }

            // Create the Film object and add it to the list
            films.add(new Film(filmId, title, shortDescription, longDescription, rating, actors));
        }
    } catch( SQLException e){
        e.printStackTrace(); // Log the exception (you could use a logger here instead)
        // Optionally, rethrow the exception or handle it more gracefully
        return new ArrayList<>(); // Return an empty list if there's an error
        
    }

        // Return the list of films with their actors
        return films;
    }

}
