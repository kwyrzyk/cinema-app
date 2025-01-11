package com.example.database.db_classes;

import java.time.LocalDateTime;

public class Reservation {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private ScreeningRoom room;

    public Reservation(LocalDateTime starTime, LocalDateTime endTime, ScreeningRoom screeningRoom){
        this.startTime = starTime;
        this.endTime = endTime;
        this.room = screeningRoom;
    }

    LocalDateTime getStarTime(){ return startTime; }
    LocalDateTime getEndTime(){ return endTime; }
    ScreeningRoom getRoom(){ return room; }

    @Override
    public String toString() {
        return "Reservation{" +
               "startTime=" + startTime +
               ", endTime=" + endTime +
               ", room=" + (room != null ? room.getName() : "Unknown room") + 
               '}';
    }
}
