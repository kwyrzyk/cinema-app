package com.example.listing;

import com.example.database.FilmRepository;
import com.example.database.db_classes.Film;

import java.util.List;

public class FilmListing {

    private List<Film> films;

    // Constructor
    public FilmListing() {
        // Initialize films list by fetching data from the database
        this.films = FilmRepository.getAllFilms();
    }

    public void update() {
        this.films = FilmRepository.getAllFilms();
    }
    // Method to get the list of films
    public List<Film> getFilms() {
        return films;
    }

    // You can add more methods to filter, sort, or display the list of films
    public void displayFilms() {
        for (Film film : films) {
            System.out.println(film);
        }
    }
}
