package com.example.database.db_classes;


public class Seat {
    private int id;
    private int showingId;
    private int rowNumber;
    private int seatNumber;
    private String status; // "available", "reserved", "booked"

    // Parameterized constructor
    public Seat(int id, int showingId, int rowNumber, int seatNumber, String status) {
        this.id = id;
        this.showingId = showingId;
        this.rowNumber = rowNumber;
        this.seatNumber = seatNumber;
        this.status = status;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getShowingId() { return showingId; }
    public void setShowingId(int showingId) { this.showingId = showingId; }
    public int getRowNumber() { return rowNumber; }
    public void setRowNumber(int rowNumber) { this.rowNumber = rowNumber; }
    public int getSeatNumber() { return seatNumber; }
    public void setSeatNumber(int seatNumber) { this.seatNumber = seatNumber; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
