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
    private List<Tag> tags; 
    private int pegi;

    // Constructor
    public Film(int id, String title, String shortDescription, String longDescription, double rating, List<Actor> actors, 
     List<Showing> showings, List<Tag> tags, int pegi) {
        this.id = id;
        this.title = title;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.rating = rating;
        this.actors = actors;
        this.showings = showings;
        this.pegi = pegi;
        this.tags = tags;
    }

    // Getters and toString method
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getShortDescription() { return shortDescription; }
    public String getLongDescription() { return longDescription; }
    public double getRating() { return rating; }
    public List<Actor> getActors() { return actors; }
    public List<Showing> getShowings() {return showings;}
    public int getPegi() {return pegi;}
    public List<Tag> getTags() {return tags;}

    @Override
    public String toString() {
        return "Film{" +
               "id=" + id +
               ", title='" + title + '\'' +
               ", shortDescription='" + shortDescription + '\'' +
               ", longDescription='" + longDescription + '\'' +
               ", rating=" + rating +
               ", pegi=" + pegi +
               ", actors=" + actors +
               '}';
    }
}