package com.example.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.database.db_classes.Actor;
import com.example.database.db_classes.Film;
import com.example.database.db_classes.Showing;;

public class FilmRepository {
    // Constructor
    public FilmRepository(DatabaseManager dbManager) {
    }

    static public Film getFilmById(int filmId) throws SQLException {
        // Query to get the film's basic info
        String filmQuery = "SELECT id_film, title, short_description, long_description, rating, pegi FROM films WHERE id_film = " + filmId;

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
        int pegi = filmResult.getInt("pegi");
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

        List<Showing> showings = ShowingRepository.getShowingsByFilmIdWithSeats(filmId);

        // Return a Film object with all details
        return new Film(id, title, shortDescription, longDescription, rating, actors, showings, pegi);
    }

      // Method to get a list of all films
      public static List<Film> getAllFilms() {
        List<Film> films = new ArrayList<>();
        String filmQuery = "SELECT id_film, title, short_description, long_description, rating, pegi FROM films";
        String actorsQuery = "SELECT a.id_actor, a.name, a.surname, fa.role " +
                             "FROM actors a " +
                             "JOIN emp_film_actors fa ON a.id_actor = fa.id_actor " +
                             "WHERE fa.id_film =";
    
        try {
            ResultSet filmResult = DatabaseManager.runSelectQuery(filmQuery);
            if (filmResult == null) {
                System.err.println("Error: filmResult is null.");
                return films;
            }
    
            while (filmResult.next()) {
                int filmId = filmResult.getInt("id_film");
                String title = filmResult.getString("title");
                String shortDescription = filmResult.getString("short_description");
                String longDescription = filmResult.getString("long_description");
                double rating = filmResult.getDouble("rating");
                int pegi = filmResult.getInt("pegi");

                String fullActorsQuery = actorsQuery + filmId;
    
                ResultSet actorsResult = DatabaseManager.runSelectQuery(fullActorsQuery);
                if (actorsResult == null) {
                    System.err.println("Error: actorsResult is null for filmId " + filmId);
                    continue; // Przejdź do następnego filmu
                }
    
                List<Actor> actors = new ArrayList<>();
                while (actorsResult.next()) {
                    int actorId = actorsResult.getInt("id_actor");
                    String name = actorsResult.getString("name");
                    String surname = actorsResult.getString("surname");
                    String role = actorsResult.getString("role");
                    actors.add(new Actor(actorId, name, surname, role));
                }
    
                List<Showing> showings = ShowingRepository.getShowingsByFilmIdWithSeats(filmId);

                films.add(new Film(filmId, title, shortDescription, longDescription, rating, actors, showings, pegi));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Database error: " + e.getMessage());
        }
    
        return films;
    }
    

}
