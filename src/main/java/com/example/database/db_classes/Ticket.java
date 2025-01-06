package com.example.database.db_classes;

public class Ticket {
    private Film film;
    private Showing showing;
    private Seat seat;
    private String name;

    public Ticket(Film film, Showing showing, Seat seat){
        this.showing = showing;
        this.seat = seat;
        this.name = film.getTitle()+ " " +  showing.getShowTime() + " Seat: row:" + seat.getRowNumber() + "nr : " + seat.getSeatNumber();
    }

    public String getName(){
        return name;
    }

    public Film getFilm(){
        return film;
    }

    public Showing getShowing(){
        return showing;
    }

    public int getId(){
        return seat.getId();
    }

}
