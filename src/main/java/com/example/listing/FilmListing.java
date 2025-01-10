package com.example.listing;

import java.util.ArrayList;
import java.util.List;

import com.example.database.FilmRepository;
import com.example.database.db_classes.Film;
import com.example.database.db_classes.Tag;

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

    public List<Film> getFilmsByTag(Tag tag) {
        List<Film> filmsWithTag = new ArrayList<>();
        for (Film film : films){
            for (Tag a : film.getTags()){
                if (a.getName().equals(tag.getName())){
                    filmsWithTag.add(film);
                    break;
                }
            }

        }
        return filmsWithTag;
    }

    public List<Film> getFilmsByPegi(Integer pegi){
        List<Film> filmsWithPegi = new ArrayList<>();
        for (Film film : films){
            if(film.getPegi()<=pegi){
                filmsWithPegi.add(film);
            }
        }
        return filmsWithPegi;
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
