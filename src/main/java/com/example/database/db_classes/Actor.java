package com.example.database.db_classes;

public class Actor {
    private int id;
    private String name;
    private String surname;
    private String role; 

    public Actor(int id, String name, String surname, String role) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.role = role;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public String getRole() { return role; }

    @Override
    public String toString() {
        return this.getName() + " " + this.getSurname();
    }
}