package com.example.listing;



import java.sql.Connection;
import java.util.List;

import com.example.database.db_classes.Tag;
import com.example.database.TagsRepository;

public class TagListing {
    List<Tag> tags;

    public TagListing(Connection connection){
        this.tags = TagsRepository.getAllTags(connection);
    }

    public List<Tag> getTags(){
        return tags;
    }
}
