package com.example.listing;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;

import com.example.database.DatabaseManager;
import com.example.database.FilmRepository;
import com.example.database.ShowingRepository;
import com.example.database.db_classes.Film;
import com.example.database.db_classes.Tag;
import com.example.exceptions.ErrorHandler;
import com.example.database.db_classes.Showing;

public class FilmListing {
    private List<Film> films;
    private DatabaseManager databaseManager;
    private HashSet<Showing> modified = new HashSet<>();

    /**
     * Constructor initializes the films list and database manager.
     * @param databaseManager the database manager
     */
    public FilmListing(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        try {
            this.films = FilmRepository.getAllFilms(this.databaseManager.getConnection());
        } catch (Exception e) {
            ErrorHandler.handle(e);
        }
    }

    /**
     * Update the list of films.
     */
    public void update() {
        try {
            this.films = FilmRepository.getAllFilms(this.databaseManager.getConnection());
        } catch (Exception e) {
            ErrorHandler.handle(e);
        }
    }

    /**
     * Get films by a specific tag.
     * @param tag the tag
     * @return a list of films with the specified tag
     */
    public List<Film> getFilmsByTag(Tag tag) {
        List<Film> filmsWithTag = new ArrayList<>();
        for (Film film : films) {
            for (Tag a : film.getTags()) {
                if (a.getName().equals(tag.getName())) {
                    filmsWithTag.add(film);
                    break;
                }
            }
        }
        return filmsWithTag;
    }

    /**
     * Get films by PEGI rating.
     * @param pegi the PEGI rating
     * @return a list of films with the specified PEGI rating
     */
    public List<Film> getFilmsByPegi(Integer pegi) {
        List<Film> filmsWithPegi = new ArrayList<>();
        for (Film film : films) {
            if (film.getPegi() <= pegi) {
                filmsWithPegi.add(film);
            }
        }
        return filmsWithPegi;
    }

    /**
     * Get the list of films.
     * @return a list of films
     */
    public List<Film> getFilms() {
        return films;
    }

    /**
     * Display all the films.
     */
    public void displayFilms() {
        for (Film film : films) {
            System.out.println(film);
        }
    }

    /**
     * Add a modified showing.
     * @param showing the showing
     */
    public void addModified(Showing showing) {
        modified.add(showing);
    }

    /**
     * Update the modified showings.
     */
    public void updateModified() {
        for (Showing sh : modified) {
            try {
                sh.setSeats(ShowingRepository.getSeatsByShowingId(sh.getId(), databaseManager.getConnection()));
            } catch (Exception e) {
                ErrorHandler.handle(e);
            }
        }
    }
}
