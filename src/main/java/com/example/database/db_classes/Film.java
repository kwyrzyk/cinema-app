package com.example.database.db_classes;

import java.util.List;

public class Film {
    private int id;
    private String title;
    private String shortDescription;
    private String longDescription;
    private double rating;
    private List<Actor> actors; // A list of associated actors
    private List<Showing> showings;

    // Constructor
    public Film(int id, String title, String shortDescription, String longDescription, double rating, List<Actor> actors, List<Showing> showings) {
        this.id = id;
        this.title = title;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.rating = rating;
        this.actors = actors;
        this.showings = showings;
    }

    // Getters and toString method
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getShortDescription() { return shortDescription; }
    public String getLongDescription() { return longDescription; }
    public double getRating() { return rating; }
    public List<Actor> getActors() { return actors; }
    public List<Showing> getShowings() {return showings;}

    @Override
    public String toString() {
        return "Film{" +
               "id=" + id +
               ", title='" + title + '\'' +
               ", shortDescription='" + shortDescription + '\'' +
               ", longDescription='" + longDescription + '\'' +
               ", rating=" + rating +
               ", actors=" + actors +
               '}';
    }
}