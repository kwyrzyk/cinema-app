package com.example.database;

import com.example.database.db_classes.Tag;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class TagsRepository {

    public static List<Tag> getAllTags(){

        String query = "SELECT name, id_tag FROM tags";

        List<Tag> tags = new ArrayList<>();

        try{
            ResultSet tagsResault = DatabaseManager.runSelectQuery(query);
            
            if(tagsResault == null){
                System.err.println("Error: tagsResult is null.");
                return tags;
            }
            
            while (tagsResault.next()) {
                int id = tagsResault.getInt("id_tag");
                String name = tagsResault.getString("name");

                tags.add(new Tag(id, name));
            }
        } catch (SQLException e){
            e.printStackTrace();
            System.err.println("Database error while getting the tags: " + e.getMessage());
        }
        return tags;
    }

}
