package com.example.database;

import com.example.database.db_classes.Tag;
import com.example.exceptions.NonRecoverableDatabaseException;
import com.example.exceptions.RecoverableDatabaseException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;

public class TagsRepository {

    public static List<Tag> getAllTags(Connection connection){

        String query = "SELECT name, id_tag FROM tags";

        List<Tag> tags = new ArrayList<>();

        try{
            ResultSet tagsResault = DatabaseManager.runSelectQuery(query, connection);
            
            if(tagsResault == null){
                System.err.println("Error: tagsResult is null.");
                return tags;
            }
            
            while (tagsResault.next()) {
                int id = tagsResault.getInt("id_tag");
                String name = tagsResault.getString("name");

                tags.add(new Tag(id, name));
            }
        } catch (SQLSyntaxErrorException e) {
            throw new NonRecoverableDatabaseException("Syntax error in SQL query: " + e.getMessage(), e);
        } catch (SQLException e) {
            throw new RecoverableDatabaseException("Database query getting the films: " + e.getMessage(), e);
        }
        return tags;
    }

}
