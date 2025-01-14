package com.example.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.database.db_classes.Actor;
import com.example.database.db_classes.Film;
import com.example.database.db_classes.Showing;
import com.example.database.db_classes.Tag;


public class FilmRepository {
    // Constructor
    public FilmRepository(DatabaseManager dbManager) {
    }

    static public Film getFilmById(int filmId, Connection connection) throws SQLException {
        String filmQuery = "SELECT id_film, title, short_description, long_description, rating, pegi FROM films WHERE id_film = " + filmId;
        String actorsQuery = "SELECT a.id_actor, a.name, a.surname, fa.role " +
                             "FROM actors a " +
                             "JOIN film_actors fa ON a.id_actor = fa.id_actor " +
                             "WHERE fa.id_film = " + filmId;

        String tagsQuery = "SELECT t.id_tag, t.name " +
                        "FROM tags t " +
                        "JOIN film_tags ft ON t.id_tag = ft.id_tag " +
                        "WHERE ft.id_film = " + filmId;

        ResultSet filmResult = DatabaseManager.runSelectQuery(filmQuery, connection);
        if (!filmResult.next()) {
            return null; 
        }

        int id = filmResult.getInt("id_film");
        String title = filmResult.getString("title");
        String shortDescription = filmResult.getString("short_description");
        String longDescription = filmResult.getString("long_description");
        double rating = filmResult.getDouble("rating");
        int pegi = filmResult.getInt("pegi");
        ResultSet actorsResult = DatabaseManager.runSelectQuery(actorsQuery, connection);
        List<Actor> actors = new ArrayList<>();
        while (actorsResult.next()) {
            int actorId = actorsResult.getInt("id_actor");
            String name = actorsResult.getString("name");
            String surname = actorsResult.getString("surname");
            String role = actorsResult.getString("role");
            actors.add(new Actor(actorId, name, surname, role));
        }

        ResultSet tagsResult = DatabaseManager.runSelectQuery(tagsQuery, connection);
        List<Tag> tags = new ArrayList<>();
        while(tagsResult.next()){
            int tagId = tagsResult.getInt("id_tag"); 
            String name = tagsResult.getString("name");
            tags.add(new Tag(tagId, name));
        }

        List<Showing> showings = ShowingRepository.getShowingsByFilmIdWithSeats(filmId, connection);

        // Return a Film object with all details
        return new Film(id, title, shortDescription, longDescription, rating, actors, showings, tags, pegi);
    }

      public static List<Film> getAllFilms(Connection connection) {
        List<Film> films = new ArrayList<>();
        String filmQuery = "SELECT id_film, title, short_description, long_description, rating, pegi FROM films";
        String actorsQuery = "SELECT a.id_actor, a.name, a.surname, fa.role " +
                             "FROM actors a " +
                             "JOIN film_actors fa ON a.id_actor = fa.id_actor " +
                             "WHERE fa.id_film =";
    
        String tagsQuery = "SELECT t.id_tag, t.name " +
                            "FROM tags t " +
                            "JOIN film_tags ft ON t.id_tag = ft.id_tag " +
                            "WHERE ft.id_film = ";
     

        try {
            ResultSet filmResult = DatabaseManager.runSelectQuery(filmQuery, connection);
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
                String fullTagsQuery = tagsQuery + filmId;

                ResultSet actorsResult = DatabaseManager.runSelectQuery(fullActorsQuery, connection);
                if (actorsResult == null) {
                    System.err.println("Error: actorsResult is null for filmId " + filmId);
                    continue; 
                }
    
                List<Actor> actors = new ArrayList<>();
                while (actorsResult.next()) {
                    int actorId = actorsResult.getInt("id_actor");
                    String name = actorsResult.getString("name");
                    String surname = actorsResult.getString("surname");
                    String role = actorsResult.getString("role");
                    actors.add(new Actor(actorId, name, surname, role));
                }
    
                
                ResultSet tagsResult = DatabaseManager.runSelectQuery(fullTagsQuery, connection);
                List<Tag> tags = new ArrayList<>();
                while(tagsResult.next()){
                    int tagId = tagsResult.getInt("id_tag");
                    String name = tagsResult.getString("name");
                    tags.add(new Tag(tagId, name));
                }

                List<Showing> showings = ShowingRepository.getShowingsByFilmIdWithSeats(filmId, connection);

                films.add(new Film(filmId, title, shortDescription, longDescription, rating, actors, showings, tags, pegi));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Database error: " + e.getMessage());
        }
    
        return films;
    }
    

}
