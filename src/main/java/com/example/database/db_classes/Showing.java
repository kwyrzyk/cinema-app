package com.example.database.db_classes;

import java.time.LocalDateTime;
import java.util.List;

public class Showing {
    private int id;
    private int filmId;
    private int roomId;
    private LocalDateTime showTime;
    private List<Seat> seats;

    public Showing(int id, int filmId, int roomId, LocalDateTime showTime, List<Seat> seats){
        this.id = id;
        this.filmId = filmId;
        this.roomId = roomId;
        this.showTime = showTime;
        this.seats = seats;
    }


    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getFilmId() { return filmId; }
    public void setFilmId(int filmId) { this.filmId = filmId; }
    public int getRoomId() { return roomId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }
    public LocalDateTime getShowTime() { return showTime; }
    public void setShowTime(LocalDateTime showTime) { this.showTime = showTime; }
    public List<Seat> getSeats(){ return this.seats; }
}
