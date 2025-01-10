package com.example.database.db_classes;

public class PointsReward {
    private int id;
    private String name;
    private int points_price;
    

    public PointsReward(int id, String name, int points_price){
        this.id = id;
        this.name = name;
        this.points_price = points_price;
    }


    public int getId(){ return id; }
    public String getName() { return name;}
    public int getPointsPrice() { return points_price; }


}
