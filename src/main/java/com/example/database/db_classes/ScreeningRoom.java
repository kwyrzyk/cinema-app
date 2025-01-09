package com.example.database.db_classes;

public class ScreeningRoom {
    private int id;
    private String name;
    private int numRows;
    private int seatsPerRow;


    public ScreeningRoom(int id, String name, int numRows, int seatsPerRow){
        this.id = id;
        this.name = name;
        this.numRows = numRows;
        this.seatsPerRow = seatsPerRow;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getNumRows() { return numRows; }
    public void setNumRows(int numRows) { this.numRows = numRows; }
    public int getSeatsPerRow() { return seatsPerRow; }
    public void setSeatsPerRow(int seatsPerRow) { this.seatsPerRow = seatsPerRow; }
}
