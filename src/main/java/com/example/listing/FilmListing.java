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


    // Constructor
    public FilmListing(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        // Initialize films list by fetching data from the database
        try{
        this.films = FilmRepository.getAllFilms(this.databaseManager.getConnection());
        } catch (Exception e){
            ErrorHandler.handle(e);
        }
    }

    public void update() {
        try{
        this.films = FilmRepository.getAllFilms(this.databaseManager.getConnection());
        } catch ( Exception e){
            ErrorHandler.handle(e);
        }
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

    public void addModified(Showing showing){
        modified.add(showing);
    }


    public void updateModified(){
        for(Showing sh : modified){
            try{
                sh.setSeats(ShowingRepository.getSeatsByShowingId(sh.getId(), databaseManager.getConnection()));
            }catch ( Exception e){
                ErrorHandler.handle(e);
            }
        }
    }

}
