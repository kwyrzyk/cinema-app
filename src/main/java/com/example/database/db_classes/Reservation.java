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

    public LocalDateTime getStarTime(){ return startTime; }
    public LocalDateTime getEndTime(){ return endTime; }
    public ScreeningRoom getRoom(){ return room; }

    @Override
    public String toString() {
        return "Room: " + room.getName() + 
                "\nStart time: " + startTime +
                "\nEnd time" + endTime;
    }
}
